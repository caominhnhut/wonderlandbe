package com.projectbase.dto;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCodes{

    NONE(null, null, null),

    CREATE_USER_MISSING_EMAIL("400", "Email is required", "The user creation requests must include the email."),
    CREATE_USER_MISSING_PASSWORD("400", "Password is required", "The user creation requests must include the password."),
    CREATE_USER_MISSING_CONFIRMED_PASSWORD("400", "Confirmed password is required", "The user creation requests must include the confirmed password."),
    CREATE_USER_INVALID_EMAIL("400", "Email is invalid", "The user creation request contains an invalid email"),
    CREATE_USER_INVALID_PASSWORD("400", "Password is invalid", "The password must at least 8 characters,\n at least one special character \n at least one upper case."),
    CREATE_USER_PASSWORDS_NOT_MATCH("400", "Password not match", "The passwords are not match"),

    UPDATE_USER_INVALID_FIRST_NAME("400", "First name is invalid", "The first name should not have special characters"),
    UPDATE_USER_INVALID_LAST_NAME("400", "Last name is invalid", "The last name should not have special characters"),

    CHANGE_PASSWORD_INVALID("400", "Password Data Invalid", "The old password or the new password should not be empty"),
    FORGOT_PASSWORD_MISSING_EMAIL("400", "Email is required", "The email address for recovering email should not be empty"),

    CREATE_CATEGORY_MISSING_NAME("400", "Name is required", "The category creation requests must include the name."),

    CREATE_PRODUCT_MISSING_NAME("400", "Name is required", "The product creation requests must include the name."),
    CREATE_PRODUCT_MISSING_COST("400", "Cost is required", "The product creation requests must include the cost."),
    CREATE_PRODUCT_MISSING_PRICE("400", "Cost is required", "The product creation requests must include the price."),

    INTERNAL_SERVER_ERROR("500", "Server Error", "Something went wrong. Please try again");

    private final HttpStatus httpStatus;

    private final String status;

    private final String title;

    private final String detail;

    ErrorCodes(String status, String title, String detail) {
        this.httpStatus = null;
        this.status = status;
        this.title = title;
        this.detail = detail;
    }
}
