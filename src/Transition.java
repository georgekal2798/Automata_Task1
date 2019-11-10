public class Transition {
	private State outState;
	private char symbol;
	
	public Transition(State outState, char symbol) {
		super();
		this.outState = outState;
		this.symbol = symbol;
	}

	public State getOutState() {
		return outState;
	}

	public char getSymbol() {
		return symbol;
	}
}
