package backend.operators;

import backend.LanguageData;
import backend.Type;

public class BinaryOperator extends Operator {
	public BinaryOperator(int[][] tableConfiguration) {
		super(tableConfiguration);
	}

	@Override
	public Type verify() {
		LanguageData a = operands.get(0);
		LanguageData b = operands.get(1);
		
		return table.get(a.getType().ordinal(), b.getType().ordinal());
	}
}
