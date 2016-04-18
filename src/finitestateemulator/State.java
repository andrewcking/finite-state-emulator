package finitestateemulator;

import java.util.HashMap;

/**
 *
 * @author Andrew
 */
public class State {

    HashMap<Character, State> transitions;
    private boolean isAccepting = false;

    public State() {
        transitions = new HashMap();
    }

    public void addTransition(char character, State state) {
        transitions.put(character, state);
    }

    public State getTransition(char character) {
        return transitions.get(character);
    }

    public boolean isAccepting() {
        return isAccepting;
    }

    public void setIsAccepting(boolean isAccepting) {
        this.isAccepting = isAccepting;
    }
}
