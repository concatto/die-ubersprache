package backend.operators;

import backend.Type;

public class UnaryOperator extends Operator {
	public UnaryOperator(int[] tableConfiguration) {
		super(new int[][] { tableConfiguration });
	}

	@Override
	public Type verify() {
		Type type = operands.get(0);
		
		return table.get(0, type.ordinal());
	}
}
