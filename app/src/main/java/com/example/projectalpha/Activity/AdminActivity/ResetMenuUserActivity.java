package com.example.projectalpha.Activity.AdminActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.SearchView;

import com.example.projectalpha.Adapter.ResetUserAdapter;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.Models.SubModels.UsersData;
import com.example.projectalpha.Presenter.ApplicationPresenter;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.ApplicationViews;

import java.util.ArrayList;
import java.util.List;

public class ResetMenuUserActivity extends CustomCompatActivity implements ApplicationViews, ApplicationViews.UsersViews.GetRequest {

    private String PRIVILAGE_SELECT;

    private ProgressDialog mDialog;
    private SearchView searchView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerview;
    private ResetUserAdapter mAdapter;

    private ApplicationPresenter applicationPresenter;

    private List<UsersData> itemUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_menu_user);

        setVariable();
        createView();
        setBtnFooter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter = new ResetUserAdapter(itemUpdate);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        createView();
        mAdapter.filterList(itemUpdate);
        mAdapter.notifyDataSetChanged();
    }

    private void setVariable() {
        PRIVILAGE_SELECT = String.valueOf(getIntent().getIntExtra(ENVIRONMENT.PREVILAGE_SELECT, 3));
        applicationPresenter = new ApplicationPresenter(ResetMenuUserActivity.this);

        searchView = findViewById(R.id.searchResetUser);
        mRecyclerview = findViewById(R.id.recycleResetUser);

        mDialog = new ProgressDialog(ResetMenuUserActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);

        swipeRefreshLayout = findViewById(R.id.swlayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                onResume();
            }
        });
    }

    private void createView(){
        mDialog.show();

        applicationPresenter.getUsers();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void setBtnFooter(){
        outClick();
        homeClick(MainAdminActivity.class);
    }

    private void filter(String text){
        List<UsersData> filteredList = new ArrayList<>();
        for (UsersData item : itemUpdate){
            if ((item.getNama().toLowerCase().contains(text.toLowerCase()))
                    || (item.getUsername().toLowerCase().contains(text.toLowerCase()))){
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestFailled(String message) {
        simpleToast(message);
        mDialog.dismiss();
    }

    @Override
    public void successRequest() {

    }

    @Override
    public void SuccessRequestGetUsers(ArrayList<UsersData> dataResponse) {
        itemUpdate = new ArrayList<>();

        if (dataResponse != null){
            for (UsersData data:dataResponse){
                if (data.getPrivileges() == Integer.parseInt(PRIVILAGE_SELECT)){
                    itemUpdate.add(data);
                }
            }
        }

        mAdapter.filterList(itemUpdate);
        mAdapter.notifyDataSetChanged();
        mDialog.dismiss();
    }
}
