package backend.operators;

public class Multiply extends BinaryOperator {
	public Multiply() {
		super(new int[][] {
				{1, 2, 0, 1, 0, 0},
				{2, 2, 0, 2, 0, 0},
				{0, 0, 0, 0, 0, 0},
				{1, 2, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0}
		});
	}
}
