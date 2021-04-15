package com.dofl.qlct.presenter;

public interface UpdateInterface {
    void connectFailed();

    void reconnectMaintenance();

    void reconnectListAccount();

    void reconnectListRecord();

    void reconnectListPreviousRecord();

    void reconnectPreviousMPM();

    void reconnectPrePreviousMPM();
}
