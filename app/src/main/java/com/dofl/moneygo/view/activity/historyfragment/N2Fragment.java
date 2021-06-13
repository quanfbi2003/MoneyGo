package com.dofl.moneygo.view.activity.historyfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.dofl.moneygo.R;
import com.dofl.moneygo.model.Record;
import com.dofl.moneygo.view.activity.HistoryDetailActivity;
import com.dofl.moneygo.view.adapter.ListViewHistoryAdapter;

import java.util.Map;

public class N2Fragment extends Fragment {
    private final Map<String, Record> recordPackage;
    private View view;
    private ListViewHistoryAdapter adapter;

    public N2Fragment(Map<String, Record> recordPackage) {
        this.recordPackage = recordPackage;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_n2, container, false);

        initValue();

        return view;
    }


    /****************************Initial Value***************************/
    private void initValue() {
        ListView listView = view.findViewById(R.id.list);
        adapter = new ListViewHistoryAdapter(requireContext(), recordPackage);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(requireContext(),
                    HistoryDetailActivity.class);
            intent.putExtra("key", adapter.getKey(position));
            intent.putExtra("record", recordPackage.get(adapter.getKey(position)));
            startActivity(intent);
        });
    }
}