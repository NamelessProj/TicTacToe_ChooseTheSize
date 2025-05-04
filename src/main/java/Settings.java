import java.util.Objects;

public class Settings {
    private static String[][] board = {};
    private static int boardSize = 3;

    /**
     * Initializes the game board with the specified size.<br><br>
     * This method creates a 2D array representing the game board and fills it with numbers.
     *
     * @param size The size of the board (number of rows and columns).
     */
    public static void initializeBoard(int size) {
        // Setting the board size and creating the board
        boardSize = size;
        board = new String[boardSize][boardSize];

        // Getting thr number of char the last number has
        int lastNumberLength = String.valueOf(boardSize * boardSize).length();

        // Creating the format string for leading zeros
        String format = "%0" + lastNumberLength + "d";

        int counter = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                // Formatting the number with leading zeros
                board[i][j] = String.format(format, ++counter);
            }
        }
    }

    /**
     * Returns the size of the board.<br><br>
     * This method calculates the total number of squares on the board.
     *
     * @return The total number of squares on the board.
     */
    public static int getBoardSize() {
        return boardSize * boardSize;
    }

    /**
     * Prints the current state of the board.<br><br>
     * This method iterates through the board array and prints each element.
     */
    public static void printBoard() {
        System.out.println();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Updates the board with the player's move.<br><br>
     * This method takes an index and a player character ('X' or 'O') as input.
     *
     * @param index The index of the cell to be updated (1 to boardSize * boardSize).
     * @param player The player character ('X' or 'O').
     * @return true if the move is valid and the board is updated, false otherwise.
     */
    public static boolean updateBoard(int index, char player) {
        // Calculate the row and column based on the index
        int row = (index - 1) / boardSize;
        int col = (index - 1) % boardSize;

        // Check if the index is valid and not already occupied by a player
        if (index < 1 || index > boardSize * boardSize || board[row][col].equals("X") || board[row][col].equals("O"))
            return false; // Invalid move

        String strPlayer = Character.toString(player);

        // Update the board with the player's symbol
        board[row][col] = strPlayer;
        return true; // Valid move
    }

    /**
     * Checks if the game has a winner or a draw.<br><br>
     * This method checks all possible winning combinations and returns true if the game is over.
     *
     * @param player The player character ('X' or 'O') to check for a win.
     * @return true if the game is over, false otherwise.
     */
    public static boolean checkIfWinner(char player) {
        String strPlayer = Character.toString(player);

        // Check rows
        for (int i = 0; i < boardSize; i++) {
            boolean rowWin = true;
            for (int j = 0; j < boardSize; j++) {
                if (!Objects.equals(board[i][j], strPlayer)) {
                    rowWin = false;
                    break;
                }
            }
            if (rowWin) return true;
        }

        // Check columns
        for (int j = 0; j < boardSize; j++) {
            boolean colWin = true;
            for (int i = 0; i < boardSize; i++) {
                if (!Objects.equals(board[i][j], strPlayer)) {
                    colWin = false;
                    break;
                }
            }
            if (colWin) return true;
        }

        // Check diagonals
        boolean diagWin1 = true, diagWin2 = true;
        for (int i = 0; i < boardSize; i++) {
            if (!Objects.equals(board[i][i], strPlayer)) diagWin1 = false;
            if (!Objects.equals(board[i][boardSize - 1 - i], strPlayer)) diagWin2 = false;
        }
        return diagWin1 || diagWin2;
    }
}