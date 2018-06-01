package backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import backend.generator.AssemblyProgram;
import backend.operators.Operator;
import grammar.SemanticError;

public class Evaluator {
	private Stack<LanguageData> dataStack = new Stack<>();
	private Stack<Operator> operatorStack = new Stack<>();
	private AssemblyProgram program;
	
	public Evaluator() {
		
	}
	
	public void push(LanguageData data) {
		dataStack.push(data);
	}
	
	public void pushOperator(Operator op) {
		operatorStack.push(op);
	}
	
	public void evaluate(int nOperands) throws SemanticError {
		List<LanguageData> operands = new ArrayList<>(nOperands);
		for (int i = 0; i < nOperands; i++) {
			operands.add(0, dataStack.pop());
		}
		
		Operator op = operatorStack.pop();
		op.setOperands(operands);
		
		String phrase = operands.stream()
				.map(v -> String.format("%s - %s", v.getType(), v.getVariant()))
				.collect(Collectors.joining(", ", "[", "]"));
		System.out.printf("Evaluating %s with operand(s) %s\n", op, phrase);
		
		Type result = op.verify();
		System.out.println("Result: " + result);
		
		if (result == null) {
			throw new SemanticError(String.format("Operand(s) %s incompatible for operation %s.", phrase, op));
		} else {			
			// This is testing territory!
			if (nOperands == 2) {
				Temporary temp = program.evaluateBinary(operands.get(0), operands.get(1), null, result);
				push(temp);
			} else {
				throw new UnsupportedOperationException("NYI");
			}
		}
	}
	
	public LanguageData pop() {
		return dataStack.pop();
	}

	public void pushLiteral(int code, String lexeme) {
		Type type = Type.deduceLiteral(code);
		
		// Assume everything is an integer
		push(new Literal<Integer>(type, Integer.parseInt(lexeme)));
	}

	public void pushSymbol(Symbol symbol) {
		push(symbol);		
	}

	public void setProgram(AssemblyProgram program) {
		this.program = program;
	}
}
