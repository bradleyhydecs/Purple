package com.example.brad.purple;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button buttoneasy = (Button) findViewById(R.id.button_easy);
        buttoneasy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(MainActivity.this, GameScreen.class);
                i.putExtra("size", 4);
                startActivity(i);
            }
        });

        final Button buttonhard = (Button) findViewById(R.id.button_hard);
        buttonhard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(MainActivity.this, GameScreen.class);
                i.putExtra("size", 6);
                startActivity(i);
            }
        });

        final Button buttonhowtoplay = (Button) findViewById(R.id.button_howtoplay);
        buttonhowtoplay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(MainActivity.this, HowToPlay.class);
                startActivity(i);
            }
        });
    }
}
