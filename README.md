My object oriented game consists of:
1. SmallBox.java: SmallBox created an object which creates each of the 36 small boxes
on the Sudoku Grid
2. SudokuBoard.java: This is an object class which enables the user to create a new
Sudoku game. It also contains the functionality of the game.
3. SudokuPlay.java: This is where the main method is. In this main method, 
I have already provided all the functionality needed to begin the game. So
all the user needs to do in order to play a Sudoku game, is to type the
following into the interactions pane:
 java SudokuPlay filename. 
 4. an audio clip with congratulatory song
 5. game1 and game 2(two text files with no errors)

Restrictions: My personal creation of the sudoku reads the numbers in in a different
way. The array [0][0] is actually in the bottom left corner. 
The columns increase to the right, and the rows increase upward. 
I have provided game1.txt and game2.txt to illustrate this. 
However, no matter in which order my
array grid is filled, the answer order will always be the same. That is, even
though my array may look "upside down", the same answers apply to my game too. If
you would like to view the Sudoku in a certain way, just follow this:

If you have a text that you would like to fill into my Sudoku program,
just made sure that the first line you want to fill the topmost row of the grid must
be at the bottom. And the line which you want to go into the bottommost row must be
at the topmost of your text file.
Otherwise, the answer to a specific text file will be the same for my sudoku game too
So it does not pose any problems at all.

All permanent numbers will be black. Any number you enter will be red color. Any
problem number inside a row, column or bolded box will be surrounded by a red square.
The permanent numbers will never be surrounded by a red square because 
permanent numbers are not supposed to be changed.

My game highlights a row, a column or bolded box in grey pen if an error exists
there.

if you make an error, the game will not let you go on until you correct that error.
That is, even if you click somewhere else and enter a number, the number change will
always happen in the highlighted box.

When the user wins a game, a congratulatory message and a song is played. All the
necessary files have been provided.
