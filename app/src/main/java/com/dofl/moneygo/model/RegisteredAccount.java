package com.dofl.moneygo.model;

import java.io.Serializable;

public class RegisteredAccount implements Serializable {
    private String N1;
    private String N2;
    private String N3;
    private String N4;

    public RegisteredAccount() {
    }

    public String getN1() {
        return N1;
    }

    public void setN1(String n1) {
        N1 = n1;
    }

    public String getN2() {
        return N2;
    }

    public void setN2(String n2) {
        N2 = n2;
    }

    public String getN3() {
        return N3;
    }

    public void setN3(String n3) {
        N3 = n3;
    }

    public String getN4() {
        return N4;
    }

    public void setN4(String n4) {
        N4 = n4;
    }
}
