package com.example.brad.purple;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GameScreen extends Activity
{
    private GridLayout gridLayout;
    private ImageButton b[][];
    private int SIZE;

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

        // you win!
        final Button buttonplayagain = (Button) findViewById(R.id.button_playagain);
        buttonplayagain.setVisibility(View.VISIBLE);

        TextView text = (TextView) findViewById(R.id.instruction);
        text.setText("You win!");
        return true;
    }

    /**
     * initializes the grid state, before adding the starter blocks.
     */
    private void initializeGrid()
    {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = (displaymetrics.widthPixels / (SIZE)) - 16;

        for (int row = 0; row < SIZE; row++)
        {
            for (int col = 0; col < SIZE; col++)
            {
                b[row][col] = new ImageButton(GameScreen.this);
                b[row][col].setMinimumHeight(width);
                b[row][col].setMinimumWidth(width);

                final ImageButton button = b[row][col];
                button.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        changeColor(button);
                        if (isLevelComplete())
                        {
                            // you win!
                            // display win message
                        }
                    }
                });
                b[row][col] = button;
                b[row][col].setBackgroundColor(Color.GRAY);
                b[row][col].setTag(BlockColor.NONE);

                gridLayout.addView(b[row][col]);
            }
        }
    }

    /**
     * sets the color of the given block, and sets it as not clickable.
     *
     * @param block     the block to change color of.
     * @param color     the color to change to.
     */
    private void setStarterBlock(ImageButton block, int color)
    {
        block.setBackgroundColor(color);
        block.setClickable(false);

        if (color == Color.RED)
        {
            block.setTag(BlockColor.RED);
        }
        else
        {
            block.setTag(BlockColor.BLUE);
        }
    }

    /**
     * gets a valid index that is not contained in the given List.
     *
     * @param  excludedNums     the List of numbers to avoid
     * @return randNum          the number to return
     */
    private int getValidNum(List<Integer> excludedNums)
    {
        Random rand = new Random();
        int randNum = rand.nextInt(SIZE);

        while (excludedNums.contains(randNum))
        {
            randNum = rand.nextInt(SIZE);
        }
        return randNum;
    }

    /**
     * places the starter blocks so the user has a place to start.
     */
    private void placeStarterBlocks()
    {
        Random rand = new Random();

        // keep track of rows/cols to exclude
        // this is to prevent creating an unsolvable grid.
        List<Integer> excludedRows = new ArrayList<>();
        List<Integer> excludedCols = new ArrayList<>();

        // randomize the ordering of the colors
        int colorA;
        int colorB;
        if (rand.nextInt(3) > 1)
        {
            colorA = Color.RED;
            colorB = Color.BLUE;
        }
        else
        {
            colorA = Color.BLUE;
            colorB = Color.RED;
        }

        // place first 2/3 blocks of color A
        int randRow = rand.nextInt(SIZE);
        int randCol = 0;
        excludedRows.add(randRow);

        for (int i = 0; i < (SIZE/2); i++)
        {
            randCol = getValidNum(excludedCols);
            excludedCols.add(randCol);
            setStarterBlock(b[randRow][randCol], colorA);
        }

        // place next 1/2 blocks of color A
        randCol = excludedCols.get(0);
        for (int i = 0; i < (SIZE/2)-1; i++)
        {
            randRow = getValidNum(excludedRows);
            excludedRows.add(randRow);

            setStarterBlock(b[randRow][randCol], colorA);
        }

        // place 1/2 blocks of color B because why not you don't control my life
        for (int i = 0; i < (SIZE/2)-1; i++)
        {
            // place final BLUE block
            randRow = getValidNum(excludedRows);
            excludedRows.add(randRow);
            randCol = getValidNum(excludedCols);
            excludedCols.add(randCol);

            setStarterBlock(b[randRow][randCol], colorB);
        }
    }

    /**
     * initialization step upon creation.
     *
     * @param savedInstanceState        our previous state of MainActivity.
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_layout);
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        gridLayout.setUseDefaultMargins(true);

        final Button buttonplayagain = (Button) findViewById(R.id.button_playagain);
        buttonplayagain.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                onCreate(savedInstanceState);
            }
        });
        buttonplayagain.setVisibility(View.INVISIBLE);

        Bundle bundle = getIntent().getExtras();
        SIZE = bundle.getInt("size");

        gridLayout.setRowCount(SIZE);
        gridLayout.setColumnCount(SIZE);

        // represent our grid with a 2D array
        b = new ImageButton[SIZE][SIZE];

        // set up board, place buttons into gridLayout
        initializeGrid();

        // place starter blocks to help the player start
        placeStarterBlocks();
    }
}