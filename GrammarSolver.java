/*
Shriya Gautam
CSE 143, TA: Yafqa Khan
The GrammarSolver class takes in a list of grammar rules in Backus-Naur Form and allows the client to perform tasks with it. The client can print out the symbols in the grammar, check if a symbol is in the grammer, and generate instances of a symbol
*/
import java.io.*;
import java.util.*;

public class GrammarSolver{
   private Map<String, List<String>> grammar = new TreeMap<>();
   /*
   Throws an Illegal Argument Exception if the list is empty or if there are two
   entries for the same terminal in the list parameter
   Initializes the grammar solver using the list provided in the parameter
   */
   public GrammarSolver(List<String> rules){
      if (rules.size() == 0){
         throw new IllegalArgumentException();
      }
      for (int i = 0; i < rules.size(); i++){
         String[] pieces = rules.get(i).split("::=");
         String[] stringsplit = pieces[1].split("\\|");
         if (grammar.containsKey(pieces[0])){
            throw new IllegalArgumentException();
         }
         List<String> list= new ArrayList<String>();
         for (String a: stringsplit){
            list.add(a.trim());
         }
         grammar.put(pieces[0], list);
      }
   }
   
   /*
   returns a boolean about if the grammar set contains a certain non-terminal or not
   */
   public boolean grammarContains(String symbol){
      return grammar.containsKey(symbol.trim());
   }
   
   /*
   Returns a string representation of all the terminals in the grammar
   Example format: [sentence, noun, verb]
   */
   public String getSymbols(){
      String result = "[";
      Iterator<String> itr = grammar.keySet().iterator();
      if (itr.hasNext()){
         result += itr.next();
      }
      while (itr.hasNext()){
         result += (", " + itr.next());
      }
      result += "]";
      return result;
   }
   
   /*
   Throws an IllegalArgumentException if the times parameter is less than 0
   Throws an IllegalArgumentException if the parameter String is not a non-terminal
   Takes in an int and String and returns an int number of occurences of 
   the String non-terminal in the grammar. For example, generate(sentence, 5)
   would generate 5 sentences
   */
   public String[] generate(String symbol, int times){
      if (times < 0){
         throw new IllegalArgumentException();
      }
      if (!(grammar.containsKey(symbol))){
         throw new IllegalArgumentException();
      }
      String[] strings = new String[times];
      for (int i = 0; i < times; i++){
         strings[i] = generate(symbol);
      }
      return strings;
   }
   //private method that generates one occurance of the parameter symbol.
   private String generate(String symbol){
      if (!grammarContains(symbol)){
         return symbol;
      } else {
         Random rand = new Random();
         String newString = "";
         List<String> options = grammar.get(symbol);
         int random = rand.nextInt(Math.abs(options.size()));
         String[] rules = options.get(random).split(" ");
         for (int i = 0; i < rules.length; i++){
             newString += (generate(rules[i]).trim() + " ");            
         }      
         return newString.trim();
      }
   }
 }