package grammar.actions;

import backend.SymbolTable;
import backend.generator.AssemblyProgram;

public abstract class AbstractAction {
	protected SymbolTable table;
	protected AssemblyProgram program;
	
	public AbstractAction(SymbolTable table, AssemblyProgram program) {
		this.table = table;
		this.program = program;
	}
}
