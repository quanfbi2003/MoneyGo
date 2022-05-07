package com.dofl.moneygo.view.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.dofl.moneygo.model.Record;
import com.dofl.moneygo.model.RegisteredAccount;
import com.dofl.moneygo.presenter.utils.DataProcessing;
import com.dofl.moneygo.view.activity.historyfragment.N1Fragment;
import com.dofl.moneygo.view.activity.historyfragment.N2Fragment;
import com.dofl.moneygo.view.activity.historyfragment.N3Fragment;
import com.dofl.moneygo.view.activity.historyfragment.N4Fragment;

import java.util.HashMap;
import java.util.Map;

public class ViewPagerHistoryAdapter extends FragmentStatePagerAdapter {
    private final int pageNum;
    private Map<String, Record> recordPackage;
    private RegisteredAccount registeredAccount;

    public ViewPagerHistoryAdapter(@NonNull FragmentManager fm, int behavior,
                                   RegisteredAccount registeredAccount) {
        super(fm, behavior);
        this.pageNum = behavior;
        recordPackage = new HashMap<>();
        this.registeredAccount = registeredAccount;
    }

    public void update(Map<String, Record> recordPackage) {
        this.recordPackage = recordPackage;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new N1Fragment(DataProcessing.getRecordPackage(1, recordPackage));
            case 1:
                return new N2Fragment(DataProcessing.getRecordPackage(2, recordPackage));
            case 2:
                return new N3Fragment(DataProcessing.getRecordPackage(3, recordPackage));
            default:
                return new N4Fragment(DataProcessing.getRecordPackage(4, recordPackage));
        }
    }

    @Override
    public int getCount() {
        return pageNum;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return registeredAccount.getShortNameN1();
            case 1:
                return registeredAccount.getShortNameN2();
            case 2:
                return registeredAccount.getShortNameN3();
            default:
                return registeredAccount.getShortNameN4();
        }
    }
}
