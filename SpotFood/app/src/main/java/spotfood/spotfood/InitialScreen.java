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
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.poili.spotfood.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InitialScreen extends Activity implements Constants {

    private DatabaseReference mSpotFoodDataBaseReference;
    private ImageButton mSearchButton;
    private Button mLoginButton;
    private TextView mSearchText;
    private ListView mListRestaurants;
    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> mRestaurantsList;
    private ImageButton mAddRestaurantButton;
    private TextView mEmptyText;
    private boolean mStateLogin; //Used to check if it's login(true) or logout(false)
    private static final boolean LOGIN = true;
    private static final boolean LOGOUT = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_initial_screen);

        //check internet Connection
        if (!hasNetworkConnection()) {
            internetConnectionErrorDialog md = new internetConnectionErrorDialog();
            md.show(getFragmentManager(), "TAG");
        }

        //inicialize variables
        this.inicializeVariables();
        //Aqui temos que fazer isto ou procurar por todos se for um administrador(já temos essa função feita)

        //show open restaurants
        //this.searchOpenRestaurants();
    }

    /** Inicialize all the variables */
    private void inicializeVariables() {
        //Get database reference
        mSpotFoodDataBaseReference = FirebaseDatabase.getInstance().getReference();
        this.mStateLogin = LOGIN;
        this.mRestaurantsList = new ArrayList<>();
        this.mSearchButton = (ImageButton) findViewById(R.id.searchButton);
        this.mListRestaurants = (ListView) findViewById(R.id.listRestaurants);
        this.mListRestaurants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String restaurantName = mListRestaurants.getItemAtPosition(i).toString();
                searchRestaurantByName(restaurantName);
            }
        });
        this.mEmptyText = (TextView)findViewById(android.R.id.empty);
        this.mSearchText = (TextView) findViewById(R.id.searchText);
        this.mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSearchText.getText().toString() == null || mSearchText.getText().length() == 0) {
                    searchOpenRestaurants();
                }
                else {
                    searchRestaurantByNameOrTypeOfFood(mSearchText.getText().toString());
                }
            }

        });
        this.mLoginButton = (Button)findViewById(R.id.loginButton);
        this.mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mStateLogin == LOGIN){
                    Intent intent = new Intent(getApplication(), LoginScreen.class);
                    startActivity(intent);
                    finish();
                }
                else if(mStateLogin == LOGOUT) {
                    mStateLogin = LOGOUT;
                    mAddRestaurantButton.setVisibility(View.INVISIBLE);
                    mLoginButton.setText("Login");
                }
            }
        });
        this.mAddRestaurantButton = (ImageButton) findViewById(R.id.addRestaurantButton);

        //Set mAdapter to one row of list view
        this.mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mRestaurantsList);
        this.mListRestaurants.setAdapter(this.mAdapter);

        this.checkIntentResult();
    }

    /** Search on firebase database all the restaurants that are open at the current time */
    private void searchOpenRestaurants() {

        mRestaurantsList.clear();

        DatabaseReference userRef = this.mSpotFoodDataBaseReference.child("restaurants");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            Calendar c = Calendar.getInstance();
            int currentTime = c.get(Calendar.HOUR_OF_DAY)*100+c.get(Calendar.MINUTE);
            int day = c.get(Calendar.DAY_OF_WEEK);
            int restaurantOpen, restaurantClose;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Restaurant u = postSnapshot.getValue(Restaurant.class);
                    restaurantOpen = u.getRestaurantHour(day).getOpenHour()*100 + u.getRestaurantHour(day).getOpenMinutes();
                    restaurantClose = u.getRestaurantHour(day).getCloseHour()*100 + u.getRestaurantHour(day).getCloseMinutes();

                    //in case open hours are lower than close hours and the current time is in that range
                    if(restaurantOpen < restaurantClose && (currentTime >= restaurantOpen
                            && currentTime <= restaurantClose)) {
                        mRestaurantsList.add(u.getName());
                    }
                    /* in case open hours are higher than close hours and the current time is higher
                     than open hours and lower than close hours*/
                    else if( restaurantOpen > restaurantClose && (currentTime >= restaurantOpen
                            || currentTime <= restaurantClose)) {
                        mRestaurantsList.add(u.getName());
                    }
                }

                if(mRestaurantsList.size() == 0){
                    mEmptyText.setText("There are no restaurants open at this time!");
                    mListRestaurants.setEmptyView(mEmptyText);
                }
                else{
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    /** Search on firebase database all the restaurants (this is for administrator) */
    private void searchAllRestaurants() {

        mRestaurantsList.clear();

        //Get restaurants reference
        final DatabaseReference restaurantsRef = this.mSpotFoodDataBaseReference.child("restaurants");

        //add Listener
        restaurantsRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Cicle For that go through all the restaurants in firebase database
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Restaurant rest = postSnapshot.getValue(Restaurant.class);
                    mRestaurantsList.add(rest.getName());
                }

                if(mRestaurantsList.size() == 0){
                    mEmptyText.setText("There are no restaurants on Firebase Database!");
                    mListRestaurants.setEmptyView(mEmptyText);
                }
                else{
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    /** Search on firebase database all the restaurants by their name or type of food */
    private void searchRestaurantByNameOrTypeOfFood(final String nameOrTypeOfFood) {

        //Check if string is null or empty
        if (nameOrTypeOfFood == null || nameOrTypeOfFood.isEmpty()) {
            searchAllRestaurants();
            return;
        }

        //clear ListView
        mRestaurantsList.clear();

        //Get restaurants reference
        final DatabaseReference restaurantsRef = this.mSpotFoodDataBaseReference.child("restaurants");

        //add Listener
        restaurantsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Cicle For that go through all the restaurants in firebase database
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Restaurant rest = postSnapshot.getValue(Restaurant.class);
                    boolean find = false;

                    //search by type of food
                    if(rest.getTypeOfFood() != null) {
                        for (String typeOfFood : rest.getTypeOfFood()) {
                            //if (typeOfFood.toUpperCase().equals(nameOrTypeOfFood.toUpperCase())) {
                            if(typeOfFood.toUpperCase().contains(nameOrTypeOfFood.toUpperCase())){
                                find = true;
                                mRestaurantsList.add(rest.getName());
                                break;
                            }
                        }
                    }

                    //search by name if not find by type of food
                    if(!find){
                        if(rest.getName().toUpperCase().contains(nameOrTypeOfFood.toUpperCase())){
                            mRestaurantsList.add(rest.getName());
                        }
                    }
                }

                if(mRestaurantsList.size() == 0){
                    mEmptyText.setText("There are no restaurants with that name" +
                            " or that serve that type of food!");
                    mListRestaurants.setEmptyView(mEmptyText);
                }
                else{
                    mAdapter.notifyDataSetChanged();
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
        String location = restaurant.getLocation();
        String contacts = restaurant.getContacts();
        List<String> typeOfFoodList = restaurant.getTypeOfFood();
        String typeOfFood = "";
        for(int i = 0; i < typeOfFoodList.size(); i++){
            if(i == typeOfFoodList.size() - 1){
                typeOfFood += typeOfFoodList.get(i);
            }
            else {
                typeOfFood += typeOfFoodList.get(i) + ", ";
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

        Intent intent = new Intent(getApplication(), Details.class);
        intent.putExtra(ONLYTOSHOW, true);
        intent.putExtra(RESTAURANT_NAME, restaurantName);
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
        intent.putExtra(LOCATION, location);
        intent.putExtra(CONTACTS, contacts);
        intent.putExtra(TYPE_OF_FOOD, typeOfFood);


        startActivity(intent);
        finish();
    }


    /** Check if there is internet connection on android phone */
    private boolean hasNetworkConnection(){
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    }

    /** Check if this class was called by an intent */
    private void checkIntentResult() {
        Intent intent = getIntent();
        boolean userRole = intent.getBooleanExtra("ADMIN", false);
        if(userRole){
            this.mStateLogin = LOGOUT;
            this.mLoginButton.setText("Logout");
            this.mAddRestaurantButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    /** Fragment Dialog to show internet connection error */
    class internetConnectionErrorDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder .setTitle("ERROR")
                    .setMessage("No internet connection")
                    .setIcon(android.R.drawable.stat_notify_error)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
            return builder.create();
        }
    }
}