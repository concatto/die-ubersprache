package grammar.actions;

import backend.Symbol;
import backend.SymbolTable;
import backend.Type;
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

	public Assigner(SymbolTable table) {
		super(table);
	}
	
	public void setIdentifier(String identifier) throws SemanticError {
		this.identifier = identifier;
		table.getSymbol(identifier);
	}
	
	public void commit(Type resultingType) throws SemanticError {
		Symbol symbol = table.getSymbol(identifier);
		
		System.out.printf("The resulting type for %s %s is %s\n", symbol.getType(), symbol.getIdentifier(), resultingType);
		
		assignmentOperator.setOperands(symbol.getType(), resultingType);
		Type result = assignmentOperator.verify();
		
		if (result != symbol.getType()) {
			String message = "Type %s cannot be assigned to a symbol of type %s.";
			
			throw new SemanticError(String.format(message, resultingType, symbol.getType()));
		}
		
		symbol.setInitialized(true);
	}
}
