package backend.operators;

import backend.Type;

public class BinaryOperator extends Operator {
	public BinaryOperator(int[][] tableConfiguration) {
		super(tableConfiguration);
	}

	@Override
	public Type verify() {
		Type a = operands.get(0);
		Type b = operands.get(1);
		
		return table.get(a.ordinal(), b.ordinal());
	}
}
