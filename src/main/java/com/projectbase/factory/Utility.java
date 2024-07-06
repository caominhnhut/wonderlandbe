package com.projectbase.factory;

import java.util.Calendar;
import java.util.regex.Pattern;

public class Utility{


    private Utility(){

    }

    private static final String VALID_PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

    private static final String VALID_EMAIL_ADDRESS_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    public static final String STORED_REPORTS_LOCATION = "/Users/nhut.nguyen/Desktop/Reports";

    public static boolean patternMatches(String data, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(data)
                .matches();
    }

    public static boolean validatePassword(String password){
        return Utility.patternMatches(password, VALID_PASSWORD_REGEX);
    }


    public static boolean validateEmail(String email){
        return Utility.patternMatches(email, VALID_EMAIL_ADDRESS_REGEX);
    }

    public static String createUniqueName(String name) {

        Long timeInMillis = Calendar.getInstance().getTimeInMillis();

        return String.format("%s_%s", timeInMillis, name);
    }
}
