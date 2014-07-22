import java.util.ArrayList;
import java.util.List;
/** Curtis Noonan
 * CS 251 3/7/14
 * Takes a word or phrase and finds all anagrams. 
 *
 * 
 */
public class AnagramSolver{
    public ArrayList<String> dict;
    /** @param List of strings
    * Sets the dictionary to compare words with
    */
    public AnagramSolver(List<String> dict){
        LetterInventory li = new LetterInventory (dict.get(0));
        this.dict = new ArrayList<String>(dict);
    }
    /** @param String, int
    *  Prints to System.out all combinations of words 
    *  from its dictionary that are anagrams of the phrase 
    *  and that include at most maxWords words. 
    *  Throws an IllegalArgumentException if maxWords is 
    *  less than 0.
    */    
    void print(String phrase, int maxWords){
        if(maxWords < 0){
            throw new IllegalArgumentException();
        }
        else{
            System.out.print(findAnagrams(""));
        }
    }
    /** @param String
    * @return String
    * Attempts recursive backtracking, but does not quite work.
    * Finds all anagrams of a given string
    */
    public String findAnagrams(String phrase){
        String anagrams = ""; 
        LetterInventory sortedPhrase = new LetterInventory(phrase);
        if(sortedPhrase.isEmpty() == false){
            return anagrams;
        }
        else{
            for(int counter = 0; counter < dict.size(); counter++){
                String temp = Character.toString(sortedPhrase.toString().charAt(counter)); 
                for(int counterTwo = 0; counterTwo < sortedPhrase.getSize(); counterTwo++){
                    if(dict.get(counter).startsWith(temp) == false){ 
                        temp = Character.toString(sortedPhrase.toString().charAt(counter));
                    }
                    if(dict.get(counter).startsWith(temp)){
                        temp += sortedPhrase.toString().charAt(counterTwo+1);
                        if(temp.equals(dict.get(counter))){ 
                            anagrams += temp;
                            LetterInventory inventory2 = sortedPhrase.subtract(new LetterInventory(temp));
                            return findAnagrams(inventory2.toString());
                        }
                    }
                }
            }
        }
        return anagrams;
    }
                        
    /** Takes a word or phrase and indexes all letters 
    *   then arranges them in alphabetical order.
    */
    public static class LetterInventory{
        int [] inventory = new int[26];
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        /** @param String
        * Puts a string into an array giving an index of all the letters.
        */
        public LetterInventory(String input){
            int length = input.length();
            String newInput = input.toLowerCase();
            for(int counter = 0; counter < length; counter++){
                for(int alphabetCounter = 0; alphabetCounter < alphabet.length(); alphabetCounter++){
                    if(newInput.charAt(counter) == alphabet.charAt(alphabetCounter)){
                        inventory[alphabetCounter] += 1;
                    }
                }
            }
        }
        /** @return boolean
        * Returns true if the array contains anything.
        */
        public boolean isEmpty(){
            boolean emptyOrNot = false;
            for(int counter = 0; counter < inventory.length; counter++){
                if (inventory[counter] >= 1){
                    emptyOrNot = true;
                }
            }
            return emptyOrNot;
        }
        /** @return String
        * Converts an array the string of corresponding letters.
        */
        public String toString(){
            String lettersSorted = "";
            for(int counter = 0; counter < inventory.length; counter++){
                if(inventory[counter] != 0){
                    for(int numToAdd = 0; numToAdd < inventory[counter]; numToAdd++){
                        lettersSorted += alphabet.charAt(counter);
                    }
                }
            }
            lettersSorted = "[" + lettersSorted + "]";
            return lettersSorted;
        }
        /** @param LetterInventory
        * @return String
        * Subtracts an array from another array
        */
        LetterInventory subtract (LetterInventory other){
            int [] inventorySub = new int [26];
            for(int counterTwo = 0; counterTwo < inventory.length; counterTwo++){
                    inventorySub[counterTwo] = inventory[counterTwo];
                }
            for(int counter = 0; counter < inventory.length; counter++){  
                int checkForNeg = inventory[counter] - other.inventory[counter];
                if(checkForNeg < 0){
                    return null;
                }
                else{
                    inventorySub[counter] = inventory[counter] - other.inventory[counter];
                }
            }
            return new LetterInventory(inventorySub.toString());
        }
        /** @return int
        * Finds the size of the inventory array
        */
        public int getSize(){
            int size = 0;
            for(int counter = 0; counter < inventory.length; counter++){
                size += inventory[counter];
            }
            return size;
        }
    }
}