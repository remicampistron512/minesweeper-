package minesweeper;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * Le démineur (minesweeper).
 */
public class MineSweeper {

  /**
   * Methode principale.
   *
   * @param args argument de cli
   */
  public static void main(String[] args) {
    // Définit la taille de la grille
    int rows = 6;
    int cols = 12;

    Scanner scan = new Scanner(System.in);

    // va stocker les coordonnées des bombes
    Map<Integer, int[]> bombs = new HashMap<>();
    // les coordonnées des coups joués par le joueur
    Map<Integer, int[]> playerMoves = new HashMap<>();

    // nombre de bombes
    int nbBombs = 9;


    // générateur de nombres aléatoires

    Random ran = new Random();

    // remplit la hasmap des coordonnées des bombes
    for (int i = 0; i < nbBombs; i++) {
      bombs.put(i, calculateBombPosition(bombs, rows, cols,ran));
    }

    // Affiche la grille
    printGrid(rows, cols, bombs, playerMoves);

    // compteur qui enregistre le nombre de coups joués
    int nbPlayerMoves = 0;
    boolean gameOver = false;
    while (!gameOver) {

      // Input utilisateur des coordonnées
      int inputX = readIntInRange(scan, "Rentrer une coordonnée en x", 0, cols - 1);
      int inputY = readIntInRange(scan, "Rentrer une coordonnée en y", 0, rows - 1);

      nbPlayerMoves++;
      // enregistre les coordonnées des coups joués
      playerMoves.put(nbPlayerMoves, new int[]{inputX, inputY});
      printGrid(rows, cols, bombs, playerMoves);

      // le coup joué tombe sur une bombe, on affiche la grille, la partie est finie
      if (isaminedtile(inputX, inputY, bombs)) {
        printGrid(rows, cols, bombs, playerMoves);
        System.out.println("Game over :( ");
        gameOver = true;
      }
      // L'utilisateur quitte le jeu
      if (inputX < 0 || inputY < 0) {
        gameOver = true;
      }
    }
  }

  /**
   * Determine si la case jouée contient une bombe.
   */
  private static boolean isaminedtile(int inputX, int inputY, Map<Integer, int[]> bombs) {

    for (int[] bombArr : bombs.values()) {
      if (bombArr[0] == inputX && bombArr[1] == inputY) {
        return true;
      }
    }
    return false;
  }

  /**
   * Valide les inputs de coordonnées par l'utilisateur.
   */
  private static int readIntInRange(Scanner scan, String prompt, int min, int max) {

    while (true) {
      System.out.print(prompt + " [" + min + "-" + max + "] : ");
      String userInputString = scan.nextLine().trim();

      // L'utilisateur quitte le jeu on retourne -1
      if (userInputString.equalsIgnoreCase("quit")) {
        return -1;
      }

      if (!userInputString.isEmpty() && isAllDigits(userInputString)) {
          // contrôle que les valeurs données sont bien entre les bornes fournies
          int val = Integer.parseInt(userInputString);
          if (val >= min && val <= max) {
            return val;
          }
        }

      System.out.println("Entrez un entier entre " + min + " et " + max + ".");
    }
  }

  private static boolean isAllDigits(String userInputString) {
    // contrôle si tous les caractères sont des chiffres
    for (int i = 0; i < userInputString.length(); i++) {
      if (!Character.isDigit(userInputString.charAt(i))) {
        return false;
      }
    }
    return true;
  }


  private static int[] calculateBombPosition(Map<Integer, int[]> bombs, int rows, int cols,Random ran) {
    /* donne des coordonnées aléatoires a chaque bombe, si les coordonnées existent déja dans la
    map bombs on reboucle*/


    while (true) {
      int posX = ran.nextInt(cols);
      int posY = ran.nextInt(rows);
      boolean taken = false;
      for (int[] bombArr : bombs.values()) {
        if (bombArr[0] == posX && bombArr[1] == posY) {
          taken = true;
          break;
        }
      }
      if (!taken) {
        return new int[]{posX, posY};
      }
    }
  }

  /**
   * Affiche la grille.
   *
   * @param rows les lignes
   * @param cols les colonnes
   * @param bombs la hashmap des bombes et leur coordonnée
   * @param playerMoves enregistre les coups joués et leur coordonnée
   */
  public static void printGrid(int rows, int cols, Map<Integer, int[]> bombs,
      Map<Integer, int[]> playerMoves) {
    /*
     * Gère l'affichage de la grille , l'affichage des bombes et l'affichage des cases découvertes
     */

    System.out.print(" ".repeat(3));
    for (int j = 0; j < cols; j++) {
      System.out.printf("%3d", j);
    }
    System.out.println();
    boolean isaBomb;
    boolean isDiscovered;
    for (int i = 0; i < rows; i++) {
      System.out.printf("%3d", i);
      for (int j = 0; j < cols; j++) {
        isaBomb = false;
        isDiscovered = false;
        isaBomb = isBomb(bombs, j, i, isaBomb);
        isDiscovered = isDiscovered(playerMoves, j, i, isDiscovered);
        int nbBombs = nbBombsAround(i, j, bombs);
        if (isaBomb) {
          System.out.printf("%3s", "B");
        } else if (isDiscovered) {
          System.out.printf("%3s", nbBombs);
        } else {
          System.out.printf("%3s", "x");
        }
      }

      System.out.println();
    }
  }

  private static boolean isDiscovered(Map<Integer, int[]> playerMoves, int j, int i,
      boolean isDiscovered) {
    for (int[] movesArr : playerMoves.values()) {
      if (movesArr[0] == j && movesArr[1] == i) {
        isDiscovered = true;
        break;
      }
    }
    return isDiscovered;
  }

  private static boolean isBomb(Map<Integer, int[]> bombs, int j, int i, boolean isaBomb) {
    for (int[] bombArr : bombs.values()) {
      if (bombArr[0] == j && bombArr[1] == i) {
        isaBomb = true;
        break;
      }
    }
    return isaBomb;
  }


  private static int nbBombsAround(int x, int y, Map<Integer, int[]> bombs) {
    /*
     * Calcule les bombes aux alentours de la case découverte, si la valeur absolue de la
     * différence de coordonnées x (ou y)
     *  est inférieure ou égale à 1 cela veut dire qu'une bombe se trouve aux alentours
     */
    int nbBombs = 0;
    for (int[] bombArr : bombs.values()) {
      int dx = Math.abs(bombArr[0] - x);
      int dy = Math.abs(bombArr[1] - y);
      // voisin si |dx|<=1 et |dy|<=1, en excluant la case (x,y) elle-même
      if ((dx <= 1 && dy <= 1) && !(dx == 0 && dy == 0)) {
        nbBombs++;
      }

    }
    return nbBombs;
  }

}
