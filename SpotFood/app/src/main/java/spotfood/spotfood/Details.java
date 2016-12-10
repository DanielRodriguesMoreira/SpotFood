package spotfood.spotfood;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.poili.spotfood.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;

public class Details extends Activity implements Constants{

    private DatabaseReference mSpotFoodDataBaseReference;
    private TabHost tabHost;
    private NumberPicker mHoursMondayOpen;
    private NumberPicker mMinutesMondayOpen;
    private NumberPicker mHoursMondayClose;
    private NumberPicker mMinutesMondayClose;
    private NumberPicker mHoursTuesdayOpen;
    private NumberPicker mMinutesTuesdayOpen;
    private NumberPicker mHoursTuesdayClose;
    private NumberPicker mMinutesTuesdayClose;
    private NumberPicker mHoursWednesdayOpen;
    private NumberPicker mMinutesWednesdayOpen;
    private NumberPicker mHoursWednesdayClose;
    private NumberPicker mMinutesWednesdayClose;
    private NumberPicker mHoursThursdayOpen;
    private NumberPicker mMinutesThursdayOpen;
    private NumberPicker mHoursThursdayClose;
    private NumberPicker mMinutesThursdayClose;
    private NumberPicker mHoursFridayOpen;
    private NumberPicker mMinutesFridayOpen;
    private NumberPicker mHoursFridayClose;
    private NumberPicker mMinutesFridayClose;
    private NumberPicker mHoursSaturdayOpen;
    private NumberPicker mMinutesSaturdayOpen;
    private NumberPicker mHoursSaturdayClose;
    private NumberPicker mMinutesSaturdayClose;
    private NumberPicker mHoursSundayOpen;
    private NumberPicker mMinutesSundayOpen;
    private NumberPicker mHoursSundayClose;
    private NumberPicker mMinutesSundayClose;
    private Button mLogoutButton;
    private TextView mRestaurantName;
    private TextView mContactsText;
    private TextView mLocationText;
    private TextView mTypeOfFoodText;
    private ImageButton mSaveHoursButton;
    private ImageButton mDeleteHoursButton;
    private ImageButton mSaveConctactsButton;
    private ImageButton mDeleteContactsButton;
    private ImageButton mSaveLocationButton;
    private ImageButton mDeleteLocationButton;
    private ImageButton mSaveMenuButton;
    private ImageButton mDeleteMenuButton;
    private ImageButton mSaveOffersButton;
    private ImageButton mDeleteOffersButton;
    private ImageButton mAddMenuButton;
    private String mUserID;
    private boolean mIsANewRestaurant;
    private String mRestaurantID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.details);

        this.inicializeVariables();

        this.checkIntentResult();
    }

    private void inicializeVariables() {

        mSpotFoodDataBaseReference = FirebaseDatabase.getInstance().getReference();
        this.mIsANewRestaurant = false;
        this.mRestaurantName = (TextView)findViewById(R.id.nameRestaurant);
        this.mRestaurantName.setHintTextColor(Color.WHITE);
        this.mContactsText = (TextView)findViewById(R.id.editTextContacts);
        this.mTypeOfFoodText = (TextView)findViewById(R.id.typeOfFood);
        this.mLocationText = (TextView)findViewById(R.id.locationText);
        this.mLogoutButton = (Button)findViewById(R.id.loginButton);
        this.mSaveHoursButton = (ImageButton) findViewById(R.id.saveButtonHours);
        this.mSaveHoursButton.setOnClickListener(new saveRestaurantDetailsListener());
        this.mDeleteHoursButton = (ImageButton) findViewById(R.id.deleteButton);
        this.mSaveConctactsButton = (ImageButton)findViewById(R.id.saveButtonContacts);
        this.mDeleteContactsButton = (ImageButton)findViewById(R.id.deleteButtonContacts);
        this.mSaveLocationButton = (ImageButton)findViewById(R.id.saveButtonLocation);
        this.mDeleteLocationButton = (ImageButton)findViewById(R.id.deleteButtonLocation);
        this.mSaveMenuButton = (ImageButton)findViewById(R.id.saveButtonMenu);
        this.mDeleteMenuButton = (ImageButton)findViewById(R.id.deleteButtonMenu);
        this.mSaveOffersButton = (ImageButton)findViewById(R.id.saveButtonOffers);
        this.mDeleteOffersButton = (ImageButton)findViewById(R.id.deleteButtonOffers);
        this.mAddMenuButton = (ImageButton)findViewById(R.id.addButtonMenu);
        this.mSaveHoursButton.setOnClickListener(new saveRestaurantDetailsListener());
        this.mSaveConctactsButton.setOnClickListener(new saveRestaurantDetailsListener());
        this.mSaveLocationButton.setOnClickListener(new saveRestaurantDetailsListener());
        this.mSaveMenuButton.setOnClickListener(new saveRestaurantDetailsListener());
        this.mSaveOffersButton.setOnClickListener(new saveRestaurantDetailsListener());
        this.mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), InitialScreen.class);
                startActivity(intent);
                finish();
            }
        });
        tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        //Tab 1
        TabHost.TabSpec spec = tabHost.newTabSpec("Tab One");
        spec.setContent(R.id.openHours);
        spec.setIndicator("", getResources().getDrawable(R.mipmap.open_hours_icon));
        tabHost.addTab(spec);

        //Tab 2
        spec = tabHost.newTabSpec("Tab Two");
        spec.setContent(R.id.contacts);
        spec.setIndicator("", getResources().getDrawable(R.mipmap.contacts_icon));
        tabHost.addTab(spec);

        //Tab 3
        spec = tabHost.newTabSpec("Tab Three");
        spec.setContent(R.id.location);
        spec.setIndicator("", getResources().getDrawable(R.mipmap.location_icon));
        tabHost.addTab(spec);

        //Tab 4
        spec = tabHost.newTabSpec("Tab Three");
        spec.setContent(R.id.menu);
        spec.setIndicator("", getResources().getDrawable(R.mipmap.menu_icon));
        tabHost.addTab(spec);

        //Tab 5
        spec = tabHost.newTabSpec("Tab Three");
        spec.setContent(R.id.offers);
        spec.setIndicator("", getResources().getDrawable(R.mipmap.offers_icon));
        tabHost.addTab(spec);

        mHoursMondayOpen = (NumberPicker)findViewById(R.id.hoursMondayOpen);
        mMinutesMondayOpen = (NumberPicker)findViewById(R.id.minutesMondayOpen);
        mHoursMondayClose = (NumberPicker)findViewById(R.id.hoursMondayClose);
        mMinutesMondayClose = (NumberPicker)findViewById(R.id.minutesMondayClose);

        mHoursTuesdayOpen = (NumberPicker)findViewById(R.id.hoursTuesdayOpen);
        mMinutesTuesdayOpen = (NumberPicker)findViewById(R.id.minutesTuesdayOpen);
        mHoursTuesdayClose = (NumberPicker)findViewById(R.id.hoursTuesdayClose);
        mMinutesTuesdayClose = (NumberPicker)findViewById(R.id.minutesTuesdayClose);

        mHoursWednesdayOpen = (NumberPicker)findViewById(R.id.hoursWednesdayOpen);
        mMinutesWednesdayOpen = (NumberPicker)findViewById(R.id.minutesWednesdayOpen);
        mHoursWednesdayClose = (NumberPicker)findViewById(R.id.hoursWednesdayClose);
        mMinutesWednesdayClose = (NumberPicker)findViewById(R.id.minutesWednesdayClose);

        mHoursThursdayOpen = (NumberPicker)findViewById(R.id.hoursThursdayOpen);
        mMinutesThursdayOpen = (NumberPicker)findViewById(R.id.minutesThursdayOpen);
        mHoursThursdayClose = (NumberPicker)findViewById(R.id.hoursThursdayClose);
        mMinutesThursdayClose = (NumberPicker)findViewById(R.id.minutesThursdayClose);

        mHoursFridayOpen = (NumberPicker)findViewById(R.id.hoursFridayOpen);
        mMinutesFridayOpen = (NumberPicker)findViewById(R.id.minutesFridayOpen);
        mHoursFridayClose = (NumberPicker)findViewById(R.id.hoursFridayClose);
        mMinutesFridayClose = (NumberPicker)findViewById(R.id.minutesFridayClose);

        mHoursSaturdayOpen = (NumberPicker)findViewById(R.id.hoursSaturdayOpen);
        mMinutesSaturdayOpen = (NumberPicker)findViewById(R.id.minutesSaturdayOpen);
        mHoursSaturdayClose = (NumberPicker)findViewById(R.id.hoursSaturdayClose);
        mMinutesSaturdayClose = (NumberPicker)findViewById(R.id.minutesSaturdayClose);

        mHoursSundayOpen = (NumberPicker)findViewById(R.id.hoursSundayOpen);
        mMinutesSundayOpen = (NumberPicker)findViewById(R.id.minutesSundayOpen);
        mHoursSundayClose = (NumberPicker)findViewById(R.id.hoursSundayClose);
        mMinutesSundayClose = (NumberPicker)findViewById(R.id.minutesSundayClose);

        mHoursMondayOpen.setMinValue(0);
        mHoursMondayOpen.setMaxValue(23);
        mHoursMondayOpen.setFormatter(new MyTwoDigitFormatter());
        mMinutesMondayOpen.setMinValue(0);
        mMinutesMondayOpen.setMaxValue(59);
        mMinutesMondayOpen.setFormatter(new MyTwoDigitFormatter());
        mHoursMondayClose.setMinValue(0);
        mHoursMondayClose.setMaxValue(23);
        mHoursMondayClose.setFormatter(new MyTwoDigitFormatter());
        mMinutesMondayClose.setMinValue(0);
        mMinutesMondayClose.setMaxValue(59);
        mMinutesMondayClose.setFormatter(new MyTwoDigitFormatter());

        mHoursTuesdayOpen.setMinValue(0);
        mHoursTuesdayOpen.setMaxValue(23);
        mHoursTuesdayOpen.setFormatter(new MyTwoDigitFormatter());
        mMinutesTuesdayOpen.setMinValue(0);
        mMinutesTuesdayOpen.setMaxValue(59);
        mMinutesTuesdayOpen.setFormatter(new MyTwoDigitFormatter());
        mHoursTuesdayClose.setMinValue(0);
        mHoursTuesdayClose.setMaxValue(23);
        mHoursTuesdayClose.setFormatter(new MyTwoDigitFormatter());
        mMinutesTuesdayClose.setMinValue(0);
        mMinutesTuesdayClose.setMaxValue(59);
        mMinutesTuesdayClose.setFormatter(new MyTwoDigitFormatter());

        mHoursWednesdayOpen.setMinValue(0);
        mHoursWednesdayOpen.setMaxValue(23);
        mHoursWednesdayOpen.setFormatter(new MyTwoDigitFormatter());
        mMinutesWednesdayOpen.setMinValue(0);
        mMinutesWednesdayOpen.setMaxValue(59);
        mMinutesWednesdayOpen.setFormatter(new MyTwoDigitFormatter());
        mHoursWednesdayClose.setMinValue(0);
        mHoursWednesdayClose.setMaxValue(23);
        mHoursWednesdayClose.setFormatter(new MyTwoDigitFormatter());
        mMinutesWednesdayClose.setMinValue(0);
        mMinutesWednesdayClose.setMaxValue(59);
        mMinutesWednesdayClose.setFormatter(new MyTwoDigitFormatter());

        mHoursThursdayOpen.setMinValue(0);
        mHoursThursdayOpen.setMaxValue(23);
        mHoursThursdayOpen.setFormatter(new MyTwoDigitFormatter());
        mMinutesThursdayOpen.setMinValue(0);
        mMinutesThursdayOpen.setMaxValue(59);
        mMinutesThursdayOpen.setFormatter(new MyTwoDigitFormatter());
        mHoursThursdayClose.setMinValue(0);
        mHoursThursdayClose.setMaxValue(23);
        mHoursThursdayClose.setFormatter(new MyTwoDigitFormatter());
        mMinutesThursdayClose.setMinValue(0);
        mMinutesThursdayClose.setMaxValue(59);
        mMinutesThursdayClose.setFormatter(new MyTwoDigitFormatter());

        mHoursFridayOpen.setMinValue(0);
        mHoursFridayOpen.setMaxValue(23);
        mHoursFridayOpen.setFormatter(new MyTwoDigitFormatter());
        mMinutesFridayOpen.setMinValue(0);
        mMinutesFridayOpen.setMaxValue(59);
        mMinutesFridayOpen.setFormatter(new MyTwoDigitFormatter());
        mHoursFridayClose.setMinValue(0);
        mHoursFridayClose.setMaxValue(23);
        mHoursFridayClose.setFormatter(new MyTwoDigitFormatter());
        mMinutesFridayClose.setMinValue(0);
        mMinutesFridayClose.setMaxValue(59);
        mMinutesFridayClose.setFormatter(new MyTwoDigitFormatter());

        mHoursSaturdayOpen.setMinValue(0);
        mHoursSaturdayOpen.setMaxValue(23);
        mHoursSaturdayOpen.setFormatter(new MyTwoDigitFormatter());
        mMinutesSaturdayOpen.setMinValue(0);
        mMinutesSaturdayOpen.setMaxValue(59);
        mMinutesSaturdayOpen.setFormatter(new MyTwoDigitFormatter());
        mHoursSaturdayClose.setMinValue(0);
        mHoursSaturdayClose.setMaxValue(23);
        mHoursSaturdayClose.setFormatter(new MyTwoDigitFormatter());
        mMinutesSaturdayClose.setMinValue(0);
        mMinutesSaturdayClose.setMaxValue(59);
        mMinutesSaturdayClose.setFormatter(new MyTwoDigitFormatter());

        mHoursSundayOpen.setMinValue(0);
        mHoursSundayOpen.setMaxValue(23);
        mHoursSundayOpen.setFormatter(new MyTwoDigitFormatter());
        mMinutesSundayOpen.setMinValue(0);
        mMinutesSundayOpen.setMaxValue(59);
        mMinutesSundayOpen.setFormatter(new MyTwoDigitFormatter());
        mHoursSundayClose.setMinValue(0);
        mHoursSundayClose.setMaxValue(23);
        mHoursSundayClose.setFormatter(new MyTwoDigitFormatter());
        mMinutesSundayClose.setMinValue(0);
        mMinutesSundayClose.setMaxValue(59);
        mMinutesSundayClose.setFormatter(new MyTwoDigitFormatter());
    }

    class MyTwoDigitFormatter implements NumberPicker.Formatter {
        public String format(int value) {
            return String.format("%02d", value);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplication(), InitialScreen.class);
        startActivity(intent);
        finish();
    }

    /** Check if this class was called by an intent */
    private void checkIntentResult() {
        Intent intent = getIntent();
        boolean onlyToShow = intent.getBooleanExtra(ONLYTOSHOW, false);

        if(onlyToShow){
            this.disableComponents();
        }

        this.fillDetails(intent);
        this.mUserID = intent.getStringExtra(USER_ID);
        this.mRestaurantID = intent.getStringExtra(RESTAURANT_ID);
        this.mIsANewRestaurant = intent.getBooleanExtra(NEWRESTAURANT, false);

        /*
        if(onlyToShow){
            this.disableComponents();
            this.fillDetails(intent);
        }
        else{
            this.mUserID = intent.getStringExtra(USER_ID);
            this.mRestaurantID = intent.getStringExtra(RESTAURANT_ID);
            this.mIsANewRestaurant = intent.getBooleanExtra(NEWRESTAURANT, false);
            this.fillDetails(intent);
        }
        */
    }

    private void disableComponents() {
        this.mLogoutButton.setVisibility(View.INVISIBLE);
        this.mSaveHoursButton.setVisibility(View.INVISIBLE);
        this.mDeleteHoursButton.setVisibility(View.INVISIBLE);
        this.mSaveConctactsButton.setVisibility(View.INVISIBLE);
        this.mDeleteContactsButton.setVisibility(View.INVISIBLE);
        this.mSaveLocationButton.setVisibility(View.INVISIBLE);
        this.mDeleteLocationButton.setVisibility(View.INVISIBLE);
        this.mSaveMenuButton.setVisibility(View.INVISIBLE);
        this.mDeleteMenuButton.setVisibility(View.INVISIBLE);
        this.mSaveOffersButton.setVisibility(View.INVISIBLE);
        this.mDeleteOffersButton.setVisibility(View.INVISIBLE);
        this.mAddMenuButton.setVisibility(View.INVISIBLE);
        this.mRestaurantName.setEnabled(false);
        this.mHoursMondayOpen.setEnabled(false);
        this.mMinutesMondayOpen.setEnabled(false);
        this.mHoursMondayClose.setEnabled(false);
        this.mMinutesMondayClose.setEnabled(false);
        this.mHoursTuesdayOpen.setEnabled(false);
        this.mMinutesTuesdayOpen.setEnabled(false);
        this.mHoursTuesdayClose.setEnabled(false);
        this.mMinutesTuesdayClose.setEnabled(false);
        this.mHoursWednesdayOpen.setEnabled(false);
        this.mMinutesWednesdayOpen.setEnabled(false);
        this.mHoursWednesdayClose.setEnabled(false);
        this.mMinutesWednesdayClose.setEnabled(false);
        this.mHoursThursdayOpen.setEnabled(false);
        this.mMinutesThursdayOpen.setEnabled(false);
        this.mHoursThursdayClose.setEnabled(false);
        this.mMinutesThursdayClose.setEnabled(false);
        this.mHoursFridayOpen.setEnabled(false);
        this.mMinutesFridayOpen.setEnabled(false);
        this.mHoursFridayClose.setEnabled(false);
        this.mMinutesFridayClose.setEnabled(false);
        this.mHoursSaturdayOpen.setEnabled(false);
        this.mMinutesSaturdayOpen.setEnabled(false);
        this.mHoursSaturdayClose.setEnabled(false);
        this.mMinutesSaturdayClose.setEnabled(false);
        this.mHoursSundayOpen.setEnabled(false);
        this.mMinutesSundayOpen.setEnabled(false);
        this.mHoursSundayClose.setEnabled(false);
        this.mMinutesSundayClose.setEnabled(false);
        this.mContactsText.setEnabled(false);
        this.mLocationText.setEnabled(false);
        this.mTypeOfFoodText.setEnabled(false);
    }

    private void fillDetails(Intent intent){

        this.mRestaurantName.setText(intent.getStringExtra(RESTAURANT_NAME));
        this.mHoursMondayOpen.setValue(intent.getIntExtra(MONDAY_OPEN_HOURS, 0));
        this.mMinutesMondayOpen.setValue(intent.getIntExtra(MONDAY_OPEN_MINUTES, 0));
        this.mHoursTuesdayOpen.setValue(intent.getIntExtra(TUESDAY_OPEN_HOURS, 0));
        this.mMinutesTuesdayOpen.setValue(intent.getIntExtra(TUESDAY_OPEN_MINUTES, 0));
        this.mHoursWednesdayOpen.setValue(intent.getIntExtra(WEDNESDAY_OPEN_HOURS, 0));
        this.mMinutesWednesdayOpen.setValue(intent.getIntExtra(WEDNESDAY_OPEN_MINUTES, 0));
        this.mHoursThursdayOpen.setValue(intent.getIntExtra(THURSDAY_OPEN_HOURS, 0));
        this.mMinutesThursdayOpen.setValue(intent.getIntExtra(THURSDAY_OPEN_MINUTES, 0));
        this.mHoursFridayOpen.setValue(intent.getIntExtra(FRIDAY_OPEN_HOURS, 0));
        this.mMinutesFridayOpen.setValue(intent.getIntExtra(FRIDAY_OPEN_MINUTES, 0));
        this.mHoursSaturdayOpen.setValue(intent.getIntExtra(SATURDAY_OPEN_HOURS, 0));
        this.mMinutesSaturdayOpen.setValue(intent.getIntExtra(SATURDAY_OPEN_MINUTES, 0));
        this.mHoursSundayOpen.setValue(intent.getIntExtra(SUNDAY_OPEN_HOURS, 0));
        this.mMinutesSundayOpen.setValue(intent.getIntExtra(SUNDAY_OPEN_MINUTES, 0));
        this.mHoursMondayClose.setValue(intent.getIntExtra(MONDAY_CLOSE_HOURS, 0));
        this.mMinutesMondayClose.setValue(intent.getIntExtra(MONDAY_CLOSE_MINUTES, 0));
        this.mHoursTuesdayClose.setValue(intent.getIntExtra(TUESDAY_CLOSE_HOURS, 0));
        this.mMinutesTuesdayClose.setValue(intent.getIntExtra(TUESDAY_CLOSE_MINUTES, 0));
        this.mHoursWednesdayClose.setValue(intent.getIntExtra(WEDNESDAY_CLOSE_HOURS, 0));
        this.mMinutesWednesdayClose.setValue(intent.getIntExtra(WEDNESDAY_CLOSE_MINUTES, 0));
        this.mHoursThursdayClose.setValue(intent.getIntExtra(THURSDAY_CLOSE_HOURS, 0));
        this.mMinutesThursdayClose.setValue(intent.getIntExtra(THURSDAY_CLOSE_MINUTES, 0));
        this.mHoursFridayClose.setValue(intent.getIntExtra(FRIDAY_CLOSE_HOURS, 0));
        this.mMinutesFridayClose.setValue(intent.getIntExtra(FRIDAY_CLOSE_MINUTES, 0));
        this.mHoursSaturdayClose.setValue(intent.getIntExtra(SATURDAY_CLOSE_HOURS, 0));
        this.mMinutesSaturdayClose.setValue(intent.getIntExtra(SATURDAY_CLOSE_MINUTES, 0));
        this.mHoursSundayClose.setValue(intent.getIntExtra(SUNDAY_CLOSE_HOURS, 0));
        this.mMinutesSundayClose.setValue(intent.getIntExtra(SUNDAY_CLOSE_MINUTES, 0));
        this.mLocationText.setText(intent.getStringExtra(LOCATION));
        this.mContactsText.setText(intent.getStringExtra(CONTACTS));
        this.mTypeOfFoodText.setText(intent.getStringExtra(TYPE_OF_FOOD));
    }

    class saveRestaurantDetailsListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            List<String> typeOfFoodList = new ArrayList<String>();
            StringTokenizer tokenizer = new StringTokenizer(mTypeOfFoodText.getText().toString(), ",;", false);
            String restaurantID;
            while(tokenizer.hasMoreTokens()){
                String token = tokenizer.nextToken();
                typeOfFoodList.add(token);
            }

            if(mIsANewRestaurant){
                restaurantID = UUID.randomUUID().toString();
            }
            else{
                restaurantID = mRestaurantID;
            }

            Restaurant r = new Restaurant(
                    restaurantID,
                    mUserID,
                    mRestaurantName.getText().toString(),
                    new Hour(mHoursMondayOpen.getValue(), mMinutesMondayOpen.getValue(),
                            mHoursMondayClose.getValue(), mMinutesMondayClose.getValue()),
                    new Hour(mHoursTuesdayOpen.getValue(), mMinutesTuesdayOpen.getValue(),
                            mHoursTuesdayClose.getValue(), mMinutesTuesdayClose.getValue()),
                    new Hour(mHoursWednesdayOpen.getValue(), mMinutesWednesdayOpen.getValue(),
                            mHoursWednesdayClose.getValue(), mMinutesWednesdayClose.getValue()),
                    new Hour(mHoursThursdayOpen.getValue(), mMinutesThursdayOpen.getValue(),
                            mHoursThursdayClose.getValue(), mMinutesThursdayClose.getValue()),
                    new Hour(mHoursFridayOpen.getValue(), mMinutesFridayOpen.getValue(),
                            mHoursFridayClose.getValue(), mMinutesFridayClose.getValue()),
                    new Hour(mHoursSaturdayOpen.getValue(), mMinutesSaturdayOpen.getValue(),
                            mHoursSaturdayClose.getValue(), mMinutesSaturdayClose.getValue()),
                    new Hour(mHoursSundayOpen.getValue(), mMinutesSundayOpen.getValue(),
                            mHoursSundayClose.getValue(), mMinutesSundayClose.getValue()),
                    mContactsText.getText().toString(),
                    mLocationText.getText().toString(),
                    typeOfFoodList
            );

            mSpotFoodDataBaseReference.child("restaurants").
                    child(r.getIdRestaurant()).setValue(r);

            Toast.makeText(getApplicationContext(),
                    "Restaurant details successfully saved!", Toast.LENGTH_LONG).show();
        }
    }
}