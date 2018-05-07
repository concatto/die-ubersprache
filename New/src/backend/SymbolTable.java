package backend;

import java.util.HashMap;

import grammar.SemanticError;

public class SymbolTable {
	private HashMap<String, Symbol> table = new HashMap<>();
	
	public void addSymbol(Symbol symbol) {
		table.put(symbol.getIdentifier(), symbol);
	}

	public Symbol getSymbol(String identifier) throws SemanticError {
		Symbol symbol = table.get(identifier);
		
		if (symbol == null) {
			throw new SemanticError("Symbol not found: " + identifier);
		}
		
		return symbol;
	}
	
	public boolean exists(String identifier) {
		return table.get(identifier) != null;
	}

	public void print() {
		System.out.println("Symbol table:");
		table.values().forEach(symbol -> {
			System.out.println(symbol);
		});
	}
	
}
