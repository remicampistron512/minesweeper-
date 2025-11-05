package minesweeper;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

public class MineSweeper {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int rows = 6;
		int cols = 12;
		
		printGrid(rows, cols);
		Map <Integer, int []> bombs = new HashMap<>();
		
		int nbBombs = 9;
		
		for (int i =0; i <nbBombs;i++) {
			
			bombs.put(i,calculateBombPosition(bombs,rows,cols));
		}
		
		
		// Debug print of bomb coordinates
        for (Entry<Integer, int[]> bomb : bombs.entrySet()) {
            int[] b = bomb.getValue();
            System.out.println("Bomb " + bomb.getKey() + " -> (" + b[0] + ", " + b[1] + ")");
        }
	}

	private static int[] calculateBombPosition(Map<Integer, int[]> bombs,int rows,int cols) {
		/* donne des coordonnées aleatoires a chaque bombe, si les coordonnées existent déja dans la map bombs on reboucle*/
		Random ran = new Random();
			
		while(true) {						
				int posX = ran.nextInt(cols);				
				int posY = ran.nextInt(rows);
				boolean taken = false;
				for (int[] bombArr: bombs.values()) {					
					if(bombArr[0] == posX && bombArr[1] == posY) {
						taken = true;
	                    break;
					}
				}
				if (!taken) {
	                return new int[]{posX, posY};
	            }		
		}		
	}

	public static void printGrid(int rows, int cols) {
		
		System.out.print(" ".repeat(3));
		for (int j =0; j<cols;j++) {
			 System.out.printf("%3d", j);
		}
		 System.out.println();
		
		for(int i = 0; i<rows; i++) {
			System.out.printf("%3d", i);
			for (int j =0; j<cols;j++) {						
				System.out.printf("%3s", "+");
			}
				
			
			System.out.println();
		}
	}

}
