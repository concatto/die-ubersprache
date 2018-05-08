package backend.operators;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import backend.Type;
import backend.TypeTable;

public abstract class Operator {
	protected TypeTable table;
	protected List<Type> operands;
	private static HashMap<String, Operator> operators = new HashMap<>();
	
	static {
		operators.put("+", new Add());
		operators.put("-", new Subtract());
		operators.put("*", new Multiply());
		operators.put("/", new Divide());
		operators.put("nicht", new Negate());
	}
	
	public Operator(int[][] tableConfiguration) {
		table = new TypeTable(tableConfiguration);
	}
	
	public void setOperands(Type... operands) {
		this.operands = Arrays.asList(operands);
	}
	
	public void setOperands(List<Type> operands) {
		this.operands = operands;
	}
	
	public List<Type> getOperands() {
		return operands;
	}
	
	public abstract Type verify();

	public static Operator fromLexeme(String lexeme) {
		return operators.get(lexeme);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
