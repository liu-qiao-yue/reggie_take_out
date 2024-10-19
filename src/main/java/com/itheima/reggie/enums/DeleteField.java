package com.itheima.reggie.enums;

public enum DeleteField {
    DELECTED(1),// deleted
    ACTITVE(0);//active

    private final int value;

    DeleteField(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
