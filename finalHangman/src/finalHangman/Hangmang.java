package finalHangman;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Hangmang {
	public static ArrayList<String> dictionary = new ArrayList<>();
	public static ArrayList<Character> targetWord = new ArrayList<>();
	public static ArrayList<Character> guessedChars = new ArrayList<>();
	public static ArrayList<Character> hiddenWord = new ArrayList<>();
	public static ArrayList<Character> wrongGuesses = new ArrayList<>();
	public static ArrayList<Integer> highScores = new ArrayList<>();
	public static int guesses = 7;
	public static int score = 0;
	public static boolean gameOver = false;
	public static int numWrongGuesses = wrongGuesses.size();

	public static void main(String[] args) {
		
	
		readFile("dictionary.txt");
		readFile("highscore.txt");
		splitWord();
		while ((!gameOver)) {
//			System.out.println((guesses > 0) || !gameOver);
//			System.out.println(guesses);
//			System.out.println(gameOver);
			//System.out.print(targetWord);
			// takeGuess();
			display();
		}
		
		try {
			Save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("These are the high scores " + highScores);

		System.out.println("These are wrong guesses: " + wrongGuesses);
		System.out.println("The word was: " + targetWord);
	}

	public static void readFile(String fileName) {
		// Scanner input = new Scanner(System.in);
		File file = new File(fileName);
		Scanner readFile = null;
		try {
			readFile = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (file.exists()) {
			while (readFile.hasNext()) {

				if (fileName == "dictionary.txt") {
					String word = readFile.next();
					dictionary.add(word);
				} else {
					int score = readFile.nextInt();
					highScores.add(score);
				}

			}
		}

	}

	public static String getWord() {
		Random randomIndex = new Random();
		int x = randomIndex.nextInt(120000);

		return dictionary.get(x);
	}

	public static void splitWord() {
		String theWord = getWord();

		for (int i = 0; i < theWord.length() - 1; i++) {
			targetWord.add(theWord.charAt(i));
			hiddenWord.add("_".charAt(0));
		}
	}

	public static void display() {
		char guess = takeGuess();
		boolean match = false;
		// Check the guess
		for (int i = 0; i <= targetWord.size() - 1; i++) {
			if (guess == targetWord.get(i)) {
				char c = targetWord.get(i);
				hiddenWord.set(i, c);
				score += 10;
				match = true;
			}
		}

		// Check for no match
		if (!match) {

			if (guesses > 0) {
				guesses--;
			} else {
				gameOver = true;
			}

			wrongGuesses.add(guess);
		}

		System.out.println("checking...");
		System.out.println("You have " + guesses + " guesses left");
		System.out.println(targetWord);
		
		
		
		//System.out.println(targetWord.equals(hiddenWord));

		// GAME OVER
		if (targetWord.equals(hiddenWord)) {
			System.out.println("You win!");
			score += guesses * 30;
			gameOver = true;
			if (highScores.isEmpty()) {
				highScores.add(0, score);
			} else if (highScores.size() < 5) {
				highScores.add(0, score);
				Collections.sort(highScores);
			}

		}
		
		
		System.out.println(hiddenWord);
		System.out.println("Your score is " + score);
		if(wrongGuesses.size() > 0){
			System.out.println("Incorrect guesses: " + wrongGuesses);
			//WHY IS THIS NOT WORKING
			switch (numWrongGuesses){
			case 1: 
				System.out.println("Hangman now has a body");
			break;
			
			case 2:
				System.out.println("Hangman now has a leg");
				break;
				
			case 3: System.out.println("Hangman now has an arm");
			break;
			
			case 4:
				System.out.println("Hangman now has another arm");
				break;
			
			case 5: 
				System.out.println("Hangman now has another leg");
			break;
			
			case 6:
				System.out.println("Hangman now has hands");
			break;
			
			case 7:
				System.out.println("The noose has been applied!");
				break;
			
			}
			
		}
	
		
	}

	public static Character takeGuess() {
		// System.out.println("takeguess");
		Scanner input = new Scanner(System.in);
		System.out.print("Guess a letter:");
		String guess = input.nextLine();
		// guessedChars.add(guess.charAt(0));
		// System.out.println("You have " + guesses + " left \nYour score is " +
		// score);
		return guess.charAt(0);

	}

	public static void Save() throws IOException {
		File file = new File("highscore.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
	//	System.out.println("Try writing " + highScores);
		for (int i = 0; i <= highScores.size() - 1; i++) {
		//	System.out.println("write " + highScores.get(i));
			out.write(highScores.get(i) + "\n");
			
			if(i == 5){
				break;
			}
		}
		out.close();
	}
}
