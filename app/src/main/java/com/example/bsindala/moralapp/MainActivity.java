package com.example.bsindala.moralapp;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.app.NotificationManager;
import android.app.Notification;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private NotificationManager myNotificationManager; // Manage notifications

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myNotificationManager = (NotificationManager) this.getApplicationContext().getSystemService(this.NOTIFICATION_SERVICE);
        Button cyberbullying = (Button) findViewById(R.id.cyberbullying);
        Button bullying = (Button) findViewById(R.id.bullying);
        Button feelGood = (Button) findViewById(R.id.feelGood);

        cyberbullying.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                goToCyberbullyingActivity();
            }
        });

        bullying.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                goToBullyingActivity();
            }
        });

        feelGood.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                goToFeelGoodActivity();
            }
        });
    }

    private void goToCyberbullyingActivity()
    {
        Intent cyberbullyingIntent = new Intent(this, CyberbullyingActivity.class);
        startActivity(cyberbullyingIntent);
    }

    private void goToBullyingActivity()
    {
        Intent bullyingIntent = new Intent(this, BullyingActivity.class);
        startActivity(bullyingIntent);
    }

    private void goToFeelGoodActivity()
    {
        Intent feelGoodIntent = new Intent(this, FeelGoodActivity.class);
        startActivity(feelGoodIntent);
    }

    /**
     * Function to show nofications
     */
    public void showNotification()
    {
        Intent notificationIntent = new Intent(this, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent notificationPending = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Building Notifications
        NotificationCompat.Builder noticationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Cyberbullying")
                .setContentText("Notification tester")
                .setContentIntent(notificationPending);

        // mId allows you to update the notification later on.
        myNotificationManager.notify(10, noticationBuilder.build());
    }

    /**
     * Function to expand notification
     */
    public void expandNotification()
    {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // Building the expandable content
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            String lorem = this.getResources().getString(R.string.quote1); // Change this to quote
            String [] content = lorem.split("\\.");

            inboxStyle.setBigContentTitle("This is a big title");
            for (String line : content) {
                inboxStyle.addLine(line);
            }

            // Building the notification
            NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                    .setContentTitle("Expandable notification") // title of notification
                    .setContentText("This is an example of an expandable notification") // text inside the notification
                    .setStyle(inboxStyle); // adds the expandable content to the notification

            myNotificationManager.notify(11, nBuilder.build());

        } else {
            Toast.makeText(this, "Can't show", Toast.LENGTH_LONG).show();
        }
    }

    void notificationWithButton(){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // Prepare intent which is triggered if the  notification button is pressed
            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

            // Building the notifcation
            NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                    .setContentTitle("Button notification") // notification title
                    .setContentText("Expand to show the buttons...") // content text
                    .setTicker("Showing button notification"); // status bar message
                    //.addAction(R.drawable, "Accept", pIntent) // accept notification button
                    //.addAction(R.drawable.cancel, "Cancel", pIntent); // cancel notification button

            myNotificationManager.notify(1001, nBuilder.build());

        } else {
            Toast.makeText(this, "You need a higher version", Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cyberbullying:
                notificationWithButton();
                break;
            case R.id.bullying:
                expandNotification();
                break;
            case R.id.feelGood:
                showNotification();
                break;

        }
    }
}
