package com.dofl.moneygo.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dofl.moneygo.R;
import com.dofl.moneygo.model.Record;
import com.dofl.moneygo.presenter.utils.DataProcessing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ListViewHistoryAdapter extends BaseAdapter {
    private final Context context;
    private final List<String> key;
    private Map<String, Record> recordPackage;

    public ListViewHistoryAdapter(Context context, Map<String, Record> recordPackage) {
        this.context = context;
        this.recordPackage = recordPackage;
        key = new ArrayList<>();
        for (Map.Entry<String, Record> entry : recordPackage.entrySet()) {
            key.add(entry.getKey());
        }
        key.sort(Collections.reverseOrder());
    }

    @Override
    public int getCount() {
        return recordPackage.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public String getKey(int position) {
        return key.get(position);
    }

    @SuppressLint({"SetTextI18n", "ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.list_view_history_layout, null);
        TextView description = convertView.findViewById(R.id.description);
        TextView date = convertView.findViewById(R.id.date);
        TextView time = convertView.findViewById(R.id.time);
        TextView money = convertView.findViewById(R.id.money);
        ImageView icon = convertView.findViewById(R.id.icon);

        description.setText(Objects
                .requireNonNull(recordPackage.get(key.get(position))).getDescription());
        date.setText(DataProcessing.getExactDate(DataProcessing
                .getDate(Objects
                        .requireNonNull(recordPackage.get(key.get(position))).getDateCreate())));
        time.setText(Objects
                .requireNonNull(recordPackage.get(key.get(position))).getTimeCreate());
        money.setText(DataProcessing.formatIntToString(Objects
                .requireNonNull(recordPackage.get(key.get(position))).getTotal()) + " đ");
        icon.setImageResource(R.drawable.main_menu_add);
        return convertView;
    }
}
