package spotfood.spotfood;

import android.app.Activity;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.TabHost;

import com.example.poili.spotfood.R;

public class Details extends Activity {

    TabHost tabHost;

    NumberPicker hoursMondayOpen;
    NumberPicker minutesMondayOpen;
    NumberPicker hoursMondayClose;
    NumberPicker minutesMondayClose;
    NumberPicker hoursTuesdayOpen;
    NumberPicker minutesTuesdayOpen;
    NumberPicker hoursTuesdayClose;
    NumberPicker minutesTuesdayClose;
    NumberPicker hoursWednesdayOpen;
    NumberPicker minutesWednesdayOpen;
    NumberPicker hoursWednesdayClose;
    NumberPicker minutesWednesdayClose;
    NumberPicker hoursThursdayOpen;
    NumberPicker minutesThursdayOpen;
    NumberPicker hoursThursdayClose;
    NumberPicker minutesThursdayClose;
    NumberPicker hoursFridayOpen;
    NumberPicker minutesFridayOpen;
    NumberPicker hoursFridayClose;
    NumberPicker minutesFridayClose;
    NumberPicker hoursSaturdayOpen;
    NumberPicker minutesSaturdayOpen;
    NumberPicker hoursSaturdayClose;
    NumberPicker minutesSaturdayClose;
    NumberPicker hoursSundayOpen;
    NumberPicker minutesSundayOpen;
    NumberPicker hoursSundayClose;
    NumberPicker minutesSundayClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

      //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.openHours);
        spec.setIndicator("Tab One");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.contacts);
        spec.setIndicator("Tab Two");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.location);
        spec.setIndicator("Tab Three");
        host.addTab(spec);

        //Tab 4
        spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.menu);
        spec.setIndicator("Tab Three");
        host.addTab(spec);
        //Tab 5

        spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.offers);
        spec.setIndicator("Tab Three");
        host.addTab(spec);

        hoursMondayOpen = (NumberPicker)findViewById(R.id.hoursMondayOpen);
        minutesMondayOpen = (NumberPicker)findViewById(R.id.minutesMondayOpen);
        hoursMondayClose = (NumberPicker)findViewById(R.id.hoursMondayClose);
        minutesMondayClose = (NumberPicker)findViewById(R.id.minutesMondayClose);

        hoursTuesdayOpen = (NumberPicker)findViewById(R.id.hoursTuesdayOpen);
        minutesTuesdayOpen = (NumberPicker)findViewById(R.id.minutesTuesdayOpen);
        hoursTuesdayClose = (NumberPicker)findViewById(R.id.hoursTuesdayClose);
        minutesTuesdayClose = (NumberPicker)findViewById(R.id.minutesTuesdayClose);

        hoursWednesdayOpen = (NumberPicker)findViewById(R.id.hoursWednesdayOpen);
        minutesWednesdayOpen = (NumberPicker)findViewById(R.id.minutesWednesdayOpen);
        hoursWednesdayClose = (NumberPicker)findViewById(R.id.hoursWednesdayClose);
        minutesWednesdayClose = (NumberPicker)findViewById(R.id.minutesWednesdayClose);

        hoursThursdayOpen = (NumberPicker)findViewById(R.id.hoursThursdayOpen);
        minutesThursdayOpen = (NumberPicker)findViewById(R.id.minutesThursdayOpen);
        hoursThursdayClose = (NumberPicker)findViewById(R.id.hoursThursdayClose);
        minutesThursdayClose = (NumberPicker)findViewById(R.id.minutesThursdayClose);

        hoursFridayOpen = (NumberPicker)findViewById(R.id.hoursFridayOpen);
        minutesFridayOpen = (NumberPicker)findViewById(R.id.minutesFridayOpen);
        hoursFridayClose = (NumberPicker)findViewById(R.id.hoursFridayClose);
        minutesFridayClose = (NumberPicker)findViewById(R.id.minutesFridayClose);

        hoursSaturdayOpen = (NumberPicker)findViewById(R.id.hoursSaturdayOpen);
        minutesSaturdayOpen = (NumberPicker)findViewById(R.id.minutesSaturdayOpen);
        hoursSaturdayClose = (NumberPicker)findViewById(R.id.hoursSaturdayClose);
        minutesSaturdayClose = (NumberPicker)findViewById(R.id.minutesSaturdayClose);

        hoursSundayOpen = (NumberPicker)findViewById(R.id.hoursSundayOpen);
        minutesSundayOpen = (NumberPicker)findViewById(R.id.minutesSundayOpen);
        hoursSundayClose = (NumberPicker)findViewById(R.id.hoursSundayClose);
        minutesSundayClose = (NumberPicker)findViewById(R.id.minutesSundayClose);

        hoursMondayOpen.setMinValue(0);
        hoursMondayOpen.setMaxValue(23);
        hoursMondayOpen.setFormatter(new Details.MyTwoDigitFormatter());
        minutesMondayOpen.setMinValue(0);
        minutesMondayOpen.setMaxValue(59);
        minutesMondayOpen.setFormatter(new Details.MyTwoDigitFormatter());
        hoursMondayClose.setMinValue(0);
        hoursMondayClose.setMaxValue(23);
        hoursMondayClose.setFormatter(new Details.MyTwoDigitFormatter());
        minutesMondayClose.setMinValue(0);
        minutesMondayClose.setMaxValue(59);
        minutesMondayClose.setFormatter(new Details.MyTwoDigitFormatter());

        hoursTuesdayOpen.setMinValue(0);
        hoursTuesdayOpen.setMaxValue(23);
        hoursTuesdayOpen.setFormatter(new Details.MyTwoDigitFormatter());
        minutesTuesdayOpen.setMinValue(0);
        minutesTuesdayOpen.setMaxValue(59);
        minutesTuesdayOpen.setFormatter(new Details.MyTwoDigitFormatter());
        hoursTuesdayClose.setMinValue(0);
        hoursTuesdayClose.setMaxValue(23);
        hoursTuesdayClose.setFormatter(new Details.MyTwoDigitFormatter());
        minutesTuesdayClose.setMinValue(0);
        minutesTuesdayClose.setMaxValue(59);
        minutesTuesdayClose.setFormatter(new Details.MyTwoDigitFormatter());

        hoursWednesdayOpen.setMinValue(0);
        hoursWednesdayOpen.setMaxValue(23);
        hoursWednesdayOpen.setFormatter(new Details.MyTwoDigitFormatter());
        minutesWednesdayOpen.setMinValue(0);
        minutesWednesdayOpen.setMaxValue(59);
        minutesWednesdayOpen.setFormatter(new Details.MyTwoDigitFormatter());
        hoursWednesdayClose.setMinValue(0);
        hoursWednesdayClose.setMaxValue(23);
        hoursWednesdayClose.setFormatter(new Details.MyTwoDigitFormatter());
        minutesWednesdayClose.setMinValue(0);
        minutesWednesdayClose.setMaxValue(59);
        minutesWednesdayClose.setFormatter(new Details.MyTwoDigitFormatter());

        hoursThursdayOpen.setMinValue(0);
        hoursThursdayOpen.setMaxValue(23);
        hoursThursdayOpen.setFormatter(new Details.MyTwoDigitFormatter());
        minutesThursdayOpen.setMinValue(0);
        minutesThursdayOpen.setMaxValue(59);
        minutesThursdayOpen.setFormatter(new Details.MyTwoDigitFormatter());
        hoursThursdayClose.setMinValue(0);
        hoursThursdayClose.setMaxValue(23);
        hoursThursdayClose.setFormatter(new Details.MyTwoDigitFormatter());
        minutesThursdayClose.setMinValue(0);
        minutesThursdayClose.setMaxValue(59);
        minutesThursdayClose.setFormatter(new Details.MyTwoDigitFormatter());

        hoursFridayOpen.setMinValue(0);
        hoursFridayOpen.setMaxValue(23);
        hoursFridayOpen.setFormatter(new Details.MyTwoDigitFormatter());
        minutesFridayOpen.setMinValue(0);
        minutesFridayOpen.setMaxValue(59);
        minutesFridayOpen.setFormatter(new Details.MyTwoDigitFormatter());
        hoursFridayClose.setMinValue(0);
        hoursFridayClose.setMaxValue(23);
        hoursFridayClose.setFormatter(new Details.MyTwoDigitFormatter());
        minutesFridayClose.setMinValue(0);
        minutesFridayClose.setMaxValue(59);
        minutesFridayClose.setFormatter(new Details.MyTwoDigitFormatter());

        hoursSaturdayOpen.setMinValue(0);
        hoursSaturdayOpen.setMaxValue(23);
        hoursSaturdayOpen.setFormatter(new Details.MyTwoDigitFormatter());
        minutesSaturdayOpen.setMinValue(0);
        minutesSaturdayOpen.setMaxValue(59);
        minutesSaturdayOpen.setFormatter(new Details.MyTwoDigitFormatter());
        hoursSaturdayClose.setMinValue(0);
        hoursSaturdayClose.setMaxValue(23);
        hoursSaturdayClose.setFormatter(new Details.MyTwoDigitFormatter());
        minutesSaturdayClose.setMinValue(0);
        minutesSaturdayClose.setMaxValue(59);
        minutesSaturdayClose.setFormatter(new Details.MyTwoDigitFormatter());

        hoursSundayOpen.setMinValue(0);
        hoursSundayOpen.setMaxValue(23);
        hoursSundayOpen.setFormatter(new Details.MyTwoDigitFormatter());
        minutesSundayOpen.setMinValue(0);
        minutesSundayOpen.setMaxValue(59);
        minutesSundayOpen.setFormatter(new Details.MyTwoDigitFormatter());
        hoursSundayClose.setMinValue(0);
        hoursSundayClose.setMaxValue(23);
        hoursSundayClose.setFormatter(new Details.MyTwoDigitFormatter());
        minutesSundayClose.setMinValue(0);
        minutesSundayClose.setMaxValue(59);
        minutesSundayClose.setFormatter(new Details.MyTwoDigitFormatter());
    }

    public class MyTwoDigitFormatter implements NumberPicker.Formatter {
        public String format(int value) {
            return String.format("%02d", value);
        }
    }
}