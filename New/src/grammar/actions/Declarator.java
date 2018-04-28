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
	
	public void commit(boolean function) {
		Symbol symbol = new Symbol(currentIdentifier, currentType, function);
		table.addSymbol(symbol);
		
	}
}
