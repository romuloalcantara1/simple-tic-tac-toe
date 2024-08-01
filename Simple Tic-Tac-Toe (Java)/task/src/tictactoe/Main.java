package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize an empty board
        String input = "_________";
        char currentPlayer = 'X'; // The first player starts as 'X'

        // Print the initial empty game board
        printGameBoard(input);

        // Game loop
        while (true) {
            System.out.print("> ");
            String coordinates = scanner.nextLine();

            // Check for valid coordinates
            if (!areValidCoordinates(coordinates)) {
                System.out.println("You should enter numbers!");
                continue;
            }

            int row = coordinates.charAt(0) - '0';
            int col = coordinates.charAt(2) - '0';

            if (!areCoordinatesInRange(row, col)) {
                System.out.println("Coordinates should be from 1 to 3!");
            } else if (!isCellEmpty(input, row, col)) {
                System.out.println("This cell is occupied! Choose another one!");
            } else {
                // Update the board with the current player's move
                input = updateBoard(input, row, col, currentPlayer);
                printGameBoard(input);

                // Check for a win or draw
                String result = checkGameState(input);
                if (!result.equals("Game not finished")) {
                    System.out.println(result);
                    break; // End the game loop if the game is over
                }

                // Switch players
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            }
        }
    }

    private static void printGameBoard(String input) {
        System.out.println("---------");
        System.out.printf("| %c %c %c |\n", input.charAt(0), input.charAt(1), input.charAt(2));
        System.out.printf("| %c %c %c |\n", input.charAt(3), input.charAt(4), input.charAt(5));
        System.out.printf("| %c %c %c |\n", input.charAt(6), input.charAt(7), input.charAt(8));
        System.out.println("---------");
    }

    private static boolean areValidCoordinates(String coordinates) {
        // Check if input consists of two numbers
        return coordinates.length() == 3 &&
               Character.isDigit(coordinates.charAt(0)) &&
               coordinates.charAt(1) == ' ' &&
               Character.isDigit(coordinates.charAt(2));
    }

    private static boolean areCoordinatesInRange(int row, int col) {
        // Check if row and col are in the range of 1 to 3
        return row >= 1 && row <= 3 && col >= 1 && col <= 3;
    }

    private static boolean isCellEmpty(String input, int row, int col) {
        // Convert (row, col) to the corresponding index in the input string
        int index = (row - 1) * 3 + (col - 1);
        return input.charAt(index) == '_';
    }

    private static String updateBoard(String input, int row, int col, char player) {
        // Convert the input string to a character array
        char[] board = input.toCharArray();
        // Convert (row, col) to the corresponding index
        int index = (row - 1) * 3 + (col - 1);
        // Update the board with the player's move
        board[index] = player;
        // Return the updated board as a string
        return new String(board);
    }

    private static String checkGameState(String input) {
        // Count X's and O's
        int xCount = 0;
        int oCount = 0;
        for (char c : input.toCharArray()) {
            if (c == 'X') xCount++;
            if (c == 'O') oCount++;
        }

        // Check for wins
        boolean xWins = checkWin(input, 'X');
        boolean oWins = checkWin(input, 'O');

        // Determine the game state
        if (xWins) {
            return "X wins";
        } else if (oWins) {
            return "O wins";
        } else if (!input.contains("_")) {
            return "Draw";
        } else {
            return "Game not finished";
        }
    }

    private static boolean checkWin(String input, char player) {
        // Check rows, columns, and diagonals for the specified player
        boolean row1 = input.charAt(0) == player && input.charAt(1) == player && input.charAt(2) == player;
        boolean row2 = input.charAt(3) == player && input.charAt(4) == player && input.charAt(5) == player;
        boolean row3 = input.charAt(6) == player && input.charAt(7) == player && input.charAt(8) == player;
        boolean col1 = input.charAt(0) == player && input.charAt(3) == player && input.charAt(6) == player;
        boolean col2 = input.charAt(1) == player && input.charAt(4) == player && input.charAt(7) == player;
        boolean col3 = input.charAt(2) == player && input.charAt(5) == player && input.charAt(8) == player;
        boolean diag1 = input.charAt(0) == player && input.charAt(4) == player && input.charAt(8) == player;
        boolean diag2 = input.charAt(2) == player && input.charAt(4) == player && input.charAt(6) == player;

        return row1 || row2 || row3 || col1 || col2 || col3 || diag1 || diag2;
    }
}