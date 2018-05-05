package backend;

import java.util.HashMap;
import java.util.stream.Collectors;

public class SymbolTable {
	private HashMap<String, Symbol> table = new HashMap<>();
	
	public void addSymbol(Symbol symbol) {
		if (isInsertionValid(symbol)) {
			table.put(symbol.getIdentifier(), symbol);
		}
	}
	
	private boolean isInsertionValid(Symbol symbol) {
		return true;
	}

	public Symbol getSymbol(String identifier) {
		table.keySet().stream().collect(Collectors.joining(", "));
		return table.get(identifier);
	}
	

	
}
