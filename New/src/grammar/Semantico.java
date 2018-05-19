package grammar;

import java.net.Socket;
import java.util.Stack;

import backend.Evaluator;
<<<<<<< Updated upstream
import backend.Symbol;
=======
import backend.ScopeManager;
>>>>>>> Stashed changes
import backend.SymbolTable;
import backend.Type;
import backend.operators.Operator;
import grammar.actions.Accessor;
import grammar.actions.Action;
import grammar.actions.Assigner;
import grammar.actions.Declarer;
import javafx.util.Pair;

public class Semantico implements Constants
{
	private Declarer declarer;
	private Assigner assigner;
	private Accessor accessor;
	private Evaluator evaluator = new Evaluator();
<<<<<<< Updated upstream
	private Stack scopes = new Stack<>();
	private int level = 0;
	private int count = 0;
	private SymbolTable table;
=======
	private SymbolTable table;
	private ScopeManager scopeManager;
>>>>>>> Stashed changes

	public Semantico(SymbolTable table) {
		this.table = table;
		this.declarer = new Declarer(table);
		this.assigner = new Assigner(table);
		this.accessor = new Accessor(table);

<<<<<<< Updated upstream
=======
		scopeManager = new ScopeManager();
		this.table.setScopeManager(scopeManager);
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
        	scopes.push(level);
        	count++;
        	level++;
=======
        	scopeManager.push();
>>>>>>> Stashed changes
        	break;

        case STORE_ID_DECLARATION:
        	declarer.setCurrentIdentifier(lexeme);
        	declarer.setCount(count);
        	break;

        case STORE_ID_GENERAL:
        	accessor.pushIdentifier(lexeme);
        	break;

        case ASSIGNMENT_FROM_DECLARATION:
        	assigner.setIdentifier(declarer.getCurrentIdentifier());
        	break;

        case ASSIGNMENT_FROM_ACCESS:
<<<<<<< Updated upstream
        	Symbol s = table.search(accessor.getCurrentIdentifier(), scopes.size());
        	
        	if(s == null) {
        		throw new SemanticError("A variavel "+accessor.getCurrentIdentifier()+" não foi declarada nesse escopo!");
        	}
=======
        	// Checks if the symbol exists
        	table.getSymbol(accessor.getCurrentIdentifier());
        	
>>>>>>> Stashed changes
        	assigner.setIdentifier(accessor.getCurrentIdentifier());
        	break;

        case COMPLETE_DECLARATION:
<<<<<<< Updated upstream
        	declarer.setScope(level);
        	declarer.commit(scopes.size());
        	break;

        case COMPLETE_FUNCTION_DECLARATION:
        	declarer.commitFunction(scopes.size());
=======
        	declarer.setScope(scopeManager.getTotalScopes());
        	declarer.commit();
        	break;

        case COMPLETE_FUNCTION_DECLARATION:
        	declarer.setScope(scopeManager.getTotalScopes());
        	declarer.commitFunction();
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
        	scopes.pop();
        	break;
        case OPEN_SCOPE:
        	level++;
        	count++;
        	scopes.push(level);
=======
        	scopeManager.pop();
        	break;
        case OPEN_SCOPE:
        	scopeManager.push();
>>>>>>> Stashed changes
        	break;
        }
    }

////////////////////////CORRETO!

}
