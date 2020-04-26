package com.example.tmaadminapp.AppModules.InfrastructureHead.ComplaintsFragments.PendingComplaints;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tmaadminapp.R;
import com.example.tmaadminapp.AppModules.SanitationHead.SanitationComplaints.ModelForComplaints;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfraPendingComplaints extends Fragment {

    private View pComplaintsView;
    private RecyclerView listOfPendingComplaints;


    public InfraPendingComplaints() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        pComplaintsView = inflater.inflate(R.layout.fragment_infra_pending_complaints, container, false);

        initViews();

        return pComplaintsView;
    }

    private void initViews() {
        listOfPendingComplaints = pComplaintsView.findViewById(R.id.pendingComplaintsListOfInfra);
        listOfPendingComplaints.setLayoutManager(new LinearLayoutManager(getContext()));



    }


}