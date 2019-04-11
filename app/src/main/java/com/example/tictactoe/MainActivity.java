package com.example.tictactoe;

import android.service.quicksettings.Tile;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;

public class MainActivity extends AppCompatActivity {

    // Initialize game and create a grid with the ID's of the buttons as they are in the UI
    Game game;
    int [][] ids = {
            {R.id.button, R.id.button2, R.id.button3},
            {R.id.button4, R.id.button5, R.id.button6},
            {R.id.button7, R.id.button8, R.id.button9}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start new game
        game = new Game();

        // Check if an instance state was saved, If so, restore tilestates
        if (savedInstanceState != null) {
            game = (Game) savedInstanceState.getSerializable("game");

            // Iterate over the buttons using the grid of button ID's, and get their tilestates
            for (int i =0; i < 3; i++){
                for (int j = 0; j < 3; j++){
                    Button button = (Button) findViewById(ids[i][j]);

                    // Set text of buttons according to tilestates
                    TileState oldstate = game.saved(i,j);
                    switch (oldstate) {
                        case CROSS:
                            button.setText("X");
                            break;
                        case CIRCLE:
                            button.setText("O");
                            break;
                    }
                }
            }
        }
    }

    public void tileClicked(View view) {

        // When a button is clicked, save the button's X and Y coordinates
        Button button = (Button) view;
        float xpos = button.getX();
        float ypos = button.getY();

        // Translate coordinates to row and column position
        int row = (int) (xpos / 200);
        int column = (int) (ypos / 200);

        // Get new tilestate and change the buttons text accordingly
        TileState state = game.choose(row, column);
        switch (state) {
            case CROSS:
                button.setText("X");
                break;
            case CIRCLE:
                button.setText("O");
                break;
            case INVALID:
                break;
        }

        // Check the state of the game and change the progress text button accordingly
        GameState gState = game.won();
        Button button11 = (Button) findViewById(R.id.button11);
        switch (gState){
            case IN_PROGRESS:
                button11.setText("Game = in progress");
                break;

            case PLAYER_ONE:
                button11.setText("Game was won by player 1");
                break;

            case PLAYER_TWO:
                button11.setText("Game was won by player 2");
                break;

            case DRAW:
                button11.setText("Draw!");
                break;

        }

    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the game's state
        outState.putSerializable("game",game);
    }

    public void resetClicked(View view) {

        // Start new game
        game = new Game();

        // Set all the buttons text fields to blank
        Button button1 = findViewById(R.id.button);
        button1.setText("");
        Button button2 = findViewById(R.id.button2);
        button2.setText("");
        Button button3 = findViewById(R.id.button3);
        button3.setText("");
        Button button4 = findViewById(R.id.button4);
        button4.setText("");
        Button button5 = findViewById(R.id.button5);
        button5.setText("");
        Button button6 = findViewById(R.id.button6);
        button6.setText("");
        Button button7 = findViewById(R.id.button7);
        button7.setText("");
        Button button8 = findViewById(R.id.button8);
        button8.setText("");
        Button button9 = findViewById(R.id.button9);
        button9.setText("");
    }
}
