package spotfood.spotfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.poili.spotfood.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private NumberPicker mMoursFridayClose;
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
    private Restaurant mRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        this.inicializeVariables();

        this.checkIntentResult();
    }

    private void inicializeVariables() {

        mSpotFoodDataBaseReference = FirebaseDatabase.getInstance().getReference();
        this.mRestaurantName = (TextView)findViewById(R.id.nameRestaurant);
        this.mLogoutButton = (Button)findViewById(R.id.loginButton);
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
        mMoursFridayClose = (NumberPicker)findViewById(R.id.hoursFridayClose);
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
        mMoursFridayClose.setMinValue(0);
        mMoursFridayClose.setMaxValue(23);
        mMoursFridayClose.setFormatter(new MyTwoDigitFormatter());
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
            fillDetails(intent);
        }
    }

    private void fillDetails(Intent intent){

        this.mRestaurantName.setText(intent.getStringExtra(RESTAURANT_NAME));
        this.mHoursMondayOpen.setValue(intent.getIntExtra(MONDAY_OPEN_HOURS, 0));
        this.mMinutesMondayOpen.setValue(intent.getIntExtra(MONDAY_OPEN_MINUTES, 0));
    }
}