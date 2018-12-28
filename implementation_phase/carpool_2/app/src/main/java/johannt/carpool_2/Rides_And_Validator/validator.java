package johannt.carpool_2.Rides_And_Validator;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    //constructor
    public Validator() {
    }

    private Pattern pattern;
    private Matcher matcher;

    private static final String DATE_PATTERN =
            "(0?[1-9]|1[012]) [/.-] (0?[1-9]|[12][0-9]|3[01]) [/.-] ((19|20)\\d\\d)";

    //Date Validator
    public boolean checkDate(String date ,  Context context) {

        matcher = Pattern.compile(DATE_PATTERN).matcher(date);

        //checking if date is empty
        if (TextUtils.isEmpty(date)) {
            Toast.makeText(context, "Please enter date", Toast.LENGTH_LONG).show();
            return false;
        }

        //checking if the date is valid
        else if (!matcher.matches() || !(validateDate(date))) {
            Toast.makeText(context, "Invalid date", Toast.LENGTH_SHORT).show();
        }
        return  true;
    }

    //Between dates validator
    public  Boolean checkBetweenTime(String time1 , String time2){
        return time1.compareTo(time2) >= 0 ? true :false;
    }

    public Boolean matchDates(String date1 , String date2){
        return date1.compareTo(date2) == 0? true : false;
    }

    //Email Validator
    public boolean checkEmail(String email , Context context) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        //checking if email are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(context,"Please enter email",Toast.LENGTH_LONG).show();
            return false;
        }
        //checking if email is valid
        else if(matcher.matches() == false){
            Toast.makeText(context,"Please enter valid mail address",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    //Password Validator
    public boolean checkPassword(String password , Context context) {
        //check if password not empty
        if(TextUtils.isEmpty(password)){
            Toast.makeText(context,"Please enter password",Toast.LENGTH_LONG).show();
            return false;
        }

        //checking if password contain at least 6 digit
        else if(password.length() < 6){
            Toast.makeText(context,"Password must contain atleast 6 digits/charcontexters",Toast.LENGTH_LONG).show();
            return false;
        }
        return  true;
    }

    //Repeat password Validator
    public boolean checkRepeatPassword(String repeatPassword , String password ,  Context context) {
        //check if password not empty
        if(TextUtils.isEmpty(repeatPassword)){
            Toast.makeText(context,"Please repeat the password",Toast.LENGTH_LONG).show();
            return false;
        }

        //checking if password contain at least 6 digit
        else if((repeatPassword.length() < 6) || (!(repeatPassword.equals(password)))){
            Toast.makeText(context,"Passowrd doesnt match",Toast.LENGTH_LONG).show();
            return false;
        }
        return  true;
    }

    //Time Validator
    public boolean checkTime(String time , Context context) {
        //checking if time is not empty
        if (TextUtils.isEmpty(time)) {
            Toast.makeText(context , "Please enter arrival time and pickup time", Toast.LENGTH_LONG).show();
            return false;
        }
        //Todo : time validator
        return true;
    }

    //price validator
    public boolean checkPrice(String price ,  Context context){

        //checking if price is empty
        if (TextUtils.isEmpty(price)) {
            Toast.makeText(context, "Please enter a price", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(price.compareTo("0")<0){
            Toast.makeText(context, "price cannot be less than 0", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    //source validator
    public boolean checkSrc(String src ,  Context context){

        //checking if src is city = src is empty
        if (src.equals("City")) {
            Toast.makeText(context, "Please pick a city", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    //Destination validator
    public boolean checkdst(String dst , Context context){

        //checking if dst is university = src is empty
        if (dst.equals("University")) {
            Toast.makeText(context, "Please pick a university", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    //firstName validator
    public boolean checkFirstName(String firstName ,  Context context){

        //checking if firstName is empty
        if (TextUtils.isEmpty(firstName)) {
            Toast.makeText(context, "Please enter a first name", Toast.LENGTH_LONG).show();
            return false;
        }

        //checking if lastName is valid
        else if(!(Pattern.matches("[a-zA-Z]+", firstName))){
            Toast.makeText(context, "First name can olny contain letters", Toast.LENGTH_LONG).show();
            return false;
        }
            return true;
    }

    //lastName validator
    public boolean checkLastName(String lastName ,  Context context){

        //checking if lastName is empty
        if (TextUtils.isEmpty(lastName)) {
            Toast.makeText(context, "Please enter a last name", Toast.LENGTH_LONG).show();
            return false;
        }

        //checking if lastName is valid
        else if(!(Pattern.matches("[a-zA-Z]+", lastName))){
            Toast.makeText(context, "LastName can olny contain letters", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    //phoneNumber validator
    public boolean checkPhonenumber(String phoneNumber ,  Context context){

        //checking if phoneNumber is empty
        if (phoneNumber.length() > 10) {
            Toast.makeText(context, "please enter a valid number", Toast.LENGTH_LONG).show();
            return false;
        }
        //checking if phone number is valid
        else if(!phoneNumber.isEmpty() && !(Pattern.matches("[0-9]+", phoneNumber))){
            Toast.makeText(context, "Phone number can olny contain numbers", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    //price validator
    public Boolean checkPrice(String price1 , String price2){
        return price1.compareTo(price2)<=0 ? true : false;
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
