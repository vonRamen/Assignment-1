package main;

import main.metamodel.Machine;
import main.metamodel.State;
import main.metamodel.Transition;

import java.util.HashMap;
import java.util.Map;

public class StateMachine {

	private Machine machine = new Machine();
	private HashMap<String, State> stateNameAndState = new HashMap<>();
	private HashMap<String, Integer> variables = new HashMap<>();
	private State currentState, defaultState = null;
	private Transition currentTransition;
	private HashMap<Transition, String> transitionTargets = new HashMap<>();

	public Machine build() {
		machine.setIntegers(variables);
		machine.setStates(stateNameAndState);
		machine.setInitialState(defaultState);

		for (Map.Entry<Transition, String> transitionStringEntry : transitionTargets.entrySet()) {
			transitionStringEntry.getKey().setTarget(stateNameAndState.get(transitionStringEntry.getValue()));
		}
		return machine;
	}

	public StateMachine state(String name) {
		this.currentState = new State(name);
		stateNameAndState.put(name, this.currentState);
		if(this.defaultState == null) {
			this.defaultState = this.currentState;
		}
		return this;
	}

	public StateMachine initial() {
		this.defaultState = currentState;
		return this;
	}

	public StateMachine when(String eventName) {
		currentTransition = new Transition(eventName);
		currentTransition.setTarget(currentState);
		this.currentState.addTransition(eventName, currentTransition);
		return this;
	}

	public StateMachine to(String string) {
		transitionTargets.put(currentTransition, string);
		return this;
	}

	public StateMachine integer(String string) {
		variables.put(string, 0);
		return this;
	}

	public StateMachine set(String string, int i) {
		currentTransition.setOperationType(Transition.OperationType.SET);
		currentTransition.setSetValue(i);
		currentTransition.setOperationVariableName(string);
		return this;
	}

	public StateMachine increment(String string) {
		currentTransition.setOperationType(Transition.OperationType.INCREMENT);
		currentTransition.setOperationVariableName(string);
		return this;
	}

	public StateMachine decrement(String string) {
		currentTransition.setOperationType(Transition.OperationType.DECREMENT);
		currentTransition.setOperationVariableName(string);
		return this;
	}

	public StateMachine ifEquals(String string, int i) {
		currentTransition.setConditionalType(Transition.ConditionalType.EQUALS);
		currentTransition.setConditionVariableName(string);
		currentTransition.setComparedValue(i);
		return this;
	}

	public StateMachine ifGreaterThan(String string, int i) {
		currentTransition.setConditionalType(Transition.ConditionalType.GREATER_THAN);
		currentTransition.setConditionVariableName(string);
		currentTransition.setComparedValue(i);
		return this;
	}

	public StateMachine ifLessThan(String string, int i) {
		currentTransition.setConditionalType(Transition.ConditionalType.LESS_THAN);
		currentTransition.setConditionVariableName(string);
		currentTransition.setComparedValue(i);
		return this;
	}

}
