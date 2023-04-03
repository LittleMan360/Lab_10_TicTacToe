/*

Make an empty 3x3 board (represented as a 2D array of characters)
Display the empty board to the user
Set a variable to represent the current player (X or O)
While the game is not over:
a. Prompt the current player to enter a row and column to place their marker
b. If the move is valid (i.e. the space is empty), place the player's marker on the board
c. If the move is invalid, prompt the player to enter a valid move
d. Check if the current player has won the game (i.e. by having 3 in a row, column, or diagonal)
i. If the player has won, display a message indicating that they have won and end the game
e. Check if the game is a tie (i.e. no more empty spaces on the board and no player has won)
i. If the game is a tie, display a message indicating that the game is over and end the game
f. Switch to the other player
Display the final state of the board and a message indicating the winner or a tie

*/
// Import Scanner class to receive user input
import java.util.Scanner;

public class TicTacToe {

    // Define ROW and COL as final and set them as 3
    private static final int ROW = 3;
    private static final int COL = 3;
    // Create a 2D character array called BOARD with dimensions
    private static final char[][] BOARD = new char[ROW][COL];
    // Create a static character variable called currentPlayer
    private static char currentPlayer;

    public static void main(String[] args) {
        // Create a new Scanner object
        Scanner scanner = new Scanner(System.in);
        // Create  variables
        int moveNumber; // keep track of # of moves
        int userRow, userCol; // the row and column chosen by the user for their move
        boolean gameOver; // determines if the game is over
        boolean validMove; // determines if the move made by the user is valid or not

        // Loop until the user stops playing
        do {
            // Clear board
            clearBoard();
            // Set the currentPlayer to 'X'
            currentPlayer = 'X';
            // Reset the moveNumber to 0
            moveNumber = 0;
            // Set gameOver to false
            gameOver = false;

            // Start game
            do {
                // Display the current state of board
                display();
                // user input and check if the move is valid
                do {
                    // Receive user input for row and column
                    userRow = SafeInput.getRangedInt(scanner, "Please enter the row for your move", 1, 3) - 1;
                    userCol = SafeInput.getRangedInt(scanner, "Please enter the column for your move", 1, 3) - 1;
                    // Check if the move is valid and prompt the user to try again if not
                    validMove = isValidMove(userRow, userCol);
                    if(!validMove) {
                        System.out.println("That was not a valid move, try again.");
                    }
                } while(!validMove);
                // Make the move and increase moveNumber
                BOARD[userRow][userCol] = currentPlayer;
                moveNumber++;

                // Check if the game is over and if so, announce winner
                if(moveNumber >= 5) {
                    if(isWin(currentPlayer)) {
                        gameOver = true;
                        System.out.println(currentPlayer + " is the winning player!");
                    }
                }

                // Check if the game is a tie and if so, announce tie
                if(moveNumber >= 7 && !gameOver) {
                    if(isTie()) {
                        gameOver = true;
                        System.out.println("The game is a tie!");
                    }
                }

                // Switch player after turn
                if(currentPlayer == 'X'){
                    currentPlayer = 'O';
                }
                else {
                    currentPlayer = 'X';
                }
            }while(!gameOver);
        }while(SafeInput.getYNConfirm(scanner, "Play again?"));
    }

    // Method to clear board
    private static void clearBoard() {
        for(int i = 0; i < BOARD.length; i++) {
            for(int j = 0; j < BOARD[i].length; j++) {
                BOARD[i][j] = ' ';
            }
        }
    }

    // Method to display board
    private static void display() {
        for(int i = 0; i < BOARD.length; i++) {
            for(int j = 0; j < BOARD[i].length; j++) {
                System.out.print(BOARD[i][j]);
                System.out.print(' ');
            }
            System.out.print("\n");
        }
    }

    // Check if a given move is valid by verifying that the square is empty
    private static boolean isValidMove(int row, int col) {
        boolean validMove = false;
        if(BOARD[row][col] == ' ') {
            validMove = true;
        }
        return validMove;
    }

    // Check if the current player has won by checking all possible ways
    private static boolean isWin(char player) {
        return isColWin(player) || isRowWin(player) || isDiagonalWin(player);
    }

    // Check if the current player has won by checking columns
    private static boolean isColWin(char player) {
        boolean hasWon = false;

        for(int i = 0; i < BOARD.length && !hasWon; i++) {
            if((BOARD[0][i] == player) && (BOARD[1][i] == player) && (BOARD[2][i] == player)) {
                hasWon = true;
            }
        }

        return hasWon;
    }

    // Check if the current player has won by checking rows
    private static boolean isRowWin(char player) {
        boolean hasWon = false;

        for(int i = 0; i < BOARD.length && !hasWon; i++) {
            if((BOARD[i][0] == player) && (BOARD[i][1] == player) && (BOARD[i][2] == player)) {
                hasWon = true;
            }
        }

        return hasWon;
    }

    // Check if the current player has won by checking diagonals
    private static boolean isDiagonalWin(char player) {
        boolean hasWon = false;

        if((BOARD[0][0] == player) && (BOARD[1][1] == player) && (BOARD[2][2] == player)) {
            hasWon = true;
        }
        if((BOARD[0][2] == player) && (BOARD[1][1] == player) && (BOARD[2][0] == player)) {
            hasWon = true;
        }

        return hasWon;
    }

    // This method checks if the game is tied if so returns boolean
    private static boolean isTie() {
        boolean isTie = false;

        // Check rows for a tie

        for(char[] row : BOARD) {
            if(row[0] == 'X' || row[1] == 'X' || row[2] == 'X') {
                if(row[0] == 'O' || row[1] == 'O' || row[2] == 'O') {
                    isTie = true;
                }
            }
        }

        // Check columns for a tie
        for(int i = 0; i < BOARD.length && !isTie; i++) {
            if((BOARD[0][i] == 'X')|| (BOARD[1][i] == 'X') || (BOARD[2][i] == 'X')) {
                if((BOARD[0][i] == 'O')|| (BOARD[1][i] == 'O') || (BOARD[2][i] == 'O')) {
                    isTie = true;
                }
            }
        }

        // Check diagonal from top-left to bottom-right for a tie
        if(!isTie) {
            if(BOARD[0][0] == 'X' || BOARD[1][1] == 'X' || BOARD[2][2] == 'X') {
                if(BOARD[0][0] == 'O' || BOARD[1][1] == 'O' || BOARD[2][2] == 'O') {
                    isTie = true;
                }
            }
        }
        // Check diagonal from top-right to bottom-left for a tie
        if(!isTie) {
            if(BOARD[0][2] == 'X' || BOARD[1][1] == 'X' || BOARD[2][0] == 'X') {
                if(BOARD[0][2] == 'O' || BOARD[1][1] == 'O' || BOARD[2][0] == 'O') {
                    isTie = true;
                }
            }
        }

        return isTie;
    }

}
