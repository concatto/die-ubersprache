package grammar.actions;

import backend.Symbol;
import backend.SymbolTable;
import backend.Type;
import grammar.SemanticError;

public class Declarer {
	private Symbol functionSymbol = new Symbol();
	private Symbol currentSymbol;
	private int parameterPosition = -1;
	private SymbolTable table;
	
	public Declarer(SymbolTable table) {
		this.table = table;
		
		reset();
	}
	
	public void setFunctionIdentifier(String identifier) {
		functionSymbol.setIdentifier(identifier);
		functionSymbol.setFunction(true);
	}
	
	public void commitFunction() throws SemanticError {
		// The type is always stored in the general symbol
		functionSymbol.setType(currentSymbol.getType());
		
		commitSymbol(functionSymbol);
		
		// Reset the symbol
		functionSymbol = new Symbol();
	}
	
	public void setCurrentIdentifier(String identifier) {
		currentSymbol.setIdentifier(identifier);
	}
	
	public String getCurrentIdentifier() {
		return currentSymbol.getIdentifier();
	}
	
	public void setCurrentType(Type type) {
		currentSymbol.setType(type);
	}
	
	public void setFunction(boolean function) {
		currentSymbol.setFunction(function);
	}
	
	public void setArray(int size) {
		currentSymbol.setSize(size);
	}
	
	public void setParameter(boolean parameter) {
		currentSymbol.setParameter(parameter);
	}
	
	public void commit() throws SemanticError {
		commitSymbol(currentSymbol);
	}
	
	private void commitSymbol(Symbol symbol) throws SemanticError {		
		if (table.exists(symbol.getIdentifier())) {
			throw new SemanticError(String.format("Symbol %s already exists.", symbol.getIdentifier()));
		}
		
		if (symbol.isParameter()) {
			parameterPosition++;
		} else {
			parameterPosition = -1;
		}
		
		table.addSymbol(new Symbol(
				symbol.getIdentifier(), symbol.getType(),
				symbol.isFunction(), false, symbol.isParameter(), symbol.getSize(),
				0, 0, parameterPosition
		));
		System.out.printf("Symbol added: %s %s\n", symbol.getType(), symbol.getIdentifier());
	}
	
	public void reset() {
		currentSymbol = new Symbol();
	}
}
