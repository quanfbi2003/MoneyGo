package com.dofl.moneygo.presenter;

import android.util.Log;

import com.dofl.moneygo.model.Account;
import com.dofl.moneygo.model.FeeDetails;
import com.dofl.moneygo.model.MoneyPackage;
import com.dofl.moneygo.model.Summary;
import com.dofl.moneygo.presenter.utils.DataProcessing;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class SettlementPresenter {
    private final DatabaseReference databaseReference;
    private final SettlementInterface settlementInterface;

    public SettlementPresenter(SettlementInterface settlementInterface) {
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
        this.settlementInterface = settlementInterface;
    }

    public void newMonth(Account account, MoneyPackage n1MoneyPackage, MoneyPackage n2MoneyPackage,
                         MoneyPackage n3MoneyPackage, MoneyPackage n4MoneyPackage,
                         Summary presentSummaryPackage,
                         Summary previousSummaryPackage, int soDien, int soNuoc,
                         int neighborNetwork) {

        /****************************Complete PresentSummaryPackage***************************/
        presentSummaryPackage.setNumberOfElectricity(soDien);
        presentSummaryPackage.setNumberOfWater(soNuoc);
        Log.e("TotalOfElectricity", (soDien -
                previousSummaryPackage.getNumberOfElectricity())+"");
        presentSummaryPackage.setTotalOfElectricity(soDien -
                previousSummaryPackage.getNumberOfElectricity());
        Log.e("TotalOfElectricity", (soNuoc - previousSummaryPackage.getNumberOfWater())+"");
        presentSummaryPackage.setTotalOfWater(soNuoc - previousSummaryPackage.getNumberOfWater());
        presentSummaryPackage.setEndDate(DataProcessing.getFormattedDate(new Date()));
        presentSummaryPackage.setTotalMoneyPaid(FeeDetails.ROOM_CHARGE +
                FeeDetails.SERVICES + FeeDetails.INTERNET +
                presentSummaryPackage.getAirConditional() +
                presentSummaryPackage.getTotalOfElectricity() * FeeDetails.ELECTRICITY +
                presentSummaryPackage.getTotalOfWater() * FeeDetails.WATER);
        databaseReference.child("Summary").child(n1MoneyPackage.getSummaryPackage())
                .setValue(presentSummaryPackage);

        /****************************Complete PresentMoneyPackage of N1***************************/
        n1MoneyPackage.setPresentMoney(n1MoneyPackage.getPresentMoney()
                + presentSummaryPackage.getTotalOfElectricity() * FeeDetails.ELECTRICITY / 4
                + presentSummaryPackage.getTotalOfWater() * FeeDetails.WATER / 4);
        n1MoneyPackage.setTotalMoney(n1MoneyPackage.getTotalMoney()
                + presentSummaryPackage.getTotalOfElectricity() * FeeDetails.ELECTRICITY / 4
                + presentSummaryPackage.getTotalOfWater() * FeeDetails.WATER / 4);
        databaseReference.child("Account").child("1").child("Money Package")
                .child(account.getPresentMoneyPackage()).setValue(n1MoneyPackage);

        /****************************Complete PresentMoneyPackage of N2***************************/
        n2MoneyPackage.setPresentMoney(n2MoneyPackage.getPresentMoney()
                + presentSummaryPackage.getTotalOfElectricity() * FeeDetails.ELECTRICITY / 4
                + presentSummaryPackage.getTotalOfWater() * FeeDetails.WATER / 4);
        n2MoneyPackage.setTotalMoney(n2MoneyPackage.getTotalMoney()
                + presentSummaryPackage.getTotalOfElectricity() * FeeDetails.ELECTRICITY / 4
                + presentSummaryPackage.getTotalOfWater() * FeeDetails.WATER / 4);
        databaseReference.child("Account").child("2").child("Money Package")
                .child(account.getPresentMoneyPackage()).setValue(n2MoneyPackage);

        /****************************Complete PresentMoneyPackage of N3***************************/
        n3MoneyPackage.setPresentMoney(n3MoneyPackage.getPresentMoney()
                + presentSummaryPackage.getTotalOfElectricity() * FeeDetails.ELECTRICITY / 4
                + presentSummaryPackage.getTotalOfWater() * FeeDetails.WATER / 4);
        n3MoneyPackage.setTotalMoney(n3MoneyPackage.getTotalMoney()
                + presentSummaryPackage.getTotalOfElectricity() * FeeDetails.ELECTRICITY / 4
                + presentSummaryPackage.getTotalOfWater() * FeeDetails.WATER / 4);
        databaseReference.child("Account").child("3").child("Money Package")
                .child(account.getPresentMoneyPackage()).setValue(n3MoneyPackage);

        /****************************Complete PresentMoneyPackage of N4***************************/
        n4MoneyPackage.setPresentMoney(n4MoneyPackage.getPresentMoney()
                + presentSummaryPackage.getTotalOfElectricity() * FeeDetails.ELECTRICITY / 4
                + presentSummaryPackage.getTotalOfWater() * FeeDetails.WATER / 4);
        n4MoneyPackage.setTotalMoney(n4MoneyPackage.getTotalMoney()
                + presentSummaryPackage.getTotalOfElectricity() * FeeDetails.ELECTRICITY / 4
                + presentSummaryPackage.getTotalOfWater() * FeeDetails.WATER / 4);
        databaseReference.child("Account").child("4").child("Money Package")
                .child(account.getPresentMoneyPackage()).setValue(n4MoneyPackage);

        /****************************Create new package key***************************/
        String newSummaryPackageKey = databaseReference.child("Summary").push().getKey();
        String newRecordPackageKey = databaseReference.child("Record").push().getKey();
        String newMoneyPackageKey = databaseReference.child("Account").child("1")
                .child("Money Package").push().getKey();
        String presentMoneyPackageKey = account.getPresentMoneyPackage();

        /****************************Create new SummaryPackage***************************/
        Summary newSummaryPackage = new Summary();
        if (presentSummaryPackage.getMonth() == 12) {
            newSummaryPackage.setMonth(1);
        } else {
            newSummaryPackage.setMonth(presentSummaryPackage.getMonth() + 1);
        }
        newSummaryPackage.setAirConditional(DataProcessing.getAirConditional(newSummaryPackage));
        newSummaryPackage.setMonthOfYear(DataProcessing
                .increaseMonthOfYear(presentSummaryPackage.getMonthOfYear()));
        newSummaryPackage.setStartDate(DataProcessing.increaseExactDate(new Date()));
        newSummaryPackage.setTotalMoneySpent(0);
        newSummaryPackage.setTotalMoneyPaid(FeeDetails.ROOM_CHARGE + FeeDetails.SERVICES
                + FeeDetails.INTERNET + newSummaryPackage.getAirConditional());
        assert newSummaryPackageKey != null;
        databaseReference.child("Summary").child(newSummaryPackageKey)
                .setValue(newSummaryPackage);

        /****************************Create new MoneyPackage***************************/
        MoneyPackage newMoneyPackage = new MoneyPackage();
        newMoneyPackage.setMoneyPaid(0);
        newMoneyPackage.setMoneySpent(0);
        newMoneyPackage.setMoneySent(0);
        newMoneyPackage.setNumberOfRecord(0);
        newMoneyPackage.setPresentMoney((newSummaryPackage.getTotalMoneyPaid() - 50000) / 4);
        newMoneyPackage.setRecordPackage(newRecordPackageKey);
        newMoneyPackage.setSummaryPackage(newSummaryPackageKey);
        /********************Create new MoneyPackage for N1********************/
        newMoneyPackage.setPreviousMoney(n1MoneyPackage.getTotalMoney()); // sửa
        newMoneyPackage.setTotalMoney(newMoneyPackage.getPresentMoney()
                + newMoneyPackage.getPreviousMoney());
        assert newMoneyPackageKey != null;
        databaseReference.child("Account").child("1").child("Money Package")
                .child(newMoneyPackageKey).setValue(newMoneyPackage);
        /********************Create new MoneyPackage for N2********************/
        newMoneyPackage.setPreviousMoney(n2MoneyPackage.getTotalMoney()); // sửa
        newMoneyPackage.setTotalMoney(newMoneyPackage.getPresentMoney()
                + newMoneyPackage.getPreviousMoney());
        databaseReference.child("Account").child("2").child("Money Package")
                .child(newMoneyPackageKey).setValue(newMoneyPackage);
        /********************Create new MoneyPackage for N3********************/
        newMoneyPackage.setPreviousMoney(n3MoneyPackage.getTotalMoney()); // sửa
        newMoneyPackage.setTotalMoney(newMoneyPackage.getPresentMoney()
                + newMoneyPackage.getPreviousMoney());
        databaseReference.child("Account").child("3").child("Money Package")
                .child(newMoneyPackageKey).setValue(newMoneyPackage);
        /********************Create new MoneyPackage for N4********************/
        newMoneyPackage.setPreviousMoney(n4MoneyPackage.getTotalMoney()); // sửa
        newMoneyPackage.setTotalMoney(newMoneyPackage.getPresentMoney()
                + newMoneyPackage.getPreviousMoney());
        databaseReference.child("Account").child("4").child("Money Package")
                .child(newMoneyPackageKey).setValue(newMoneyPackage);


        /****************************Update new package key for N1***************************/
        databaseReference.child("Account").child("1").child("Account Details")
                .child("-M_KmXp_Ac7BY7MOlWYf").child("previousMoneyPackage")
                .setValue(presentMoneyPackageKey);
        databaseReference.child("Account").child("1").child("Account Details")
                .child("-M_KmXp_Ac7BY7MOlWYf").child("presentMoneyPackage")
                .setValue(newMoneyPackageKey);

        /****************************Update new package key for N2***************************/
        databaseReference.child("Account").child("2").child("Account Details")
                .child("-M_Kmrj_cC9QppUkvQmc").child("previousMoneyPackage")
                .setValue(presentMoneyPackageKey);
        databaseReference.child("Account").child("2").child("Account Details")
                .child("-M_Kmrj_cC9QppUkvQmc").child("presentMoneyPackage")
                .setValue(newMoneyPackageKey);

        /****************************Update new package key for N3***************************/
        databaseReference.child("Account").child("3").child("Account Details")
                .child("-M_Km6Yl-PMAU_cC1pu9").child("previousMoneyPackage")
                .setValue(presentMoneyPackageKey);
        databaseReference.child("Account").child("3").child("Account Details")
                .child("-M_Km6Yl-PMAU_cC1pu9").child("presentMoneyPackage")
                .setValue(newMoneyPackageKey);

        /****************************Update new package key for N4***************************/
        databaseReference.child("Account").child("4").child("Account Details")
                .child("-M_KmzPHkvhXqt1YIY51").child("previousMoneyPackage")
                .setValue(presentMoneyPackageKey);
        databaseReference.child("Account").child("4").child("Account Details")
                .child("-M_KmzPHkvhXqt1YIY51").child("presentMoneyPackage")
                .setValue(newMoneyPackageKey);

        /****************************Add notification***************************/
        databaseReference.child("Account").child("1").child("Notification").child("Settlement")
                .setValue("Số tiền tháng này bạn phải đóng là: " + n1MoneyPackage.getTotalMoney()
                        + " đ\nBạn nhớ đóng tiền trước ngày 15 tháng này nhé!!!");
        databaseReference.child("Account").child("2").child("Notification").child("Settlement")
                .setValue("Số tiền tháng này bạn phải đóng là: " + n2MoneyPackage.getTotalMoney()
                        + " đ\nBạn nhớ đóng tiền trước ngày 15 tháng này nhé!!!");
        databaseReference.child("Account").child("3").child("Notification").child("Settlement")
                .setValue("Số tiền tháng này bạn phải đóng là: " + n3MoneyPackage.getTotalMoney()
                        + " đ\nBạn nhớ đóng tiền trước ngày 15 tháng này nhé!!!");
        databaseReference.child("Account").child("4").child("Notification").child("Settlement")
                .setValue("Số tiền tháng này bạn phải đóng là: " + n4MoneyPackage.getTotalMoney()
                        + " đ\nBạn nhớ đóng tiền trước ngày 15 tháng này nhé!!!");

        /****************************Update NeighborNetwork***************************/
        databaseReference.child("Neighbor Network").setValue(neighborNetwork + 50000);

        settlementInterface.success();
    }


}
