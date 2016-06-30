package com.example.brad.purple;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button buttoneasy = (Button) findViewById(R.id.button_easy);
        buttoneasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttoneasy.setText("You clicked!");
                Intent i = new Intent(MainActivity.this, GameScreen.class);
                startActivity(i);
            }
        });

    }

   // public void
}
