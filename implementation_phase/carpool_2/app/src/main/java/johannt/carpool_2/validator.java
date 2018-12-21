package johannt.carpool_2;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import java.sql.Time;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validator {


    private Pattern pattern;
    private Matcher matcher;

    private static final String DATE_PATTERN =
            "(0?[1-9]|1[012]) [/.-] (0?[1-9]|[12][0-9]|3[01]) [/.-] ((19|20)\\d\\d)";

    //Date Validator
    public boolean checkDate(String date , Activity act) {

        matcher = Pattern.compile(DATE_PATTERN).matcher(date);

        //checking if date is empty
        if (TextUtils.isEmpty(date)) {
            Toast.makeText(act, "Please enter date", Toast.LENGTH_LONG).show();
            return false;
        }

        //checking if the date is valid
        else if (!matcher.matches() || !(validateDate(date))) {
            Toast.makeText(act, "Invalid date", Toast.LENGTH_SHORT).show();
        }
        return  true;
    }

    //Email Validator
    public boolean checkEmail(String email , Activity act) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        //checking if email are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(act,"Please enter email",Toast.LENGTH_LONG).show();
            return false;
        }
        //checking if email is valid
        else if(matcher.matches() == false){
            Toast.makeText(act,"Please enter valid mail address",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    //Password Validator
    public boolean checkPassword(String password , Activity act) {
        //check if password not empty
        if(TextUtils.isEmpty(password)){
            Toast.makeText(act,"Please enter password",Toast.LENGTH_LONG).show();
            return false;
        }

        //checking if password contain at least 6 digit
        else if(password.length() < 6){
            Toast.makeText(act,"Password must contain atleast 6 digits/characters",Toast.LENGTH_LONG).show();
            return false;
        }
        return  true;
    }

    //Repeat password Validator
    public boolean checkRepeatPassword(String repeatPassword , String password , Activity act) {
        //check if password not empty
        if(TextUtils.isEmpty(repeatPassword)){
            Toast.makeText(act,"Please repeat the password",Toast.LENGTH_LONG).show();
            return false;
        }

        //checking if password contain at least 6 digit
        else if((repeatPassword.length() < 6) || (!(repeatPassword.equals(password)))){
            Toast.makeText(act,"Passowrd doesnt match",Toast.LENGTH_LONG).show();
            return false;
        }
        return  true;
    }

    //Time Validator
    public boolean checkTime(String time , Activity act) {
        //checking if time is not empty
        if (TextUtils.isEmpty(time)) {
            Toast.makeText(act , "Please enter arrival time and pickup time", Toast.LENGTH_LONG).show();
            return false;
        }
        //Todo : time validator
        return true;
    }

    //price validator
    public boolean checkPrice(String price ,  Activity act){

        //checking if price is empty
        if (TextUtils.isEmpty(price)) {
            Toast.makeText(act, "Please enter a price", Toast.LENGTH_LONG).show();
            return false;
        }
        //Todo: price validator
        return true;
    }

    //source validator
    public boolean checkSrc(String src , Activity act){

        //checking if src is city = src is empty
        if (src == "city") {
            Toast.makeText(act, "Please pick a city", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    //Destination validator
    public boolean checkdst(String dst , Activity act){

        //checking if dst is university = src is empty
        if (dst == "university") {
            Toast.makeText(act, "Please pick a university", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    //firstName validator
    public boolean checkFirstName(String firstName , Activity act){

        //checking if firstName is empty
        if (TextUtils.isEmpty(firstName)) {
            Toast.makeText(act, "Please enter a first name", Toast.LENGTH_LONG).show();
            return false;
        }

        //checking if lastName is valid
        else if(!(Pattern.matches("[a-zA-Z]+", firstName))){
            Toast.makeText(act, "First name can olny contain letters", Toast.LENGTH_LONG).show();
            return false;
        }
            return true;
    }

    //lastName validator
    public boolean checkLastName(String lastName , Activity act){

        //checking if lastName is empty
        if (TextUtils.isEmpty(lastName)) {
            Toast.makeText(act, "Please enter a last name", Toast.LENGTH_LONG).show();
            return false;
        }

        //checking if lastName is valid
        else if(!(Pattern.matches("[a-zA-Z]+", lastName))){
            Toast.makeText(act, "LastName can olny contain letters", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    //phoneNumber validator
    public boolean checkPhonenumber(String phoneNumber , Activity act){

        //checking if phoneNumber is empty
        if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(act, "Please enter a phone number", Toast.LENGTH_LONG).show();
            return false;
        }

        //checking if phone number is valid
        else if(!(Pattern.matches("[0-9]+", phoneNumber))){
            Toast.makeText(act, "Phone number can olny contain numbers", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    /**
     * Validate date format with regular expression
     * @param date date address for validation
     * @return true valid date format, false invalid date format
     */
    public boolean validateDate(final String date){

        matcher = pattern.matcher(date);

        if(matcher.matches()){
            matcher.reset();

            if(matcher.find()){
                String day = matcher.group(1);
                String month = matcher.group(2);
                int year = Integer.parseInt(matcher.group(3));

                if (day.equals("31") &&
                        (month.equals("4") || month .equals("6") || month.equals("9") ||
                                month.equals("11") || month.equals("04") || month .equals("06") ||
                                month.equals("09"))) {
                    return false; // only 1,3,5,7,8,10,12 has 31 days
                }

                else if (month.equals("2") || month.equals("02")) {
                    //leap year
                    if(year % 4==0){
                        if(day.equals("30") || day.equals("31")){
                            return false;
                        }
                        else{
                            return true;
                        }
                    }
                    else{
                        if(day.equals("29")||day.equals("30")||day.equals("31")){
                            return false;
                        }
                        else{
                            return true;
                        }
                    }
                }

                else{
                    return true;
                }
            }

            else{
                return false;
            }
        }
        else{
            return false;
        }
    }


}
