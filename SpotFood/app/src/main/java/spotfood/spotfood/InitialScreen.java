package spotfood.spotfood;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
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

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class InitialScreen extends Activity {

    private DatabaseReference SpotFood;
    ImageButton searchButton;
    ArrayAdapter<String> adapter;
    ListView liTeste;
    String [] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    public ArrayList<String> restaurant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);      //Teste para tirar a barra do titulo
        setContentView(R.layout.activity_initial_screen);

        SpotFood = FirebaseDatabase.getInstance().getReference();

        restaurant = new ArrayList<>();
        this.searchButton = (ImageButton) findViewById(R.id.searchButton);
        this.liTeste = (ListView) findViewById(R.id.listRestaurants);
        this.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hourSearch();
                adapter.notifyDataSetChanged();
            }
        });
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, restaurant);
        this.liTeste.setAdapter(adapter);

    }
    private void createRestaurant(Restaurant r) {
        SpotFood.child("restaurants").child(r.getIdRestaurant()).setValue(r);
    }



    private void hourSearch(){
        DatabaseReference userRef = this.SpotFood.child("restaurants");
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
                    System.out.println("Restaurant:"+u.getName()+" open:"+restaurantOpen+" close:"+restaurantClose + " current:"+currentTime);
                    if(restaurantOpen < restaurantClose && (currentTime >= u.getRestaurantHour(day).getOpen() && currentTime < u.getRestaurantHour(day).getClose()))
                        restaurant.add(u.getName());
                    else if( restaurantOpen > restaurantClose && (currentTime >= u.getRestaurantHour(day).getOpen() || currentTime < u.getRestaurantHour(day).getClose()))
                        restaurant.add(u.getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void searchRestaurant(final String s) {
        DatabaseReference userRef = this.SpotFood.child("restaurants");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Restaurant u = postSnapshot.getValue(Restaurant.class);
                    restaurant.add(u.getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}