package com.luv2code.shopme.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameValidator {
    private static final String NAME_REGEX = "^[^\\s]+(\\s+[^\\s]+)*$";

    public static boolean validate(String name) {
        Pattern pattern = Pattern.compile(NAME_REGEX);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
}
