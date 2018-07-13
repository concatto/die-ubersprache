package grammar;

import java.util.List;
import java.util.stream.Collectors;

import backend.Evaluator;
import backend.FlowManager;
import backend.LanguageData;
import backend.ScopeManager;
import backend.Symbol;
import backend.SymbolTable;
import backend.Temporary;
import backend.Type;
import backend.generator.AssemblyProgram;
import backend.operators.Operator;
import backend.operators.RelationalOperator;
import grammar.actions.Accessor;
import grammar.actions.Action;
import grammar.actions.Assigner;
import grammar.actions.Declarer;

public class Semantico implements Constants {
	private Declarer declarer;
	private Assigner assigner;
	private Accessor accessor;
	private Evaluator evaluator = new Evaluator();
	private SymbolTable table;
	private ScopeManager scopeManager;
	private FlowManager flowManager;
	private AssemblyProgram program;

	public Semantico(SymbolTable table, AssemblyProgram program) {
		this.table = table;
		this.program = program;
		this.declarer = new Declarer(table, program);
		this.assigner = new Assigner(table, program);
		this.accessor = new Accessor(table, program);

		evaluator.setProgram(program);
		scopeManager = new ScopeManager();
		flowManager = new FlowManager(scopeManager, program);
		this.table.setScopeManager(scopeManager);
	}
	
	boolean firstLDI = true;
	boolean afterErhalt = false; 
	int countParams = 0;
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
        	afterErhalt = true;
        	assigner.setIdentifier(accessor.getCurrentIdentifier());
        	break;

        case COMPLETE_DECLARATION:
        	declarer.setParentFunction(declarer.getCurrentFunctionSymbol());
        	declarer.setScope(scopeManager.getTotalScopes());
        	declarer.setDepth(scopeManager.getDepth());
        	declarer.commit();
        	break;

        case COMPLETE_FUNCTION_DECLARATION: {
        	declarer.setScope(scopeManager.getTotalScopes());
        	Symbol func = declarer.commitFunction();
        	
        	flowManager.pushFunction(func);
        	
        	break;
        }

        case COMPLETE_ASSIGNMENT:
        	assigner.commit(evaluator.dataStack.peek());
        	afterErhalt = false;
        	//System.out.println("TESTE: "+((Literal<Integer>) evaluator.pop()).getValue());
        	break;

        case RESET_DECLARER:
        	declarer.reset();
        	break;

        case PUSH_OPERATOR:
        	evaluator.pushOperator(Operator.fromLexeme(lexeme));
        	break;

        case PUSH_LITERAL:
        	evaluator.pushLiteral(code, lexeme);
        	//if(firstLDI) {
        		//program.load(evaluator.dataStack.peek());
        		//firstLDI = false;
        	//}
        	program.load(evaluator.dataStack.peek());
        	
        	break;


        case PUSH_SYMBOL:
        	evaluator.pushSymbol(accessor.access());
        	
        	break;
        	
        case SET_ARRAY_SIZE:
        	declarer.setArray(Integer.parseInt(lexeme));
        	break;
        	
        case ACCESS_POSITION:
        	LanguageData index = evaluator.dataStack.peek();
        	System.out.println("Pilha Data"+evaluator.dataStack.peek().getVariant());
        	Symbol nameVector = table.getSymbol(accessor.getCurrentIdentifier());
        	accessor.testArrayAccess(index.getType());
        	Temporary temp = program.evaluateVector(index, nameVector, afterErhalt, evaluator.dataStack.peek());

        	evaluator.push(temp);
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
        	
        case OPEN_SCOPE:
        	scopeManager.push();
        	//flowManager.pushLabel(lexeme);
        	break;
        	
        case CLOSE_SCOPE:
        	scopeManager.pop();
        	//flowManager.popLabel();
        	break;
        	
        case OPEN_SCOPE_FUNCTION:
        	break;
        	
        case READ:
        	Symbol s = table.getSymbol(accessor.getCurrentIdentifier());
        	program.read(s);
        	
        	break;
        
        case WRITE:
        	LanguageData data = evaluator.pop();
        	program.write(data);
        	
        	break;
        
        case INSERT_LABEL:
        	flowManager.insertTopLabel();
        	break;
        	
        case PUSH_NEW_LABEL:
        	flowManager.pushLabel();
        	break;
        
        case POP_LABEL:
        	flowManager.popLabel();
        	break;
        	
        case PUSH_START_LABEL:
        	flowManager.pushStart();
        	break;
        	
        case PUSH_END_LABEL:
        	flowManager.pushEnd();
        	break;
        	
    	case INSERT_START_LABEL:
    		flowManager.insertStart();
    		break;
    		
    	case INSERT_END_LABEL:
    		flowManager.insertEnd();
    		break;
    		
    	case POP_START_LABEL:
    		flowManager.popStart();
    		break;
    		
    	case POP_END_LABEL:
    		flowManager.popEnd();
    		break;
    		
    	case BRANCH_TO_START:
        	// No need to keep the last temporary
        	Temporary.release((Temporary) evaluator.pop());
    		flowManager.branchStart((RelationalOperator) evaluator.getLastOperator());
    		break;
    		
    	case JUMP_TO_START:
    		flowManager.jumpStart();
    		break;
    		    		
    	case BRANCH_TO_END:
        	// No need to keep the last temporary
        	Temporary.release((Temporary) evaluator.pop());
    		flowManager.branchEnd((RelationalOperator) evaluator.getLastOperator());
    		break;
    		
    	case JUMP_TO_END:
    		flowManager.jumpEnd();
    		break;
    		
    	case START_RECORDING:
    		program.startRecording();
    		break;
    		
    	case END_RECORDING:
    		program.endRecording();
    		break;
    		
    	case INSERT_RECORDING:
    		program.insertRecording();
    		break;
    		
    	case OPEN_DO:
    		flowManager.pushLabel(lexeme);
    		flowManager.insertTopLabel();	
    		break;
    	
    	case CALL_PARARAMETERS:
    		Symbol function = (Symbol) evaluator.getFunction(); 
    		List<Symbol> parameters = table.getParameters(function);
    		
    		if (parameters.size() == 0 || parameters.size() < countParams + 1) {	
    			throw new SemanticError(String.format("Function does not have the same amount of parameters. TOO MUCH!!"));
    		} else if (evaluator.dataStack.size() > 1) {
        		LanguageData data2 = evaluator.pop();
        		countParams++;
        		program.load(data2);
        		program.store(parameters.get(countParams-1));     			        		
    		}
    	
    		break;
    		
		case TEST_CONDITION_DO_WHILE:
			// No need to keep the last temporary
        	Temporary.release((Temporary) evaluator.pop());
    		flowManager.branchDoWhile((RelationalOperator) evaluator.getLastOperator());
			
			break;
		case STORE_RETURN_VALUE: {
			Symbol func = flowManager.getCurrentFunction();
			LanguageData returnValue = evaluator.pop();
			
			if (!assigner.canAssign(func, returnValue)) {
				String template = "Cannot return type %s from a function that returns %s.";
				
				throw new SemanticError(String.format(template, returnValue.getType(), func.getType()));
			}
			
			program.storeReturnValue(returnValue);
			break;
		}
		
		case CALL_PROCEDURE: {
			Symbol func = (Symbol) evaluator.pop();
		
			System.out.println("Parameters of " + func.getIdentifier());
			for (Symbol param : table.getParameters(func)) {
				System.out.println(param);
			}
			
			List<Symbol> parameters2 = table.getParameters(func);
			if(parameters2.size() > countParams) {
				throw new SemanticError(String.format("Function does not have the same amount of parameters. NOT ENOUGH!"));
			}
			countParams = 0;
			program.call(func);
			break;
		}
		
		case RETURN:
			flowManager.popFunction();
			break;
		
		}

		
    }


}
