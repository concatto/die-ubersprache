package backend.operators;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import backend.LanguageData;
import backend.Type;
import backend.TypeTable;

public abstract class Operator {
	protected TypeTable table;
	protected List<LanguageData> operands;
	private static HashMap<String, Operator> operators = new HashMap<>();
	
	static {
		operators.put("+", new Add());
		operators.put("-", new Subtract());
		operators.put("*", new Multiply());
		operators.put("/", new Divide());
		operators.put("nicht", new Negate());
		operators.put("&", new BitwiseAnd());
		operators.put("|", new BitwiseOr());
		operators.put("grösser als", new GreaterThan());
		operators.put("kleiner als", new LessThan());
		operators.put("grösser oder gleich", new GreaterOrEqualThan());
		operators.put("kleiner oder gleich", new LessOrEqualThan());
		operators.put("gleich", new EqualThan());
		operators.put("ungleich", new DifferentThan());
	}
	
	public Operator(int[][] tableConfiguration) {
		table = new TypeTable(tableConfiguration);
	}
	
	public void setOperands(LanguageData... operands) {
		this.operands = Arrays.asList(operands);
	}
	
	public void setOperands(List<LanguageData> operands) {
		this.operands = operands;
	}
	
	public List<LanguageData> getOperands() {
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
