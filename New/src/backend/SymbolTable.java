package backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import grammar.SemanticError;

public class SymbolTable {
	private HashMap<String, Symbol> table = new HashMap<>();
	private List<Symbol> secondary = new ArrayList<>();
	
	public void addSymbol(Symbol symbol) {
		table.put(symbol.getIdentifier(), symbol);
		secondary.add(symbol);
	}

	public Symbol search(String identifier, int current) {
		return secondary.stream().filter(s -> s.getIdentifier().equals(identifier) && s.getScope() <= current).findFirst().get();		
	}
	
	public Symbol getSymbol(String identifier) throws SemanticError {
		Symbol symbol = table.get(identifier);
		
		if (symbol == null) {
			throw new SemanticError("Symbol not found: " + identifier);
		}
		
		return symbol;
	}
	
	
	public int getScope(String identifier) throws SemanticError {
		Symbol symbol = table.get(identifier);
		
		if (symbol == null) {
			throw new SemanticError("Scope not found: " + identifier);
		}
		
		return symbol.getScope();
	}
	
	public boolean exists(Symbol symbol, int depth) {
		System.out.println(symbol.getCount());
		System.out.println(symbol.getDepth());
		secondary.stream().forEach(s -> {
			System.out.println(s.getCount() + ", " + s.getDepth());
		});
		
		
		return secondary.stream().filter(s -> s.getIdentifier().equals(symbol.getIdentifier()) && s.getScope() <= depth && s.getCount() <= symbol.getCount()).count() != 0;
	}
	
	public boolean existsScope(String identifier) {
		
		return table.get(identifier) != null;
	}
	
	

	public void print() {
		System.out.println("Symbol table:");
		table.values().forEach(symbol -> {
			System.out.println(symbol);
		});
	}
	
	public Collection<Symbol> getTable() {
		return table.values();
	}
	
}
