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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView searchText;
    ArrayAdapter<String> adapter;
    ListView liTeste;
    String [] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    public ArrayList<String> restaurant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);      //Teste para tirar a barra do titulo
        setContentView(R.layout.activity_initial_screen);

        if(!hasNetworkConnection()){
            MyDialog md = new MyDialog();
            md.show(getFragmentManager(),"Hugo");
        }



        SpotFood = FirebaseDatabase.getInstance().getReference();

        restaurant = new ArrayList<>();
        this.searchButton = (ImageButton) findViewById(R.id.searchButton);
        this.liTeste = (ListView) findViewById(R.id.listRestaurants);
        this.searchText = (TextView) findViewById(R.id.searchText);


        this.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(getApplicationContext(), searchText.getText(), Toast.LENGTH_SHORT).show();

                if (searchText.getText().toString() == null || searchText.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Estou aqui", Toast.LENGTH_SHORT).show();
                    searchRestaurant();
                }
                else {
                    Toast.makeText(getApplicationContext(), "estou acolá", Toast.LENGTH_SHORT).show();
                    searchRestaurantByNameOrTypeOfFood(searchText.getText().toString());
                }
                /*
                createRestaurant(new Restaurant("3", "0", "Casa dos bifes", new Hour(new Date(), new Date()),
                        new Hour(new Date(), new Date()),
                        new Hour(new Date(), new Date()),
                        new Hour(new Date(), new Date()),
                        new Hour(new Date(), new Date()),
                        new Hour(new Date(), new Date()),
                        new Hour(new Date(), new Date()),   "231568123", "Rua principal", new ArrayList<String>()));

                createRestaurant(new Restaurant("4", "0", "Rei das Bifanas", new Hour(new Date(), new Date()),
                    new Hour(new Date(), new Date()),
                    new Hour(new Date(), new Date()),
                    new Hour(new Date(), new Date()),
                    new Hour(new Date(), new Date()),
                    new Hour(new Date(), new Date()),
                    new Hour(new Date(), new Date()),   "231568123", "Rua principal", new ArrayList<String>()));

                ArrayList<String> tipoDeComida = new ArrayList<String>();
                tipoDeComida.add(new String("Bifanas"));
                createRestaurant(new Restaurant("5", "0", "Comes de tudo", new Hour(new Date(), new Date()),
                    new Hour(new Date(), new Date()),
                    new Hour(new Date(), new Date()),
                    new Hour(new Date(), new Date()),
                    new Hour(new Date(), new Date()),
                    new Hour(new Date(), new Date()),
                    new Hour(new Date(), new Date()),   "231568123", "Rua principal", tipoDeComida));
                    */
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
            createRestaurant(new Restaurant("3", "0", "Casa dos bifes", new Hour(new Date(), new Date()),
                        new Hour(new Date(), new Date()),
                        new Hour(new Date(), new Date()),
                        new Hour(new Date(), new Date()),
                        new Hour(new Date(), new Date()),
                        new Hour(new Date(), new Date()),
                        new Hour(new Date(), new Date()),   "231568123", "Rua principal", new ArrayList<String>()));
            }
            createRestaurant(new Restaurant("4", "0", "Rei das Bifanas", new Hour(new Date(), new Date()),
                        new Hour(new Date(), new Date()),
                        new Hour(new Date(), new Date()),
                        new Hour(new Date(), new Date()),
                        new Hour(new Date(), new Date()),
                        new Hour(new Date(), new Date()),
                        new Hour(new Date(), new Date()),   "231568123", "Rua principal", new ArrayList<String>()));
            }
            ArrayList<String> tipoDeComida = new ArrayList<String>();
            tipoDeComida.add(new String("Bifanas"));
            createRestaurant(new Restaurant("5", "0", "Comes de tudo", new Hour(new Date(), new Date()),
                        new Hour(new Date(), new Date()),
                        new Hour(new Date(), new Date()),
                        new Hour(new Date(), new Date()),
                        new Hour(new Date(), new Date()),
                        new Hour(new Date(), new Date()),
                        new Hour(new Date(), new Date()),   "231568123", "Rua principal", tipoDeComida));
            }
        });*/
    }
    private void createRestaurant(Restaurant r) {
        SpotFood.child("restaurants").child(r.getIdRestaurant()).setValue(r);
    }

    private void searchRestaurant() {

        restaurant.clear();

        //Get restaurants reference
        final DatabaseReference restaurantsRef = this.SpotFood.child("restaurants");

        //add Listener
        restaurantsRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Cicle For that go through all the restaurants in firebase database
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Restaurant rest = postSnapshot.getValue(Restaurant.class);
                    restaurant.add(rest.getName());
                    searchText.setText(searchText.getText() + ", " + rest.getName());
                }

                //refresh ListView
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void searchRestaurantByNameOrTypeOfFood(final String nameOrTypeOfFood) {

        //Check if string is null or empty
        if (nameOrTypeOfFood == null || nameOrTypeOfFood.isEmpty()) {
            searchRestaurant();
            return;
        }

        //clear ListView
        restaurant.clear();

        //Get restaurants reference
        final DatabaseReference restaurantsRef = this.SpotFood.child("restaurants");

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
                                restaurant.add(rest.getName());
                                break;
                            }
                        }
                    }

                    //search by name if not find by type of food
                    if(!find){
                        if(rest.getName().toUpperCase().contains(nameOrTypeOfFood.toUpperCase())){
                            restaurant.add(rest.getName());
                        }
                    }
                }

                //refresh ListView
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    private boolean hasNetworkConnection(){
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    }

    class MyDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder .setTitle("Titulo da Janela")
                    .setMessage("Arq. Móveis")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setPositiveButton("0-2", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setNegativeButton("Cancelar",null);
            return builder.create();
            //return super.onCreateDialog(savedInstanceState);
        }
    }
}