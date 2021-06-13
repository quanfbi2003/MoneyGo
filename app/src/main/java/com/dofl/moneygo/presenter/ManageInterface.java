package com.dofl.moneygo.presenter;

import com.dofl.moneygo.model.MoneyPackage;

public interface ManageInterface {

    void updateN1MoneyPackageSuccess(MoneyPackage n1MoneyPackage);

    void updateError(String msg);

    void updateN2MoneyPackageSuccess(MoneyPackage n2MoneyPackage);

    void updateN3MoneyPackageSuccess(MoneyPackage n3MoneyPackage);

    void updateN4MoneyPackageSuccess(MoneyPackage n4MoneyPackage);

    void updateNeighborNetworkSuccess(int neighborNetwork);
}
