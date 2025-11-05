package minesweeper;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Map.Entry;

public class MineSweeper {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int rows = 6;
		int cols = 12;
		
		printGrid(rows, cols);
		Scanner scan = new Scanner(System.in);
		Map <Integer, int []> bombs = new HashMap<>();
		// les coordonnées des coups joués par le joueur
		Map <Integer, int []> playerMoves = new HashMap<>();
		int nbBombs = 9;
		
		for (int i =0; i <nbBombs;i++) {			
			bombs.put(i,calculateBombPosition(bombs,rows,cols));
		}
		
		
		// Debug print of bomb coordinates
        for (Entry<Integer, int[]> bomb : bombs.entrySet()) {
            int[] b = bomb.getValue();
            System.out.println("Bomb " + bomb.getKey() + " -> (" + b[0] + ", " + b[1] + ")");
        }
        int nbPlayerMoves = 0;
        boolean gameOver = false;
        while(!gameOver) {
        	 int inputX = readIntInRange(scan, "Rentrer une coordonnée en x", 0, cols - 1);
        	 int inputY = readIntInRange(scan, "Rentrer une coordonnée en y", 0, rows - 1);
        	 nbPlayerMoves ++;
        	 
        	 
        	 playerMoves.put(nbPlayerMoves,new int[] {inputX,inputY});
        	 
        	 if(isAMinedTile(inputX,inputY,bombs)) {
        		 printGrid(rows, cols);
        		 System.out.println("Game over :( ");
        	 }
        	 // L'utilisateur quitte le jeu 
        	 if (inputX < 0 || inputY < 0) break;        	
        }
	}
	


	private static boolean isAMinedTile(int inputX, int inputY, Map<Integer, int[]> bombs) {
		for (int[] bombArr: bombs.values()) {	
			if(bombArr[0] == inputX && bombArr[1] == inputY) {
				return true;
			}
		}
		return false;
	}



	private static int readIntInRange(Scanner scan, String prompt, int min, int max) {
	    while (true) {
	        System.out.print(prompt + " [" + min + "-" + max + "] : ");
	        String userInputString = scan.nextLine().trim();
	        
	        //L'utilisateur quitte le jeu on retourne -1
	        if (userInputString.equalsIgnoreCase("quit")) return -1; 

	        if (!userInputString.isEmpty()) {
	            boolean allDigits = true;
	            for (int i = 0; i < userInputString.length(); i++) {
	                if (!Character.isDigit(userInputString.charAt(i))) {
	                	allDigits = false; 
	                	break; 
	                }
	            }
	            if (allDigits) {
	                int val = Integer.parseInt(userInputString);
	                if (val >= min && val <= max) return val;
	            }
	        }
	        System.out.println("Entrez un entier entre " + min + " et " + max + ".");
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
