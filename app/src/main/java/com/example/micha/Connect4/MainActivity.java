package com.example.micha.Connect4;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //Grid Array
    private Button[][] buttons = new Button[8][9];

    //HashMap to store the button id's and row col integers in string form
    HashMap<String, String> map = new HashMap<>();

    //Player's turn
    private boolean turn = true;

    //Pauses screen for few seconds to show that player won
    Handler handler = new Handler();

    private int redPoints;
    private int blackPoints;

    private TextView textViewRed;
    private TextView textViewBlack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewBlack = findViewById(R.id.text_view_black);
        textViewRed = findViewById(R.id.text_view_red);
        int id = 29;
        int i = 0;
        int jump = 0;
        //Stores all the known button id's and row/column integers in a hashmap
        for (int row = 1; row < 7; row++) {
            for(int col = 1; col < 8; col++) {
                int k = Integer.valueOf(String.valueOf(row) + String.valueOf(col));
                String numAsString = String.valueOf((id + i));
                String numAsString1 = String.valueOf(k);
                map.put(numAsString, numAsString1);
                i++;
                jump++;
                if (jump == 7) {
                    i+=2;
                    jump = 0;
                }
            }
        }

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 9; col++) {
                String buttonID = "button_" + row + col;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[row][col] = findViewById(resID);
                buttons[row][col].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (turn) {
            //The gird is 7x8, setting the bottom text to some Text value will allow the button to fall to the bottom-most row
            buttons[7][1].setText("Z"); buttons[7][2].setText("Z"); buttons[7][3].setText("Z"); buttons[7][4].setText("Z"); buttons[7][5].setText("Z"); buttons[7][6].setText("Z"); buttons[7][7].setText("Z");
            //Sets the color of the button to red
            Drawable roundDrawable = getDrawable(R.drawable.circle_button);
            roundDrawable.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            v.setBackground(roundDrawable);

            ((Button) v).setTextColor(Color.RED);
            ((Button) v).setText("O");
            ((Button) v).setTextColor(getResources().getColor(android.R.color.transparent));
            //Once the button is clicked you can't change the button's color anymore
            v.setClickable(false);

            //Gets the id of the button and gets the last 2 digits of the number and searches it through hashmap
            int key = v.getId();
            int val = key % 100;
            String numAsString1 = String.valueOf(val);
            String valString = map.get(numAsString1);
            int arrVal = Integer.parseInt(valString);
            int firstDigitArr = Integer.parseInt(valString);
            //get column number of board
            arrVal = arrVal%10;

            int firstDigit = Integer.parseInt(Integer.toString(firstDigitArr).substring(0,1));
            //checks if bottom-most button is empty if so remove the button clicked and create bottom at bottom-most row
            if (buttons[firstDigit+1][arrVal].getText() == "") {
                 v.setClickable(true);
                 for (int row = 6; row > 0; row--) {
                     if (buttons[row][arrVal].getText() == "") {
                         buttons[firstDigit][arrVal].setText("");
                         Drawable resetDrawable = getDrawable(R.drawable.circle_button);
                         resetDrawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                         buttons[firstDigit][arrVal].setTextColor(Color.BLACK);
                         buttons[firstDigit][arrVal].setBackground(resetDrawable);

                         buttons[row][arrVal].setText("O");
                         Drawable redDrawable = getDrawable(R.drawable.circle_button);
                         redDrawable.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                         buttons[row][arrVal].setBackground(redDrawable);
                         buttons[row][arrVal].setTextColor(Color.RED);
                         buttons[row][arrVal].setTextColor(getResources().getColor(android.R.color.transparent));
                         buttons[row][arrVal].setClickable(false);
                         break;
                     }
                 }
            }
        }

        else {
            buttons[7][1].setText("Z"); buttons[7][2].setText("Z"); buttons[7][3].setText("Z"); buttons[7][4].setText("Z"); buttons[7][5].setText("Z"); buttons[7][6].setText("Z"); buttons[7][7].setText("Z");
            Drawable roundDrawable = getDrawable(R.drawable.circle_button);
            roundDrawable.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
            v.setBackground(roundDrawable);

            ((Button) v).setText("X");
            ((Button) v).setTextColor(getResources().getColor(android.R.color.transparent));
            v.setClickable(false);
            int key = v.getId();
            int val = key % 100;
            String numAsString1 = String.valueOf(val);
            String valString = map.get(numAsString1);
            int arrVal = Integer.parseInt(valString);
            int firstDigitArr = Integer.parseInt(valString);
            //get column number of board
            arrVal = arrVal%10;

            int firstDigit = Integer.parseInt(Integer.toString(firstDigitArr).substring(0,1));
            if (buttons[firstDigit+1][arrVal].getText() == "") {
                v.setClickable(true);
                for (int row = 6; row > 0; row--) {
                    if (buttons[row][arrVal].getText() == "") {
                        buttons[firstDigit][arrVal].setText("");
                        Drawable resetDrawable = getDrawable(R.drawable.circle_button);
                        resetDrawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                        buttons[firstDigit][arrVal].setTextColor(Color.BLACK);
                        buttons[firstDigit][arrVal].setBackground(resetDrawable);

                        buttons[row][arrVal].setText("X");
                        Drawable redDrawable = getDrawable(R.drawable.circle_button);
                        redDrawable.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
                        buttons[row][arrVal].setBackground(redDrawable);
                        buttons[row][arrVal].setTextColor(Color.BLACK);
                        buttons[row][arrVal].setTextColor(getResources().getColor(android.R.color.transparent));
                        buttons[row][arrVal].setClickable(false);
                        break;
                    }
                }
            }
        }

        int x = 0;
        //Black = 1, Red = 0;
        if (checkWin(x) == 0) {
            RedWin();
        }
        if (checkWin(x) == 1) {
            BlackWin();
        }

        //Switches piece's turn
        turn = !turn;

    }

    private int checkWin(int x) {
        //Loops through the array and checks if there is a diagonal win, horizontal win, or vertical win
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 9; col++) {
                //Black = 1, Red = 0

                //Checks Vertically
                if ((buttons[row][col].getText() == "O") && (buttons[row][col+1].getText() == "O") && (buttons[row][col+2].getText() == "O") && (buttons[row][col+3].getText() == "O")) {
                    return 0;
                }
                if ((buttons[row][col].getText() == "X") && (buttons[row][col+1].getText() == "X") && (buttons[row][col+2].getText() == "X") && (buttons[row][col+3].getText() == "X")) {
                    return 1;
                }

                //Checks Vertically
                if ((buttons[row][col].getText() == "O") && (buttons[row+1][col].getText() == "O") && (buttons[row+2][col].getText() == "O") && (buttons[row+3][col].getText() == "O")) {
                    return 0;
                }

                if ((buttons[row][col].getText() == "X") && (buttons[row+1][col].getText() == "X") && (buttons[row+2][col].getText() == "X") && (buttons[row+3][col].getText() == "X")) {
                    return 1;
                }

                //Diagonal Top left to bottom right checker
                if((buttons[row][col].getText() == "O") && (buttons[row+1][col+1].getText() == "O") && (buttons[row+2][col+2].getText() == "O") && (buttons[row+3][col+3].getText() == "O")) {
                    return 0;
                }
                if((buttons[row][col].getText() == "X") && (buttons[row+1][col+1].getText() == "X") && (buttons[row+2][col+2].getText() == "X") && (buttons[row+3][col+3].getText() == "X")) {
                    return 1;
                }

                //Diagonal Bottom left to top right checker
                if((buttons[row][col].getText() == "O") && (buttons[row-1][col+1].getText() == "O") && (buttons[row-2][col+2].getText() == "O") && (buttons[row-3][col+3].getText() == "O")) {
                    return 0;
                }
                if((buttons[row][col].getText() == "X") && (buttons[row-1][col+1].getText() == "X") && (buttons[row-2][col+2].getText() == "X") && (buttons[row-3][col+3].getText() == "X")) {
                    return 1;
                }
            }
        }
        return 2;
    }

    private void RedWin() {
        //Changes every piece to be un clickable so no pieces can be added on the board anymore
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 9; col++) {
                buttons[row][col].setClickable(false);
            }
        }
        redPoints++;
        Toast redWins = Toast.makeText(this, "Red Wins!", Toast.LENGTH_SHORT);
        redWins.setGravity(Gravity.TOP, 0, 200);
        redWins.show();
        updatePoints();
        reset();
    }

    private void BlackWin() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 9; col++) {
                buttons[row][col].setClickable(false);
            }
        }
        blackPoints++;
        Toast blackWins = Toast.makeText(this, "Black Wins!", Toast.LENGTH_SHORT );
        blackWins.setGravity(Gravity.TOP, 0, 200);
        blackWins.show();
        updatePoints();
        reset();
    }

    private void updatePoints() {
        textViewRed.setText("Red: " + redPoints);
        textViewBlack.setText("Black: " + blackPoints);
    }

    private void reset() {
        //Delays the reset so user can see how they won
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 9; col++) {
                        buttons[row][col].setText("");
                        Drawable roundDrawable = getDrawable(R.drawable.circle_button);
                        roundDrawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                        buttons[row][col].setTextColor(Color.BLACK);
                        buttons[row][col].setBackground(roundDrawable);
                        buttons[row][col].setClickable(true);
                    }
                }
            }
        }, 3000);
    }

    private void resetGame() {
        redPoints = 0;
        blackPoints = 0;
        updatePoints();
        reset();
    }
}
