import java.awt.*;        // Uses AWT's Layout Managers
import java.awt.event.*;  // Uses AWT's Event Handlers
//import java.util.concurrent.Flow;
import javax.swing.*;     // Uses Swing's Container/Components

/**
 * The Sudoku game.
 * To solve the number puzzle, each row, each column, and each of the
 * nine 3×3 sub-grids shall contain all of the digits from 1 to 9
 */
public class GUI extends JFrame {
    //    Constants for the game
    static final int GRID_SIZE = 9;
    static final int SET_SIZE = (int)Math.sqrt(GRID_SIZE);

    //  UI Constants
    static final int CELL_SIZE = 60;
    static final int DISPLAY_WIDTH = CELL_SIZE * GRID_SIZE;
    static final int DISPLAY_HEIGHT = DISPLAY_WIDTH;
    static final Color FREE_CELL_BGCOLOR = Color.CYAN;
    static final Color CORRECT_NUM_COLOR = Color.GREEN;
    static final Color INCORRECT_NUM_COLOR = Color.RED;
    static final Color FULL_CELL_BGCOLOR = Color.LIGHT_GRAY;
    static final Color FULL_TEXT_COLOR = Color.BLACK;
    //    Game board Data
    private JTextField[][] cells = new JTextField[GRID_SIZE][GRID_SIZE];
    //
    // Puzzle to be solved and the mask (which can be used to control the
    //  difficulty level).
    // Hardcoded here. Extra credit for automatic puzzle generation
    //  with various difficulty levels.
    int[][] _puzzle;
    Tile[][] _solution;
    //     For testing, open only 2 cells.
    private Board _myBoard;

    /**
     * Constructor to setup the game and the UI Components
     */
    public GUI(int[][] puzzle) {
        _puzzle = puzzle;
        _myBoard = new Board(puzzle, GRID_SIZE);
        JFrame myFrame = new JFrame("Sudoku");
        FlowLayout layout = new FlowLayout();
        myFrame.setLayout(layout);
        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(new SolveListener());
        myFrame.add(solveButton);
        Container cp = getContentPane();
        cp.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));  // 9x9 GridLayout

        // Allocate a common listener as the ActionEvent listener for all the
        //  JTextFields
        // ... [TODO 3] (Later) ....
        InputListener listener = new InputListener();

        // Construct 9x9 JTextFields and add to the content-pane
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                cells[row][col] = new JTextField(); // Allocate element of array
                cp.add(cells[row][col]);            // ContentPane adds JTextField
//                _myBoard.addTile(new Tile(row, col, GRID_SIZE));
                if (_puzzle[row][col] == 0) {
                    cells[row][col].setText("");     // set to empty string
                    cells[row][col].setEditable(true);
                    cells[row][col].setBackground(FREE_CELL_BGCOLOR);

                    // Add ActionEvent listener to process the input
                    // ... [TODO 4] (Later) ...
                    cells[row][col].addActionListener(listener);   // For all editable rows and cols

                } else {
//                    Tile myTile = _myBoard.getTile(row, col);
//                    myTile.setVal(_puzzle[row][col]);
                    cells[row][col].setText(_puzzle[row][col] + "");
                    cells[row][col].setEditable(false);
                    cells[row][col].setBackground(FULL_CELL_BGCOLOR);
                    cells[row][col].setForeground(FULL_TEXT_COLOR);
                }
                // Beautify all the cells
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
            }
        }

        // Set the size of the content-pane and pack all the components
        //  under this container.
        cp.setPreferredSize(new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT));
        myFrame.add(cp);
        myFrame.pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Handle window closing
        myFrame.setTitle("Sudoku");
        myFrame.setVisible(true);

    }

    /** The entry main() entry method */
    public static void main(String[] args) {
        // [TODO 1] (Now)
        int[][] puzzle =
                {       {0, 0, 3, 0, 5, 0, 0, 0, 0},
                        {0, 0, 2, 0, 4, 0, 5, 0, 0},
                        {9, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 4, 0, 0, 2, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 8, 4, 0, 0},
                        {0, 0, 8, 0, 0, 7, 0, 0, 1},
                        {0, 0, 0, 0, 9, 2, 0, 0, 8},
                        {0, 8, 5, 0, 0, 0, 1, 0, 0},
                        {1, 2, 0, 5, 0, 0, 0, 3, 0}};

        GUI myGUI = new GUI(puzzle);
//        myGUI._myBoard.solve();
//        myGUI._solution = myGUI._myBoard._tiles;
    }

    // Define the Listener Inner Class
    // ... [TODO 2] (Later) ...
    // [TODO 2]
    // Inner class to be used as ActionEvent listener for ALL JTextFields
    private class SolveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            _myBoard.solve();
            _solution = _myBoard._tiles;
            for (int row = 0; row < GRID_SIZE; ++row) {
                for (int col = 0; col < GRID_SIZE; ++col) {
                    if (_puzzle[row][col] == 0) {
                        cells[row][col].setText(Integer.toString(_solution[row][col]._num));
                        cells[row][col].setBackground(Color.YELLOW);
                    }
                }
            }
        }
    }
    private class InputListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
//            _myBoard.solve();
//             All the 9*9 JTextFileds invoke this handler. We need to determine
//             which JTextField (which row and column) is the source for this invocation.
            int rowSelected = -1;
            int colSelected = -1;

            // Get the source object that fired the event
            JTextField source = (JTextField)e.getSource();
            // Scan JTextFileds for all rows and columns, and match with the source object
            boolean found = false;
            for (int row = 0; row < GRID_SIZE && !found; ++row) {
                for (int col = 0; col < GRID_SIZE && !found; ++col) {
                    if (cells[row][col] == source) {
                        rowSelected = row;
                        colSelected = col;
                        found = true;  // break the inner/outer loops
                    }
                }
            }

            /*
             * [TODO 5]
             * 1. Get the input String via tfCells[rowSelected][colSelected].getText()
             * 2. Convert the String to int via Integer.parseInt().
             * 3. Assume that the solution is unique. Compare the input number with
             *    the number in the puzzle[rowSelected][colSelected].  If they are the same,
             *    set the background to green (Color.GREEN); otherwise, set to red (Color.RED).
             */
            String input = cells[rowSelected][colSelected].getText();
            Integer num = Integer.parseInt(input);
            if (_solution[rowSelected][colSelected]._num != num) {
                cells[rowSelected][colSelected].setBackground(INCORRECT_NUM_COLOR);
            } else {
                cells[rowSelected][colSelected].setBackground(CORRECT_NUM_COLOR);
            }
            /*
             * [TODO 6] Check if the player has solved the puzzle after this move.
             * You could update the masks[][] on correct guess, and check the masks[][] if
             * any input cell pending.
             */
            boolean solved = true;
            for (int i = 0 ; i < GRID_SIZE; i += 1) {
                for (int j = 0; j < GRID_SIZE; j += 1) {
                    if (num != _solution[i][j]._num) {
                        solved = false;
                    }
                }
            }
            if (solved) {
                JOptionPane.showMessageDialog(null, "Congratulations!");
            }
        }
        }
    }
