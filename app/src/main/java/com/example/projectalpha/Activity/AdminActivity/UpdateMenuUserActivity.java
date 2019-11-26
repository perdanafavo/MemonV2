package com.example.projectalpha.Activity.AdminActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.SearchView;

import com.example.projectalpha.Adapter.ItemUpdateAdapter;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.Models.SubModels.UsersData;
import com.example.projectalpha.Presenter.ApplicationPresenter;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.ApplicationViews;
import com.example.projectalpha.Views.UpdateMenuUserViews;

import java.util.ArrayList;
import java.util.List;

public class UpdateMenuUserActivity extends CustomCompatActivity
        implements UpdateMenuUserViews, ApplicationViews, ApplicationViews.UsersViews.GetRequest, ApplicationViews.UsersViews {

    private String PRIVILAGE_SELECT;

    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView searchView;
    private RecyclerView mRecyclerview;
    private ProgressDialog mDialog;
    private ItemUpdateAdapter mAdapter;

    private ApplicationPresenter applicationPresenter;

    private List<UsersData> itemUpdate = null;

    private String IDUSER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_menu_user);

        setVariable();
        createView();
        setBtnFooter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        createView();
        mAdapter.filterList(itemUpdate);
        mAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAdapter = new ItemUpdateAdapter(itemUpdate);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setAdapter(mAdapter);
    }

    private void setVariable() {
        mDialog = new ProgressDialog(UpdateMenuUserActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);

        PRIVILAGE_SELECT = String.valueOf(getIntent().getIntExtra(ENVIRONMENT.PREVILAGE_SELECT, 3));
        applicationPresenter = new ApplicationPresenter(UpdateMenuUserActivity.this);

        searchView = findViewById(R.id.searchUpdateUser);
        mRecyclerview = findViewById(R.id.recycleUpdateUser);

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
    private void setBtnFooter() {
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
    public void deleteUsers(String IndexEmployee) {
        mDialog.show();
        this.IDUSER = IndexEmployee;
        applicationPresenter.deleteUsers();
    }

    @Override
    public void requestFailled(String message) {
        simpleToast(message);
        mDialog.dismiss();
    }

    @Override
    public void successRequest() {
        applicationPresenter.getUsers();
        simpleToast("Berhasil Menghapus Petugas");
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

    @Override
    public int getStoArea() {
        return 0;
    }

    @Override
    public String getEmployee() {
        return IDUSER;
    }
}
