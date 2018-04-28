package grammar;

import backend.SymbolTable;
import backend.Type;
import grammar.actions.Action;
import grammar.actions.Declarator;

public class Semantico implements Constants
{
	private Declarator declarator;
	
	
	
	public Semantico(SymbolTable table) {
		this.declarator = new Declarator(table);
	}
	
    public void executeAction(int code, Token token)	throws SemanticError {
        System.out.println("Ação #"+code+", Token: "+token);
        
        Action action = Action.parse(code);
        String lexeme = token.getLexeme();
        
        switch (action) {
        case DECLARE_TYPE:
        	declarator.setCurrentType(Type.fromLexeme(lexeme));
        	break;
        case DECLARE_ID:
        	declarator.setCurrentIdentifier(lexeme);
        	declarator.commit(false);
        	break;
        }
    }
    
    
    
}
