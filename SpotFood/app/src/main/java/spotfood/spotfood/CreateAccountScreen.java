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
import java.util.List;
import java.util.UUID;

public class CreateAccountScreen extends Activity {

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
        User user = new User(UUID.randomUUID().toString(), username, password, mRoleRM);
        mSpotFoodDataBaseReference.child("users").child(user.getIdUser()).setValue(user);

        Intent intent = new Intent(getApplication(), Details.class);
        startActivity(intent);
        finish();
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
                final List<String> mRestaurantsNamesList = new ArrayList<String>();
                mRestaurantsNamesList.add("New Restaurant");

                //Cicle For that go through all the restaurants in firebase database
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String idUser = postSnapshot.child("idUser").getValue(String.class);
                    // if idUser equals 0, it means that it is not associated with a user
                    if(idUser.equals("0")){
                        String restaurantName = postSnapshot.child("name").getValue(String.class);
                        mRestaurantsNamesList.add(restaurantName);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplication(), InitialScreen.class);
        startActivity(intent);
        finish();
    }
}
