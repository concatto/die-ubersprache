package grammar.actions;

import backend.Symbol;
import backend.SymbolTable;
import backend.Type;
import grammar.SemanticError;

public class Declarer {
	private String currentIdentifier;
	private Type currentType;
	private boolean function;
	private int size;
	private SymbolTable table;
	
	public Declarer(SymbolTable table) {
		this.table = table;
		
		reset();
	}
	
	public void setCurrentIdentifier(String currentIdentifier) {
		this.currentIdentifier = currentIdentifier;
	}
	
	public void setCurrentType(Type currentType) {
		this.currentType = currentType;
	}
	
	public void setFunction(boolean function) {
		this.function = function;
	}
	
	public boolean isCurrentlyFunction() {
		return function;
	}
	
	public void commit() throws SemanticError {
		if (table.getSymbol(currentIdentifier) != null) {
			throw new SemanticError(String.format("Symbol %s already exists.", currentIdentifier));
		}
		
		Symbol symbol = new Symbol(currentIdentifier, currentType, function, false, size, 0);
		
		table.addSymbol(symbol);
		System.out.printf("Symbol added: %s %s\n", currentType, currentIdentifier);
		
		reset();
	}
	
	public void reset() {
		currentType = null;
		function = false;
		size = 1;
	}

	public void setArray(int size) {
		this.size = size;
	}
}
