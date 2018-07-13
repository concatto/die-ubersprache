package backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import grammar.SemanticError;

public class SymbolTable {
	private List<Symbol> table = new ArrayList<>();
	private ScopeManager scopeManager;
	
	public void setScopeManager(ScopeManager scopeManager) {
		this.scopeManager = scopeManager;
	}
	
	public void addSymbol(Symbol symbol) {
		scopeManager.declare(symbol);
		table.add(symbol);
	}
	
	public Symbol getSymbol(String identifier) throws SemanticError {
		Optional<Integer> scopeOpt = scopeManager.find(identifier);
		
		if (!scopeOpt.isPresent()) {
			throw new SemanticError(String.format("Symbol %s was not declared in this scope.", identifier));
		}
		
		System.out.println("Looking for scope " + scopeOpt.get());
		table.stream().forEach(s -> {
			System.out.println(s.getIdentifier() + ", " + s.getScope());
		});
		
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
	
	public Collection<Symbol> getSymbols() {
		return table;
	}
	
	public List<Symbol> getParameters(Symbol function) {
		return table.stream()
				.filter(s -> s.getParentFunction() != null && s.getParentFunction().equals(function))
				.sorted((a, b) -> a.getParameterPosition() - b.getParameterPosition())
				.collect(Collectors.toList());
	}
}
