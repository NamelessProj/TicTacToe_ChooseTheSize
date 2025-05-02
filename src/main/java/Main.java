import java.util.Scanner;

public class Main {
    public static final int MIN_BOARD_SIZE = 1;
    public static final int MAX_BOARD_SIZE = 10;

    public static char player = 'X';
    public static int squaresPlayed = 0;
    public static boolean hasWinner = false;

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
            Settings.initializeBoard(size);

            // Get the number of squares on the board
            int numberOfSquares = Settings.getBoardSize();

            // Run the game loop
            while (squaresPlayed < numberOfSquares) {
                // Print the board
                Settings.printBoard();

                // Get the player's move
                System.out.print("Player " + player + ", enter your move (1 to " + numberOfSquares + "): ");
                int move = scanner.nextInt();

                // Validate the move
                if (!Settings.updateBoard(move, player)) {
                    System.out.println("Invalid move. Try again.");
                    continue;
                }

                // Check for a win
                if (Settings.checkIfWinner(player)) {
                    hasWinner = true;
                    Settings.printBoard();
                    System.out.println("Player " + player + " wins!");
                    break;
                }

                // Update the player and increment the squares played
                player = player == 'X' ? 'O' : 'X';
                squaresPlayed++;
            }

            // Check for a draw
            if (!hasWinner) {
                Settings.printBoard();
                System.out.println("It's a draw!");
            }

            // Ask if the players want to play again
            System.out.println();
            System.out.print("Do you want to play again? (y/n): ");
            char playAgain = scanner.next().charAt(0);

            if (playAgain == 'y' || playAgain == 'Y') {
                // Reset the game state
                player = 'X';
                squaresPlayed = 0;
                hasWinner = false;
            } else
                break;
        }

        // Close the scanner
        scanner.close();

        // Print the final board
        Settings.printBoard();

        // Print the final message
        System.out.println();
        System.out.println("Thank you for playing!");
    }
}