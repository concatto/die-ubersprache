package backend;

import java.util.Stack;

import backend.generator.AssemblyProgram;
import backend.operators.RelationalOperator;

public class FlowManager {
	private Stack<String> labelStack = new Stack<>();
	private Stack<String> startStack = new Stack<>();
	private Stack<String> endStack = new Stack<>();
	private ScopeManager scopeManager;
	private AssemblyProgram program;
	private int totalLabels = 0;
	private Symbol currentFunction;
	
	public FlowManager(ScopeManager scopeManager, AssemblyProgram program) {
		this.scopeManager = scopeManager;
		this.program = program;
	}
	
	public void branch(RelationalOperator op) {
		program.branch(op, labelStack.peek());
	}
	
	public void pushLabel(String lexeme) {
		pushLabelExact(lexeme.toUpperCase() + "_" + scopeManager.getTotalScopes());
	}
	
	private void pushLabelExact(String label) {
		labelStack.push(label);
	}
	
	public void pushLabel() {
		totalLabels++;
		labelStack.push("LABEL" + totalLabels);
	}
	
	public void insertTopLabel() {
		String label = labelStack.peek();
		program.insertLabel(label);
	}
	
	public void popLabel() {
		labelStack.pop();
	}
	
	private String generateLabel() {
		totalLabels++;
		return "LABEL" + totalLabels;
	}
	
	public void pushStart() {
		startStack.push("START_" + generateLabel());
	}
	
	public void pushEnd() {
		endStack.push("END_" + generateLabel());
	}
	
	public void popStart() {
		startStack.pop();
	}
	
	public void popEnd() {
		endStack.pop();
	}
	
	public void insertStart() {
		program.insertLabel(startStack.peek());
	}
	
	public void insertEnd() {
		program.insertLabel(endStack.peek());
	}
	
	public void branchStart(RelationalOperator op) {
		program.branch(op, startStack.peek());
	}
	
	public void branchEnd(RelationalOperator op) {
		program.branch(op, endStack.peek());
	}
	
	public void branchDoWhile(RelationalOperator op) {
		program.branchDoWhile(op, labelStack.peek());
	}	

	
	public void jumpStart() {
		program.jump(startStack.peek());
	}
	
	public void jumpEnd() {
		program.jump(endStack.peek());
	}

	public void pushFunction(Symbol func) {
		pushLabelExact(AssemblyProgram.generateName(func));
    	insertTopLabel();
    	popLabel();
    	
    	
    	this.currentFunction = func;
    	System.out.println("CURRENT FUNCTION HAS BEEN DEFINED AS");
    	System.out.println(this.currentFunction);
	}
	
	public void popFunction() {		
		if (!currentFunction.getIdentifier().equals("main")) {
			program.returnToCaller();
		}
		
		this.currentFunction = null;
	}
	
	public Symbol getCurrentFunction() {
		return currentFunction;
	}
}
