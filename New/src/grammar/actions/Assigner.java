package grammar.actions;

import application.Logger;
import backend.DataVariant;
import backend.LanguageData;
import backend.Symbol;
import backend.SymbolTable;
import backend.Temporary;
import backend.Type;
import backend.generator.AssemblyProgram;
import backend.operators.BinaryOperator;
import grammar.SemanticError;

public class Assigner extends AbstractAction {
	private String identifier;
	private static BinaryOperator assignmentOperator = new BinaryOperator(new int[][] {
			{1, 1, 0, 1, 0, 0},
			{2, 2, 0, 0, 0, 0},
			{3, 0, 3, 0, 0, 0},
			{4, 0, 0, 4, 0, 0},
			{0, 0, 0, 0, 5, 0},
			{0, 0, 0, 0, 0, 0}
	});

	public Assigner(SymbolTable table, AssemblyProgram program) {
		super(table, program);
	}
	
	public void setIdentifier(String identifier) throws SemanticError {
		this.identifier = identifier;
		table.getSymbol(identifier);
	}
	
	public Symbol getSymbol(String identifier) throws SemanticError {
		return table.getSymbol(identifier);
	}
	
	public void commit(LanguageData rhs) throws SemanticError {
		Symbol symbol = table.getSymbol(identifier);
		
		System.out.printf("The resulting type for %s %s is %s\n", symbol.getType(), symbol.getIdentifier(), rhs.getType());
		
		if (rhs.getType() == Type.REAL && symbol.getType() == Type.INTEGER) {
			Logger.warn(String.format("Precision loss when assigning to symbol %s.", symbol.getIdentifier()));
		}
		
		// TODO Will have to be fixed.
		if (!canAssign(symbol, rhs)) {
			String message = "Type %s cannot be assigned to a symbol of type %s.";
			
			throw new SemanticError(String.format(message, rhs.getType(), symbol.getType()));
		}
		
		symbol.setInitialized(true);
		program.load(rhs);
		program.store(symbol);
		
		
		if (rhs.getVariant() == DataVariant.TEMPORARY) {
			Temporary.release((Temporary) rhs);
		}
	}
	
	public boolean canAssign(LanguageData lhs, LanguageData rhs) {
		assignmentOperator.setOperands(lhs, rhs);
		Type result = assignmentOperator.verify();
		System.out.println("lhs " + lhs.getType() + " , rhs " + rhs.getType() + ". result " + result);
		
		return result == lhs.getType();
	}
}
