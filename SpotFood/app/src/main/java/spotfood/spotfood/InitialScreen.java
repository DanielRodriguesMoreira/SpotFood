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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
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

public class InitialScreen extends Activity {

    private DatabaseReference mSpotFoodDataBaseReference;
    private ImageButton mSearchButton;
    private TextView mSearchText;
    private ListView mListRestaurants;
    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> mRestaurantsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);      //Teste para tirar a barra do titulo
        setContentView(R.layout.activity_initial_screen);

        //check internet Connection
        if(!hasNetworkConnection()){
            internetConnectionErrorDialog md = new internetConnectionErrorDialog();
            md.show(getFragmentManager(),"TAG");
        }

        //Get database reference
        mSpotFoodDataBaseReference = FirebaseDatabase.getInstance().getReference();

        //inicialize variables
        this.inicializeVariables();

        //show open restaurants
        this.searchOpenRestaurants();
    }

    /** Inicialize all the variables */
    private void inicializeVariables() {
        this.mRestaurantsList = new ArrayList<>();
        this.mSearchButton = (ImageButton) findViewById(R.id.searchButton);
        this.mListRestaurants = (ListView) findViewById(R.id.listRestaurants);
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

        //Set mAdapter to one row of list view
        this.mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mRestaurantsList);
        this.mListRestaurants.setAdapter(this.mAdapter);

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
                    restaurantOpen = u.getRestaurantHour(day).getOpen();
                    restaurantClose = u.getRestaurantHour(day).getClose();

                    //in case open hours are lower than close hours and the current time is in that range
                    if(restaurantOpen < restaurantClose && (currentTime >= u.getRestaurantHour(day).getOpen()
                            && currentTime < u.getRestaurantHour(day).getClose())) {
                        mRestaurantsList.add(u.getName());
                    }
                    /* in case open hours are higher than close hours and the current time is higher
                     than open hours and lower than close hours*/
                    else if( restaurantOpen > restaurantClose && (currentTime >= u.getRestaurantHour(day).getOpen()
                            || currentTime < u.getRestaurantHour(day).getClose())) {
                        mRestaurantsList.add(u.getName());
                    }
                }

                mAdapter.notifyDataSetChanged();
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

                //refresh ListView
                mAdapter.notifyDataSetChanged();
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

                //refresh ListView
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    /** Check if there is internet connection on android phone */
    private boolean hasNetworkConnection(){
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    }

    /** Fragment Dialog to show internet connection error */
    class internetConnectionErrorDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder .setTitle("ERROR")
                    .setMessage("No internet connection")
                    .setIcon(android.R.drawable.stat_notify_error)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
            return builder.create();
        }
    }
}