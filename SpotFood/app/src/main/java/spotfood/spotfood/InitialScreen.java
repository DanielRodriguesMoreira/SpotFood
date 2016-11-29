package spotfood.spotfood;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class InitialScreen extends Activity {

    private DatabaseReference SpotFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_screen);

        SpotFood = FirebaseDatabase.getInstance().getReference();

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
        });
    }
    private void createRestaurant(Restaurant r) {
        SpotFood.child("restaurants").child(r.getIdRestaurant()).setValue(r);
    }
}