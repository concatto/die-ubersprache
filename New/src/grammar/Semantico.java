package grammar;

import backend.Evaluator;
import backend.SymbolTable;
import backend.Type;
import backend.operators.Operator;
import grammar.actions.Accessor;
import grammar.actions.Action;
import grammar.actions.Assigner;
import grammar.actions.Declarer;

public class Semantico implements Constants
{
	private Declarer declarer;
	private Assigner assigner;
	private Accessor accessor;
	private Evaluator evaluator = new Evaluator();
	
	public Semantico(SymbolTable table) {
		this.declarer = new Declarer(table);
		this.assigner = new Assigner(table);
		this.accessor = new Accessor(table);
	}
	
    public void executeAction(int code, Token token) throws SemanticError {
        System.out.println("A��o #"+code+", Token: "+token);
        
        Action action = Action.parse(code);
        String lexeme = token.getLexeme();
        
        if (action == null) {
        	return;
        }
        
        System.out.println(action);
        
        switch (action) {
        case DECLARE_TYPE:
        	declarer.setCurrentType(Type.fromLexeme(lexeme));
        	break;
        	
        case STORE_ID_FUNCTION:
        	declarer.setCurrentIdentifier(lexeme);
        	declarer.setFunction(true);
        	break;
        	
        case STORE_ID_DECLARATION:
        	declarer.setCurrentIdentifier(lexeme);
        	break;
        	
        case STORE_ID_GENERAL:
        	accessor.pushIdentifier(lexeme);
        	break;
        
        case ASSIGNMENT_FROM_DECLARATION:
        	assigner.setIdentifier(declarer.getCurrentIdentifier());
        	break;
        	
        case ASSIGNMENT_FROM_ACCESS:
        	assigner.setIdentifier(accessor.getCurrentIdentifier());
        	break;
        	
        case COMPLETE_DECLARATION:
        	declarer.commit();	
        	break;
        	
        case COMPLETE_ASSIGNMENT:
        	assigner.commit(evaluator.pop());
        	break;
        
        case RESET_DECLARER:
        	declarer.reset();
        	
        case PUSH_OPERATOR:
        	evaluator.pushOperator(Operator.fromLexeme(lexeme));
        	break;
        	
        case PUSH_LITERAL:
        	evaluator.pushType(Type.deduceLiteral(code));
        	break;
        	
        case PUSH_SYMBOL:
        	evaluator.pushType(accessor.access());
        	break;
        	
        case SET_ARRAY_SIZE:
        	declarer.setArray(Integer.parseInt(lexeme));
        	break;
        
        case ACCESS_POSITION:
        	accessor.testArrayAccess(evaluator.pop());
        	break;
        	
        case EVALUATE:
        	evaluator.evaluate();
        	break;
        	
        case PUSH_SCOPE:
        	declarer.pushScope();
        	break;
        	
        case POP_SCOPE:
        	declarer.popScope();
        	break;
        	
        case ASCEND_SCOPE:
        	declarer.ascend();
        	break;
        	
        case DESCEND_SCOPE:
        	declarer.descend();
        	break;
        
        case BEGIN_PARAMETERS:
        	declarer.setParameter(true);
        	break;
        }
    }
    
    
    
}
