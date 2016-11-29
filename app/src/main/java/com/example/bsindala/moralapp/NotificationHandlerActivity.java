package com.example.bsindala.moralapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by bsindala on 11/29/16.
 */

public class NotificationHandlerActivity
{
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
     * Display a simple notification
     * @param context application context
     */
    public void createMoralNotification(Context context)
    {
        // Creates an explicit intent for an Activity
        Intent resultIntent = new Intent(context, TestActivity.class);

        // Create an artificial activity stack for the notification activity
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(TestActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        // Pending intent to the notification manager
        PendingIntent resultPending = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Building the notification
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.choose_one) // notification icon
                .setContentTitle("Moral Reminder") // main title of the notification
                .setContentText("Moral Reminder") // TODO get content from the strings ** maybe we are cool
                .setContentIntent(resultPending); // notification intent

        // Allows for later updating of the notification
        mNotificationManager.notify(10, mBuilder.build());
    }

    public void createExpandableNotification (Context context)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            // Building the expandable content
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            String moralReminder = context.getResources().getString(R.string.bully1);
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

    /**
     * Show a determinate and undeterminate progress notification
     * @param context activity context
     */
    public void createProgressNotification(final Context context)
    {
        // used to update the progress notification
        final int progressID = new Random().nextInt(1000);

        // building the notification
        final NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.moral_background)
                .setContentTitle("Progress Notification")
                .setContentText("Now waiting")
                .setTicker("Progress notification created")
                .setUsesChronometer(true)
                .setProgress(100, 0, true);

        AsyncTask<Integer, Integer, Integer> downloadTask = new AsyncTask<Integer, Integer, Integer>()
        {
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                mNotificationManager.notify(progressID, nBuilder.build());
            }

            @Override
            protected Integer doInBackground(Integer... params)
            {
                try {
                    // Sleep 2 seconds to show the undeterminate progress
                    Thread.sleep(5000);

                    // update the progress
                    for (int i = 0; i < 101; i+=5)
                    {
                        nBuilder.setContentTitle("Progress running...")
                                .setContentText("Now running...")
                                .setProgress(100, i, false)
                                .setSmallIcon(R.drawable.moral_background)
                                .setContentInfo(i + " %");

                        // use the same id for update instead created another one
                        mNotificationManager.notify(progressID, nBuilder.build());
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute (Integer integer)
            {
                super.onPostExecute(integer);

                nBuilder.setContentText("Progress finished :D")
                        .setContentTitle("Progress finished!!!")
                        .setTicker("Progress Finished !!!")
                        .setSmallIcon(R.drawable.moral_background)
                        .setUsesChronometer(false);

                mNotificationManager.notify(progressID, nBuilder.build());
            }
        };

        // Execute the progress task
        downloadTask.execute();
    }

    public void createButtonNotification (Context context)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            // prepare intent which is triggered if the notification button is pressed
            Intent intent = new Intent(context, TestActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Build the notification
            NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.choose_one)  // notification icon
                    .setContentTitle("Button notification") // notification icon
                    .setContentText("Expand to show the buttons...") // content text
                    .setTicker("Showing button notification") // status bar message
                    .addAction(R.drawable.moral_background, "Accept", pIntent)  // accept notification button
                    .addAction(R.drawable.choose_one, "Cancel", pIntent); // cancel notification button

            mNotificationManager.notify(1001, nBuilder.build());
        }
        else
        {
            Toast.makeText(context, "You need a higher version", Toast.LENGTH_LONG).show();
        }
    }
}
