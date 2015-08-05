/**
 * 
 */
package com.home.purecalendar;

import java.util.Calendar;
import java.util.List;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GooglePlayServicesUtil;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.extensions.android.http.AndroidHttp;
//import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.gson.GsonFactory;
//import com.google.api.client.util.ExponentialBackOff;
//import com.google.api.services.calendar.CalendarScopes;

/**
 * @author dancy
 *
 */
public class MonthlyCalendarActivity extends AppCompatActivity implements MonthFragment.MonthChangeListener, HolidayFragment.OnFragmentInteractionListener {
//  remote client
//    com.google.api.services.calendar.Calendar mService;
//
//    GoogleAccountCredential credential;
//    final HttpTransport transport = AndroidHttp.newCompatibleTransport();
//    final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    private static final String PREF_ACCOUNT_NAME = "accountName";
//    remote client
    //    private static final String[] SCOPES = { CalendarScopes.CALENDAR_READONLY };

    public static Calendar currentMonth;
	static int currentIndex = 0;
    private FrameLayout holidayContainer;
    private AppCompatActivity context;

	/**
	 * 
	 */
	public MonthlyCalendarActivity() {
		currentMonth = Calendar.getInstance();
		currentMonth.set(Calendar.DATE, 1);
        context = this;
	}

    @Override
    public void onMonthChanged(Calendar thisMonth) {
//        if (holidayContainer != null)
//            holidayContainer.setText("Happy "+String.format("%1$tB, %1$tY", thisMonth));
    }

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		setContentView(R.layout.calendar_month_pager);

        //create actionbar
        Toolbar actionBar = (Toolbar)findViewById(R.id.actionbar);
        if (actionBar != null) {
            actionBar.setLogo(R.drawable.ic_launcher);
            actionBar.setTitle(R.string.app_name);
            setSupportActionBar(actionBar);
        }

        //add holiday fragment
        holidayContainer = (FrameLayout)findViewById(R.id.holidayscontainer);
        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentByTag("holidayfragment") == null)
            fm.beginTransaction().add(R.id.holidayscontainer, HolidayFragment.newInstance("", ""), "holidayfragment").commit();

        //add month view
        int month;
        if (savedInstance != null && (month = savedInstance.getInt("currentmonth")) != 0) {
            currentMonth.set(Calendar.MONTH, month);
        }
        final MonthPagerAdapter pageAdapter = new MonthPagerAdapter(fm);
		final ViewPager pager = (ViewPager)this.findViewById(R.id.pager);
		pager.setAdapter(pageAdapter);
		pager.setCurrentItem(1); //the index for current month is 1

		pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentIndex = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {}

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    currentMonth.add(Calendar.MONTH, currentIndex - 1);
                    pager.setCurrentItem(1, false);//this does the trick
                    for (int i = 0; i < 3; i++) {
                        MonthFragment fragment = (MonthFragment) ((MonthPagerAdapter) pager.getAdapter()).instantiateItem(pager, i);
                        ViewGroup rootView = (ViewGroup) fragment.getView();
                        if (rootView != null) {
                            fragment.updateView(rootView);
                        }
                    }
                }
            }
        });

        // Initialize credentials and service object.
        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
        MyLog.d(1, "confidential: %s", settings.getString(PREF_ACCOUNT_NAME, null));
//    remote client
//        credential = GoogleAccountCredential.usingOAuth2(
//                getApplicationContext(), Arrays.asList(SCOPES))
//                .setBackOff(new ExponentialBackOff())
//                .setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME, null));
//
//        mService = new com.google.api.services.calendar.Calendar.Builder(
//                transport, jsonFactory, credential)
//                .setApplicationName("Google Calendar API Android Quickstart")
//                .build();
//
    }

    @Override
    protected void onResume() {
        if (isGooglePlayServicesAvailable()) {
            refreshResults();
        } else {
//            mStatusText.setText("Google Play Services required: " +
//                    "after installing, close and relaunch this app.");
        }

        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentmonth", currentMonth.get(Calendar.MONTH));
    }

    /**
     * Called when an activity launched here (specifically, AccountPicker
     * and authorization) exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     * @param requestCode code indicating which activity result is incoming.
     * @param resultCode code indicating the result of the incoming
     *     activity result.
     * @param data Intent (containing result data) returned by incoming
     *     activity result.
     */
    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    isGooglePlayServicesAvailable();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
//    remote client
//                        credential.setSelectedAccountName(accountName);
                        SharedPreferences settings =
                                getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.commit();
                    }
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Account unspecified.", Toast.LENGTH_LONG);
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode != RESULT_OK) {
                    chooseAccount();
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Attempt to get a set of data from the Google Calendar API to display. If the
     * email address isn't known yet, then call chooseAccount() method so the
     * user can pick an account.
     */
    private void refreshResults() {
//    remote client
//        if (credential.getSelectedAccountName() == null) {
//            chooseAccount();
//        } else {
////            if (isDeviceOnline()) {
////                remote client
////                new ApiAsyncTask(this).execute();
////            } else {
//                Toast.makeText(context, "No network connection available.", Toast.LENGTH_LONG);
////            }
//        }
    }

    /**
     * Clear any existing Google Calendar API data from the TextView and update
     * the header message; called from background threads and async tasks
     * that need to update the UI (in the UI thread).
     */
    public void clearResultsText() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "Retrieving data…", Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * Fill the data TextView with the given List of Strings; called from
     * background threads and async tasks that need to update the UI (in the
     * UI thread).
     * @param dataStrings a List of Strings to populate the main TextView with.
     */
    public void updateResultsText(final List<String> dataStrings) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (dataStrings == null) {
                    Toast.makeText(context, "Error retrieving data!", Toast.LENGTH_LONG).show();
                } else if (dataStrings.size() == 0) {
                    Toast.makeText(context, "No data found.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Data retrieved using" +
                            " the Google Calendar API:", Toast.LENGTH_LONG).show();
                    Toast.makeText(context, TextUtils.join("\n\n", dataStrings), Toast.LENGTH_LONG).show();
                    HolidayFragment fragment = (HolidayFragment)context.getSupportFragmentManager().findFragmentByTag("holidayfragment");
                    fragment.updateData();
                    MyLog.d(1, "%s", dataStrings);
                }
            }
        });
    }

    /**
     * Show a status message in the list header TextView; called from background
     * threads and async tasks that need to update the UI (in the UI thread).
     * @param message a String to display in the UI header TextView.
     */
    public void updateStatus(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * Starts an activity in Google Play Services so the user can pick an
     * account.
     */
    private void chooseAccount() {
//    remote client
//        startActivityForResult(
//                credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
    }

    /**
     * Checks whether the device currently has a network connection.
     * @return true if the device has a network connection, false otherwise.
     */
//    remote client
//    private boolean isDeviceOnline() {
//        ConnectivityManager connMgr =
//                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        return (networkInfo != null && networkInfo.isConnected());
//    }

    /**
     * Check that Google Play services APK is installed and up to date. Will
     * launch an error dialog for the user to update Google Play Services if
     * possible.
     * @return true if Google Play Services is available and up to
     *     date on this device; false otherwise.
     */
    private boolean isGooglePlayServicesAvailable() {
//        remote client
//        final int connectionStatusCode =
//                GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
//        if (GooglePlayServicesUtil.isUserRecoverableError(connectionStatusCode)) {
//            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
//            return false;
//        } else if (connectionStatusCode != ConnectionResult.SUCCESS ) {
//            return false;
//        }
        return true;
    }

    /**
     * Display an error dialog showing that Google Play Services is missing
     * or out of date.
     * @param connectionStatusCode code describing the presence (or lack of)
     *     Google Play Services on this device.
     */
    void showGooglePlayServicesAvailabilityErrorDialog(
//    remote client
            final int connectionStatusCode) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//    remote client
//                Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
//                        connectionStatusCode,
//                        MonthlyCalendarActivity.this,
//                        REQUEST_GOOGLE_PLAY_SERVICES);
//                dialog.show();
            }
        });
    }

    @Override
    public void onFragmentInteraction(String id) {
        MyLog.d(0, "onFragmentInteraction is called");
    }

    private static class MonthPagerAdapter extends FragmentPagerAdapter {

		public MonthPagerAdapter(FragmentManager fm) {
            super(fm);
		}

		@Override
		public Fragment getItem(int idx) {
            MonthFragment fragment = MonthFragment.newInstance(idx);
            return fragment;
		}

		@Override
		public int getCount() {
			return 3;
		}
		
	}
	
}
