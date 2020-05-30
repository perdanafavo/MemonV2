package com.example.projectalpha.Activity.UsersActivity.FormActivity;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.projectalpha.Activity.UsersActivity.MainUserActivity;
import com.example.projectalpha.Adapter.KontakAdapter;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.Models.SubModels.ContactData;
import com.example.projectalpha.Models.SubModels.UsersData;
import com.example.projectalpha.Presenter.ApplicationPresenter;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.ApplicationViews;

import java.util.ArrayList;
import java.util.List;

public class    KontakPentingActivity extends CustomCompatActivity
        implements ApplicationViews, ApplicationViews.UsersViews.GetRequestContact {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerview;
    private KontakAdapter kontakAdapter;

    private ApplicationPresenter applicationPresenter;

    private ProgressDialog mDialog;
    private List<ContactData> itemKontak = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontak_penting);

        setVariable();
        createView();
        setBtnFooter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        kontakAdapter = new KontakAdapter(itemKontak);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setAdapter(kontakAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        createView();
        kontakAdapter.setData(itemKontak);
        kontakAdapter.notifyDataSetChanged();
    }

    private void setVariable(){
        mRecyclerview = findViewById(R.id.listContact);
        applicationPresenter = new ApplicationPresenter(KontakPentingActivity.this);

        mDialog = new ProgressDialog(KontakPentingActivity.this);
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
        applicationPresenter.getContact();
    }

    private void setBtnFooter() {
        super.homeClick(MainUserActivity.class);
        super.backClick();
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
    public void SuccessRequestContact(ArrayList<ContactData> dataResponse) {
        itemKontak = new ArrayList<>();
        for (ContactData data:dataResponse){
            itemKontak.add(data);
        }

        kontakAdapter.setData(itemKontak);
        kontakAdapter.notifyDataSetChanged();
        mDialog.dismiss();
    }
}
