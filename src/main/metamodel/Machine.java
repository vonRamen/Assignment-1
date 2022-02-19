package main.metamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Machine {

	private HashMap<String, Integer> integers = new HashMap<>();
	private HashMap<String, State> states = new HashMap<>();
	private State initialState;
	private State currentState;

	public List<State> getStates() {
		return new ArrayList<>(states.values());
	}

	public State getInitialState() {
		return initialState;
	}

	public State getState(String string) {
		return states.get(string);
	}

	public int numberOfIntegers() {
		return integers.size();
	}

	public boolean hasInteger(String string) {
		return integers.containsKey(string);
	}

	public void setInitialState(State state) {
		this.currentState = state;
		this.initialState = state;
	}

	public void addState(State state) {
		this.states.put(state.getName(), state);
	}

	public HashMap<String, Integer> getIntegers() {
		return integers;
	}

	public void setIntegers(HashMap<String, Integer> integers) {
		this.integers = integers;
	}

	public void setStates(HashMap<String, State> states) {
		this.states = states;
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}
}
