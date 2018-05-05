package grammar.actions;

import backend.Symbol;
import backend.SymbolTable;
import backend.Type;
import backend.TypeTable;

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
	
	public void setCurrentSymbol(String identifier) {
		System.out.println("Searching " + identifier);
		currentSymbol = table.getSymbol(identifier);
		
		if (currentSymbol == null) {
			System.out.println("Not found");
		} else {
			System.out.printf("Current symbol is now %s %s\n", currentSymbol.getType(), currentSymbol.getIdentifier());
		}
	}
	
	public void commit(Type resultingType) {
		System.out.printf("The resulting type for %s %s is %s\n", currentSymbol.getType(), currentSymbol.getIdentifier(), resultingType);
		
		Type result = assignmentTable.apply(currentSymbol.getType(), resultingType);
		
		if (result == currentSymbol.getType()) {
			currentSymbol.setInitialized(true);
			System.out.println("Ok!");
		} else {
			System.out.println("Invalid assignment");
		}
		
	}
}
