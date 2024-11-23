/* Author: Sam Smith
** Date: 19 November 2024
** Description: A game of hangman! Guess letters and find the word within 7 tries.
*/

// This is a source code update! - Sam

import java.util.Scanner;

// Define main objects, classes, and variables
public class Hangman {
    private final WordList wordList = new WordList();
    private final String word;
    private final int wordLength;
    private int remainingLetters;
    private String displayedWord;
    private int numGuesses = 0;
    private int numWrong = 0;
    private String guessedLetters = "";
    private final Scanner sc = new Scanner(System.in);
    
    public Hangman() {
        // Print basic hangman UI
        word = wordList.getRandomWord().toUpperCase();
        wordLength = word.length();
        remainingLetters = wordLength;
        displayedWord = "_".repeat(wordLength);
    }
    
    public char getLetter() {
        // Get letter input from user
        while (true) {
            System.out.print("Enter a letter: ");
            String s = sc.nextLine().toUpperCase();
            
            if (s.length() == 1) {
                char c = s.charAt(0);
                if (Character.isLetter(c)) {
                    if (guessedLetters.indexOf(c) == -1) {
                        numGuesses++;
                        guessedLetters += c;
                        return c;
                    } else {
                        // Handle repeat input
                        System.out.println("You already tried that letter.");
                        continue;
                    }
                }
            } 
            // if you're here, user didn't enter a single letter
            System.out.println("Please enter 1 and only 1 letter.");
        }
    }
    
    // Draw border for each frame of the game
    public void drawLine() {
        System.out.println("*".repeat(64));
    }
    
    public void drawScreen() {
        drawLine();
        drawHangman(numWrong);
        // Print formatted screen display
        String display = String.format(
            "Word: %s   Guesses: %d  Wrong: %d  Tried: %s", 
            wordList.addSpaces(displayedWord), 
            numGuesses, numWrong, 
            wordList.addSpaces(guessedLetters));
        System.out.println(display);
    }
    
    public void playGame() {
        // Draw the UI
        drawScreen();
        
        // Repeat gameloop until win or loss
        while (numWrong < 7 && remainingLetters > 0) {
            char guess = getLetter();
            // Process answers
            int index = word.indexOf(guess);            
            if (index == -1) {
                numWrong++;
            } else {
                displayedWord = "";
                remainingLetters = word.length();
                
                for(char c: word.toCharArray()){
                    if (guessedLetters.indexOf(c) == -1) {
                        displayedWord += "_";
                    } else {
                        displayedWord += c;
                        remainingLetters--;  //decrement remaining letters
                    }
                }
            }
            drawScreen();
        }
        drawLine();
        
        // Handle ending and print message
        if (remainingLetters == 0) {
            System.out.println(String.format(
                "Congratulations! You got it in %d guesses.", numGuesses));
        } else {
            System.out.println(String.format(
                "Sorry, you lost. The word was %s.", word));
        }
    }
    public void drawHangman(int numWrong){
        // Print different stages of hangman based on mistakes
        switch(numWrong) {
            case 0 -> System.out.println("""
                                         ___
                                            |
                                            
                                         """);
            case 1 -> System.out.println("""
                                         ___
                                            |
                                            O
                                         """);
            case 2 -> System.out.println("""
                                         ___
                                            |
                                            O
                                           \\
                                         """);
            case 3 -> System.out.println("""
                                         ___
                                            |
                                            O
                                           \\|
                                         """);
            case 4 -> System.out.println("""
                                         ___
                                            |
                                            O
                                           \\|/
                                         """);
            case 5 -> System.out.println("""
                                         ___
                                            |
                                            O
                                           \\|/
                                            |
                                         """);
            case 6 -> System.out.println("""
                                         ___
                                            |
                                            O
                                           \\|/
                                            |
                                           /
                                         """);
            case 7 -> System.out.println("""
                                         ___
                                            |
                                            O
                                           \\|/
                                            |
                                           / \\
                                         """);
        }
    }
}