package grammar;

import java.util.Random;

import backend.Evaluator;
import backend.SymbolTable;
import backend.Type;
import backend.operators.Operator;
import grammar.actions.Action;
import grammar.actions.Assigner;
import grammar.actions.Declarer;

public class Semantico implements Constants
{
	private Declarer declarator;
	private Assigner assigner;
	private Evaluator evaluator = new Evaluator();
	
	public Semantico(SymbolTable table) {
		this.declarator = new Declarer(table);
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
        case PUSH_LITERAL:
        	evaluator.pushType(Type.deduceLiteral(code));
        	break;
        case EVALUATE:
        	evaluator.evaluate();
        	break;
        }
    }
    
    
    
}
