package com.joshuawyllie.spaceshooter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(this);

        final TextView highScore = (TextView) findViewById(R.id.highscore_text);
        SharedPreferences prefs = getSharedPreferences(Game.PREFS, Context.MODE_PRIVATE);
        int longestDistance = prefs.getInt(Game.LONGEST_DIST, 0);
        highScore.setText("Longest Distance: " + longestDistance + "km");
                //todo: move to resourse all magic stings etc

    }

    @Override
    public void onClick(View v) {
        final Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }
}
