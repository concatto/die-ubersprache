package grammar;

import backend.Evaluator;
import backend.LanguageData;
import backend.Literal;
import backend.ScopeManager;
import backend.Symbol;
import backend.SymbolTable;
import backend.Temporary;
import backend.Type;
import backend.generator.AssemblyProgram;
import backend.generator.InstructionSection;
import backend.operators.Operator;
import grammar.actions.Accessor;
import grammar.actions.Action;
import grammar.actions.Assigner;
import grammar.actions.Declarer;

public class Semantico implements Constants {
	private Declarer declarer;
	private Assigner assigner;
	private Accessor accessor;
	private Evaluator evaluator = new Evaluator();
	private InstructionSection instructionSection = new InstructionSection();
	private SymbolTable table;
	private ScopeManager scopeManager;
	private AssemblyProgram program;

	public Semantico(SymbolTable table, AssemblyProgram program) {
		this.table = table;
		this.program = program;
		this.declarer = new Declarer(table, program);
		this.assigner = new Assigner(table, program);
		this.accessor = new Accessor(table, program);

		evaluator.setProgram(program);
		scopeManager = new ScopeManager();
		this.table.setScopeManager(scopeManager);
	}
	
	boolean firstLDI = true;
	boolean afterErhalt = false; 
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
        	afterErhalt = true;
        	assigner.setIdentifier(accessor.getCurrentIdentifier());
        	break;

        case COMPLETE_DECLARATION:
        	declarer.setScope(scopeManager.getTotalScopes());
        	declarer.setDepth(scopeManager.getDepth());
        	declarer.commit();
        	break;

        case COMPLETE_FUNCTION_DECLARATION:
        	declarer.setScope(scopeManager.getTotalScopes());
        	declarer.commitFunction();
        	break;

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
        	Symbol nameVector =  table.getSymbol(accessor.getCurrentIdentifier());
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
        	break;
        case CLOSE_SCOPE:
        	scopeManager.pop();
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
        }
    }


}
