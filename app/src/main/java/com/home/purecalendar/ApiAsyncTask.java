package com.home.purecalendar;

import android.os.AsyncTask;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.util.DateTime;

import com.google.api.services.calendar.model.*;
import com.home.purecalendar.holiday.HolidayContent;

import java.io.IOException;
import java.util.*;
import java.util.Calendar;

/**
 /**
 * An asynchronous task that handles the Google Calendar API call.
 * Placing the API calls in their own task ensures the UI stays responsive.
 * Created by dancy on 7/28/2015.
 */
public class ApiAsyncTask extends AsyncTask<Void, Void, Void> {
    private MonthlyCalendarActivity mActivity;
    public ApiAsyncTask(MonthlyCalendarActivity monthlyCalendarActivity) {
        mActivity = monthlyCalendarActivity;
    }

    /**
     * Background task to call Google Calendar API.
     * @param params no parameters needed for this task.
     */
    @Override
    protected Void doInBackground(Void... params) {
        try {
            mActivity.clearResultsText();
            mActivity.updateResultsText(getDataFromApi());

        } catch (final GooglePlayServicesAvailabilityIOException availabilityException) {
            mActivity.showGooglePlayServicesAvailabilityErrorDialog(
                    availabilityException.getConnectionStatusCode());

        } catch (UserRecoverableAuthIOException userRecoverableException) {
            mActivity.startActivityForResult(
                    userRecoverableException.getIntent(),
                    MonthlyCalendarActivity.REQUEST_AUTHORIZATION);

        } catch (Exception e) {
            mActivity.updateStatus("The following error occurred:\n" +
                    e.getMessage());
        }
        return null;
    }

    /**
     * Fetch a list of the next 10 events from the primary calendar.
     * @return List of Strings describing returned events.
     * @throws IOException
     */
    private List<String> getDataFromApi() throws IOException {
        // List the next 10 events from the primary calendar.
        Calendar thisYear = (Calendar)MonthlyCalendarActivity.currentMonth.clone();
        String strYear = String.format("%1$tY", thisYear);
        if (HolidayContent.holidayCache.contains(strYear)) {
            return null;
        }
        thisYear.add(Calendar.YEAR, -1);
        thisYear.set(Calendar.DAY_OF_YEAR, 1);
        DateTime minDate = new DateTime(thisYear.getTime().getTime());//System.currentTimeMillis());
        thisYear.add(Calendar.YEAR, 1);
        thisYear.add(Calendar.DATE, -1);
        DateTime maxDate = new DateTime(thisYear.getTime().getTime());//System.currentTimeMillis());

        List<String> eventStrings = new ArrayList<String>();
        Events events = mActivity.mService.events().list("en.china#holiday@group.v.calendar.google.com")
                .setMaxResults(50).setTimeMax(maxDate)
                .setTimeMin(minDate)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();

        for (Event event : items) {
            DateTime start = event.getStart().getDateTime();
            if (start == null) {
                // All-day events don't have start times, so just use
                // the start date.
                start = event.getStart().getDate();
            }
            HolidayContent.addHoliday(String.format("%s (%s)", event.getSummary(), start));

//            eventStrings.add(
//                    String.format("%s (%s)", event.getSummary(), start));

        }
        HolidayContent.holidayCache.add(strYear);
        return eventStrings;
    }

}