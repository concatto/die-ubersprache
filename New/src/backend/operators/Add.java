package backend.operators;

public class Add extends BinaryOperator {
	public Add() {
		super(new int[][] {
				{1, 2, 0, 1, 0, 0},
				{2, 2, 0, 0, 0, 0},
				{0, 0, 3, 0, 0, 0},
				{1, 0, 0, 4, 0, 0},
				{0, 0, 0, 0, 5, 0},
				{0, 0, 0, 0, 0, 0}
		});
	}
}
