package com.fastPuter.website.common;

public enum ServiceResultEnum {
    ERROR("error"),

    SUCCESS("success"),

    DATA_NOT_EXIST("No records found"),

    SAME_CATEGORY_EXIST("There are categories with the same level and the same name!"),

    SAME_LOGIN_NAME_EXIST("Username already exists！"),

    LOGIN_NAME_NULL("Please enter your login name！"),

    LOGIN_PASSWORD_NULL("Please enter your password！"),

    LOGIN_VERIFY_CODE_NULL("Please enter the captcha！"),

    LOGIN_VERIFY_CODE_ERROR("Error in captcha！"),

    GOODS_NOT_EXIST("Product does not exist！"),

    GOODS_PUT_DOWN("Product has been removed！"),

    LOGIN_ERROR("Fail to login！"),

    LOGIN_USER_LOCKED("User has been banned from logging in！"),

    DB_ERROR("database error");

    private String result;

    ServiceResultEnum(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
