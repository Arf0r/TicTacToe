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
        board = new TileState[BOARD_SIZE][BOARD_SIZE];
        for(int i=0; i<BOARD_SIZE; i++)
            for(int j=0; j<BOARD_SIZE; j++)
                board[i][j] = TileState.BLANK;

        playerOneTurn = true;
        gameOver = false;
    }

    public TileState choose(int row, int column) {
        if (gameOver == true) return TileState.INVALID;
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
        else {
            return TileState.INVALID;
        }
    }
    public GameState won() {

        for(int i = 0; i < BOARD_SIZE; i++)
        {
            int rowXcount = 0;
            int rowOcount = 0;
            int columnXcount = 0;
            int columnOcount = 0;

            for (int j = 0; j < BOARD_SIZE; j++){
                if (board[i][j] == TileState.CROSS) ++rowXcount;
                else if(board[i][j] == TileState.CIRCLE) ++ rowOcount;
                if (board[j][i] == TileState.CROSS) ++columnXcount;
                else if(board[j][i] == TileState.CIRCLE) ++columnOcount;
            }

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
        if (board[0][0] == TileState.CROSS && board[1][1] == TileState.CROSS && board[2][2] ==
                TileState.CROSS){
            gameOver = true;
            return GameState.PLAYER_ONE;
        }
        if (board[0][0] == TileState.CIRCLE && board[1][1] == TileState.CIRCLE && board[2][2] ==
                TileState.CIRCLE){
            gameOver = true;
            return GameState.PLAYER_TWO;
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
        int totalmoves = BOARD_SIZE * BOARD_SIZE;
        if (movesPlayed < 9) return GameState.IN_PROGRESS;
        else return GameState.DRAW;
    }

    public TileState saved(int row, int column){
        return board[row][column];
    }
}

