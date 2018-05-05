package backend.operators;

import java.util.HashMap;

import backend.Type;
import backend.TypeTable;

public abstract class Operator {
	private TypeTable table;
	private static HashMap<String, Operator> operators = new HashMap<>();
	
	static {
		operators.put("+", new Add());
		operators.put("-", new Subtract());
		operators.put("*", new Multiply());
		operators.put("/", new Divide());
	}
	
	public Operator(int... tableConfiguration) {
		table = new TypeTable(tableConfiguration);
	}
	
	public Type verifyType(Type a, Type b) {
		return table.apply(a, b);
	}

	public static Operator fromLexeme(String lexeme) {
		return operators.get(lexeme);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
