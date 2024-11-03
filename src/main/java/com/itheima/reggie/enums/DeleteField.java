package com.itheima.reggie.enums;

/**
 * @author ellie
 */

public enum DeleteField {
    /**
     * deleted
     */
    DELECTED(1),
    /**
     * active
     */
    ACTITVE(0);

    private final int value;

    DeleteField(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
