package com.example.brad.purple;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import java.util.Random;

public class GameScreen extends Activity
{
    private GridLayout gridLayout;
    private ImageButton b[][];
    private int SIZE = 4;

    /**
     * toggles color of given button.
     * red -> blue, blue -> red
     *
     * @param button        the button we will change the color of.
     */
    private void changeColor(ImageButton button)
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

    /**
     * checks if the level is complete.
     *
     * @return boolean      true if level is complete.
     */
    private boolean isLevelComplete()
    {
        int reds = 0;
        int blues = 0;
        int goal = SIZE/2;

        // check left/right
        for (int row = 0; row < SIZE; row++)
        {
            for (int col = 0; col < SIZE; col++)
            {
                if((int) b[row][col].getTag() == 1)
                    reds++;
                else if((int) b[row][col].getTag() == 0)
                    blues++;
            }

            // early exit if win condition not met
            if ((reds != goal) || (blues != goal))
            {
                return false;
            }

            reds = 0;
            blues = 0;
        }

        // check up/down
        for (int col = 0; col < SIZE; col++)
        {
            for (int row = 0; row < SIZE; row++)
            {
                if((int) b[row][col].getTag() == 1)
                    reds++;
                else if((int) b[row][col].getTag() == 0)
                    blues++;
            }

            // early exit if win condition not met
            if ((reds != goal) || (blues != goal))
            {
                return false;
            }

            reds = 0;
            blues = 0;
        }

        return true;
    }

    // TODO:
    // - select a row/col and color 2 blocks the same color.
    //      the player can use this as a starting point.
    //
    // - record the ordering of each row, make sure it is unique
    //      and same with cols
    //

    /**
     * initialization step upon creation.
     * sets up our game grid.
     *
     * @param savedInstanceState        our previous state of MainActivity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        gridLayout.setUseDefaultMargins(true);

        // represent our grid with a 2D array
        b = new ImageButton[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++)
        {
            for (int j = 0; j < SIZE; j++)
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
                        if (isLevelComplete())
                        {
                            // you win!
                            Intent i = new Intent(GameScreen.this, MainActivity.class);
                            startActivity(i);
                        }
                    }
                });
                b[i][j] = button;

                Random rand = new Random();
                int r = rand.nextInt(10);

                // red
                if (r > 8)
                {
                    b[i][j].setBackgroundColor(Color.RED);
                    b[i][j].setTag(1);
                }
                //blue
                else if (r > 6)
                {
                    b[i][j].setBackgroundColor(Color.BLUE);
                    b[i][j].setTag(0);
                }
                // uncolored
                else
                {
                    b[i][j].setTag(2);
                }

                gridLayout.addView(b[i][j]);
            }
        }
    }
}
