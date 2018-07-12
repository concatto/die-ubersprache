package backend;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

public class ScopeManager {
	private Stack<Map<String, Integer>> scopes = new Stack<>();
	private int totalScopes = 0;
  
	public ScopeManager() {
		scopes.push(new HashMap<>());
	}
	
	public void push() {
		scopes.push(new HashMap<>());
		totalScopes++;
	}
	
	public void pop() {
		scopes.pop();
	}
	
	public int getTotalScopes() {
		return totalScopes;
	}
	
	public int getDepth() {
		return scopes.size();
	}

	public void declare(Symbol symbol) {
		if (symbol.isFunction()) {
			// Insert in the parent scope!
			scopes.get(scopes.size() - 2).put(symbol.getIdentifier(), totalScopes);
		} else {
			scopes.peek().put(symbol.getIdentifier(), totalScopes);
		}
	}
	
	public Optional<Integer> find(String identifier) {
		System.out.println("Searching for " + identifier);
		int i = 0;
		for (Map<String, Integer> identifiers : scopes) {
			System.out.println("At " + (i++));
			System.out.println(identifiers.keySet().stream().collect(Collectors.joining(", ")));
			
			if (identifiers.containsKey(identifier)) {
				return Optional.of(identifiers.get(identifier));
			}
		}
		
		return Optional.empty();
	}
}
