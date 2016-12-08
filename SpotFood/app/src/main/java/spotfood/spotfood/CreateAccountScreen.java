/*
 * SpotFood - 2016
 *
 * Authors:
 *          -> Daniel Moreira nº21240321
 *          -> Hugo Santos nº21220702
 *          -> Tiago Santos nº21230530
 *          -> Carlos Zambrano nº 21260582
 *          -> Selman Ay nº21260599
 */

package spotfood.spotfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.poili.spotfood.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CreateAccountScreen extends Activity implements Constants{

    private DatabaseReference mSpotFoodDataBaseReference;
    private TextView mUsernameTextView;
    private TextView mPasswordTextView;
    private Button mCreateAccountButton;
    private Spinner mRestaurantsSpinner;
    private List<String> mUsernamesList;
    private static String mUsernameLengthError = "Username must be longer than 5 characters";
    private static String mPasswordLengthError = "Password must be longer than 5 characters";
    private static String mUsernameAlreadyExistsError = "Username already exists on database";
    private static String mUsernameInvalidError = "Username can't contain: % & / ^";
    private static String mPasswordInvalidError = "Password can't contain: % & / ^";
    private static String mRoleRM = "RM";
    private String mUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.create_account);

        this.inicializeVariables();

        getAllRestaurantsWithoutAssociatedUser();

        getUsernamesList();
    }

    private void inicializeVariables() {
        this.mSpotFoodDataBaseReference = FirebaseDatabase.getInstance().getReference();
        this.mUsernameTextView = (TextView)findViewById(R.id.userText);
        this.mPasswordTextView = (TextView)findViewById(R.id.passText);
        this.mCreateAccountButton = (Button)findViewById(R.id.createAccountOk);
        this.mRestaurantsSpinner = (Spinner)findViewById(R.id.spinnerRestaurants);
        this.mUsernamesList = new ArrayList<>();
        this.mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = mUsernameTextView.getText().toString();
                String password = mPasswordTextView.getText().toString();

                if(!isLengthCorrect(username) || haveInvalidCharacters(username) || checkIfUsernameAlreadyExists(username)) {
                    ImageView result= (ImageView)findViewById(R.id.result_user_create_account_icon);
                    result.setImageResource(R.mipmap.error_icon);
                    result.setVisibility(ImageView.VISIBLE);
                } else {
                    ImageView result= (ImageView)findViewById(R.id.result_user_create_account_icon);
                    result.setVisibility(ImageView.INVISIBLE);
                }
                if(!isLengthCorrect(password) || haveInvalidCharacters(password)) {
                    ImageView result= (ImageView)findViewById(R.id.result_pass_create_account_icon);
                    result.setImageResource(R.mipmap.error_icon);
                    result.setVisibility(ImageView.VISIBLE);
                } else {
                    ImageView result= (ImageView)findViewById(R.id.result_pass_create_account_icon);
                    result.setVisibility(ImageView.INVISIBLE);
                }

                if (!isLengthCorrect(username)) {
                    Toast.makeText(getApplicationContext(), mUsernameLengthError, Toast.LENGTH_LONG).show();
                } else if (!isLengthCorrect(password)) {
                    Toast.makeText(getApplicationContext(), mPasswordLengthError, Toast.LENGTH_LONG).show();
                } else if (haveInvalidCharacters(username)) {
                    Toast.makeText(getApplicationContext(), mUsernameInvalidError, Toast.LENGTH_LONG).show();
                } else if (haveInvalidCharacters(password)) {
                    Toast.makeText(getApplicationContext(), mPasswordInvalidError, Toast.LENGTH_LONG).show();
                } else if (checkIfUsernameAlreadyExists(username)) {
                    Toast.makeText(getApplicationContext(), mUsernameAlreadyExistsError, Toast.LENGTH_LONG).show();
                } else {
                    createAccount(mUsernameTextView.getText().toString(),
                            mPasswordTextView.getText().toString(),
                            mRestaurantsSpinner.getSelectedItem().toString());
                }
            }
        });
    }

    /** Create Account on firebase database */
    private void createAccount(String username, String password, String restaurant) {
        mUserID = UUID.randomUUID().toString();
        User user = new User(mUserID, username, password, mRoleRM);
        mSpotFoodDataBaseReference.child("users").child(user.getIdUser()).setValue(user);

        if(restaurant.equals("New Restaurant")) {
            Intent intent = new Intent(getApplication(), Details.class);
            intent.putExtra(USER_ID, mUserID);
            intent.putExtra(NEWRESTAURANT, true);
            startActivity(intent);
            finish();
        }
        else{
            searchRestaurantByName(restaurant);
        }

    }

    /** Check if a string is null or shorten than 5 characters*/
    private boolean isLengthCorrect(String text){
        if(text == null || text.length() <= 5){
            return false;
        }
        else {
            return true;
        }
    }

    //** Check if a string contains invalid characters: % & / ^ */
    private boolean haveInvalidCharacters(String text){
        if(text.contains("%") || text.contains("&") || text.contains("/")
                || text.contains("^")){
            return true;
        }
        else {
            return false;
        }
    }

    /** Check if username already exists on firebase database*/
    private boolean checkIfUsernameAlreadyExists(String username) {

        for(String u : this.mUsernamesList){
            if(u.toUpperCase().equals(username.toUpperCase())){
                return true;
            }
        }

        return false;
    }

    /** Get all the restaurants on firebase database that don't have an user associated*/
    private void getAllRestaurantsWithoutAssociatedUser(){

        //Get restaurants reference
        final DatabaseReference mRestaurantsRef = mSpotFoodDataBaseReference.child("restaurants");

        //add listener
        mRestaurantsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<String> mRestaurantsNamesList = new ArrayList<String>();

                mRestaurantsNamesList.add("New Restaurant");

                //Cicle For that go through all the restaurants in firebase database
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String idUser = postSnapshot.child("idUser").getValue(String.class);
                    // if idUser equals 0, it means that it is not associated with a user
                    if(idUser != null) {
                        if (idUser.equals("0")) {
                            String restaurantName = postSnapshot.child("name").getValue(String.class);
                            mRestaurantsNamesList.add(restaurantName);
                        }
                    }
                }

                //Set mAdapter to one row of spinner
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateAccountScreen.this,
                        android.R.layout.simple_spinner_item, mRestaurantsNamesList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mRestaurantsSpinner.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    /** Get all the usernames on firebase database */
    private void getUsernamesList() {

        this.mUsernamesList.clear();

        //Get restaurants reference
        final DatabaseReference mRestaurantsRef = mSpotFoodDataBaseReference.child("users");

        //add listener
        mRestaurantsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Cicle For that go through all the restaurants in firebase database
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String username = postSnapshot.child("username").getValue(String.class);
                    mUsernamesList.add(username);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    private void searchRestaurantByName(final String restaurantName) {

        //Check if string is null or empty
        if (restaurantName == null || restaurantName.isEmpty()) {
            return;
        }

        //Get restaurants reference
        final DatabaseReference restaurantsRef = this.mSpotFoodDataBaseReference.child("restaurants");

        //add Listener
        restaurantsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Cicle For that go through all the restaurants in firebase database
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Restaurant rest = postSnapshot.getValue(Restaurant.class);

                    if(rest.getName().equals(restaurantName)){
                        fillRestaurantInformationAndCallIntent(rest);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    private void fillRestaurantInformationAndCallIntent(Restaurant restaurant) {
        if(restaurant == null ){
            return;
        }

        String restaurantName = restaurant.getName();
        String restaurantID = restaurant.getIdRestaurant();
        String location = restaurant.getLocation();
        String contacts = restaurant.getContacts();
        List<String> typeOfFoodList = restaurant.getTypeOfFood();
        String typeOfFood = "";
        if(typeOfFoodList != null) {
            for (int i = 0; i < typeOfFoodList.size(); i++) {
                if (i == typeOfFoodList.size() - 1) {
                    typeOfFood += typeOfFoodList.get(i);
                } else {
                    typeOfFood += typeOfFoodList.get(i) + ", ";
                }
            }
        }
        int mondayOpenHours = restaurant.getMondayHour().getOpenHour();
        int mondayOpenMinutes = restaurant.getMondayHour().getOpenMinutes();
        int tuesdayOpenHours = restaurant.getTuesdayHour().getOpenHour();
        int tuesdayOpenMinutes = restaurant.getTuesdayHour().getOpenMinutes();
        int wednesdayOpenHours = restaurant.getWednesdayHour().getOpenHour();
        int wednesdayOpenMinutes = restaurant.getWednesdayHour().getOpenMinutes();
        int thursdayOpenHours = restaurant.getThursdayHour().getOpenHour();
        int thursdayOpenMinutes = restaurant.getThursdayHour().getOpenMinutes();
        int fridayOpenHours = restaurant.getFridayHour().getOpenHour();
        int fridayOpenMinutes = restaurant.getFridayHour().getOpenMinutes();
        int saturdayOpenHours = restaurant.getSaturdayHour().getOpenHour();
        int saturdayOpenMinutes = restaurant.getSaturdayHour().getOpenMinutes();
        int sundayOpenHours = restaurant.getSundayHour().getOpenHour();
        int sundayOpenMinutes = restaurant.getSundayHour().getOpenMinutes();
        int mondayCloseHours = restaurant.getMondayHour().getCloseHour();
        int mondayCloseMinutes = restaurant.getMondayHour().getCloseMinutes();
        int tuesdayCloseHours = restaurant.getTuesdayHour().getCloseHour();
        int tuesdayCloseMinutes = restaurant.getTuesdayHour().getCloseMinutes();
        int wednesdayCloseHours = restaurant.getWednesdayHour().getCloseHour();
        int wednesdayCloseMinutes = restaurant.getWednesdayHour().getCloseMinutes();
        int thursdayCloseHours = restaurant.getThursdayHour().getCloseHour();
        int thursdayCloseMinutes = restaurant.getThursdayHour().getCloseMinutes();
        int fridayCloseHours = restaurant.getFridayHour().getCloseHour();
        int fridayCloseMinutes = restaurant.getFridayHour().getCloseMinutes();
        int saturdayCloseHours = restaurant.getSaturdayHour().getCloseHour();
        int saturdayCloseMinutes = restaurant.getSaturdayHour().getCloseMinutes();
        int sundayCloseHours = restaurant.getSundayHour().getCloseHour();
        int sundayCloseMinutes = restaurant.getSundayHour().getCloseMinutes();

        Intent intent = new Intent(getApplication(), Details.class);
        intent.putExtra(USER_ID, mUserID);
        intent.putExtra(RESTAURANT_NAME, restaurantName);
        intent.putExtra(RESTAURANT_ID, restaurantID);
        intent.putExtra(MONDAY_OPEN_HOURS, mondayOpenHours);
        intent.putExtra(MONDAY_OPEN_MINUTES, mondayOpenMinutes);
        intent.putExtra(TUESDAY_OPEN_HOURS, tuesdayOpenHours);
        intent.putExtra(TUESDAY_OPEN_MINUTES, tuesdayOpenMinutes);
        intent.putExtra(WEDNESDAY_OPEN_HOURS, wednesdayOpenHours);
        intent.putExtra(WEDNESDAY_OPEN_MINUTES, wednesdayOpenMinutes);
        intent.putExtra(THURSDAY_OPEN_HOURS, thursdayOpenHours);
        intent.putExtra(THURSDAY_OPEN_MINUTES, thursdayOpenMinutes);
        intent.putExtra(FRIDAY_OPEN_HOURS, fridayOpenHours);
        intent.putExtra(FRIDAY_OPEN_MINUTES, fridayOpenMinutes);
        intent.putExtra(SATURDAY_OPEN_HOURS, saturdayOpenHours);
        intent.putExtra(SATURDAY_OPEN_MINUTES, saturdayOpenMinutes);
        intent.putExtra(SUNDAY_OPEN_HOURS, sundayOpenHours);
        intent.putExtra(SUNDAY_OPEN_MINUTES, sundayOpenMinutes);
        intent.putExtra(MONDAY_CLOSE_HOURS, mondayCloseHours);
        intent.putExtra(MONDAY_CLOSE_MINUTES, mondayCloseMinutes);
        intent.putExtra(TUESDAY_CLOSE_HOURS, tuesdayCloseHours);
        intent.putExtra(TUESDAY_CLOSE_MINUTES, tuesdayCloseMinutes);
        intent.putExtra(WEDNESDAY_CLOSE_HOURS, wednesdayCloseHours);
        intent.putExtra(WEDNESDAY_CLOSE_MINUTES, wednesdayCloseMinutes);
        intent.putExtra(THURSDAY_CLOSE_HOURS, thursdayCloseHours);
        intent.putExtra(THURSDAY_CLOSE_MINUTES, thursdayCloseMinutes);
        intent.putExtra(FRIDAY_CLOSE_HOURS, fridayCloseHours);
        intent.putExtra(FRIDAY_CLOSE_MINUTES, fridayCloseMinutes);
        intent.putExtra(SATURDAY_CLOSE_HOURS, saturdayCloseHours);
        intent.putExtra(SATURDAY_CLOSE_MINUTES, saturdayCloseMinutes);
        intent.putExtra(SUNDAY_CLOSE_HOURS, sundayCloseHours);
        intent.putExtra(SUNDAY_CLOSE_MINUTES, sundayCloseMinutes);
        intent.putExtra(LOCATION, location);
        intent.putExtra(CONTACTS, contacts);
        intent.putExtra(TYPE_OF_FOOD, typeOfFood);


        startActivity(intent);
        finish();
    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplication(), InitialScreen.class);
        startActivity(intent);
        finish();
    }
}
