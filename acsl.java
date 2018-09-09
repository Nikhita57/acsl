import java.io.*;
import java.util.*;

/*2017-2018
American Computer Science League
Contest #1 SENIOR DIVISION
ACSL Ninety-Nine
PROBLEM: ACSL Ninety-Nine is a two-player card game played with a standard deck of 52-
card (2 – 9, T, J, Q, K, A). To start, each player gets 5 cards and the remaining cards are placed
face down on the table (the pile). In an actual game the point total is set to zero but for this
program an initial point total will be given. Each player in turn puts down one card. The player
adds the point value of his card to the point total and selects a card from the top of the pile. Each
card adds its face value in points (e.g. a 5 is worth five points, a T is worth 10 points, a J is
worth 11 points, a Q is worth 12 points, a K is worth 13 points, an A is worth 14 points) except
for certain cards that have special values or meanings:
A 9 is a pass (and does not change point total).
A T subtracts 10 points from the total.
A 7 adds either 1 or 7 to the point total. The 7 is added first as long as it does not put the
point total over 99.
Also, crossing point total borders 33:34, 55:56 and 77:78 in either direction adds an
additional 5 points to the point total.
Cards are played using the following rule: play the card that is the median point value of the 5
cards. The game ends when the card played puts the point total over 99.

INPUT: There will be 6 lines of input. The first line will have 10 one-character strings. The
first 5 one-character strings will represent the 5 cards dealt to the Player #1. The next 5
one-character strings represent the 5 cards dealt to Player #2. Lines 2 through 6 will each
contain an integer followed by a list of 10 one-character strings. The integer gives the initial
point total. The remaining one-character strings will be, in order, the card picked by Player #1
and the card picked by Player #2. Use Input Line #1 to start each of the five games.
OUTPUT: For each line of input play the game. Print the point total and the winner when the
game ends. We guarantee there will always be a winner.
SAMPLE INPUT SAMPLE OUTPUT
1. 8, 9, Q, 6, 7, K, A, 5, 9, 8 1. 107, Player #2
2. 75, J, 7, Q, T, A, 6, 2, 3, 4, 5 2. 109, Player #1
3. 50, 7, K, T, 8, 3, Q, 9, 7, 2, 3 3. 103, Player #1
4. 63, 3, 6, 8, T, 7, 7, T, 3, 5, 8 4. 100, Player #2
5. 79, A, 9, 7, T, A, 9, T, A, 6, 4 5. 102, Player #2
6. 50, A, T, Q, A, T, K, J, T, A, 7
 */

public class acsl {

	static int points = 0;
	
	public static void main(String[] args) throws IOException {
	int k = 1;
	int[] p1cards = new int[5];
	int[] p2cards = new int[5];
	String[] original = new String[10];
	int[] originaltoint = new int[10];
	String[] nextline = new String[11];
	int[] nextlinetoint = new int[11];
	String fileName = "datainput.txt";
	String line = null;
	FileReader fileReader = new FileReader(fileName);
	BufferedReader bufferedReader = new BufferedReader(fileReader);
	line = bufferedReader.readLine();
	original = line.split(",");
	
	for (int i = 0; i < original.length; i++) {
		original[i] = original[i].trim();
		originaltoint[i] = cardtoInt(original[i]);
		
	}
	
	for (int i = 0; i < originaltoint.length; i++) {
		if (i < 5) {
			p1cards[i] = originaltoint[i];
		} else {
			p2cards[i-5] = originaltoint[i];
		}
	}
	
	Arrays.sort(p1cards);
	Arrays.sort(p2cards);
	
	for (int i = 0; i < 5; i++) {
		line = bufferedReader.readLine();
		nextline = line.split(",");
		nextlinetoint[0] = Integer.parseInt(nextline[0].trim());
		for (int j = 1; j < nextline.length; j++) {
			nextline[j] = nextline[j].trim();
			nextlinetoint[j] = cardtoInt(nextline[j]);
			
		}
		
		points = nextlinetoint[0];
		Arrays.sort(p1cards);
		Arrays.sort(p2cards);
		playCards(p1cards[2]);
		playCards(p2cards[2]);
		while (k < 11 && points <= 99) {
			if ((k % 2) == 1) {
				p1cards[2] = nextlinetoint[k];
				Arrays.sort(p1cards);				
				playCards(p1cards[2]);
			} else {
				p2cards[2] = nextlinetoint[k];
				Arrays.sort(p2cards);
				playCards(p2cards[2]);
			}
			
			k++;
		}
		
		if ((k % 2) == 1) {
			System.out.println(points + ", Player #1");
		} else {
			System.out.println(points + ", Player #2");
		}
		
		k = 1;
		
		for (int j = 0; j < originaltoint.length; j++) {
			if (j < 5) {
				p1cards[j] = originaltoint[j];
			} else {
				p2cards[j-5] = originaltoint[j];
			}
	    }
	  }
		
     bufferedReader.close();       

	}
	
	
	public static int cardtoInt(String str) {
		int result = 0;
		if (str.equals("2") || str.equals("3") || str.equals("4") || str.equals("5") || str.equals("6") || str.equals("7")
				|| str.equals("8") || str.equals("9")) {
			result = Integer.parseInt(str);
		} else if (str.equals("T")) {
			result = 10;
		} else if (str.equals("J")) {
			result = 11;
		} else if (str.equals("Q")) {
			result = 12;
		} else if (str.equals("K")) {
			result = 13;
		} else if (str.equals("A")) {
			result = 14;
		} else {
			System.out.println("This is an invalid card.");
			result = -1;
		}
		return result;
	}
	
	
	public static void playCards (int card) {
		int prevpoints = points;
		if (card != 9 && card != 10 && card != 7) {
			points += card;
		} else if (card == 9) {
			points += 0;
		} else if (card == 10) {
			points -= card;
		} else if (card == 7) {
			if ((points + card) > 99) {
				points += 1;
			} else {
				points += card;
			}
		} else {
			System.out.println("Something's wrong here...");
			points += 0;
		}
		if ((prevpoints <= 33 && points >= 34) || (prevpoints <= 55 && points >= 56) || (prevpoints <= 77 && points >= 78) ||
			(prevpoints >= 34 && points <= 33) || (prevpoints >= 56 && points <= 55) || (prevpoints >= 78 && points <= 77)) {
			points += 5;
		}
	}

}
