package com.example.tictactoe;

import android.service.quicksettings.Tile;
import android.util.Log;
import android.widget.TableRow;

import java.io.Serializable;

public class Game implements Serializable {
    final private int BOARD_SIZE = 3;
    private TileState[][] board;
    private Boolean playerOneTurn;  // true if player 1's turn, false if player 2's turn
    private int movesPlayed;
    private Boolean gameOver;


    public Game() {

        // Initializes a board and sets all the tilestates to blank
        board = new TileState[BOARD_SIZE][BOARD_SIZE];
        for(int i=0; i<BOARD_SIZE; i++)
            for(int j=0; j<BOARD_SIZE; j++)
                board[i][j] = TileState.BLANK;

        // Player 1 gets the first turn, and the game is not over yet
        playerOneTurn = true;
        gameOver = false;
    }

    public TileState choose(int row, int column) {
        // If the game is over, do not allow additional moves
        if (gameOver == true) return TileState.INVALID;

        // If the Tile attempted to be changed is still blank
        // Check who's turn it is and change the tile, switch the turn, and return the tilestate
        if (board[row][column] == TileState.BLANK) {
            if (playerOneTurn == true) {
                board[row][column] = TileState.CROSS;
                playerOneTurn = false;
                return TileState.CROSS;

            } else {
                board[row][column] = TileState.CIRCLE;
                playerOneTurn = true;
                return TileState.CIRCLE;
            }
        }
        // If the tile is already taken, return invalid
        else {
            return TileState.INVALID;
        }
    }
    public GameState won() {
        // Add 1 to moves played, and initialize rows
        ++movesPlayed;
        for(int i = 0; i < BOARD_SIZE; i++)
        {
            // Initialize counters and columns
            int rowXcount = 0;
            int rowOcount = 0;
            int columnXcount = 0;
            int columnOcount = 0;
            for (int j = 0; j < BOARD_SIZE; j++){

                // Add 1 to the counters for every X or O in the rows
                if (board[i][j] == TileState.CROSS) ++rowXcount;
                else if(board[i][j] == TileState.CIRCLE) ++ rowOcount;

                // Add 1 to the counters for every X or O in the columns
                if (board[j][i] == TileState.CROSS) ++columnXcount;
                else if(board[j][i] == TileState.CIRCLE) ++columnOcount;
            }

            // If one of the counters reaches 3 the game is won, return winner
            if (rowXcount == 3){
                gameOver = true;
                return GameState.PLAYER_ONE;
            }
            if (rowOcount == 3){
                gameOver = true;
                return GameState.PLAYER_TWO;
            }
            if (columnXcount == 3){
                gameOver = true;
                return GameState.PLAYER_ONE;
            }
            if (columnOcount == 3){
                gameOver = true;
                return GameState.PLAYER_TWO;
            }
        }

        // If top left to bottom right diagonal tilestates are the same, return winner
        if (board[0][0] == TileState.CROSS && board[1][1] == TileState.CROSS && board[2][2] ==
                TileState.CROSS){
            gameOver = true;
            return GameState.PLAYER_ONE;
        }
        if (board[0][0] == TileState.CIRCLE && board[1][1] == TileState.CIRCLE && board[2][2] ==
                TileState.CIRCLE){
            gameOver = true;
            return GameState.PLAYER_TWO;

        // If top right to bottom left diagonal tilestates are the same, return winner
        }
        if (board[0][2] == TileState.CROSS && board[1][1] == TileState.CROSS && board[2][0] ==
                TileState.CROSS){
            gameOver = true;
            return GameState.PLAYER_ONE;
        }
        if (board[0][2] == TileState.CIRCLE && board[1][1] == TileState.CIRCLE && board[2][0] ==
                TileState.CIRCLE) {
            gameOver = true;
            return GameState.PLAYER_TWO;
        }

        // if the moves played is lower than the total moves and there is no winner, game is in progress
        int totalmoves = BOARD_SIZE * BOARD_SIZE;
        if (movesPlayed < totalmoves) return GameState.IN_PROGRESS;

        // if the total moves is reached but there is no winner, the game is a draw
        return GameState.DRAW;
    }

    // Gets the tilestate for a certain tile
    public TileState saved(int row, int column){
        return board[row][column];
    }
}

