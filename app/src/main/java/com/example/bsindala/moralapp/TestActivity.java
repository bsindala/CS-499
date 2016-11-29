package com.example.bsindala.moralapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by bsindala on 11/29/16.
 */

public class TestActivity extends Activity
{
    @Override
    protected void onCreate (Bundle savedInstance)
    {
        super.onCreate(savedInstance);

        TextView text = new TextView(this);
        text.setText("Test Activity");
        setContentView(text);
    }
}
