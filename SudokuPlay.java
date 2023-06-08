/* Sudoku HW9
 * 
 * Name: Amelia G L Sharpe
 * PennKey: amsharpe
 * Recitation: 206
 * Execution: java SudokuPlay
 * This program is where the user must launch the game. All the necessary functions
 * have already been called in this main method. All the user has to do in order to 
 * run the game is the input a text file as args[0]. For example, I have provided
 * game1.txt (with no errors). If you would like to play this particular game, just
 * type into
 * the interactions pane: java SudokuPlay game1.txt
 */

public class SudokuPlay {
    public static void main(String[] args) {
        SudokuBoard game = new SudokuBoard(); //creates a new sudoku game called game
        String filename = args[0]; //put in the text file as the 1st argument
        game.readInText(filename); //this reads in the text file input
        game.draw(); // this draws the board game with the input numbers from file
        /*this is what starts the game. The game is in activate as long as the user
        has not won */
        game.activate(); 
        
    }
}
