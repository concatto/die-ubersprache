package backend;

import java.util.HashMap;

public class SymbolTable {
	private HashMap<String, Symbol> table = new HashMap<>();
	
	public void addSymbol(Symbol symbol) {
		if (isInsertionValid(symbol)) {
			table.put(symbol.getIdentifier(), symbol);
		}
	}
	
	private boolean isInsertionValid(Symbol symbol) {
		return false;
	}

	public Symbol getSymbol(String identifier) {
		return table.get(identifier);
	}
	

	
}
