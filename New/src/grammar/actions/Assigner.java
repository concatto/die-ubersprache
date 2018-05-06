package grammar.actions;

import backend.Symbol;
import backend.SymbolTable;
import backend.Type;
import backend.TypeTable;
import grammar.SemanticError;

public class Assigner extends AbstractAction {
	private Symbol currentSymbol;
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
	
	public void setCurrentSymbol(String identifier) throws SemanticError {
		System.out.println("Searching " + identifier);
		currentSymbol = table.getSymbol(identifier);
		
		if (currentSymbol == null) {
			throw new SemanticError("Symbol not found: " + identifier);
		}
		
		System.out.printf("Current symbol is now %s %s\n", currentSymbol.getType(), currentSymbol.getIdentifier());
	}
	
	public void commit(Type resultingType) throws SemanticError {
		System.out.printf("The resulting type for %s %s is %s\n", currentSymbol.getType(), currentSymbol.getIdentifier(), resultingType);
		
		Type result = assignmentTable.apply(currentSymbol.getType(), resultingType);
		
		if (result != currentSymbol.getType()) {
			String message = "Type %s cannot be assigned to a symbol of type %s.";
			
			throw new SemanticError(String.format(message, resultingType, currentSymbol.getType()));
		}
		
		currentSymbol.setInitialized(true);
	}
}
