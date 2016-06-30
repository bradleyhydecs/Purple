package com.example.brad.purple;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;

import java.util.Random;

/**
 * Created by brad on 30/06/16.
 */
public class GameScreen extends Activity
{
    private GridLayout gridLayout;

    public void changeColor(ImageButton button)
    {
        // if is RED
        if ((int) button.getTag() == 1)
        {
            button.setBackgroundColor(Color.BLUE);
            button.setTag(0);
        }
        else
        {
            button.setBackgroundColor(Color.RED);
            button.setTag(1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        gridLayout.setUseDefaultMargins(true);

        // represent our grid with a 2D array
        ImageButton b[][] = new ImageButton[4][4];

        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                b[i][j] = new ImageButton(GameScreen.this);
                b[i][j].setMinimumHeight(175);
                b[i][j].setMinimumWidth(175);

                final ImageButton button = b[i][j];
                button.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        changeColor(button);
                    }
                });
                b[i][j] = button;

                Random rand = new Random();
                int r = rand.nextInt(10);
                b[i][j].setTag(0);
                if (r > 8)
                {
                    b[i][j].setBackgroundColor(Color.RED);
                    b[i][j].setTag(1);
                }
                else if (r > 6)
                {
                    b[i][j].setBackgroundColor(Color.BLUE);
                }

                gridLayout.addView(b[i][j]);
            }
        }
    }

    //super.onCreate(savedInsta)
}
