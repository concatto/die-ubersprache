package grammar.actions;

import backend.Symbol;
import backend.SymbolTable;
import backend.Type;

public class Declarator {
	private String currentIdentifier;
	private Type currentType;
	private SymbolTable table;
	
	public Declarator(SymbolTable table) {
		this.table = table;
	}
	
	public void setCurrentIdentifier(String currentIdentifier) {
		this.currentIdentifier = currentIdentifier;
	}
	
	public void setCurrentType(Type currentType) {
		this.currentType = currentType;
	}
	
	public void commit() {
		Symbol symbol = new Symbol(currentIdentifier, currentType, false, false, 0);
		table.addSymbol(symbol);
		System.out.printf("Symbol added: %s %s\n", currentType, currentIdentifier);
	}

	public boolean hasType() {
		return currentType != null;
	}
	
	public void reset() {
		currentType = null;
		System.out.println("Type forgotten.");
	}
}
