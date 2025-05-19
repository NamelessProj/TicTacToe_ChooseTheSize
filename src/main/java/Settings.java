import java.util.Objects;

public class Settings {
    // Static variables to hold the game state
    private String[][] board = {};
    private int boardSize = 3;
    private int lastNumberLength = 1;
    private int fullBoardSize = 9;

    /**
     * Initializes the game board with the specified size.<br><br>
     * This method creates a 2D array representing the game board and fills it with numbers.
     *
     * @param size The size of the board (number of rows and columns).
     */
    Settings(int size) {
        // Setting the board size and creating the board
        boardSize = size;
        board = new String[boardSize][boardSize];

        // Calculating the total number of squares on the board
        fullBoardSize = boardSize * boardSize;

        // Getting the number of characters the last number has
        lastNumberLength = String.valueOf(fullBoardSize).length();

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
     * This method returns the number of squares on the board.
     *
     * @return The total number of squares on the board.
     */
    public int getBoardSize() {
        return fullBoardSize;
    }

    /**
     * Prints the current state of the board.<br><br>
     * This method iterates through the board array and prints each element.
     */
    public void printBoard() {
        System.out.println();

        // Creating a StringBuilder to build the board string
        StringBuilder sb = new StringBuilder();
        String separator = buildSeparator();

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (j > 0)
                    sb.append("|");
                sb.append(board[i][j]);
            }
            sb.append("\n");

            if (i < boardSize - 1) {
                sb.append(separator)
                        .append("\n");
            }
        }

        System.out.println(sb);
    }

    /**
     * Builds the separator string for the board.<br><br>
     * This method creates a string of dashes and plus signs to separate the rows of the board.
     *
     * @return The separator string.
     */
    private String buildSeparator() {
        String cellSep = "-".repeat(lastNumberLength);
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < boardSize; j++) {
            if (j > 0)
                sb.append("+");
            sb.append(cellSep);
        }
        return sb.toString();
    }

    /**
     * Updates the board with the player's move.<br><br>
     * This method takes an index and a player character ('X' or 'O') as input.
     *
     * @param index The index of the cell to be updated (1 to boardSize * boardSize).
     * @param player The player character ('X' or 'O').
     * @return true if the move is valid and the board is updated, false otherwise.
     */
    public boolean updateBoard(int index, char player) {
        // Calculate the row and column based on the index
        int row = (index - 1) / boardSize;
        int col = (index - 1) % boardSize;

        // Check if the index is valid and not already occupied by a player
        if (index < 1 || index > fullBoardSize || board[row][col].contains("X") || board[row][col].contains("O"))
            return false; // Invalid move

        String strPlayer = Character.toString(player);

        // Format the player character to match the length of the last number
        String formatedPlayer = strPlayer.repeat(Math.max(0, lastNumberLength - 1)) + strPlayer;

        // Update the board with the player's symbol
        board[row][col] = formatedPlayer;
        return true; // Valid move
    }

    /**
     * Checks if the game has a winner or a draw.<br><br>
     * This method checks all possible winning combinations and returns true if the game is over.
     *
     * @param player The player character ('X' or 'O') to check for a win.
     * @return true if the game is over, false otherwise.
     */
    public boolean checkIfWinner(char player) {
        String strPlayer = Character.toString(player);

        // Format the player character to match the length of the last number
        String formatedPlayer = strPlayer.repeat(Math.max(0, lastNumberLength - 1)) + strPlayer;

        // Check rows
        for (int i = 0; i < boardSize; i++) {
            boolean rowWin = true;
            for (int j = 0; j < boardSize; j++) {
                if (!Objects.equals(board[i][j], formatedPlayer)) {
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
                if (!Objects.equals(board[i][j], formatedPlayer)) {
                    colWin = false;
                    break;
                }
            }
            if (colWin) return true;
        }

        // Check diagonals
        boolean diagWin1 = true, diagWin2 = true;
        for (int i = 0; i < boardSize; i++) {
            if (!Objects.equals(board[i][i], formatedPlayer)) diagWin1 = false;
            if (!Objects.equals(board[i][boardSize - 1 - i], formatedPlayer)) diagWin2 = false;
        }
        return diagWin1 || diagWin2;
    }
}