package grammar.actions;

import application.Logger;
import backend.Symbol;
import backend.SymbolTable;
import backend.Temporary;
import backend.Type;
import backend.generator.AssemblyProgram;
import backend.operators.BinaryOperator;
import grammar.SemanticError;

public class Assigner extends AbstractAction {
	private String identifier;
	private static BinaryOperator assignmentOperator = new BinaryOperator(new int[][] {
			{1, 1, 0, 1, 0, 0},
			{2, 2, 0, 0, 0, 0},
			{3, 0, 3, 0, 0, 0},
			{4, 0, 0, 4, 0, 0},
			{0, 0, 0, 0, 5, 0},
			{0, 0, 0, 0, 0, 0}
	});

	public Assigner(SymbolTable table, AssemblyProgram program) {
		super(table, program);
	}
	
	public void setIdentifier(String identifier) throws SemanticError {
		this.identifier = identifier;
		table.getSymbol(identifier);
	}
	
	public Symbol getSymbol(String identifier) throws SemanticError {
		return table.getSymbol(identifier);
	}
	
	public void commit(Type resultingType) throws SemanticError {
		Symbol symbol = table.getSymbol(identifier);
		
		System.out.printf("The resulting type for %s %s is %s\n", symbol.getType(), symbol.getIdentifier(), resultingType);
		
		if (resultingType == Type.REAL && symbol.getType() == Type.INTEGER) {
			Logger.warn(String.format("Precision loss when assigning to symbol %s.", symbol.getIdentifier()));
		}
		
		assignmentOperator.setOperands(symbol, new Temporary(resultingType));
		Type result = assignmentOperator.verify();
		
		if (result != symbol.getType()) {
			String message = "Type %s cannot be assigned to a symbol of type %s.";
			
			throw new SemanticError(String.format(message, resultingType, symbol.getType()));
		}
		
		symbol.setInitialized(true);
		
		program.store(symbol);
	}
}
