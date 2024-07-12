package com.example.ncc_spring.enums;

public enum PunishmentMoney {
    FORGOT_CHECK_IN_OR_CHECK_OUT(20),
    FORGOT_CHECK_IN_AND_CHECK_OUT(50);

    private final int value;

    PunishmentMoney(int value) {
        this.value = value;
    }

    public int getValue() {
        return  value;
    }
}
