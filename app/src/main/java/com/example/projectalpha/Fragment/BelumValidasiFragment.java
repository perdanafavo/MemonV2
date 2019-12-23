package com.example.projectalpha.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.projectalpha.Activity.ValidatorActivity.MainValidatorActivity;
import com.example.projectalpha.Adapter.ValidatorAdapter;
import com.example.projectalpha.Models.SubModels.LaporanData;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.ValidatorViews;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BelumValidasiFragment extends Fragment {

    private List<LaporanData> dataValidator;
    private ValidatorAdapter mAdapter;
    private ValidatorViews validatorViews;
    private Context context;
    private RecyclerView rvValidatorBV;
    private TextView tvEmptyBV;
    private String namaSTO;
    private SwipeRefreshLayout swipeRefreshLayout;

    public BelumValidasiFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_belum_validasi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new ValidatorAdapter();
        rvValidatorBV = view.findViewById(R.id.rvValidatorBV);
        swipeRefreshLayout = view.findViewById(R.id.swipeLaporanBV);
        tvEmptyBV = view.findViewById(R.id.tvEmptyBV);
        dataValidator = validatorViews.getValidasiStatus();
        namaSTO = validatorViews.getNamaSTO();

        rvValidatorBV.setHasFixedSize(true);
        rvValidatorBV.setNestedScrollingEnabled(false);
        rvValidatorBV.setAdapter(mAdapter);
        rvValidatorBV.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<LaporanData> itemLaporan = new ArrayList<>();
        if (dataValidator != null){
            for (LaporanData data:dataValidator){
                if (data.getStatus_approved() == 0){
                    itemLaporan.add(data);
                }
                if (itemLaporan.isEmpty()) {
                    rvValidatorBV.setVisibility(View.GONE);
                    tvEmptyBV.setVisibility(View.VISIBLE);
                } else {
                    rvValidatorBV.setVisibility(View.VISIBLE);
                    tvEmptyBV.setVisibility(View.GONE);
                }
            }
        }
        Collections.reverse(itemLaporan);
        mAdapter.isiValidator(itemLaporan, namaSTO,0);
        mAdapter.notifyDataSetChanged();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                validatorViews.refreshValidator();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        validatorViews = (MainValidatorActivity) context;
        this.context = context;
    }
}
