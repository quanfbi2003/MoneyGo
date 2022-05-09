package com.dofl.moneygo.view.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dofl.moneygo.model.RegisteredAccount;

public class SpinnerPaymentAdapter extends BaseAdapter {
    private final LayoutInflater flater;
    private final RegisteredAccount registeredAccount;
    private final int listItemLayoutResource;
    private final int textViewItemNameId;

    public SpinnerPaymentAdapter(Activity context, int listItemLayoutResource,
                         int textViewItemNameId,
                         RegisteredAccount registeredAccount) {
        this.listItemLayoutResource = listItemLayoutResource;

        this.textViewItemNameId = textViewItemNameId;
        this.flater = context.getLayoutInflater();
        this.registeredAccount = registeredAccount;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        String item = "";
        switch(i) {
            case 0:
                item = registeredAccount.getNameN1();
                break;
            case 1:
                item = registeredAccount.getNameN2();
                break;
            case 2:
                item = registeredAccount.getNameN3();
                break;
            case 3:
                item = registeredAccount.getNameN4();
                break;
            case 4:
                item = "Mạng Hàng Xóm";
                break;
        }

        @SuppressLint("ViewHolder")
        View rowView = this.flater.inflate(this.listItemLayoutResource, null,true);

        TextView textViewItemName = rowView.findViewById(this.textViewItemNameId);
        textViewItemName.setText(item);

        return rowView;
    }
}
