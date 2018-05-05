package grammar;

import java.util.Random;

import backend.Evaluator;
import backend.SymbolTable;
import backend.Type;
import backend.operators.Operator;
import grammar.actions.Action;
import grammar.actions.Assigner;
import grammar.actions.Declarator;

public class Semantico implements Constants
{
	private Declarator declarator;
	private Assigner assigner;
	private Evaluator evaluator = new Evaluator();
	
	public Semantico(SymbolTable table) {
		this.declarator = new Declarator(table);
		this.assigner = new Assigner(table);
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
        case STORE_ID:
        	declarator.setCurrentIdentifier(lexeme);
        	
        	if (declarator.hasType()) {
        		declarator.commit();
        	}
        	
        	assigner.setCurrentSymbol(lexeme);
        	break;
        case COMPLETE_DECLARATION:
        	declarator.reset();
        	break;
        case COMPLETE_ASSIGNMENT:
        	assigner.commit(evaluator.pop());
        	break;
        case PUSH_OPERATOR:
        	evaluator.pushOperator(Operator.fromLexeme(lexeme));
        	break;
        case PUSH_INTEGER:
        	evaluator.pushType(Type.INTEGER);
        	break;
        case PUSH_REAL:
        	evaluator.pushType(Type.REAL);
        	break;
        case PUSH_STRING:
        	evaluator.pushType(Type.STRING);
        	break;
        case PUSH_BOOLEAN:
        	evaluator.pushType(Type.BOOLEAN);
        	break;
        case EVALUATE:
        	evaluator.evaluate();
        	break;
        }
    }
    
    
    
}
