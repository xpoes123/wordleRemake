import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import javax.swing.*;

public class wordle {
    public static boolean legal(String word, String[] words) {
        if (word.length() != 5) {
            return false;
        }
        for (String s : words) {
            if (word.equals(s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean game(String word, String[] words) {
        int guess = 0;
        char[] letters = new char[5];
        char[] realLetters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                'x', 'y', 'z'};
        for (int i = 0; i < word.length(); i++) {
            letters[i] = word.charAt(i);
        }
        char[] result = new char[]{'-', '-', '-', '-', '-'};
        String guessy = Arrays.toString(result) + "\n";
        char[] lettersTemp = new char[5];
        while (guess < 6) {
            String stringGuess = JOptionPane.showInputDialog(null, "Unguessed letters: \n" + Arrays.toString(realLetters) + "\nMake a guess\n" + guessy);
            stringGuess = stringGuess.toLowerCase();
            if (legal(stringGuess, words)) {
                guess++;
                char[] letterGuess = new char[5];
                for (int i = 0; i < stringGuess.length(); i++) {
                    letterGuess[i] = stringGuess.charAt(i);
                }
                System.arraycopy(letters, 0, lettersTemp, 0, 5);
                for (int i = 0; i < 5; i++) {
                    result[i] = letterGuess[i];
                    for(int a = 0; a < realLetters.length; a++){
                        if(realLetters[a] == result[i]){
                            realLetters[a] = '-';
                        }
                    }
                    for (int j = 0; j < 5; j++) {
                        if (i != j) {
                            if (letterGuess[i] == lettersTemp[j]) {
                                result[i] = '?';
                                lettersTemp[j] = '-';
                            }
                        }
                    }
                    if (letterGuess[i] == lettersTemp[i]) {
                        result[i] = '!';
                    }
                }
                if (stringGuess.equals(word)) {
                    return true;
                }
                guessy += Arrays.toString(result) + "\n";
                System.out.println(result);
            } else {
                System.out.println("Illegal guess, try again");
            }
        }
        return false;
    }

    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream file = new FileInputStream("wordle.txt");
        Scanner scnr = new Scanner(file);
        String[] words = new String[5757];
        Random rand = new Random();
        int point = 0;
        while (scnr.hasNext()) {
            words[point] = scnr.next();
            point++;
        }
        while (true) {
            String[] options = {"Exit", "New Game"};
            int choice = JOptionPane.showOptionDialog(null, "Press Y to play Wordle, N to exit",
                    null, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[1]);
            if (choice == 1) {
                int line = rand.nextInt(words.length);
                boolean win = game(words[line], words);
                if (win) {
                    JOptionPane.showMessageDialog(null,"Nice win");
                } else {
                    JOptionPane.showMessageDialog(null,"Unlucky really, correct word is: " + words[line]);
                }
            } else {
                break;
            }
        }
    }
}