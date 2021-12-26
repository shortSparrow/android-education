package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {
    static public boolean isEmail(String text) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        if (!matcher.matches()) {
            return false;
        }

        return true;
    }
}
