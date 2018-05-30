package backend.operators;

import backend.Type;
import backend.generator.AssemblyProgram;

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
	
	@Override
	public Type verify() {
		// TODO FIX!!! THIS IS TEMPORARY
		AssemblyProgram temp = new AssemblyProgram();
		temp.add(operands.get(0), operands.get(1));
		System.out.println(temp.generateProgram());
		return super.verify();
	}
}
