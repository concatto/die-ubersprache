package grammar.actions;

import java.util.Stack;

import backend.Symbol;
import backend.SymbolTable;
import backend.Type;
import grammar.SemanticError;

public class Declarer {
	private Stack<Symbol> symbols = new Stack<>();
	private int scope = -1;
	private int levelFromTop = 0;
	private int parameterPosition = -1;
	private SymbolTable table;
	
	public Declarer(SymbolTable table) {
		this.table = table;
		
		pushScope();
	}
	
	public void pushScope() {
		symbols.push(new Symbol());
		scope++;
	}
	
	public void descend() {
		levelFromTop++;
	}
	
	public void ascend() {
		levelFromTop--;
	}
	
	public void popScope() {
		symbols.pop();
	}
	
	public void setCurrentIdentifier(String identifier) {
		current().setIdentifier(identifier);
	}
	
	public String getCurrentIdentifier() {
		return current().getIdentifier();
	}
	
	public void setCurrentType(Type type) {
		current().setType(type);
	}
	
	public void setFunction(boolean function) {
		current().setFunction(function);
	}
	
	public void setArray(int size) {
		current().setSize(size);
	}
	
	public void setParameter(boolean parameter) {
		current().setParameter(parameter);
	}
	
	public boolean isCurrentlyFunction() {
		return current().isFunction();
	}
	
	public void commit() throws SemanticError {
		Symbol current = current();
		
		if (table.exists(current.getIdentifier())) {
			throw new SemanticError(String.format("Symbol %s already exists.", current.getIdentifier()));
		}
		
		if (current.isParameter()) {
			parameterPosition++;
		} else {
			parameterPosition = -1;
		}
		
		Symbol symbol = new Symbol(
				current.getIdentifier(), current.getType(),
				current.isFunction(), false, current.isParameter(), current.getSize(),
				scope - levelFromTop, symbols.size() - levelFromTop, parameterPosition
		);
		
		table.addSymbol(symbol);
		System.out.printf("Symbol added: %s %s\n", current.getType(), current.getIdentifier());
	}
	
	public void reset() {
		symbols.pop();
		symbols.push(new Symbol());
	}
	
	private Symbol current() {
		return symbols.get(symbols.size() - 1 - levelFromTop);
	}
}
