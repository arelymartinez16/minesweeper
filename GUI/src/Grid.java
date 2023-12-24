// Link to video: https://youtu.be/hk0yHk4Z9ko

import java.util.Random;

public class Grid {
	// create member variables
	private boolean[][] bombGrid;
	private int[][] countGrid;
	private int numRows;
	private int numColumns;
	private int numBombs;
	
	// constructors
	public Grid() {
		numRows = 10;
		numColumns = 10;
		bombGrid = new boolean[numRows][numColumns];
		countGrid = new int[numRows][numColumns];
		numBombs = 25;
		createBombGrid();
		createCountGrid();
	}
	
	public Grid(int rows, int columns) {
		this();
		bombGrid = new boolean[rows][columns];
		countGrid = new int[rows][columns];
		createBombGrid();
		createCountGrid();
	}
	
	public Grid(int rows, int columns, int numBombs) {
		this();
		this.numBombs = numBombs;
		bombGrid = new boolean[rows][columns];
		countGrid = new int[rows][columns];
		createBombGrid();
		createCountGrid();
	}

	// for these two methods, return the copy of the array
	public boolean[][] getBombGrid() {
		boolean[][] bombGridCopy = new boolean[numRows][numColumns];
		
		for (int i = 0; i < bombGrid.length; i++) {
			for (int j = 0; j < bombGrid[i].length; j++) {
				bombGridCopy[i][j] = bombGrid[i][j];
			}
		}
		
		return bombGridCopy;
	}

	public int[][] getCountGrid() {
		int[][] countGridCopy = new int[numRows][numColumns];
		
		for (int i = 0; i < countGrid.length; i++) {
			for (int j = 0; j < countGrid[i].length; j++) {
				countGridCopy[i][j] = countGrid[i][j];
			}
		}
		
		return countGridCopy;
	}
	
	public boolean isBombAtLocation(int row, int column) {
		return bombGrid[row][column];
	}	

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public int getNumBombs() {
		return numBombs;
	}
	
	// this method is similar to the isBombAtLocation method
	public int getCountAtLocation(int row, int column) {
		return countGrid[row][column];
	}
	
	// private methods
	private void createBombGrid() {
		// by default, the elements are set to false
		bombGrid = new boolean[numRows][numColumns];
		Random rand = new Random();
		
		int bombsPlaced = 0;
		
		// I created a while loop to make sure that the bombs are randomly placed without exceeding the limit of 25 bombs
		while (bombsPlaced < numBombs) {
			int row = rand.nextInt(numRows);
			int column = rand.nextInt(numColumns);
			
			// I created this if statement to make sure I don't put the bomb in the same location more than once
			if (!bombGrid[row][column]) {
				bombGrid[row][column] = true;
				bombsPlaced++;
			}
		}
	}
	
	private void createCountGrid() {
		countGrid = new int[numRows][numColumns];
		
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				// bunch of if statements for each location
				if (isBombAtLocation(i, j)) {
					countGrid[i][j]++;
				} 
				
				if (i - 1 >= 0 && i - 1 < numRows && j >= 0 && j < numColumns && isBombAtLocation(i - 1, j)) {
					countGrid[i][j]++;
				}
				
				if (i - 1 >= 0 && i - 1 < numRows && j + 1 >= 0 && j + 1 < numColumns && isBombAtLocation(i - 1, j + 1)) {
					countGrid[i][j]++;
				}
				
				if (i >= 0 && i < numRows && j - 1 >= 0 && j - 1 < numColumns && isBombAtLocation(i, j - 1)) {
					countGrid[i][j]++;
				}
				
				if (i >= 0 && i < numRows && j + 1 >= 0 && j + 1 < numColumns && isBombAtLocation(i, j + 1)) {
					countGrid[i][j]++;
				}
				
				if (i + 1 >= 0 && i + 1 < numRows && j - 1 >= 0 && j - 1 < numColumns && isBombAtLocation(i + 1, j - 1)) {
					countGrid[i][j]++;
				}
				
				if (i + 1 >= 0 && i + 1 < numRows && j >= 0 && j < numColumns && isBombAtLocation(i + 1, j)) {
					countGrid[i][j]++;
				}
				
				if (i + 1 >= 0 && i + 1 < numRows && j + 1 >= 0 && j + 1 < numColumns && isBombAtLocation(i + 1, j + 1)) {
					countGrid[i][j]++;
				}
				
				if (i - 1 >= 0 && i - 1 < numRows && j - 1 >= 0 && j - 1 < numColumns && isBombAtLocation(i - 1, j - 1)) {
					countGrid[i][j]++;
				}
			}
		}
	} 
}
