package backend.operators;

public class BitwiseOr extends BinaryOperator {
	public BitwiseOr() {
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
