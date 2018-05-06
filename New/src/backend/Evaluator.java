package backend;

import java.util.Stack;

import backend.operators.Operator;
import grammar.SemanticError;

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
	
	public void evaluate() throws SemanticError {
		Type a = typeStack.pop();
		Type b = typeStack.pop();
		Operator op = operatorStack.pop();
		
		System.out.printf("Evaluating %s %s %s\n", b, op, a);
		
		Type result = op.verifyType(b, a);
		System.out.println("Result: " + result);
		if (result == null) {
			throw new SemanticError(String.format("Type %s is incompatible with %s for operation %s.", b, a, op));
		} else {
			pushType(result);
		}
	}
	
	public Type pop() {
		return typeStack.pop();
	}
}
