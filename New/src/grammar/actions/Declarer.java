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
	private int depth = 0;

	public Declarer(SymbolTable table) {
		this.table = table;
		reset();
	}

	public void setFunctionIdentifier(String identifier) {
		functionSymbol.setIdentifier(identifier);
		functionSymbol.setFunction(true);
		
	}
	
	public boolean isFunction() {
		return currentSymbol.isFunction();
	}

	public void commitFunction(int depth) throws SemanticError {
		// The type is always stored in the general symbol
		functionSymbol.setType(currentSymbol.getType());
		commitSymbol(functionSymbol, depth);

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

	public void setScope(int scope) {
		currentSymbol.setScope(scope);
	}	
	
	public void setParameter(boolean parameter) {
		currentSymbol.setParameter(parameter);
	}

	public void commit(int depth) throws SemanticError {
		commitSymbol(currentSymbol, depth);
	}

	private void commitSymbol(Symbol symbol, int depth) throws SemanticError {
		if (table.exists(symbol, depth)) {
			throw new SemanticError(String.format("Symbol %s already exists.", symbol.getIdentifier()));
		}

		if (symbol.isParameter()) {
			parameterPosition++;
		} else {
			parameterPosition = -1;
		}

		Symbol toBeAdded = new Symbol(
				symbol.getIdentifier(), symbol.getType(),
				symbol.isFunction(), false, symbol.isParameter(), false, symbol.getSize(),
				symbol.getScope(), 0, parameterPosition
		);
		toBeAdded.setCount(symbol.getCount());
		table.addSymbol(toBeAdded);
		System.out.printf("Symbol added: %s %s %s\n", symbol.getType(), symbol.getIdentifier(), symbol.getScope());
	}

	public void reset() {
		currentSymbol = new Symbol();
	}

	public void setCount(int count) {
		currentSymbol.setCount(count);
	}
}
