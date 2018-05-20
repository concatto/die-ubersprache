package grammar;

import backend.Evaluator;
import backend.ScopeManager;
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
	private SymbolTable table;
	private ScopeManager scopeManager;

	public Semantico(SymbolTable table) {
		this.table = table;
		this.declarer = new Declarer(table);
		this.assigner = new Assigner(table);
		this.accessor = new Accessor(table);

		scopeManager = new ScopeManager();
		this.table.setScopeManager(scopeManager);
	}

    public void executeAction(int code, Token token) throws SemanticError {
        System.out.println("Ação #"+code+", Token: "+token);

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
        	declarer.setFunctionIdentifier(lexeme);
        	scopeManager.push();
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
        	// Checks if the symbol exists
        	table.getSymbol(accessor.getCurrentIdentifier());
        	
        	assigner.setIdentifier(accessor.getCurrentIdentifier());
        	break;

        case COMPLETE_DECLARATION:
        	declarer.setScope(scopeManager.getTotalScopes());
        	declarer.commit();
        	break;

        case COMPLETE_FUNCTION_DECLARATION:
        	declarer.setScope(scopeManager.getTotalScopes());
        	declarer.commitFunction();
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
        	evaluator.pushType(accessor.access().getType());
        	break;

        case SET_ARRAY_SIZE:
        	declarer.setArray(Integer.parseInt(lexeme));
        	break;

        case ACCESS_POSITION:
        	accessor.testArrayAccess(evaluator.pop());
        	break;

        case EVALUATE_BINARY:
        	evaluator.evaluate(2);
        	break;

        case EVALUATE_UNARY:
        	evaluator.evaluate(1);
        	break;

        case BEGIN_PARAMETERS:
        	declarer.setParameter(true);
        	break;
        	
        case CLOSE_SCOPE:
        	scopeManager.pop();
        	break;
        case OPEN_SCOPE:
        	scopeManager.push();
        	break;
        }
    }

////////////////////////CORRETO!

}
