package com.dofl.moneygo.presenter;

import com.dofl.moneygo.model.Account;
import com.dofl.moneygo.model.MoneyPackage;
import com.dofl.moneygo.model.RegisteredAccount;
import com.dofl.moneygo.model.Summary;

public interface MenuInterface {
    void updateRegisteredAccountSuccess(RegisteredAccount registeredAccount);

    void updateAccountSuccess(Account account);

    void updatePresentMoneyPackageSuccess(MoneyPackage presentMoneyPackage);

    void updatePreviousMoneyPackageSuccess(MoneyPackage previousMoneyPackage);

    void updatePresentSummaryPackageSuccess(Summary presentSummaryPackage);

    void updatePreviousSummaryPackageSuccess(Summary previousSummaryPackage);

    void updateError(String msg);
}
