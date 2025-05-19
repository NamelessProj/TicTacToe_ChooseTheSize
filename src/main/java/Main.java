import java.util.Scanner;

public class Main {
    private static final int MIN_BOARD_SIZE = 1;
    private static final int MAX_BOARD_SIZE = 10;

    private static char player = 'X';
    private static int squaresPlayed = 0;
    private static boolean hasWinner = false;

    /**
     * Main method to run the Tic Tac Toe game.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Initiate the board
            System.out.println();
            System.out.print("Enter the size of the board (" + MIN_BOARD_SIZE + " to " + MAX_BOARD_SIZE + "): ");
            int size = scanner.nextInt();

            // Validate the size
            if (size < MIN_BOARD_SIZE || size > MAX_BOARD_SIZE) {
                System.out.println("Invalid size. Please enter a number between " + MIN_BOARD_SIZE + " to " + MAX_BOARD_SIZE + ".");
                continue;
            }

            // Initialize the board
            Settings settings = new Settings(size);

            // Get the number of squares on the board
            int numberOfSquares = settings.getBoardSize();

            // Run the game loop
            while (squaresPlayed < numberOfSquares) {
                // Print the board
                settings.printBoard();

                // Get the player's move
                System.out.print("Player " + player + ", enter your move (1 to " + numberOfSquares + "): ");
                int move = scanner.nextInt();

                // Validate the move
                if (!settings.updateBoard(move, player)) {
                    System.out.println("Invalid move. Try again.");
                    continue;
                }

                // Check for a win
                if (settings.checkIfWinner(player)) {
                    hasWinner = true;
                    settings.printBoard();
                    System.out.println("Player " + player + " wins!");
                    break;
                }

                // Update the player and increment the squares played
                changeActivePlayer();
                squaresPlayed++;
            }

            // Check for a draw
            if (!hasWinner) {
                settings.printBoard();
                System.out.println("It's a draw!");
            }

            // Ask if the players want to play again
            System.out.println();
            System.out.print("Do you want to play again? (y/n): ");
            char playAgain = scanner.next().toLowerCase().charAt(0);

            if (playAgain == 'y') {
                // Reset the game state
                changeActivePlayer();
                squaresPlayed = 0;
                hasWinner = false;

                System.out.println();
                System.out.println("Starting a new game...");
            } else {
                settings.printBoard();
                break;
            }
        }

        // Close the scanner
        scanner.close();

        // Print the final message
        System.out.println();
        System.out.println("Thank you for playing!");
    }

    /**
     *  Changes the active player.<br><br>
     *  This method toggles the active player between 'X' and 'O'.
     */
    private static void changeActivePlayer() {
        player = (player == 'X') ? 'O' : 'X';
    }
}