package grammar.actions;

import java.util.Stack;

import application.Logger;
import backend.Symbol;
import backend.SymbolTable;
import backend.Type;
import backend.generator.AssemblyProgram;
import grammar.SemanticError;

public class Accessor extends AbstractAction {
	private Stack<String> identifiers = new Stack<>();
	
	public Accessor(SymbolTable table, AssemblyProgram program) {
		super(table, program);
	}
	
	public void pushIdentifier(String identifier) {
		identifiers.push(identifier);
	}
	
	public String getCurrentIdentifier() {
		return identifiers.peek();
	}

	public void testArrayAccess(Type type) throws SemanticError {
		if (type != Type.INTEGER) {
    		throw new SemanticError(String.format("Cannot use an expression of type %s to index an array.", type));
    	}
	}
	
	public Symbol access() throws SemanticError {
		Symbol s = table.getSymbol(getCurrentIdentifier());
		
		System.out.println("here!");
		
		if (!s.isInitialized() && !s.isParameter() && !s.isFunction()) {
			//System.out.println("here!");
			Logger.warn(String.format("Symbol %s was used without being initialized.", s.getIdentifier()));
		}
		
		s.setUsed(true);
		
		identifiers.pop();
		return s;
	}
}
