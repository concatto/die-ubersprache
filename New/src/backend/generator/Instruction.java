package backend.generator;

public class Instruction {
	private String operation;
	private String operand;
	
	public Instruction(String operation, String operand) {
		this.operation = operation;
		this.operand = operand;
	}
	
	public String getOperand() {
		return operand;
	}
	
	public String getOperation() {
		return operation;
	}
}
