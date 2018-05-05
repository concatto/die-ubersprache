package grammar;

import java.util.Random;

import backend.Evaluator;
import backend.SymbolTable;
import backend.Type;
import backend.operators.Operator;
import grammar.actions.Action;
import grammar.actions.Declarator;

public class Semantico implements Constants
{
	private Declarator declarator;
	private Evaluator evaluator = new Evaluator();
	
	public Semantico(SymbolTable table) {
		this.declarator = new Declarator(table);
	}
	
    public void executeAction(int code, Token token) throws SemanticError {
        System.out.println("Ação #"+code+", Token: "+token);
        
        Action action = Action.parse(code);
        String lexeme = token.getLexeme();
        
        if (action == null) {
        	return;
        }
        
        switch (action) {
        case DECLARE_TYPE:
        	declarator.setCurrentType(Type.fromLexeme(lexeme));
        	break;
        case DECLARE_ID:
        	declarator.setCurrentIdentifier(lexeme);
        	
        	if (declarator.hasType()) {
        		declarator.commit();
        	}
        	break;
        case COMPLETE_DECLARATION:
        	declarator.reset();
        	break;
        case PUSH_OPERATOR:
        	evaluator.pushOperator(Operator.fromLexeme(lexeme));
        	break;
        case PUSH_LITERAL:
        	evaluator.pushType(new Random().nextBoolean() ? Type.INTEGER : Type.STRING);
        	break;
        case EVALUATE:
        	evaluator.evaluate();
        	break;
        }
    }
    
    
    
}
