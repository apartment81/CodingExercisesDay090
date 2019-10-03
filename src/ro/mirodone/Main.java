package ro.mirodone;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        String secretWord = readFile();
        int length = secretWord.length();
        System.out.println(secretWord);

        // Create an array to store already entered letters
        char[] enteredLetters = new char[length];
        int triesCount = 0;
        boolean wordIsGuessed = false;
        do {
            // infinitely iterate through cycle as long as enterLetter returns true
            // if enterLetter returns false that means user guessed all the letters
            // in the word e. g. no asterisks were printed by printWord
            switch (enterLetter(secretWord, enteredLetters)) {
                case 0:
                    triesCount++;
                    break;
                case 1:
                    triesCount++;
                    break;
                case 2:
                    break;
                case 3:
                    wordIsGuessed = true;
                    break;
            }
        } while (!wordIsGuessed);
        System.out.println("\nThe title is > " + secretWord + " <." +
                " You missed " + (triesCount - findEmptyPosition(enteredLetters)) +
                " time(s)");
    }

    private static String readFile() {
        int position;
        List<String> words = new ArrayList<>();
        Random rand = new Random();
        String randomWord = "";
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = Files.newBufferedReader(Paths.get("C:\\Users\\mirodone\\Desktop\\java2018\\CodingExercisesDay090\\src\\ro\\mirodone\\movieList.txt"))) {

            // read line by line
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
                words.add(line);
            }

            position = rand.nextInt(words.size());
            randomWord = words.get(position);

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }

        return randomWord;
    }

    /* Hint user to enter a guess letter,
    returns 0 if letter entered is not in the word (counts as try),
    returns 1 if letter were entered 1st time (counts as try),
    returns 2 if already guessed letter was REentered,
    returns 3 if all letters were guessed */
    private static int enterLetter(String word, char[] enteredLetters) {
        System.out.print("(Guess) Enter a letter in title: ");
        // If-clause is true if no asterisks were printed so
        // word is successfully guessed
        if (!printWord(word, enteredLetters))
            return 3;
        System.out.print(" > ");
        Scanner input = new Scanner(System.in);
        int emptyPosition = findEmptyPosition(enteredLetters);
        char userInput = input.nextLine().charAt(0);
        if (inEnteredLetters(userInput, enteredLetters)) {
            System.out.println(userInput + " is already found !");
            return 2;
        } else if (word.contains(String.valueOf(userInput))) {
            enteredLetters[emptyPosition] = userInput;
            return 1;
        } else {
            System.out.println(userInput + " is not in the word!");
            return 0;
        }
    }

    /* Print word with asterisks for hidden letters, returns true if
    asterisks were printed, otherwise return false */
    private static boolean printWord(String word, char[] enteredLetters) {
        // Iterate through all letters in word
        boolean asteriskPrinted = false;
        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            // Check if letter already have been entered by user before
            if (inEnteredLetters(letter, enteredLetters))
                System.out.print(letter); // If yes - print it
            else {
                System.out.print('*');
                asteriskPrinted = true;
            }
        }
        return asteriskPrinted;
    }

    /* Check if letter is in enteredLetters array */
    private static boolean inEnteredLetters(char letter, char[] enteredLetters) {
        return new String(enteredLetters).contains(String.valueOf(letter));
    }

    /* Find first empty position in array of entered letters (one with code \u0000) */
    private static int findEmptyPosition(char[] enteredLetters) {
        int i = 0;
        while (enteredLetters[i] != '\u0000') i++;
        return i;
    }


}

