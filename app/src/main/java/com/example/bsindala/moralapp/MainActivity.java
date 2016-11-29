package com.example.bsindala.moralapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.app.Activity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    NotificationHandlerActivity nHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nHandler = NotificationHandlerActivity.getInstance(this);
        initializeUI();
    }

    private void initializeUI() {
        setContentView(R.layout.activity_main);

        Button cyberbullying = (Button) findViewById(R.id.cyberbullying);
        Button bullying = (Button) findViewById(R.id.bullying);
        Button feelGood = (Button) findViewById(R.id.feelGood);

        /**
         * Notifications
         */
        findViewById(R.id.moral_reminder).setOnClickListener(this);
        findViewById(R.id.big_moral_reminder).setOnClickListener(this);
        findViewById(R.id.progress_notification).setOnClickListener(this);
        findViewById(R.id.button_notification).setOnClickListener(this);

        cyberbullying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCyberbullyingActivity();
            }
        });

        bullying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBullyingActivity();
            }
        });

        feelGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFeelGoodActivity();
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.moral_reminder:
                nHandler.createMoralNotification(this);
                break;

            case R.id.big_moral_reminder:
                nHandler.createExpandableNotification(this);
                break;

            case R.id.progress_notification:
                nHandler.createProgressNotification(this);
                break;

            case R.id.button_notification:
                nHandler.createButtonNotification(this);
        }
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
}
