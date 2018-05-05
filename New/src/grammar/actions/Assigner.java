package grammar.actions;

import backend.Symbol;
import backend.SymbolTable;

public class Assigner extends AbstractAction {
	private Symbol currentSymbol;

	public Assigner(SymbolTable table) {
		super(table);
	}
	
	public void setCurrentSymbol(String identifier) {
		currentSymbol = table.getSymbol(identifier);
	}
	
	public void commit() {
		currentSymbol.setInitialized(true);
	}
}
