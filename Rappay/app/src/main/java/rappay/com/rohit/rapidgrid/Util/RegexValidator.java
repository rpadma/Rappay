package rappay.com.rohit.rapidgrid.Util;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Rohit on 3/23/2016.
 */
public class RegexValidator {
    public static boolean validateEmail (final String incomming) {

        final String email_pattern = "^[A-Za-z0-9+]+(.[A-Za-z0-9]+)*@"
                +
                "[A-Za-z0-9]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(email_pattern);
        Matcher matcher = pattern.matcher(incomming);
        return matcher.matches();
    }

    public static boolean validateName (final String incoming) {

        final String name_pattern = "^[a-zA-z]+([ '-][a-zA-Z]+)*$";
        Pattern   pattern = Pattern.compile(name_pattern);
        Matcher matcher = pattern.matcher(incoming);
        return matcher.matches();
    }

    public static boolean validatePhoneNumber (final String incoming) {

        final String phonenumber_pattern = "^[0-9]{10}$";
        Pattern   pattern = Pattern.compile(phonenumber_pattern);
        Matcher matcher = pattern.matcher(incoming);
        return matcher.matches();
    }

    public static boolean validateNumber (final String incoming) {

        final String number_pattern = "^[0-9]*$";
        Pattern   pattern = Pattern.compile(number_pattern);
        Matcher matcher = pattern.matcher(incoming);
        return matcher.matches();
    }

    public static boolean validatePassword (final String incoming) {

        final String password_pattern = "^[a-zA-z0-9]*$";
        Pattern   pattern = Pattern.compile(password_pattern);
        Matcher matcher = pattern.matcher(incoming);
        return matcher.matches();
    }

    public  static boolean validateDate(int month, int day, int year){

        if(month<=12 && month >= 10 && day <=31 && day >= 1 && year >=2016 && year <= 9999){
            return true;
        }

        return false;
    }

    public  static boolean validateFutureDate(int month, int day, int year){

        Calendar calendar =  Calendar.getInstance();

        calendar.set(year, month,day);
        Date date = calendar.getTime();

        if(date.before(Calendar.getInstance().getTime())){
            return false;
        }

        return true;
    }
}
