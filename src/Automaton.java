import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class Automaton {
	static ArrayList<State> states = new ArrayList<State>();
	static Set<State> currentState = new HashSet<>();

	public static void createAutomatonFromFile(String filePath) {
		String json = new String();
		boolean fileError = false;
		
		//file read
		try {
			Scanner sc = new Scanner(new File(filePath));
			while(sc.hasNextLine()) {
				json += sc.nextLine();
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found. Check path and try again.");
			fileError = true;
		}

		if (!fileError) {
			try {
				//import JSON data to controller variable "states"
				getStates().clear();
				JSONObject obj = new JSONObject(json);
				int numOfStates = obj.getInt("states");
				for(int i=0; i<numOfStates; i++) {
					State s = new State(i);
					s.setStart(obj.getInt("start") == i);
					s.setFinal(obj.getJSONArray("final").toList().contains(i));
					addState(s);
				}

				//get transitions from JSON and add them to respective state
				JSONArray arr = obj.getJSONArray("transitions");
				for (int i=0; i<arr.length(); i++) {
					int inStateID = arr.getJSONObject(i).getInt("in");
					int outStateID = arr.getJSONObject(i).getInt("out");
					char symbol = arr.getJSONObject(i).getString("symbol").charAt(0);

					//gets state object by id and attaches it's respective transition
					State s = getStates().get(inStateID);
					s.addTransition(getStates().get(outStateID), symbol);
				}
				System.out.println("Automaton loaded from path \"" + filePath + "\". To see the automaton's details, choose option 2=Print automaton details.");
			} catch (JSONException e) {
				System.out.println("File format is not correct. Make sure it is a JSON file and the format follows the instructions. Check file and try again.");
				createAutomatonFromFile("files/automaton.json"); //resets any changes that where possibly made in the automaton before wrong format error
			}
		}
	}
	
	public static void checkWord(String w) {
		getCurrentState().clear();
		//find start state
		for (State s : getStates()) {
			if(s.isStart()) {
				getCurrentState().add(s);
				break;
			}
		}

		boolean transitionPossible = true; //if current state has no possible transitions and the word reading is not completed, this flag becomes false
		for(char c : w.toCharArray()) {
			//Debugging
//			System.out.println("Symbol: " + c);
//			System.out.println("Current states: " + getCurrentState().toString());

//			transitionPossible = false;
//			for (State s : getCurrentState()) {
//				//Debugging
////				System.out.println("Possible transitions: " + s.getTransitions().toString());
//				for(Transition t : s.getTransitions()) {
//					if (t.getSymbol() == c) {
//						getNextState().add(t.getOutState());
//						transitionPossible = true;
//					}
//				}
//			}

			Set<State> temp = findNextState(getCurrentState(), c); //temporary set of state objects to store the next possible states
			transitionPossible = !temp.isEmpty();

			if (transitionPossible){
				//Sets the newly created set to the current one
				setCurrentState(temp);
			} else {
				break;
			}
		}
		
		System.out.println(currentStateFinal() && transitionPossible? "Yay!" : "Noooo...");
	}

	public static boolean currentStateFinal(){
		for (State s : getCurrentState()){
			if (s.isFinal()) return true;
		}
		return false;
	}
	
	public static ArrayList<State> getStates() {
		return states;
	}

	public static Set<State> getCurrentState() {
		return currentState;
	}

	public static void setCurrentState(Set<State> currentState) {
		Automaton.currentState = currentState;
	}

	public static void addState(State s) {
		getStates().add(s);
	}

	public static Set<State> findNextState(Set<State> currentState, char c){
		Set<State> nextState = new HashSet<>(); //temporary set to store the next possible states
		for (State s : currentState) {
			for(Transition t : s.getTransitions()) {
				if (t.getSymbol() == c) {
					nextState.add(t.getOutState());
				}
				else if (t.getSymbol() == '*'){
					nextState.addAll(findNextState(new HashSet<State>(Collections.singleton(t.getOutState())), c));
				}
			}
		}

		return nextState;
	}

	public static void printAutomatonDetails() {
		for(State s : getStates()) {
			s.printStateDetails();
		}
	}
}