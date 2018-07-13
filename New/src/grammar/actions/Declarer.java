package grammar.actions;

import backend.Symbol;
import backend.SymbolTable;
import backend.Type;
import backend.generator.AssemblyProgram;
import grammar.SemanticError;

public class Declarer extends AbstractAction {
	private Symbol functionSymbol = new Symbol();
	private Symbol currentSymbol;
	private int parameterPosition = -1;

	public Declarer(SymbolTable table, AssemblyProgram program) {
		super(table, program);
		reset();
	}

	public void setFunctionIdentifier(String identifier) {
		functionSymbol.setIdentifier(identifier);
		functionSymbol.setFunction(true);
	}
	
	public boolean isFunction() {
		return currentSymbol.isFunction();
	}

	public Symbol commitFunction() throws SemanticError {
		// The type is always stored in the general symbol
		functionSymbol.setType(currentSymbol.getType());
		functionSymbol.setScope(currentSymbol.getScope());
		commitSymbol(functionSymbol);

		Symbol s = functionSymbol;
		
		// Reset the symbol
		functionSymbol = new Symbol();
		
		return s;
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
	
	public void setDepth(int depth) {
		currentSymbol.setDepth(depth);
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

		Symbol toBeAdded = new Symbol(
				symbol.getIdentifier(), symbol.getType(), symbol.isFunction(),
				symbol.isInitialized(), symbol.isUsed(), symbol.isParameter(), symbol.getSize(),
				symbol.getScope(), symbol.getDepth(), parameterPosition
		);
		
		toBeAdded.setParentFunction(symbol.getParentFunction());
		
		table.addSymbol(toBeAdded);
		System.out.printf("Symbol added: %s %s %s\n", symbol.getType(), symbol.getIdentifier(), symbol.getScope());
	}

	public void reset() {
		currentSymbol = new Symbol();
	}

	public void setParentFunction(Symbol currentFunction) {
		currentSymbol.setParentFunction(currentFunction);		
	}
	
	public Symbol getCurrentFunctionSymbol() {
		return functionSymbol;
	}
}
