import java.util.Objects;

public class Settings {
    // Static variables to hold the game state
    private final String[][] board;
    private final int boardSize;
    private final int lastNumberLength;
    private final int fullBoardSize;

    /**
     * Constructor to initialize the board.
     * <p>
     * This constructor takes the size of the board as input and initializes the board with numbers.
     *
     * @param size The size of the board (number of squares in one row/column).
     */
    Settings(int size) {
        // Setting the board size and creating the board
        this.boardSize = size;
        this.board = new String[size][size];

        // Calculating the total number of squares on the board
        this.fullBoardSize = boardSize * boardSize;

        // Getting the number of characters the last number has
        this.lastNumberLength = String.valueOf(fullBoardSize).length();

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
     * Returns the size of the board.
     * <p>
     * This method returns the number of squares on the board.
     *
     * @return The total number of squares on the board.
     */
    public int getBoardSize() {
        return fullBoardSize;
    }

    /**
     * Prints the current state of the board.
     * <p>
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
     * Builds the separator string for the board.
     * <p>
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
     * Updates the board with the player's move.
     * <p>
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
     * Checks if the game has a winner or a draw.
     * <p>
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
            if (checkRows(formatedPlayer, i)) {
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < boardSize; j++) {
            if (checkColumns(formatedPlayer, j)) {
                return true;
            }
        }

        // Check diagonals
        boolean diagWin1 = true, diagWin2 = true;
        for (int i = 0; i < boardSize; i++) {
            if (!Objects.equals(board[i][i], formatedPlayer))
                diagWin1 = false;
            if (!Objects.equals(board[i][boardSize - 1 - i], formatedPlayer))
                diagWin2 = false;
        }
        return diagWin1 || diagWin2;
    }

    /**
     * Checks if all elements in a row are equal to the player character.
     *
     * @param player The player character ('X' or 'O').
     * @param row The row index to check.
     * @return true if all elements in the row are equal to the player character, false otherwise.
     */
    private boolean checkRows(String player, int row) {
        for (int j = 0; j < boardSize; j++) {
            if (!Objects.equals(board[row][j], player)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if all elements in a column are equal to the player character.
     *
     * @param player The player character ('X' or 'O').
     * @param col The column index to check.
     * @return true if all elements in the column are equal to the player character, false otherwise.
     */
    private boolean checkColumns(String player, int col) {
        for (int i = 0; i < boardSize; i++) {
            if (!Objects.equals(board[i][col], player)) {
                return false;
            }
        }
        return true;
    }
}