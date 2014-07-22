import java.io.*;
import java.util.*;

/**
 * Reads a list of words from a file and uses that lexicon to generate
 * anagrams until the user gets tired of it.
 *
 * Requires Java 7 for the try-with-resources IO.
 */
public class Anagrams {
    public static void main(String[] args) throws FileNotFoundException {
        
        // Name of file with words. 
        String dictFileName = "words.txt";
        if(args.length > 0) {
            dictFileName = args[0];
        }
        System.out.println("Reading dictionary from: " + dictFileName);
            
        // List to hold the words
        List<String> words = new ArrayList<>();
        
        // Read the words from the file
        try (Scanner sc = 
             new Scanner(new BufferedReader(new FileReader(dictFileName)))) {
                
            while(sc.hasNext()) {
                String word = sc.next().trim();
                words.add(word);
            }
        }
            
        // Wrapped with unmodifiableList to be sure no one messes with the dictionary
        AnagramSolver solver = new AnagramSolver(Collections.unmodifiableList(words));
            
        Scanner input = new Scanner(System.in);
        while(true) {
            System.out.println(); // give a blank line between results
            System.out.println("Input phrase (none to quit): ");
            String phrase = input.nextLine().trim();
            if(phrase.isEmpty()) {
                // No phrase, user is done
                break;
            } else {
                System.out.println("Max words to include (0 for no max): ");
                int maxWords = input.nextInt();
                input.nextLine(); // eat the newline after the int
                solver.print(phrase, maxWords);
             }
        }
    }
}
