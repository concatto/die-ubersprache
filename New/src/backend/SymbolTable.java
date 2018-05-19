package backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import grammar.SemanticError;

public class SymbolTable {
	private List<Symbol> table = new ArrayList<>();
	private ScopeManager scopeManager;
	
	public void setScopeManager(ScopeManager scopeManager) {
		this.scopeManager = scopeManager;
	}
	
	public void addSymbol(Symbol symbol) {
		scopeManager.declare(symbol.getIdentifier());
		table.add(symbol);
	}
	
	public Symbol getSymbol(String identifier) throws SemanticError {
		Optional<Integer> scopeOpt = scopeManager.find(identifier);
		
		if (!scopeOpt.isPresent()) {
			throw new SemanticError(String.format("Symbol %s was not declared in this scope.", identifier));
		}
		
		Symbol symbol = table.stream()
			.filter(s -> s.getIdentifier().equals(identifier) && s.getScope() == scopeOpt.get())
			.findFirst()
			.get();
		
		return symbol;
	}

	public boolean exists(String identifier) {
		return scopeManager.find(identifier).isPresent();
	}
	
	public void print() {
		System.out.println("Symbol table:");
		table.forEach(symbol -> {
			System.out.println(symbol);
		});
	}
	
	public Collection<Symbol> getTable() {
		return table;
	}
	
}
