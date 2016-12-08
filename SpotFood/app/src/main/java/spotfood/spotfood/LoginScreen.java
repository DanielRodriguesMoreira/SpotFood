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
import java.util.Map;


public class LoginScreen extends Activity {

    private TextView mUsernameText;
    private TextView mPasswordText;
    private Button mLoginButton;
    private Button mCreateAccountButton;
    private Map<String, String[]> mMapLogin;
    private DatabaseReference mSpotFoodDataBaseReference;
    private String mUserRole;
    private static final String ADMIN = "ADMIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);

        this.inicializeVariables();

        getLoginList();
    }

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
                    Toast.makeText(getApplicationContext(), ("Login ok! Role = " + mUserRole), Toast.LENGTH_LONG).show();
                    if(mUserRole.toUpperCase().equals(ADMIN)){
                        Intent intent = new Intent(getApplicationContext(), InitialScreen.class);
                        intent.putExtra(ADMIN, true);
                        startActivity(intent);
                        finish();
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
                    String[] array = new String[2];
                    array[0] = password;
                    array[1] = role;
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
