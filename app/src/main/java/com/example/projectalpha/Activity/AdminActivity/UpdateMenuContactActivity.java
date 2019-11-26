package com.example.projectalpha.Activity.AdminActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;

import com.example.projectalpha.Adapter.KontakAdapter;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.Models.SubModels.UsersData;
import com.example.projectalpha.Presenter.ApplicationPresenter;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.ApplicationViews;

import java.util.ArrayList;
import java.util.List;

public class UpdateMenuContactActivity extends CustomCompatActivity
        implements ApplicationViews, ApplicationViews.UsersViews.GetRequest {

    private ProgressDialog mDialog;
    private SearchView searchView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerview;
    private KontakAdapter kontakAdapter;

    private ApplicationPresenter applicationPresenter;

    private List<UsersData> itemUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_menu_contact);

        setVariable();
        createView();
        setBtnFooter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        createView();
        kontakAdapter.setData(itemUpdate);
        kontakAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        kontakAdapter = new KontakAdapter(itemUpdate);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setAdapter(kontakAdapter);
    }

    private void setVariable() {
        applicationPresenter = new ApplicationPresenter(UpdateMenuContactActivity.this);

        searchView = findViewById(R.id.searchUpdateContact);
        mRecyclerview = findViewById(R.id.recycleUpdateContact);

        mDialog = new ProgressDialog(UpdateMenuContactActivity.this);
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

    private void createView() {
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

    private void filter(String text) {
        List<UsersData> filteredList = new ArrayList<>();
        for (UsersData item : itemUpdate){
            if ((item.getNama().toLowerCase().contains(text.toLowerCase()))
                    || (item.getNama_witel().toLowerCase().contains(text.toLowerCase()))){
                filteredList.add(item);
            }
        }

        kontakAdapter.setData(filteredList);
        kontakAdapter.notifyDataSetChanged();
    }

    private void setBtnFooter() {
        outClick();
        homeClick(MainAdminActivity.class);
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
                if (data.getPrivileges() == 2){
                    itemUpdate.add(data);
                }
            }
        }

        kontakAdapter.setData(itemUpdate);
        kontakAdapter.notifyDataSetChanged();
        mDialog.dismiss();
    }
}
