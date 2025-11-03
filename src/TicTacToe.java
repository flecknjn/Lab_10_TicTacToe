import java.util.Scanner;

public class TicTacToe {

    //define constats for rows and columns for the matrix
    private static final int ROW = 3;
    private static final int COL = 3;
    private static String board[][] = new String[ROW][COL];

    //game main run
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean gameOver = false;

        //clear the board, move count to 0, player set to X
        do {
            int moveCount = 0;
            String playerCurrent = "X";
            clearBoard();

            while (!gameOver) {

                //show the board, get coordinates for move (1-3) for row and col. loop till valid input
                showBoard();
                System.out.println("\nPlayer " + playerCurrent + ", make your move!");
                int rowMove = SafeInput.getRangedInt(in, "Pick your row", 1, 3);
                int colMove = SafeInput.getRangedInt(in, "Pick your column", 1, 3);

                //convert player move to array (0-2)
                int rowMoveFix = rowMove - 1;
                int colMoveFix = colMove - 1;

                //record move on the board and tick up move counter
                if (isValidMove(rowMoveFix, colMoveFix)) {
                    board[rowMoveFix][colMoveFix] = playerCurrent;
                    moveCount = moveCount + 1;

                    if (moveCount >= 5) {
                        if (isWin(playerCurrent)) {
                            showBoard();
                            System.out.println("Player " + playerCurrent + " wins!");
                            gameOver = true;

                        } else if (isTie()) {
                            showBoard();
                            System.out.println("It's a tie!");
                            gameOver = true;
                        }
                    }

                    if (!gameOver) {
                        if (playerCurrent.equals("X")) {
                            playerCurrent = "O";
                        } else {
                            playerCurrent = "X";
                        }
                    }
                } else {
                    System.out.println("That spot is already taken! Try again.");
                }
            }
            gameOver = SafeInput.getYNConfirm(in, "Would you like to play again?");

            if(gameOver){
                gameOver = false;
            }
            else{
                System.out.println("Thank you for playing.");
                gameOver = true;
            }

        }while(!gameOver);
    }

    //method to clear the board before a game starts
    private static void clearBoard(){
        for(int row = 0; row < ROW; row++){
            for(int col = 0; col < COL; col++){
                board[row][col] = " ";
            }
        }
    }

    //method to show board state
    private static void showBoard(){
        System.out.println("------------");
        for(int row = 0; row < ROW; row++){
            System.out.print("| ");
            for(int col = 0; col < COL; col++){
                System.out.printf(board[row][col] + " | ");
            }
            System.out.println();
            System.out.println("------------");
        }
    }

    //method for checking if a space is empty for a move to be placed
    private static boolean isValidMove(int row, int col){
        boolean retVal = false;
        if(board[row][col].equals(" ")){
            retVal = true;
        }return retVal;
    }

    //method for checking for wins
    private static boolean isWin(String player){
        if(isColWin(player) || isRowWin(player) || isDiagWin(player)){
            return true;
        }
        return false;
    }

    //method for checking a row win
    private static boolean isRowWin(String player){
        for(int row = 0; row < ROW; row++){
            if(board[row][0].equals(player) && board[row][1].equals(player) && board[row][2].equals(player)){
                return true;
            }
        }
        return false;
    }

    //method for checking column win
    private static boolean isColWin(String player){
        for(int col = 0; col < COL; col++){
            if(board[0][col].equals(player) && board[1][col].equals(player) && board[2][col].equals(player)){
                return true;
            }
        }
        return false;
    }

    //method for checking diagonal win
    private static boolean isDiagWin(String player){
        if(board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)){
            return true;
        }
        else if(board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player)){
            return true;
        }
        return false;
    }

    //method for checking tie
    private static boolean isTie(){
        //check if there are empty spaces
        boolean fullBoard = true;

        for(int row = 0; row < ROW; row++){
            for(int col = 0; col < COL; col++){
                if(board[row][col].equals(" ")){
                    fullBoard = false;
                    break;
                }
            }
            if(!fullBoard){
                break;
            }
        }

        if(fullBoard){
            return true;
        }

        //check if win vectors are blocked
        int[][][] winVectors = {
                {{0, 0}, {0, 1}, {0, 2}},
                {{1, 0}, {1, 1}, {1, 2}},
                {{2, 0}, {2, 1}, {2, 2}},

                {{0, 0}, {1, 0}, {2, 0}},
                {{0, 1}, {1, 1}, {2, 1}},
                {{0, 2}, {1, 2}, {2, 2}},

                {{0, 0}, {1, 1}, {2, 2}},
                {{0, 2}, {1, 1}, {2, 0}},
        };

        for(int i = 0; i < winVectors.length; i++){
            boolean hasMoveX = false;
            boolean hasMoveO = false;

            for(int j = 0; j < winVectors[i].length; j++){
                int row = winVectors[i][j][0];
                int col = winVectors[i][j][1];
                String cell = board[row][col];

                if(cell.equals("X")){
                    hasMoveX = true;
                }
                if(cell.equals("O")){
                    hasMoveO = true;
                }
            }

            if(!(hasMoveX && hasMoveO)){
                return false;
            }
        }
        return true;
    }
}