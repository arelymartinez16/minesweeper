import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Minesweeper_GUI extends JFrame {
	// similar to the shared drive
	private JPanel jpMain;
	private MinesweeperBoard board;
	
	// create default constructor for this class
	public Minesweeper_GUI() {
		jpMain = new JPanel();
		jpMain.setLayout(new BorderLayout());
		board = new MinesweeperBoard();
		jpMain.add(board, BorderLayout.CENTER);
		add(jpMain);
		// change the size to a number greater than 500 to see the count of the grid
		setSize(800, 800);
		setVisible(true);
		// this method is used to see what operation the program performs when the user closes the JFrame
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		board.printBombGrid();
		board.printCountGrid();
	}
	
	// create inner class to display the board
	private class MinesweeperBoard extends JPanel implements ActionListener {
		// NOTE: don't add action listener more than once, maybe once per component and you can call the methods from the Grid class to check if there is a bomb at the cell that the user clicked
		// for the extra credit, use JOptionPane to ask the user to add number of rows and columns. Connect it to the first part of the extra credit
		// create constant variables that represent the number of rows and columns we're using to display the 10 x 10 board
		// i got the idea from the shared drive TicTacToe GUI
		private static final int NUM_ROWS = 10;
		private static final int NUM_COLUMNS = 10;
		private static final int NUM_BOMBS = 25;
		// create a variable that keeps track of the cells being clicked in order to determine if the user won the game
		private int cellsClicked = 0;
		private JButton[][] msBoard;
		private boolean[][] bombGrid;
		private int[][] countGrid;
		// create a 2d boolean array to see if this helps with the 0 cells being counted once
		private boolean[][] revealedCells;
		// from calculator gui
		ImageIcon bombIcon = new ImageIcon("images/bomb-icon.png");
		ImageIcon bombIconScaled = new ImageIcon(bombIcon.getImage().getScaledInstance(50, 50, Image.SCALE_FAST));
		
		// create a default constructor for this inner class
		// i still used the shared drive
		private MinesweeperBoard() {
			setLayout(new GridLayout(NUM_ROWS, NUM_COLUMNS));
			displayBoard();
			revealedCells = new boolean[NUM_ROWS][NUM_COLUMNS];
			// recreate the methods from the Grid class to deal with the bomb grid and count grid
			createBombGrid();
			createCountGrid();
		}
		
		// might need to create other constructors for the extra credit 
		
		// place methods that are useful from the Grid class
		private void createBombGrid() {
			bombGrid = new boolean[NUM_ROWS][NUM_COLUMNS];
			Random rand = new Random();
			
			int bombsPlaced = 0;
			
			// I created a while loop to make sure that the bombs are randomly placed without exceeding the limit of 25 bombs
			while (bombsPlaced < NUM_BOMBS) {
				int row = rand.nextInt(NUM_ROWS);
				int column = rand.nextInt(NUM_COLUMNS);
				
				// I created this if statement to make sure I don't put the bomb in the same location more than once
				if (!bombGrid[row][column]) {
					bombGrid[row][column] = true;
					// msBoard[row][column].setIcon(bombIconScaled);
					bombsPlaced++;
				}
			}
		}
		
		private void createCountGrid() {
			countGrid = new int[NUM_ROWS][NUM_COLUMNS];
			
			for (int i = 0; i < NUM_ROWS; i++) {
				for (int j = 0; j < NUM_COLUMNS; j++) {
					// bunch of if statements for each location
					if (isBombAtLocation(i, j)) {
						countGrid[i][j]++;
						// msBoard[i][j].setText(countGrid[i][j]);
					} 
					
					if (i - 1 >= 0 && i - 1 < NUM_ROWS && j >= 0 && j < NUM_COLUMNS && isBombAtLocation(i - 1, j)) {
						countGrid[i][j]++;
					}
					
					if (i - 1 >= 0 && i - 1 < NUM_ROWS && j + 1 >= 0 && j + 1 < NUM_COLUMNS && isBombAtLocation(i - 1, j + 1)) {
						countGrid[i][j]++;
					}
					
					if (i >= 0 && i < NUM_ROWS && j - 1 >= 0 && j - 1 < NUM_COLUMNS && isBombAtLocation(i, j - 1)) {
						countGrid[i][j]++;
					}
					
					if (i >= 0 && i < NUM_ROWS && j + 1 >= 0 && j + 1 < NUM_COLUMNS && isBombAtLocation(i, j + 1)) {
						countGrid[i][j]++;
					}
					
					if (i + 1 >= 0 && i + 1 < NUM_ROWS && j - 1 >= 0 && j - 1 < NUM_COLUMNS && isBombAtLocation(i + 1, j - 1)) {
						countGrid[i][j]++;
					}
					
					if (i + 1 >= 0 && i + 1 < NUM_ROWS && j >= 0 && j < NUM_COLUMNS && isBombAtLocation(i + 1, j)) {
						countGrid[i][j]++;
					}
					
					if (i + 1 >= 0 && i + 1 < NUM_ROWS && j + 1 >= 0 && j + 1 < NUM_COLUMNS && isBombAtLocation(i + 1, j + 1)) {
						countGrid[i][j]++;
					}
					
					if (i - 1 >= 0 && i - 1 < NUM_ROWS && j - 1 >= 0 && j - 1 < NUM_COLUMNS && isBombAtLocation(i - 1, j - 1)) {
						countGrid[i][j]++;
					}
				}
			}
		}
		
		public boolean isBombAtLocation(int row, int column) {
			return bombGrid[row][column];
		}
		
		public int getCountAtLocation(int row, int column) {
			return countGrid[row][column];
		}
		
		// methods directly related to the gui
		// also from the shared drive tic tac toe gui
		public void displayBoard() {
			msBoard = new JButton[NUM_ROWS][NUM_COLUMNS];
			
			for (int i = 0; i < msBoard.length; i++) {
				for (int j = 0; j < msBoard[i].length; j++) {
					// might need to make changes here later when I run the gui
					msBoard[i][j] = new JButton();
					Font font = new Font(Font.SANS_SERIF, Font.BOLD, 36);
					msBoard[i][j].setForeground(Color.BLACK);
					msBoard[i][j].setFont(font);
					msBoard[i][j].addActionListener(this);
					msBoard[i][j].setEnabled(true);
					// somewhere over here or in the createBombGrid method put the bomb icon on places where the bomb is and hide it 
					// msBoard[i][j].setIcon(bombIconScaled);
					this.add(msBoard[i][j]);
				}
			}
		}
		
		// NOTE: Use ImageIcon class to add icons into the gui such as bombs and it will be visible if the user clicks on a cell that has a bomb

		// shared drive TicTacToe gui
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btnClicked = (JButton) e.getSource();
			
			// call isBombAtLocation method and then set the icon
			for (int i = 0; i < msBoard.length; i++) {
				for (int j = 0; j < msBoard[i].length; j++) {
					if (btnClicked == msBoard[i][j]) {
						if (isBombAtLocation(i, j)) {
							// modify this statement in order to make all the icons appear when they click on a bomb
							btnClicked.setIcon(bombIconScaled);
							// create a method that displays all bomb icons
							displayAllIcons();
							displayLoser();
						}
						// reveal the count if there isn't a bomb
						else if (!isBombAtLocation(i, j)){
							if (getCountAtLocation(i, j) == 0) {
								revealAdjacentCells(i, j);
							} 
							
							btnClicked.setText(String.valueOf(countGrid[i][j]));
							btnClicked.setEnabled(false);
							// increment this for the later if statement
							// so the cellsClicked doesn't increment the cell clicked twice
							if (!revealedCells[i][j]) {
								cellsClicked++;
							} 
							
							revealedCells[i][j] = true;
							// cellsClicked++;
							System.out.println(cellsClicked);
						}
						// if the user clicks on the last cell that doesn't have a bomb then the user won the game
						if (cellsClicked == (NUM_ROWS * NUM_COLUMNS) - NUM_BOMBS) {
							btnClicked.setText(String.valueOf(countGrid[i][j]));
							displayWinner();
						} 
						// create if statement 
						/* else if (countGrid[i][j] == 0) {
							revealAdjacentCells(i, j);
						} */
					}
					// cellsClicked++;
				}
			}
		}
		
		private void revealAdjacentCells(int row, int col) {
			// in case the cell was already revealed, 
			if (revealedCells[row][col]) {
				return;
			}
			
			msBoard[row][col].setText(String.valueOf(countGrid[row][col]));
			msBoard[row][col].setEnabled(false);
			revealedCells[row][col] = true;
			cellsClicked++; 
			
			// All adjacent and connected 0 cells are revealed and all (non-bomb) cells immediately adjacent to the revealed 0 cells are also revealed
			for (int i = 0; i < msBoard.length; i++) {
				for (int j = 0; j < msBoard[i].length; j++) {			
					if (countGrid[i][j] == 0 && i - 1 >= 0 && i - 1 < NUM_ROWS && j >= 0 && j < NUM_COLUMNS && msBoard[i - 1][j].isEnabled() && !isBombAtLocation(i - 1, j)) {
						revealAdjacentCells(i - 1, j);
					}
					
					if (countGrid[i][j] == 0 && i - 1 >= 0 && i - 1 < NUM_ROWS && j + 1 >= 0 && j + 1 < NUM_COLUMNS && msBoard[i - 1][j + 1].isEnabled() && !isBombAtLocation(i - 1, j + 1)) {
						revealAdjacentCells(i - 1, j + 1);
					}
					
					if (countGrid[i][j] == 0 && i >= 0 && i < NUM_ROWS && j - 1 >= 0 && j - 1 < NUM_COLUMNS && msBoard[i][j - 1].isEnabled() && !isBombAtLocation(i, j - 1)) {
						revealAdjacentCells(i, j - 1);
					}
					
					if (countGrid[i][j] == 0 && i >= 0 && i < NUM_ROWS && j + 1 >= 0 && j + 1 < NUM_COLUMNS && msBoard[i][j + 1].isEnabled() && !isBombAtLocation(i, j + 1)) {
						revealAdjacentCells(i, j + 1);
					}
					
					if (countGrid[i][j] == 0 && i + 1 >= 0 && i + 1 < NUM_ROWS && j - 1 >= 0 && j - 1 < NUM_COLUMNS && msBoard[i + 1][j - 1].isEnabled() && !isBombAtLocation(i + 1, j - 1)) {
						revealAdjacentCells(i + 1, j - 1);
					} 
					
					if (countGrid[i][j] == 0 && i + 1 >= 0 && i + 1 < NUM_ROWS && j >= 0 && j < NUM_COLUMNS && msBoard[i + 1][j].isEnabled() && !isBombAtLocation(i + 1, j)) {
						revealAdjacentCells(i + 1, j);
					}
					
					if (countGrid[i][j] == 0 && i + 1 >= 0 && i + 1 < NUM_ROWS && j + 1 >= 0 && j + 1 < NUM_COLUMNS && msBoard[i + 1][j + 1].isEnabled() && !isBombAtLocation(i + 1, j + 1)) {
						revealAdjacentCells(i + 1, j + 1);
					} 
					
					if (countGrid[i][j] == 0 && i - 1 >= 0 && i - 1 < NUM_ROWS && j - 1 >= 0 && j - 1 < NUM_COLUMNS && msBoard[i - 1][j - 1].isEnabled() && !isBombAtLocation(i - 1, j - 1)) {
						revealAdjacentCells(i - 1, j - 1);
					}
				}
			} 
		}
		
		private void displayAllIcons() {
			for (int i = 0; i < bombGrid.length; i++) {
				for (int j = 0; j < bombGrid[i].length; j++) {
					if (bombGrid[i][j]) {
						msBoard[i][j].setIcon(bombIconScaled); 
						// msBoard[i][j].setEnabled(false);
					}
					else {
						msBoard[i][j].setText(Integer.toString(getCountAtLocation(i, j)));
						// msBoard[i][j].setEnabled(false);
					}
				}
			}
		}
		
		// create methods for possible outcomes of the game
		public void displayLoser() {
			// JOptionPane
			int choice = JOptionPane.showConfirmDialog(null, "You Lost.\nDo you want to play again?", "Yes or No", JOptionPane.YES_NO_OPTION);
			
			if (choice == JOptionPane.YES_OPTION) {
				new Minesweeper_GUI();
			}
			else {
				System.exit(0);
			}
		}
		
		public void displayWinner() {
			int choice = JOptionPane.showConfirmDialog(null, "You win!\nDo you want to play again?", "Yes or No", JOptionPane.YES_NO_OPTION);
			
			if (choice == JOptionPane.YES_OPTION) {
				new Minesweeper_GUI();
			}
			else {
				System.exit(0);
			}
		}
		
		public void printBombGrid() {
			for (int i = 0; i < bombGrid.length; i++) {
				for (int j = 0; j < bombGrid[i].length; j++) {
					System.out.print(bombGrid[i][j] + " ");
				}
				
				System.out.println();
			}
		}
		
		public void printCountGrid() {
			for (int i = 0; i < countGrid.length; i++) {
				for (int j = 0; j < countGrid[i].length; j++) {
					System.out.print(countGrid[i][j] + " ");
				}
				
				System.out.println();
			}
		}
		
	}
}
