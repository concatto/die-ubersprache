package grammar.actions;

import backend.Symbol;
import backend.SymbolTable;
import backend.Type;
import backend.TypeTable;
import grammar.SemanticError;

public class Assigner extends AbstractAction {
	private String currentIdentifier;
	private static TypeTable assignmentTable = new TypeTable(
			1, 1, 0, 1, 0, 0,
			2, 2, 0, 0, 0, 0,
			3, 0, 3, 0, 0, 0,
			4, 0, 0, 4, 0, 0,
			0, 0, 0, 0, 5, 0,
			0, 0, 0, 0, 0, 0
	);

	public Assigner(SymbolTable table) {
		super(table);
	}
	
	public void setCurrentIdentifier(String identifier) {
		this.currentIdentifier = identifier;
	}
	
	public void commit(Type resultingType) throws SemanticError {
		Symbol symbol = table.getSymbol(currentIdentifier);
		
		if (symbol == null) {
			throw new SemanticError("Symbol not found: " + currentIdentifier);
		}
		
		System.out.printf("The resulting type for %s %s is %s\n", symbol.getType(), symbol.getIdentifier(), resultingType);
		
		Type result = assignmentTable.apply(symbol.getType(), resultingType);
		
		if (result != symbol.getType()) {
			String message = "Type %s cannot be assigned to a symbol of type %s.";
			
			throw new SemanticError(String.format(message, resultingType, symbol.getType()));
		}
		
		symbol.setInitialized(true);
	}
}
