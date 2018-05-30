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
        System.out.println("Ação #"+code+", Token: "+token);

        Action action = Action.parse(code);
        String lexeme = token.getLexeme();

        if (action == null) {
        	return;
        }

        System.out.println(action);

        //Variaveis responsáveis para geração do código assembly
        boolean flagexp = true;
        String oper = null;
        String nome_id_atrib = ''s;

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
        	nome_id_atrib = token.getLexeme();
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

        case COMPLETE_FUNCTION_DECLARATION:
        	declarer.commitFunction();
        	break;

        case COMPLETE_ASSIGNMENT:
        	assigner.commit(evaluator.pop());
        	gera_cod("STO", nome_id_atrib);
        	break;

        case RESET_DECLARER:
        	declarer.reset();

        case PUSH_OPERATOR:
        	evaluator.pushOperator(Operator.fromLexeme(lexeme));
        	flagexp = true;
        	oper = token.getLexeme();

        	break;

        case PUSH_LITERAL:
        	evaluator.pushType(Type.deduceLiteral(code));
        	break;

        case PUSH_SYMBOL:
        	evaluator.pushType(accessor.access().getType());
        	if(!flagexp) {
        		gera_cod("LD", token.getLexeme());
        	} else {
        		if(oper == "+")
        			gera_cod("ADD", token.getLexeme());
        		if(oper == "-")
        			gera_cod("SUB", token.getLexeme());
        		flagexp = false;
        	}

        	break;
        case LITERAL_VALUE:
        	if(!flagexp) {
        		gera_cod("LDI", token.getLexeme());
        	} else {
        		if(oper == "+")
        			gera_cod("ADDI", token.getLexeme());
        		if(oper == "-")
        			gera_cod("SUBI", token.getLexeme());
        		flagexp = false;
        	}

        	break;
        case SET_ARRAY_SIZE:
        	declarer.setArray(Integer.parseInt(lexeme));
        	break;
        case ACCESS_POSITION:
        	accessor.testArrayAccess(evaluator.pop());
        	int position = token.getLexeme();
	        if(!flagexp) {
        		if(position == 0) {
	        		gera_cod("LDI", position);
	        		gera_cod("STO", "$indr");
	        		gera_cod("LDV", "vet");
	        	} else {
	        		gera_cod("STO", "temp1");
	        		gera_cod("LD", position);
	        		gera_cod("STO", "$indr");
	        		gera_cod("LDV", "vet");
	        		gera_cod("STO", "temp2");
	        		gera_cod("LD", "temp1");
	        		//ADD OU SUB em temp2
	        	}
	        } else {
	        	gera_cod("LDI", position);
	        	gera_cod("STO", "temp1");
	        	gera_cod("LD", nome_id_atrib);
	        	gera_cod("STO", temp2);
	        	gera_cod("LD", "temp1");
	        	gera_cod("STO", "$indr");
	        	gera_cod("LD", "temp2");
	        	gera_cod("STOV", "vet");
	        }
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

        	break;
        case CLOSE_SCOPE:

        	break;
        case OPEN_SCOPE_FUNCTION:

        	break;
        }
    }

	private void gera_cod(String string, String lexeme) {
		// TODO Auto-generated method stub

	}



}
