package minesweeper;

public class MineSweeper {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int rows = 6;
		int cols = 12;
		
		printGrid(rows, cols);
	}

	public static void printGrid(int rows, int cols) {
		for(int i = 0; i<rows; i++) {
			for (int j =0; j<cols;j++) {
				System.out.print("+");
				
			}
			System.out.println();
		}
	}

}
