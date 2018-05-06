package backend;

import java.util.HashMap;

public class SymbolTable {
	private HashMap<String, Symbol> table = new HashMap<>();
	
	public void addSymbol(Symbol symbol) {
		table.put(symbol.getIdentifier(), symbol);
	}

	public Symbol getSymbol(String identifier) {		
		return table.get(identifier);
	}
	
}
