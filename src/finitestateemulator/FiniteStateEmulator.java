package finitestateemulator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrew
 */
public class FiniteStateEmulator {

    private HashMap<String, State> states;
    private State currentState;

    //For the benefit of errors
    private ArrayList<Character> language;
    private ArrayList<String> stateList;

    //Filename
    private String FILENAME = "fsa.txt";

    public FiniteStateEmulator() {
        states = new HashMap();
        stateList = new ArrayList();
        language = new ArrayList();
        readTextFile();
        readFromConsole();
    }

    private void readFromConsole() {
        System.out.println("Insert your test string");
        while (true) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                test(br.readLine());
            } catch (IOException ex) {
                Logger.getLogger(FiniteStateEmulator.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }

    }

    private void test(String string) {
        char[] chars = string.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (language.contains(chars[i])) {
                currentState = currentState.getTransition(chars[i]);
            } else {
                System.out.println(chars[i] + " is not a member of the language");
                return;
            }
        }
        if (currentState.isAccepting()) {
            System.out.println("The string is accepted");
        } else {
            System.out.println("The string is not accepted");
        }
    }

    private void createState(String name) {
        //create states
        states.put(name, new State());
        //add to list for error
        stateList.add(name);
    }

    private void readTextFile() {
        String[] lines = new String[5];
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILENAME));
            for (int i = 0; i < lines.length; i++) {
                //readline will increment for us
                lines[i] = br.readLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(FiniteStateEmulator.class.getName()).log(Level.SEVERE, null, ex);
        } 
        // Creates our states from line 0
        String[] currentLine = lines[0].split(",");
        for (int i = 0; i < currentLine.length; i++) {
            createState(currentLine[i]);
        }
        //Add our accepted language characters from line 1
        currentLine = lines[1].split(",");
        for (int i = 0; i < currentLine.length; i++) {
            language.add(currentLine[i].charAt(0));
        }
        //Set starting state based on line 2
        if (stateList.contains(lines[2])) {
            currentState = states.get(lines[2]);
        } else {
            System.out.println(lines[2] + ", from line 3 is not a valid state.");
            return;
        }
        //Set accepting states based on line 3
        currentLine = lines[3].split(",");
        for (int i = 0; i < currentLine.length; i++) {
            if (stateList.contains(currentLine[i])) {
                states.get(currentLine[i]).setIsAccepting(true);
            } else {
                System.out.println(currentLine[i] + ", from line 4 is not a valid state.");
                return;
            }
        }
        //Set transitions based on line 4
        currentLine = lines[4].split(";");
        for (int i = 0; i < currentLine.length; i++) {
            String[] splitOnComma = currentLine[i].split(",");
            String[] splitOnArrow = splitOnComma[1].split("->");

            if (!stateList.contains(splitOnComma[0])) {
                System.out.println(splitOnComma[0] + ", from line 5 is not a valid state.");
            }
            if (!language.contains(splitOnArrow[0].charAt(0))) {
                System.out.println(splitOnArrow[0] + ", from line 5 is not in the language.");
            }
            if (!stateList.contains(splitOnArrow[1])) {
                System.out.println(splitOnArrow[1] + ", from line 5 is not a valid state.");
            }

            states.get(splitOnComma[0]).addTransition(splitOnArrow[0].charAt(0),
                    states.get(splitOnArrow[1])
            );
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new FiniteStateEmulator();
    }

}
