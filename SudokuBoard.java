/* Sudoku HW9
 * 
 * Name: Amelia G L Sharpe
 * PennKey: amsharpe
 * Recitation: 206
 * Execution: java SudokuBoard
 * This is a class which contains the object SudokuBoard. SudokuBoard is what 
 * creates each sudoku game, and contains the functionality of the game.
 */


public class SudokuBoard {
    private double mouseX; //x coordinate of the mouseclick
    private double mouseY; //y coordinate of the mouseclick
    private String[][] numberInputs; //2D  array that stores the keyed-in numbers
    private SmallBox[][] wholeBoard; //2D array that stores SmallBoxes
    private String keyInNumberAsString; //stores the keyed-in number as a string
    private double drawTextY; // the y coordinate where the input number is drawn
    private double drawTextX; // the x coordinate where the input number is drawn
    //the column index where the input number is stored in numberInputs
    private int columnIndexText;
    //the row index where the input number is stored in numberInputs
    private int rowIndexText; 
    private boolean[][] isPermanentNumber; //checks to mark permanent board numbers
    private String[][] permanentNumbers; //this stores the permanent numbers
    private boolean errorExists; //checks if errors exists in all row.column and box
    private boolean errorExistsC; //checks if error exists in a column
    private boolean errorExistsR; //checks if error exists in a row
    private boolean errorExists1; //checks if error exists in 1 of 6 bolded boxes
    private boolean errorExists2; //checks if error exists in 1 of 6 bolded boxes
    private boolean errorExists3; //checks if error exists in 1 of 6 bolded boxes
    private boolean errorExists4; //checks if error exists in 1 of 6 bolded boxes
    private boolean errorExists5; //checks if error exists in 1 of 6 bolded boxes
    private boolean errorExists6; //checks if error exists in 1 of 6 bolded boxes
    
    /* 
     * Description: This is the constructor for SudokBoard. It allows the user to
     * create a new Sudoku Board.
     * */
    public SudokuBoard() {
        numberInputs = new String[6][6]; 
        isPermanentNumber = new boolean[6][6];
        permanentNumbers = new String[6][6];
    }
    
    /*
     * Description: this reads in and places the original numbers on the board
     * input: the file you want to read from. This is also where the input is 
     * tested for errors
     * output: none
     * */
    public void readInText(String filename) {
        In inStream = new In(filename);
        int countRows = 0;
        while (!inStream.isEmpty()) {
            inStream.readLine();
            countRows++;
        }
        if (countRows > 6 || countRows < 6) {
            throw new RuntimeException("This sudoku game must have 6 rows");
        }
        
        //makes sure that the numbers can fit in a 6x6 grid
        inStream = new In(filename);
        String countColumns = "";
        for (int i = 0; i < countRows; i++) {
            countColumns = inStream.readLine();
            if (countColumns.length() < 6) {
                throw new RuntimeException("Each column must have 6 numbers");
            }
        }
        
        //check if the input size fits in the 6x6 array
        //count number of characters
        inStream = new In(filename);
        String testContents = inStream.readAll();
        
        
        /*The number 41 is used here because the enter key also holds value and is on
         * the ascii table as having a value of 10
         * */
        if (testContents.length() < 41 || testContents.length() > 41) {
            throw new RuntimeException("This sudoku game must be 6x6 only");
        }
        
        //makes sure that there is only whitespace and digits from 1-6
        for (int i = 0; i < testContents.length(); i++) {
            if (!testContents.substring(i, i + 1).equals(" ") &&
                !testContents.substring(i, i + 1).equals("1") &&
                !testContents.substring(i, i + 1).equals("2") && 
                !testContents.substring(i, i + 1).equals("3") &&
                !testContents.substring(i, i + 1).equals("4") &&
                !testContents.substring(i, i + 1).equals("5") &&
                !testContents.substring(i, i + 1).equals("6")) {
                //this excludes the enter key from problems
                if (!((int) testContents.charAt(i) == 10)) {  
                    throw new RuntimeException("Put only whitespace & digits 1-6"); 
                }
            }
        }
        
        
//        //makes sure there are no repetitions in the rows
//        for (int i = 0; i < countRows; i++) {
//            for (int k = 0; k < countColumns.length(); k++) {
//                if (i != k) {
//                    if (testContents.substring(i, i + 1).equals(
//                                                                testContents.
//                                                                    substring(
//                                                                              k, 
//                                                                              k +
//   1)
//                                                               )) {
//                        if (!((int) testContents.charAt(i) == 10) &&
//                    !((int) testContents.charAt(k) == 10)) {
//                            thrownew RuntimeException("error:duplicates in rows"); 
//                        }
//                    }
//                }
//            }
//        }
        
        //this part stores the permanent numbers in the numberInputs array
        inStream = new In(filename);
        for (int e = 0; e < 6; e++) {
            String str = inStream.readLine();
            for (int f = 0; f < 6; f++) {
                //put numbers in the numberInputs grid for game interaction
                numberInputs[e][f] = "" + str.substring(f, f + 1); 
                PennDraw.setFontSize(50);
                PennDraw.setPenColor();
                //o is the x coordinate where the text is drawn
                //u is the y coordinate where the text in drawn
                double o = ((double) f) * ((double) 1 / 6) + ((double) 1 / 12); //x
                double u = ((double) e) * ((double) 1 / 6) + ((double) 1 / 12); //y
                PennDraw.text(o, u, numberInputs[e][f]);
                permanentNumbers[e][f] = "" + str.substring(f, f + 1);
            }
        }
        markPermanentNumbers();
    }
    /*
     * Description: this method just draws all the numbers from the textfile
     * no input
     * no output
     * */
    public void drawPermanentNumbers() {
        for (int e = 0; e < 6; e++) {
            for (int f = 0; f < 6; f++) {
                PennDraw.setFontSize(50);
                PennDraw.setPenColor();
                double o = ((double) f) * ((double) 1 / 6) + ((double) 1 / 12); //x
                double u = ((double) e) * ((double) 1 / 6) + ((double) 1 /  12); //y
                PennDraw.text(o, u, permanentNumbers[e][f]);
            }
        }
    }
    /*
     * Description: this method uses boolean to make sure the read-in numbers are
     * not tampered with. Permanent numbers are marked as true, while those which
     * can change are marked as false
     * no input
     * no output
     * */
    public void markPermanentNumbers() {
        for (int e = 0; e < 6; e++) {
            for (int f = 0; f < 6; f++) {
                if (numberInputs[e][f].equals(" ")) {
                    isPermanentNumber[e][f] = false;
                }
                else {
                    isPermanentNumber[e][f] = true; 
                }
            }
        }
    }
    
    /*
     * Description: this draws the sudoku board (the boxes and bold lines)
     * no input
     * no output 
     * */
    public void draw() {
        //draws 7 bold lines
        PennDraw.setPenRadius(0.008);
        PennDraw.line(0.0, 0.0, 0.0, 1); //leftside bold
        PennDraw.line(1, 0, 1, 1); // right side bold
        PennDraw.line(0.0, 0.0, 1, 0.0); //bottom bold
        PennDraw.line(0.0, 1, 1, 1); //top bold
        PennDraw.line((double) 1 / 2, 0.0, (double) 1 / 2, 1); //middle vertical bold
        PennDraw.line(0.0, (double) 1 / 3, 1, (double) 1 / 3); //3rd from top bold
        PennDraw.line(0.0, (double) 2 / 3, 1, (double) 2 / 3); //3rd from top box 
        
        mouseY = (double) 1 / 12;
        
        wholeBoard = new SmallBox [6][6];
        for (int i = 0; i < wholeBoard.length; i++) {
            mouseX = (double) 1 / 12;
            for (int j = 0; j < wholeBoard[i].length; j++) {
                wholeBoard[i][j] = new SmallBox(mouseX, mouseY);
                wholeBoard[i][j].draw();
                mouseX += (double) 1 / 6; 
            }
            mouseY += (double) 1 / 6;
            
        } 
    } 
    
    /*
     * Description: this launches the game. This is where you enter mouseMode
     * and keyBoard mode, as well as where you find out if you have won or not
     * no input
     * output: no output
     * */
    public void activate() {
        
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                /* enters empty space so that when comparing to the current
                 * number entered, there is no NPE
                 * */
                if (numberInputs[i][j].equals(" "))  {
                    numberInputs[i][j] = " "; 
                }
            }
        }
        boolean mouseMode = true;
        boolean keyboardMode = false;
        double x = 0;
        double y = 0;
        while (!hasWon()) {
            
            while (mouseMode) {
                if (PennDraw.mousePressed()) {
                    x = PennDraw.mouseX();
                    y = PennDraw.mouseY();
                    mouseMode = false;
                    keyboardMode = true; 
                    
                }
            }
            
            while (keyboardMode) {
                if (PennDraw.hasNextKeyTyped()) {
                    keyInNumberAsString = "";
                    keyInNumberAsString += PennDraw.nextKeyTyped();
                    if (keyInNumberAsString.equals("1") ||
                        keyInNumberAsString.equals("2") || 
                        keyInNumberAsString.equals("3") ||
                        keyInNumberAsString.equals("4") || 
                        keyInNumberAsString.equals("5") || 
                        keyInNumberAsString.equals("6") || 
                        keyInNumberAsString.equals(" ")) {
                        drawTextX = x - (x % ((double) 1 / 6)) + ((double) 1 / 12); 
                        drawTextY = y - (y % ((double) 1 / 6)) + ((double) 1 / 12); 
                        rowIndexText = (int) (drawTextY / ((double) 1 / 6)); 
                        columnIndexText = (int) (drawTextX / ((double) 1 / 6)); 
                        
                        if (!isPermanentNumber[rowIndexText][columnIndexText]) {
                            numberInputs[rowIndexText][columnIndexText] = 
                                keyInNumberAsString;
                            PennDraw.setFontSize(50);
                            PennDraw.setPenColor(PennDraw.WHITE);
                            PennDraw.filledSquare(drawTextX, drawTextY, 
                                                  (double) 1 / 13);
                            PennDraw.setPenColor(PennDraw.RED);
                            
                            PennDraw.text(drawTextX, drawTextY, keyInNumberAsString);
                            redrawBoard();
                            
                            errorExists1 =  boldedBoxCheck(0, 0); 
                            errorExists2 =  boldedBoxCheck(2, 0);
                            errorExists3 =  boldedBoxCheck(4, 0);
                            errorExists4 =  boldedBoxCheck(0, 3);
                            errorExists5 =  boldedBoxCheck(2, 3);
                            errorExists6 =  boldedBoxCheck(4, 3);
                            errorExistsC = columnCheck();
                            errorExistsR = rowCheck();
                            
                            errorExists = errorExistsR || errorExistsC || 
                                errorExists6 || errorExists5 || errorExists4 ||
                                errorExists3 || errorExists2 || errorExists1;
                            
                            PennDraw.setPenColor();
                        }
                        
                        if (!errorExists) {
                            keyboardMode = false;
                            mouseMode = true;
                        }
                    }
                    
                    else {
                        System.out.println("Enter numbers from 1-9 only");  
                    }
                    
                }
            }
        } 
    }
    
    /*
     * Description: this redraws the board to ensure the numbers are visible,
     * and to make sure that the problems are not drawn on top of each other 
     * every time
     * no input
     * no output
     * */
    public void redrawBoard() {
        //redraw boxes and lines too
        PennDraw.clear(); //clears the canvas
        PennDraw.setPenColor();
        draw(); //draws the boxes and bold lines
        drawPermanentNumbers(); //draws all the original numbers
        
        
        //redraws KEYED IN numbers only
        for (int h = 0; h < 6; h++) {
            for (int i = 0; i < 6; i++) {
                PennDraw.setPenColor(PennDraw.RED);
                PennDraw.setFontSize(50);
                double o = ((double) i) * ((double) 1 / 6) + ((double) 1 / 12); //x
                double u = ((double) h) * ((double) 1 / 6) + ((double) 1 / 12); //y
                
                if (!isPermanentNumber[h][i]) {
                    PennDraw.text(o, u, numberInputs[h][i]);
                } 
            }
        }   
    }
    /*
     * Description: this method inspects rows to check for errors. If an
     * error exists, the boolean is true, if not, the boolean is false
     * no input
     * output: a boolean. 
     * */
    public boolean rowCheck() {
        
        //row check
        double q = 0; // x coordinate for where grey area is drawn
        int g = 0;
        for (int h = 0; h < 6; h++) {
            for (int i = 0; i < 6; i++) {
                g = 0;
                while (g < 6) {
                    if (g != i) {
                        if (numberInputs[h][i].equals(numberInputs[h][g]) &&
                            !numberInputs[h][i].equals(" ") &&
                            !numberInputs[h][g].equals(" ")) {
                            
                            //use the indexes of only h (not g) to draw an error box
                            if (!isPermanentNumber[h][i]) {
                                
                                PennDraw.setPenRadius(0.003);
                                PennDraw.setPenColor(162, 170, 173,
                                                     50); //grey color
                                //grey rectangle
                                q = (1.0 / 6.0) * h + (1.0 / 12.0);
                                PennDraw.filledRectangle(0.5, q, 0.5, (double)
                                                             1 / 12);
                                PennDraw.setPenColor(183, 0, 3, 255); //red
                                PennDraw.setPenRadius(0.008);
                                double o = ((double) i) * ((double) 1 / 6) +
                                    ((double) 1 / 12); //x
                                double u = ((double) h) * ((double) 1 / 6) +
                                    ((double) 1 / 12); //y
                                //draw red square around problem keyed-in number
                                PennDraw.square(o, u, SmallBox.getHalfWidth());
                            }
                            
                            //use the indexes of g to draw around the other number
                            if (!isPermanentNumber[h][g]) {
                                PennDraw.setPenRadius(0.003);
                                PennDraw.setPenColor(162, 170, 173,
                                                     50); //grey color
                                //grey rectangle
                                q = (1.0 / 6.0) * h + 1.0 / 12.0;
                                PennDraw.filledRectangle(0.5, q, 0.5, (double)
                                                             1 / 12);
                                PennDraw.setPenColor(183, 0, 3, 255); //red
                                PennDraw.setPenRadius(0.008);
                                double o = ((double) g) * ((double) 1 / 6) +
                                    ((double) 1 / 12); //x
                                double u = ((double) h) * ((double) 1 / 6) +
                                    ((double) 1 / 12); //y
                                //draw red square around problem keyed-in number
                                PennDraw.square(o, u, SmallBox.getHalfWidth());
                            }
                            return true;  
                        }                         
                        
                    }
                    g++;
                }
            }
            
        }
        return false;
    }
    
    /*
     * Description: this method inspects rows to check for errors. If an
     * error exists, the boolean is true, if not, the boolean is false
     * no input
     * output: a boolean
     * */
    public boolean columnCheck() {
        double q = 0; // x coordinate for where grey area is drawn
        int g = 0;
        for (int h = 0; h < 6; h++) {
            for (int i = 0; i < 6; i++) {
                g = 0;
                while (g < 6) {
                    if (g != h) {
                        if (numberInputs[h][i].equals(numberInputs[g][i]) &&
                            !numberInputs[h][i].equals(" ") &&
                            !numberInputs[g][i].equals(" ")) {
                            
                            //use the indexes of only h (not g) to draw an error box
                            if (!isPermanentNumber[h][i]) {
                                PennDraw.setPenRadius(0.003);
                                PennDraw.setPenColor(162, 170, 173,
                                                     50); //grey color
                                //grey rectangle
                                q = (1.0 / 6.0) * i + 1.0 / 12.0;
                                PennDraw.filledRectangle(q, 0.5, (double) 1 / 12,
                                                         0.5);
                                PennDraw.setPenColor(183, 0, 3, 255); //red
                                PennDraw.setPenRadius(0.008);
                                double o = ((double) i) * ((double) 1 / 6) +
                                    ((double) 1 / 12); //x
                                double u = ((double) h) * ((double) 1 / 6) +
                                    ((double) 1 / 12); //y
                                //draw red square around problem keyed-in number
                                PennDraw.square(o, u, SmallBox.getHalfWidth());
                                
                            }
                            
                            //use the indexes of g to draw around the other number
                            if (!isPermanentNumber[g][i]) {
                                
                                PennDraw.setPenRadius(0.003);
                                PennDraw.setPenColor(162, 170, 173,
                                                     50); //grey color
                                //grey rectangle
                                q = (1.0 / 6.0) * i + 1.0 / 12.0;
                                PennDraw.filledRectangle(q, 0.5, (double) 1 / 12,
                                                         0.5);
                                PennDraw.setPenColor(183, 0, 3, 255); //red
                                PennDraw.setPenRadius(0.008);
                                double o = ((double) i) * ((double) 1 / 6) +
                                    ((double) 1 / 12); //x
                                double u = ((double) g) * ((double) 1 / 6) +
                                    ((double) 1 / 12); //y
                                //draw red square around problem keyed-in number
                                PennDraw.square(o, u, SmallBox.getHalfWidth());
                            }
                            return true;  
                        }                         
                    }
                    g++;
                }
            }
        }
        return false;
    }
    
    /*
     * Description: This method checks through all 6 bolded boxes for errors. All
     * bolded boxes have specific parameters
     * input: it takes in the indexes of the bottom left box in each bolded box
     * output: a boolean which tells you if there is an error in this bolded 2x3 box
     * */
    public boolean boldedBoxCheck(int h, int i) {
        int g = 0;
        int f = 0;
        for (int a = h; a <= h + 1; a++) {
            for (int b = i; b <= i + 2; b++) {
                //compare within same row
                g = 0;
                while (b + g < i + 3) {
                    if ((b + g) != b) {
                        //b = 0;
                        //System.out.println("b+g: " + b+g);
                        if (numberInputs[a][b].equals(numberInputs[a][b + g]) &&
                            !numberInputs[a][b].equals(" ") &&
                            !numberInputs[a][b + g].equals(" ")) {
                            if (!isPermanentNumber[a][b]) {
                                PennDraw.setPenRadius(0.003);
                                PennDraw.setPenColor(162, 170, 173,
                                                     50); //grey color
                                //grey rectangle
                                double  p;
                                if (i % 2 == 0) {
                                    p = 0.25;
                                }
                                else {
                                    p = 0.75;
                                }
                                PennDraw.filledRectangle(p , (double) 1 / 6 * h +
                                                         (double) 1 / 6,
                                                         0.25, 
                                                         SmallBox.getHalfWidth() *
                                                         2);
                                
                                PennDraw.setPenColor(183, 0, 3, 255); //red
                                PennDraw.setPenRadius(0.008);
                                double o = ((double) b) * ((double) 1 / 6) +
                                    ((double) 1  / 12); //x
                                double u = ((double) a) * ((double) 1  / 6) +
                                    ((double) 1 / 12); //y
                                //draw red square around problem keyed-in number
                                PennDraw.square(o, u, SmallBox.getHalfWidth());
                            }
                            
                            if (!isPermanentNumber[a][b + g]) {
                                PennDraw.setPenRadius(0.003);
                                PennDraw.setPenColor(162, 170, 173,
                                                     50); //grey color
                                //grey rectangle
                                if (i % 2 == 0) {
                                    PennDraw.filledRectangle(0.25 , (double) 1 / 6 *
                                                             h + (double) 1  / 6,
                                                             0.25, SmallBox.
                                                                 getHalfWidth() * 2);
                                }
                                else {
                                    PennDraw.filledRectangle(0.75 , (double) 1 / 6 *
                                                             h + (double) 1 / 6,
                                                             0.25, SmallBox.
                                                                 getHalfWidth() * 2);
                                }
                                PennDraw.setPenColor(183, 0, 3, 255); //red
                                PennDraw.setPenRadius(0.008);
                                double o = ((double) (b + g)) * ((double) 1 / 6) +
                                    ((double) 1 / 12); //x
                                double u = ((double) a) * ((double) 1 / 6) +
                                    ((double) 1 / 12); //y
                                //draw red square around problem keyed-in number
                                PennDraw.square(o, u, SmallBox.getHalfWidth());
                            } 
                            return true;  
                        }
                    }
                    g++;
                }
                
                //compare across the other row
                f = i; 
                g = h;
                while (g < h + 2) { //ensures im in valid row
                    if (a != g) {
                        //b = 0;
                        f = i;
                        while (f < (i + 3)) {
                            if (numberInputs[a][b].equals(numberInputs[g][f]) &&
                                !numberInputs[a][b].equals(" ") &&
                                !numberInputs[g][f].equals(" ")) {
                                if (!isPermanentNumber[a][b]) {
                                    PennDraw.setPenRadius(0.003);
                                    PennDraw.setPenColor(162, 170, 173,
                                                         50); //grey color
                                    //grey rectangle
                                    if (i % 2 == 0) {
                                        PennDraw.filledRectangle(0.25, 
                                                                 (double) 1 / 6 *
                                                                 h +
                                                                 (double) 1 / 6,
                                                                 0.25, 
                                                                 SmallBox.
                                                                     getHalfWidth() *
                                                                 2);
                                    }
                                    else {
                                        PennDraw.filledRectangle(0.75, 
                                                                 (double) 1 / 6 *
                                                                 h +
                                                                 (double) 1 / 6,
                                                                 0.25,
                                                                 SmallBox.
                                                                     getHalfWidth() *
                                                                 2);
                                    }
                                    PennDraw.setPenColor(183, 0, 3, 255); //red
                                    PennDraw.setPenRadius(0.008);
                                    double o = ((double) b) * ((double) 1 / 6) +
                                        ((double) 1 / 12); //x
                                    double u = ((double) a) * ((double) 1 / 6) +
                                        ((double) 1 / 12); //y
                                    //draw red square around problem keyed-in number
                                    PennDraw.square(o, u, SmallBox.getHalfWidth());
                                }
                                if (!isPermanentNumber[g][f]) {
                                    PennDraw.setPenRadius(0.003);
                                    PennDraw.setPenColor(162, 170, 173,
                                                         50); //grey color
                                    //grey rectangle
                                    if (i % 2 == 0) {
                                        PennDraw.filledRectangle(0.25,
                                                                 (double) 1 / 6 *
                                                                 h + (double) 1 / 6,
                                                                 0.25, 
                                                                 SmallBox.
                                                                     getHalfWidth() *
                                                                 2);
                                    }
                                    else {
                                        PennDraw.filledRectangle(0.75, 
                                                                 (double) 1 / 6 *
                                                                 h + (double) 1 / 6,
                                                                 0.25, 
                                                                 SmallBox.
                                                                     getHalfWidth() *
                                                                 2);
                                    }
                                    PennDraw.setPenColor(183, 0, 3, 255); //red
                                    PennDraw.setPenRadius(0.008);
                                    double o = ((double) f) * ((double) 1 / 6) +
                                        ((double) 1 / 12); //x
                                    double u = ((double) g) * ((double) 1 / 6) +
                                        ((double) 1 / 12); //y
                                    //draw red square around problem keyed-in number
                                    PennDraw.square(o, u, SmallBox.getHalfWidth());
                                }
                                return true;
                            }
                            f++;
                        }
                    }
                    g++;
                }
            }
        }
        return false;
    }
    
    
    /*
     * Description: this is one important step to check if the user has truly won a
     * game. It checks to make sure that there are no whitespaces in the board
     * no input
     * output: a boolean, which is true if the board is filled and false if there is
     * still whitespaces
     * */
    public boolean isBoardFilledCorrectly() {
        for (int h = 0; h < 6; h++) {
            for (int i = 0; i < 6; i++) {
                if (numberInputs[h][i].equals(" ")) {
                    return false;
                }
            }
        }
        return true;
    }
    /*
     * Description: if the board has no errors and is filled correctly, the
     * user wins and the game is deactivated. When the user wins, a song and
     * a congratulatory message is played and displayed respectively.
     * no input
     * output: a boolean which tells if you the user has won
     * */
    public boolean hasWon() {
        if (errorExists == false && isBoardFilledCorrectly() == true) {
            PennDraw.text(0.5, 0.5, "YOU WIN!!!");
            StdAudio.play("congrats.mid");
            return true;
        }
        return false;
    }
}