package grammar.actions;

import backend.SymbolTable;

public abstract class AbstractAction {
	protected SymbolTable table;
	
	public AbstractAction(SymbolTable table) {
		this.table = table;
	}
}
