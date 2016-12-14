/*
 * SpotFood - 2016
 *
 * Authors:
 *          -> Daniel Moreira   nº21240321
 *          -> Hugo Santos      nº21220702
 *          -> Tiago Santos     nº21230530
 *          -> Carlos Zambrano  nº 21260582
 *          -> Selman Ay        nº21260599
 */

package spotfood.spotfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.poili.spotfood.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginScreen extends Activity implements Constants{

    private TextView mUsernameText;
    private TextView mPasswordText;
    private Button mLoginButton;
    private Button mCreateAccountButton;
    private Map<String, String[]> mMapLogin;
    private DatabaseReference mSpotFoodDataBaseReference;
    private String mUserRole;
    private String mUserID;
    private static final String ADMIN = "ADMIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);

        this.inicializeVariables();

        getLoginList();
    }

    /** Inicialize all the variables */
    private void inicializeVariables() {
        this.mSpotFoodDataBaseReference = FirebaseDatabase.getInstance().getReference();
        this.mMapLogin = new HashMap<>();
        this.mUsernameText = (TextView)findViewById(R.id.loginUsername);
        this.mPasswordText = (TextView)findViewById(R.id.loginPassword);
        this.mLoginButton = (Button)findViewById(R.id.loginOk);
        this.mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String username = mUsernameText.getText().toString();
                String password = mPasswordText.getText().toString();

                if(!usernameAndPasswordAreCorrect(username, password)){
                    Toast.makeText(getApplicationContext(), "Username or password incorrect", Toast.LENGTH_LONG).show();
                }
                else{
                    if(mUserRole.toUpperCase().equals(ADMIN)){
                        Intent intent = new Intent(getApplicationContext(), InitialScreen.class);
                        intent.putExtra(ADMIN, true);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        searchRestaurantByUserIDAndCallIntent(mUserID);
                    }
                }
            }
        });
        this.mCreateAccountButton = (Button)findViewById(R.id.newAccount);
        this.mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), CreateAccountScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /** Search a restaurant by User ID and call the activity that will show the information */
    private void searchRestaurantByUserIDAndCallIntent(final String mUserID) {

        if (mUserID == null || mUserID.isEmpty()) {
            return;
        }

        //Get restaurants reference
        final DatabaseReference restaurantsRef = this.mSpotFoodDataBaseReference.child("restaurants");

        //add Listener
        restaurantsRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Cicle For that go through all the restaurants in firebase database
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Restaurant rest = postSnapshot.getValue(Restaurant.class);
                    if(rest.getIdUser().equals(mUserID)){
                        fillRestaurantInformationAndCallIntent(rest);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    /** Fill restaurant information and call the activity to shows that information */
    private void fillRestaurantInformationAndCallIntent(Restaurant restaurant) {
        if (restaurant == null) {
            return;
        }

        String restaurantName = restaurant.getName();
        String restaurantID = restaurant.getIdRestaurant();
        String restaurantUserID = restaurant.getIdUser();
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

        intent.putExtra(ONLYTOSHOW, false);
        intent.putExtra(RESTAURANT_ID, restaurantID);
        intent.putExtra(RESTAURANT_NAME, restaurantName);
        intent.putExtra(USER_ID, restaurantUserID);
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

    /** Check if username and password are correct */
    private boolean usernameAndPasswordAreCorrect(String username, String password) {
        if(username == null || username.length() == 0
                || password == null || password.length() == 0){
            return false;
        }
        else{
            for (Map.Entry<String, String[]> entry : mMapLogin.entrySet())
            {
                if(entry.getKey().equals(username) && entry.getValue()[0].equals(password)){
                    this.mUserRole = entry.getValue()[1];
                    this.mUserID = entry.getValue()[2];
                    return true;
                }
            }
        }

        return false;
    }

    /** Get all the usernames and passwords on firebase database */
    private void getLoginList() {

        this.mMapLogin.clear();

        //Get restaurants reference
        final DatabaseReference mUsersRef = mSpotFoodDataBaseReference.child("users");

        //add listener
        mUsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Cicle For that go through all the users in firebase database
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String username = postSnapshot.child("username").getValue(String.class);
                    String password = postSnapshot.child("password").getValue(String.class);
                    String role     = postSnapshot.child("role").getValue(String.class);
                    String id       = postSnapshot.child("idUser").getValue(String.class);
                    String[] array = new String[3];
                    array[0] = password;
                    array[1] = role;
                    array[2] = id;
                    mMapLogin.put(username, array);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplication(), InitialScreen.class);
        startActivity(intent);
        finish();
    }
}
