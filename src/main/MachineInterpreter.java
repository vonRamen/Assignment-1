package main;

import main.metamodel.Machine;
import main.metamodel.State;
import main.metamodel.Transition;

import java.util.List;

public class MachineInterpreter {

	private Machine m;

	public void run(Machine m) {
		this.m = m;
	}

	public State getCurrentState() {
		return m.getCurrentState();
	}

	public void processEvent(String string) {
		List<Transition> eventTransitions = m.getCurrentState().getTransitionsByEvent(string);

		for (Transition eventTransition : eventTransitions) {
			if(eventTransition != null) {
				if(eventTransition.getOperationType() != Transition.OperationType.NONE)
					eventTransition.setOperationValue(getInteger(eventTransition.getOperationVariableName()));

				if(isConditionTrue(eventTransition)) {
					executeTransition(eventTransition);
					return;
				}
			}
		}
	}

	private boolean isConditionTrue(Transition transition) {
		if (transition == null) return false;
		if (!transition.isConditional()) return true;

		int value = getInteger(transition.getConditionVariableName());

		return switch (transition.getConditionalType()) {
			case EQUALS -> transition.getConditionComparedValue() == value;
			case GREATER_THAN -> transition.getConditionComparedValue() < value;
			case LESS_THAN -> transition.getConditionComparedValue() > value;
			default -> true;
		};
	}

	private void executeTransition(Transition transition) {
		switch (transition.getOperationType()) {
			case SET -> m.getIntegers().put(transition.getOperationVariableName(), transition.getSetValue());
			case DECREMENT -> m.getIntegers().put(transition.getOperationVariableName(), transition.getOperationValue()-1);
			case INCREMENT -> m.getIntegers().put(transition.getOperationVariableName(), transition.getOperationValue()+1);
		}

		m.setCurrentState(transition.getTarget());
	}

	public int getInteger(String string) {
		return m.getIntegers().get(string);
	}

}
