package backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

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
	
	public void evaluate(int nOperands) throws SemanticError {
		List<Type> operands = new ArrayList<>(nOperands);
		for (int i = 0; i < nOperands; i++) {
			operands.add(0, typeStack.pop());
		}
		
		Operator op = operatorStack.pop();
		op.setOperands(operands);
		
		String phrase = operands.stream().map(Type::toString).collect(Collectors.joining(", ", "[", "]"));
		System.out.printf("Evaluating %s with operand(s) %s\n", op, phrase);
		
		Type result = op.verify();
		System.out.println("Result: " + result);
		if (result == null) {
			throw new SemanticError(String.format("Operand(s) %s incompatible for operation %s.", phrase, op));
		} else {
			pushType(result);
		}
	}
	
	public Type pop() {
		return typeStack.pop();
	}
}
