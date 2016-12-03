package com.example.bsindala.moralapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import junit.framework.Test;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by bsindala on 11/29/16.
 */

public class NotificationHandlerActivity
{
    // Random string array
    private String[] myMoralStrings;
    private static final Random generateReminder = new Random();

    // Notification handler singleton
    private static NotificationHandlerActivity nHandler;
    private static NotificationManager mNotificationManager;

    private NotificationHandlerActivity () { }

    /**
     * Singleton pattern implementation
     * @return nHandler
     */
    public static NotificationHandlerActivity getInstance(Context context)
    {
        if (nHandler == null)
        {
            nHandler = new NotificationHandlerActivity();
            mNotificationManager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return nHandler;
    }

    /**
     * Display a moral notification
     * @param context application context
     */
    public void createExpandableNotification (Context context)
    {
        /**
         * Create random reminder
         */
        myMoralStrings = context.getResources().getStringArray(R.array.remindersArray);
        String randomReminder = myMoralStrings[generateReminder.nextInt(myMoralStrings.length)];

        // Display notifications at 8, 12 and 21
        //TODO Create the times

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            // Building the expandable content
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            String moralReminder = randomReminder; // Random Reminder
            String[] content = moralReminder.split("\\.");

            inboxStyle.setBigContentTitle("Big Moral Reminder");
            for (String line : content)
                inboxStyle.addLine(line);

            // Building the notification
            NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.choose_one) // notification icon
                    .setContentTitle("Expandable Moral Reminder") // title of notification
                    .setContentText("Expandable Moral Reminder") // text inside the notification
                    .setStyle(inboxStyle); // adds the expandable content to the notification

            mNotificationManager.notify(11, nBuilder.build());
        }
        else
        {
            Toast.makeText(context, "Can't Show Reminder", Toast.LENGTH_LONG).show();
        }
    }
}
