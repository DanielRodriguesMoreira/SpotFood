package spotfood.spotfood;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.poili.spotfood.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
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
    private ImageView mImageMenu;
    private ImageView mImageOffers;
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
    private ImageButton mAddOffersButton;
    private String mUserID;
    private boolean mIsANewRestaurant;
    private String mRestaurantID;
    private Bitmap mMenuBitmap;
    private Bitmap mOffersBitmap;

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
        this.mDeleteHoursButton.setOnClickListener(new deleteRestaurantDetailsListener());
        this.mSaveConctactsButton = (ImageButton)findViewById(R.id.saveButtonContacts);
        this.mDeleteContactsButton = (ImageButton)findViewById(R.id.deleteButtonContacts);
        this.mDeleteContactsButton.setOnClickListener(new deleteRestaurantDetailsListener());
        this.mSaveLocationButton = (ImageButton)findViewById(R.id.saveButtonLocation);
        this.mDeleteLocationButton = (ImageButton)findViewById(R.id.deleteButtonLocation);
        this.mDeleteLocationButton.setOnClickListener(new deleteRestaurantDetailsListener());
        this.mSaveMenuButton = (ImageButton)findViewById(R.id.saveButtonMenu);
        this.mDeleteMenuButton = (ImageButton)findViewById(R.id.deleteButtonMenu);
        this.mDeleteMenuButton.setOnClickListener(new deleteRestaurantDetailsListener());
        this.mSaveOffersButton = (ImageButton)findViewById(R.id.saveButtonOffers);
        this.mDeleteOffersButton = (ImageButton)findViewById(R.id.deleteButtonOffers);
        this.mDeleteOffersButton.setOnClickListener(new deleteRestaurantDetailsListener());
        this.mAddMenuButton = (ImageButton)findViewById(R.id.addButtonMenu);
        this.mAddMenuButton.setOnClickListener(new addImageListener(new String("MENU")));
        this.mAddOffersButton = (ImageButton)findViewById(R.id.addButtonOffers);
        this.mAddOffersButton.setOnClickListener(new addImageListener(new String("OFFERS")));
        this.mSaveHoursButton.setOnClickListener(new saveRestaurantDetailsListener());
        this.mSaveConctactsButton.setOnClickListener(new saveRestaurantDetailsListener());
        this.mSaveLocationButton.setOnClickListener(new saveRestaurantDetailsListener());
        this.mSaveMenuButton.setOnClickListener(new saveRestaurantDetailsListener());
        this.mSaveOffersButton.setOnClickListener(new saveRestaurantDetailsListener());
        this.mImageMenu = (ImageView)findViewById(R.id.imageMenu);
        this.mImageOffers = (ImageView)findViewById(R.id.imageOffers);
        this.mMenuBitmap = null;
        this.mOffersBitmap = null;
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
        this.mAddOffersButton.setVisibility(View.INVISIBLE);
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

        String menu = intent.getStringExtra(MENU);
        if(menu != null && !menu.isEmpty()) {
            byte[] decodedString = Base64.decode(menu, Base64.DEFAULT);
            this.mMenuBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            this.mImageMenu.setImageBitmap(this.mMenuBitmap);
        }

        String offers = intent.getStringExtra(OFFERS);
        if(offers != null && !offers.isEmpty()) {
            byte[] decodedString = Base64.decode(offers, Base64.DEFAULT);
            this.mOffersBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            this.mImageOffers.setImageBitmap(this.mOffersBitmap);
        }
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

            //
            String encodeMenu = this.encodeBitmap(mMenuBitmap);
            String encodeOffers = this.encodeBitmap(mOffersBitmap);
            //

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
                    typeOfFoodList,
                    encodeMenu,
                    encodeOffers
            );

            mSpotFoodDataBaseReference.child("restaurants").
                    child(r.getIdRestaurant()).setValue(r);

            Toast.makeText(getApplicationContext(),
                    "Restaurant details successfully saved!", Toast.LENGTH_LONG).show();

            mIsANewRestaurant = false;
        }

        private String encodeBitmap(Bitmap bitmap) {

            ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
            byte[] byteArray;
            String encodeString = null;

            if(bitmap != null){
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bYtE);
                bitmap.recycle();
                byteArray = bYtE.toByteArray();
                encodeString = Base64.encodeToString(byteArray, Base64.DEFAULT);
            }

            return encodeString;
        }
    }

    class deleteRestaurantDetailsListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            confirmationDeleteDialog confirmationDialog = new confirmationDeleteDialog();
            confirmationDialog.show(getFragmentManager(), "TAG");
        }
    }

    /**
     * Fragment Dialog to show internet connection error
     */
    class confirmationDeleteDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Delete restaurant")
                    .setMessage("Are you sure you want to delete this restaurant?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(mIsANewRestaurant){
                                //Delete only account because the restaurant isn't created
                                mSpotFoodDataBaseReference.child("users").child(mUserID).removeValue();
                            }
                            else{
                                mSpotFoodDataBaseReference.child("restaurants").child(mRestaurantID)
                                        .removeValue();
                                mSpotFoodDataBaseReference.child("users").child(mUserID)
                                        .removeValue();
                            }

                            Toast.makeText(getApplicationContext(),
                                    "Account and restaurant were deleted successfully",
                                    Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplication(), InitialScreen.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Do nothing
                        }
                    });
            return builder.create();
        }
    }

    class addImageListener implements View.OnClickListener{

        private String name = null;

        public addImageListener(String name){
            this.name = name;
        }
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            if(this.name.equals("MENU")) {
                startActivityForResult(intent, 10);
            }
            else{
                startActivityForResult(intent, 20);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 10 && data != null && data.getData() != null){
            Uri uri = data.getData();
            if(uri != null){
                Cursor cursor = getContentResolver().query(uri,
                        new String[] {android.provider.MediaStore.Images.ImageColumns.DATA},
                        null, null, null);
                cursor.moveToFirst();
                final String imageFilePath = cursor.getString(0);
                cursor.close();

                this.mMenuBitmap = BitmapFactory.decodeFile(imageFilePath);
                this.mImageMenu.setImageBitmap(this.mMenuBitmap);
            }
        }
        if(requestCode == 20 && data != null && data.getData() != null){
            Uri uri = data.getData();
            if(uri != null){
                Cursor cursor = getContentResolver().query(uri,
                        new String[] {android.provider.MediaStore.Images.ImageColumns.DATA},
                        null, null, null);
                cursor.moveToFirst();
                final String imageFilePath = cursor.getString(0);
                cursor.close();

                this.mOffersBitmap = BitmapFactory.decodeFile(imageFilePath);
                this.mImageOffers.setImageBitmap(this.mOffersBitmap);
            }
        }
    }
}