package backend;

import java.util.Stack;

import backend.operators.Operator;

public class Evaluator {
	private Stack<Type> typeStack = new Stack<>();
	private Stack<Operator> operatorStack = new Stack<>();
	
	public Evaluator() {
		
	}
	
	public void pushType(Type type) {
		typeStack.push(type);
	}
	
	public void pushOperator(Operator op) {
		operatorStack.push(op);
	}
	
	public void evaluate() {
		Type a = typeStack.pop();
		Type b = typeStack.pop();
		Operator op = operatorStack.pop();
		
		System.out.printf("Evaluating %s %s %s\n", b, op, a);
		
		Type result = op.verifyType(b, a);
		System.out.println("Result: " + result);
		if (result == null) {
			System.out.println("Tipos incompativeis!");
		} else {
			pushType(result);
		}
	}
	
	public Type pop() {
		return typeStack.pop();
	}
}
