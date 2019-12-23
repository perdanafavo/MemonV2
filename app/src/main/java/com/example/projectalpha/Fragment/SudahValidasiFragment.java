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

public class SudahValidasiFragment extends Fragment {

    private List<LaporanData> dataValidator;
    private ValidatorAdapter mAdapter;
    private ValidatorViews validatorViews;
    private Context context;
    private RecyclerView rvValidatorSV;
    private TextView tvEmptySV;
    private String namaSTO;
    private SwipeRefreshLayout swipeRefreshLayout;

    public SudahValidasiFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sudah_validasi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new ValidatorAdapter();
        rvValidatorSV = view.findViewById(R.id.rvValidatorSV);
        tvEmptySV = view.findViewById(R.id.tvEmptySV);
        dataValidator = validatorViews.getValidasiStatus();
        namaSTO = validatorViews.getNamaSTO();
        swipeRefreshLayout = view.findViewById(R.id.swipeLaporanSV);

        rvValidatorSV.setLayoutManager(new LinearLayoutManager(context));
        rvValidatorSV.setHasFixedSize(true);
        rvValidatorSV.setNestedScrollingEnabled(false);
        rvValidatorSV.setAdapter(mAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<LaporanData> itemLaporan = new ArrayList<>();
        if (dataValidator != null){
            for (LaporanData data:dataValidator){
                if (data.getStatus_approved() == 1){
                    itemLaporan.add(data);
                }
                if (itemLaporan.isEmpty()) {
                    rvValidatorSV.setVisibility(View.GONE);
                    tvEmptySV.setVisibility(View.VISIBLE);
                } else {
                    rvValidatorSV.setVisibility(View.VISIBLE);
                    tvEmptySV.setVisibility(View.GONE);
                }
            }
        }
        Collections.reverse(itemLaporan);
        mAdapter.isiValidator(itemLaporan, namaSTO,1);
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
