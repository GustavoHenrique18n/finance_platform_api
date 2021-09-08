package com.finance.api.regex;

import java.util.regex.Pattern;

public class RegexUserPassword {

    public static Boolean userPassword(String password) {
        if(password.length() == 10){
            boolean regexMatch = Pattern.compile("[a-zA-Z0-9]+[a-zA-Z0-9]+[a-zA-Z0-9]+[^[^<>{}\\\"/|;:.,~!?@#$%^=&*\\\\]\\\\\\\\()\\\\[¿§«»ω⊙¤°℃℉€¥£¢¡®©_+]]").matcher(password).matches();
            return regexMatch;
        }
        return false;
    }
}
