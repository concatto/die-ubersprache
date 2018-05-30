package backend.operators;

import backend.LanguageData;
import backend.Type;

public class UnaryOperator extends Operator {
	public UnaryOperator(int[] tableConfiguration) {
		super(new int[][] { tableConfiguration });
	}

	@Override
	public Type verify() {
		LanguageData data = operands.get(0);
		
		return table.get(0, data.getType().ordinal());
	}
}
