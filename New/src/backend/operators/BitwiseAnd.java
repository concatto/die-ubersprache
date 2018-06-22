package backend.operators;

public class BitwiseAnd extends BinaryOperator {
	public BitwiseAnd() {
		super(new int[][] {
				{1, 0, 1, 0, 0, 0},
				{0, 0, 0, 0, 0, 0},
				{1, 0, 3, 0, 0, 0},
				{0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0}
		});
	}
}
