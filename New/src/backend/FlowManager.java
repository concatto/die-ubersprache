package backend;

import java.util.Stack;

import backend.generator.AssemblyProgram;
import backend.operators.RelationalOperator;

public class FlowManager {
	private Stack<String> labelStack = new Stack<>();	
	private ScopeManager scopeManager;
	private AssemblyProgram program;
	
	public FlowManager(ScopeManager scopeManager, AssemblyProgram program) {
		this.scopeManager = scopeManager;
		this.program = program;
	}
	
	public void branch(RelationalOperator op) {
		program.branch(op, labelStack.peek());
	}
	
	
	public void pushLabel(String lexeme) {
		labelStack.push(lexeme.toUpperCase() + "_" + scopeManager.getTotalScopes());
	}
	
	public void insertTopLabel() {
		String label = labelStack.peek();
		program.insertLabel(label);
	}
	
	public void popLabel() {
		labelStack.pop();
	}
}
