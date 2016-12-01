package spotfood.spotfood;

import android.app.Activity;
import android.os.Bundle;
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

import java.util.ArrayList;
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
                searchRestaurant(null);
                adapter.notifyDataSetChanged();
            }
        });
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, restaurant);
        this.liTeste.setAdapter(adapter);



        /* HUGO
        Button botao = (Button) findViewById(R.id.button2);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createRestaurant(new Restaurant("1", "0", "Mc Donals", new Hour(new Date(), new Date()),
                    new Hour(new Date(), new Date()),
                    new Hour(new Date(), new Date()),
                    new Hour(new Date(), new Date()),
                    new Hour(new Date(), new Date()),
                    new Hour(new Date(), new Date()),
                    new Hour(new Date(), new Date()),   "231568123", "Rua principal", new ArrayList<String>()));


                createRestaurant(new Restaurant("2", "0", "Mc Donals 2.0", new Hour(new Date(), new Date()),
                        new Hour(new Date(), new Date()),
                        new Hour(new Date(), new Date()),
                        new Hour(new Date(), new Date()),
                        new Hour(new Date(), new Date()),
                        new Hour(new Date(), new Date()),
                        new Hour(new Date(), new Date()),   "231568123", "Rua principal", new ArrayList<String>()));
            }
        });*/
    }
    private void createRestaurant(Restaurant r) {
        SpotFood.child("restaurants").child(r.getIdRestaurant()).setValue(r);
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