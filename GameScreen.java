package com.example.brad.purple;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GameScreen extends Activity
{
    private GridLayout gridLayout;
    private ImageButton b[][];
    private int SIZE = 4;

    /**
     * toggles color of given button between RED and BLUE.
     *
     * @param button        the button we will change the color of.
     */
    private void changeColor(ImageButton button)
    {
        if (button.getTag() == BlockColor.RED)
        {
            button.setBackgroundColor(Color.BLUE);
            button.setTag(BlockColor.BLUE);
        }
        else
        {
            button.setBackgroundColor(Color.RED);
            button.setTag(BlockColor.RED);
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

        // store each row / col as a string
        // so we can easily check if they are unique
        Set<String> seqs = new HashSet<>();
        StringBuilder tempSeq = new StringBuilder();

        // check left/right
        for (int row = 0; row < SIZE; row++)
        {
            for (int col = 0; col < SIZE; col++)
            {
                if(b[row][col].getTag() == BlockColor.RED)
                {
                    reds++;
                    tempSeq.append("r");
                }
                else if(b[row][col].getTag() == BlockColor.BLUE)
                {
                    blues++;
                    tempSeq.append("b");
                }
                else
                {
                    tempSeq.append("n");
                }
            }
            seqs.add(tempSeq.toString());

            // reset tempSeq to empty
            tempSeq.setLength(0);

            // early exit if win condition not met
            if ((reds != goal) || (blues != goal))
            {
                return false;
            }

            reds = 0;
            blues = 0;
        }

        // check if every sequence of blocks is unique
        if (seqs.size() != SIZE)
        {
            // error message:
            // each row should be unique!
            return false;
        }

        seqs.clear();

        // check up/down
        for (int col = 0; col < SIZE; col++)
        {
            for (int row = 0; row < SIZE; row++)
            {
                if(b[row][col].getTag() == BlockColor.RED)
                {
                    reds++;
                    tempSeq.append("r");
                }
                else if(b[row][col].getTag() == BlockColor.BLUE)
                {
                    blues++;
                    tempSeq.append("b");
                }
                else
                {
                    tempSeq.append("n");
                }
            }
            seqs.add(tempSeq.toString());

            // reset tempSeq to empty
            tempSeq.setLength(0);

            // early exit if win condition not met
            if ((reds != goal) || (blues != goal))
            {
                return false;
            }

            reds = 0;
            blues = 0;
        }

        // check if every sequence of blocks is unique
        if (seqs.size() != SIZE)
        {
            // error message:
            // each row should be unique!
            return false;
        }
        return true;
    }

    // TODO:
    // - select a row/col and color 2 blocks the same color.
    //      the player can use this as a starting point.
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
                    b[i][j].setTag(BlockColor.RED);
                }
                //blue
                else if (r > 6)
                {
                    b[i][j].setBackgroundColor(Color.BLUE);
                    b[i][j].setTag(BlockColor.BLUE);
                }
                // uncolored
                else
                {
                    b[i][j].setTag(BlockColor.NONE);
                }

                gridLayout.addView(b[i][j]);
            }
        }
    }
}
