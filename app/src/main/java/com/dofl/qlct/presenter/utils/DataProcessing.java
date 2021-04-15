package com.dofl.qlct.presenter.utils;

import android.util.Log;

import com.dofl.qlct.model.Account;
import com.dofl.qlct.model.MPM;
import com.dofl.qlct.model.Maintenance;
import com.dofl.qlct.model.Record;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class DataProcessing {

    /****************************Record***************************/
    public static Record getRecord(Record record) {
        return new Record(record.getId(), record.getTotal(), record.getDescription(),
                record.getTimeCreate(), record.getDateCreate(),record.getBuyer(),
                record.getN1Qty(),record.getN2Qty(),record.getN3Qty(),record.getN4Qty(),
                record.getPackageNumber(), record.getIcon());
    }

    public static String formatDescription(String description) {
        return description.substring(0, 1).toUpperCase() + description.substring(1);
    }

    public static Record setTotalOfRecord(Record record) {
        record.setQty(record.getN1Qty() + record.getN2Qty() +
                record.getN3Qty() + record.getN4Qty());

        int n1Total = (int) (((double) record.getTotal() / record.getQty()) * record.getN1Qty());
        int n2Total = (int) (((double) record.getTotal() / record.getQty()) * record.getN2Qty());
        int n3Total = (int) (((double) record.getTotal() / record.getQty()) * record.getN3Qty());
        int n4Total = (int) (((double) record.getTotal() / record.getQty()) * record.getN4Qty());

        switch (record.getBuyer()) {
            case 1:
                n1Total -= record.getTotal();
                break;
            case 2:
                n2Total -= record.getTotal();
                break;
            case 3:
                n3Total -= record.getTotal();
                break;
            default:
                n4Total -= record.getTotal();
                break;
        }

        record.setN1Total(n1Total);
        record.setN2Total(n2Total);
        record.setN3Total(n3Total);
        record.setN4Total(n4Total);

        return record;
    }

    public static Record setIdOfRecord(Record record) {
        String id = record.getTotal() + record.getDescription() + record.getTimeCreate() +
                record.getDateCreate() + record.getBuyer() + record.getN1Qty() + record.getN2Qty() +
                record.getN3Qty() + record.getN4Qty() + record.getPackageNumber();
        return new Record(Hash.md5(id), record.getTotal(), record.getDescription(),
                record.getTimeCreate(), record.getDateCreate(),
                record.getBuyer(), record.getN1Qty(), record.getN2Qty(),
                record.getN3Qty(), record.getN4Qty(), record.getPackageNumber());
    }

    public static List<Record> getRecordPackage(Account account, List<Record> list) {
        List<Record> listRecord = new ArrayList<>();
        for (Record record : list) {
            if (account.getId() == record.getBuyer()) {
                listRecord.add(record);
            }
        }
        return listRecord;
    }


    /****************************List Record***************************/
    public static List<Record> getListRecord(List<Record> listRecord) {
        List<Record> newListRecord = new ArrayList<>();
        for (Record record : listRecord) {
            newListRecord.add(getRecord(record));
        }
        return newListRecord;
    }

    public static int getTotalOfQty(Account account, List<Record> listRecord) {
        int qty = 0;
        for (Record record : listRecord) {
            if (record.getBuyer() == account.getId()) {
                qty++;
            }
        }
        return qty;
    }

    public static int getTotalOfListRecord(List<Record> listRecord) {
        int total = 0;
        for (Record record : listRecord) {
            total += record.getTotal();
        }
        return total;
    }

    public static int getTotalOfAmountSpent(Account account, List<Record> listRecord) {
        int amount = 0;
        for (Record record : listRecord) {
            if (record.getBuyer() == account.getId()) {
                amount += record.getTotal();
            }
        }
        return amount;
    }

    public static int getTotalOfAmountPaid(Account account, List<Record> listRecord) {
        int amount = 0;
        for (Record record : listRecord) {
            Record recordTemp = setTotalOfRecord(record);
            if (record.getBuyer() != account.getId()) {
                switch (account.getId()) {
                    case 1:
                        amount += recordTemp.getN1Total();
                        break;
                    case 2:
                        amount += recordTemp.getN2Total();
                        break;
                    case 3:
                        amount += recordTemp.getN3Total();
                        break;
                    case 4:
                        amount += recordTemp.getN4Total();
                }
            } else {
                switch (account.getId()) {
                    case 1:
                        amount += (recordTemp.getN1Total() + recordTemp.getTotal());
                        break;
                    case 2:
                        amount += (recordTemp.getN2Total() + recordTemp.getTotal());
                        break;
                    case 3:
                        amount += (recordTemp.getN3Total() + recordTemp.getTotal());
                        break;
                    case 4:
                        amount += (recordTemp.getN4Total() + recordTemp.getTotal());
                }
            }
        }
        return amount;
    }

    public static int getAirConditional(MPM presentMPM) {
        if (presentMPM.getMonth() >= 5 && presentMPM.getMonth() <= 7) {
            return 100000;
        } else return 0;
    }

    public static int getRoomCharge(MPM presentMPM) {
        int roomCharge = (2000000 + 80000 + 50000) / 4; //Tiền mạng - 50,000 đ
        return roomCharge + getAirConditional(presentMPM) / 4;
    }

    public static int getTotalOfAmount(Account account, List<Record> listRecord,
                                              MPM previousMPM, MPM presentMPM) {
        return getTotalOfAmountSpent(account, listRecord)
                - getTotalOfAmountPaid(account, listRecord)
                - getRoomCharge(presentMPM)
                - (presentMPM.getNumberOfElectricity()
                - previousMPM.getNumberOfElectricity()) * 4000 / 4
                - (presentMPM.getNumberOfWater() - previousMPM.getNumberOfWater()) * 20000 / 4;
    }


    /****************************Date***************************/
    public static boolean compareValidateDate(String startDate, String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy",
                Locale.getDefault());
        String[] startDateTemp = startDate.split(" - ");
        String[] dateTemp = date.split(" - ");
        try {
            if (!Objects.requireNonNull(simpleDateFormat.parse(dateTemp[1]))
                    .before(simpleDateFormat.parse(startDateTemp[1]))) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getExactDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("u-dd/MM/yyyy",
                Locale.getDefault()); //T7-12/12/2012
        String[] list = {"Thứ hai", "Thứ ba", "Thứ tư", "Thứ năm",
                "Thứ sáu", "Thứ bảy", "Chủ nhật"};
        String[] dateTemp = simpleDateFormat.format(date).split("-");
        return (list[Integer.parseInt(dateTemp[0]) - 1] + " - " + dateTemp[1]);
    }

    public static String getEditDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("u-dd/MM/yyyy",
                Locale.getDefault());
        String[] list = {"Hôm nay", "Thứ hai", "Thứ ba", "Thứ tư", "Thứ năm",
                "Thứ sáu", "Thứ bảy", "Chủ nhật", "Hôm qua"};
        String[] dateTemp = simpleDateFormat.format(date).split("-");
        String[] dateTemp1 = dateTemp[1].split("/");
        String[] datenow = simpleDateFormat.format(new Date()).split("-");
        String[] datenow1 = datenow[1].split("/");
        if (dateTemp[1].equalsIgnoreCase(datenow[1])) {
            return (list[0] + " - " + dateTemp[1]);
        } else if (dateTemp1[2].equalsIgnoreCase(datenow1[2]) &&
                dateTemp1[1].equalsIgnoreCase(datenow1[1]) &&
                Integer.parseInt(datenow1[0]) - Integer.parseInt(dateTemp1[0]) == 1) {
            return (list[8] + " - " + dateTemp[1]);
        } else {
            return (list[Integer.parseInt(dateTemp[0])] + " - " + dateTemp[1]);
        }
    }

    public static String increaseMonthOfMPM(String month) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/yyyy",
                Locale.getDefault());
        String[] monthOfYear = month.split(" ");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(Objects.requireNonNull(simpleDateFormat.parse(monthOfYear[1])));
        } catch (Exception e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.MONTH, 1);
        return "Tháng " + simpleDateFormat.format(calendar.getTime());

    }

    public static String increaseExactDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return getExactDate(calendar.getTime());
    }


    /****************************Initial Value***************************/
    public static String formatIntToString(int value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(value);
    }

    public static int formatStringToInt(String value) {
        return Integer.parseInt(value.replaceAll(",", "")
                .replaceAll("\\.", ""));
    }


    /****************************Account***************************/
    public static Account getNewAccount(Account account, List<Account> listAccount) {
        for (int i = 0; i < listAccount.size(); i++) {
            if (account.getId() == listAccount.get(i).getId()) {
                account.setPackageNumber(listAccount.get(i).getPackageNumber());
                account.setStartDate(listAccount.get(i).getStartDate());
                account.setMoneyPaid(0);
                account.setPreviousMoney(listAccount.get(i).getPreviousMoney());
            }
        }
        return account;
    }

    public static Account getAccount(Account account) {
        return new Account(account.getId(), account.getRole(), account.getDisplayName(),
                account.getPackageNumber(), account.getStartDate(), account.getPreviousMoney(),
                account.getMoneyPaid());
    }


    /****************************List Account***************************/
    public static List<Account> getListAccount(List<Account> listAccount) {
        List<Account> newListAccount = new ArrayList<>();
        for (Account account : listAccount) {
            newListAccount.add(getAccount(account));
        }
        return newListAccount;
    }

    public static List<Account> getNewListAccount(Account account,
                                                  List<Account> listAccount,
                                                  List<Record> listPreviousRecord,
                                                  MPM prePreviousMPM, MPM previousMPM) {
        for (int i = 0; i < listAccount.size(); i++) {
            listAccount.get(i).setPreviousMoney(listAccount.get(i).getMoneyPaid()
                    + listAccount.get(i).getPreviousMoney()
                    + DataProcessing.getTotalOfAmount(account, listPreviousRecord,
                    prePreviousMPM, previousMPM));
            listAccount.get(i).setMoneyPaid(0);

            listAccount.get(i).setStartDate(increaseExactDate(new Date()));
            listAccount.get(i).setPackageNumber(account.getPackageNumber() + 1);
        }
        return listAccount;
    }


    /****************************Money Per Month***************************/
    public static MPM getMPM(MPM mpm) {
        return new MPM(mpm.getMonthOfYear(), mpm.getMonth(), mpm.getNumberOfElectricity(),
                mpm.getNumberOfWater(), mpm.getAirConditional(), mpm.getPackageNumber(),
                mpm.getStartDate(), mpm.getEndDate());
    }
    public static MPM getPresentMPM(Account account, MPM previousMPM, int numberOfElectricity,
                                    int numberOfWater) {
        MPM presentMPM = new MPM();

        presentMPM.setMonthOfYear(increaseMonthOfMPM(previousMPM.getMonthOfYear()));
        presentMPM.setMonth(previousMPM.getMonth() + 1);
        presentMPM.setPackageNumber(previousMPM.getPackageNumber() + 1);
        presentMPM.setStartDate(account.getStartDate());
        presentMPM.setEndDate(getExactDate(new Date()));
        presentMPM.setNumberOfElectricity(numberOfElectricity);
        presentMPM.setNumberOfWater(numberOfWater);
        presentMPM.setAirConditional(getAirConditional(presentMPM));
        if (numberOfElectricity == 0 || numberOfWater == 0) {
            presentMPM.setNumberOfElectricity(previousMPM.getNumberOfElectricity());
            presentMPM.setNumberOfWater(previousMPM.getNumberOfWater());
        }
        return presentMPM;
    }


    /****************************Maintenance***************************/
    public static Maintenance getMaintenance(Maintenance maintenance) {
        return new Maintenance(maintenance.isMaintenance(), maintenance.getnNetMoney());
    }

}
