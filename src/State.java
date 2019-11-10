import java.util.ArrayList;

public class State {
	private int id;
	private boolean isFinal, isStart;
	private ArrayList<Transition> transitions = new ArrayList<Transition>();
	
	public State(int id) {
		super();
		this.id = id;
		this.isFinal = false;
		this.isStart = false;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public boolean isStart() {
		return isStart;
	}

	public ArrayList<Transition> getTransitions() {
		return transitions;
	}
	
	public int getId() {
		return id;
	}

	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	public void addTransition(State out, char symbol) {
		this.getTransitions().add(new Transition(out, symbol));
	}
	
	public void printStateDetails() {
		System.out.println("State ID: " + this.getId() + ". " + (this.isStart()?"Start ":"") + (this.isFinal()?"Final ":""));
		System.out.println("Transitions: ");
		for(Transition t : this.getTransitions()) {
			System.out.println(this.getId() +  " -" + t.getSymbol() + "-> " + t.getOutState().getId());
		}
		System.out.println();
	}
}
