package com.andresAronica.hyperskillProjects.easyProjects.ticTacToe;

import java.util.Scanner;

public class TicTacToe {
    private static Scanner scanner = new Scanner(System.in);
    // Ensures that you always work with the same board
    private static char[][] board;
    // Ensures that the board CAN'T have a different size
    private static final int BOARD_SIZE = 3;
    // Used to switch between X and O
    private static int turn;
    // Ending conditions
    private static boolean XWins = false;
    private static boolean OWins = false;
    private static boolean draw = false;

    public static void main(String[] args) {
        createBoard();
        printBoard();
        checkBoardResult();
        XOmoves();

        //Always close the scanner after you are done with it, otherwise you leave it to consume resources.
        scanner.close();
    }

    // Create the board for the first (and only) time. After that, this same board is edited.
    private static void createBoard() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        int temp1 = 0;
        int temp2 = 0;

        for (int i = 0; i < 9; i++) {
            board[temp1][temp2] = '_';
            temp2++;
            if (temp2 == 3) {
                if (temp1 <= 1) {
                    temp1++;
                }
                temp2 = 0;
            }
        }
    }

    // Print the board.
    private static void printBoard() {

        System.out.println("---------");
        for (char[] strings : board) {
            System.out.print("| ");
            for (int j = 0; j < board.length; j++) {
                System.out.print(strings[j] + " ");
            }
            System.out.print("|");
            System.out.println();
        }

        System.out.println("---------");

    }

    private static void XOmoves(/*Scanner scanner*/) {
        int validInput;
        int xCoord;
        int yCoord;

        while (!XWins && !OWins && !draw) {
            try {
                System.out.println("Enter the coordinates: ");
                String stringCoords = scanner.nextLine();

                if (stringCoords.matches("\\d\\s\\d")) {
                    validInput = 1;
                }
                else {
                    validInput = 0;
                }

                String[] coords = stringCoords.split(" ");

                switch (validInput) {
                    case 0:
                        System.out.println("You should enter numbers!");
                        break;
                    case 1:
                        xCoord = Integer.parseInt(coords[0]);
                        yCoord = Integer.parseInt(coords[1]);
                        if (!between(xCoord, 1, 3) || !between(yCoord, 1, 3)) {
                            System.out.println("Coordinates should be from 1 to 3!");
                            break;
                        } else if (board[3 - yCoord][xCoord - 1] == 88 || board[3 - yCoord][xCoord - 1] == 79) {
                            System.out.println("This cell is ocupied! Choose another one!");
                            break;
                        } else if (board[3 - yCoord][xCoord - 1] == 95) {

                            switch(turn) {
                                case 0:
                                    board[3 - yCoord][xCoord - 1] = 'X';
                                    printBoard();
                                    checkBoardResult();
                                    turn++;
                                    break;
                                case 1:
                                    board[3 - yCoord][xCoord - 1] = 'O';
                                    printBoard();
                                    checkBoardResult();
                                    turn--;
                                    break;
                            }
                        }
                        break;
                }
            } catch (Exception e) {
                /*At the time, there are no exceptions to be thrown, because you can control all the flow
                from the if's conditions directly, and its unnecessary for now. That doesn't mean that
                error handling isn't important. In fact, is one of the most important parts of the program, helping
                to maintain the flow of the program without crashing with every exception.*/
            }
        }
    }

    // Checks for the result of the board, after every turn.
    private static void checkBoardResult(/*char[][] board*/) {
        int Xs = 0;
        int Os = 0;

        // Iterate over the board to count Os and Xs.
        // ASCII values of X = 88, O = 79.
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == 88) {
                    Xs++;
                }
                if (board[i][j] == 79) {
                    Os++;
                }
            }
        }

        // Iterate over the board to verify if there is a winner
        // O+O+O = 237
        // X+X+X = 364, based on their ASCII values
        for (int i = 0; i < BOARD_SIZE; i++) {

            if (board[i][0] + board[i][1] + board[i][2] == 237 ||
                    board[0][i] + board[1][i] + board[2][i] == 237 ||
                    board[0][0] + board[1][1] + board[2][2] == 237 ||
                    board[0][2] + board[1][1] + board[2][0] == 237 &&
                            !OWins && !XWins && !draw) {
                OWins = true;
            }

            if (board[i][0] + board[i][1] + board[i][2] == 264 ||
                    board[0][i] + board[1][i] + board[2][i] == 264 ||
                    board[0][0] + board[1][1] + board[2][2] == 264 ||
                    board[0][2] + board[1][1] + board[2][0] == 264 &&
                            !OWins && !XWins && !draw) {
                XWins = true;
            }
        }

        String resultado;

        // Designates the result to be printed.
        if (Math.abs(Xs - Os) > 1 || XWins && OWins && !draw) resultado = "Impossible";
        else if (XWins && !draw) resultado = "X wins";
        else if (OWins && !draw) resultado = "O wins";
        else if (Xs + Os == 9 && !XWins && !OWins) {
            resultado = "Draw";
            draw = true;
        }
        else resultado = "Game not finished";


        // If it founds an ending condition, it prints out the result.
        if (XWins || OWins || draw) {
            System.out.println(resultado);
        }
    }

    // Helper method. Used to check if a number is in the especified closed interval (including both ends).
    private static boolean between(int i, int minValueInclusive, int maxValueInclusive) {
        return (i >= minValueInclusive && i <= maxValueInclusive);
    }
}
