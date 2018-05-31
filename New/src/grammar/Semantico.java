package grammar;

import backend.Evaluator;
import backend.SymbolTable;
import backend.Type;
import backend.generator.InstructionSection;
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
	private InstructionSection instructionSection = new InstructionSection();

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
        boolean flagexp = false;
        String oper = '';
        String name_id_atrib = '';
				String value = '0';

        switch (action) {
        case DECLARE_TYPE:
        	declarer.setCurrentType(Type.fromLexeme(lexeme));
        	break;

        case STORE_ID_FUNCTION:
        	declarer.setFunctionIdentifier(lexeme);
        	break;

        case STORE_ID_DECLARATION:
        	declarer.setCurrentIdentifier(lexeme);
					name_id_atrib = token.getLexeme();
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

        case COMPLETE_FUNCTION_DECLARATION:
        	declarer.commitFunction();
        	break;

        case COMPLETE_ASSIGNMENT:
        	assigner.commit(evaluator.pop());
					value = token.getLexeme();
					instructionSection.addInstruction(name_id_atrib, value); //const = 1 ou qualquerOutroNome = QualquerValor
					value = '0';
        	break;

        case RESET_DECLARER:
        	declarer.reset();

        case PUSH_OPERATOR:
        	evaluator.pushOperator(Operator.fromLexeme(lexeme));
        	flagexp = true; //flag de <exp>
        	oper = token.getLexeme(); //oper = +,-,*,%,/

        	break;

        case PUSH_LITERAL:
        	evaluator.pushType(Type.deduceLiteral(code));
					if(!flagexp) {
						instructionSection.loadImmediate(token.getLexeme()); //LDI
					} else {
						if(oper == "+")
							instructionSection.addImediate(token.getLexeme()); //ADDI
						if(oper == "-")
							instructionSection.subtractImediate(token.getLexeme()); //SUBI
						flagexp = false;
					}
        	break;

        case PUSH_SYMBOL:
        	evaluator.pushType(accessor.access().getType());
        	if(!flagexp) {
						instructionSection.load(token.getLexeme()); //LD
        	} else {
        		if(oper == "+")
							instructionSection.add(token.getLexeme()); //ADD
        		if(oper == "-")
							InstructionSection.subtract(token.getLexeme()); //SUB
        		flagexp = false;
        	}

        	break;
        case SET_ARRAY_SIZE:
        	declarer.setArray(Integer.parseInt(lexeme));
        	break;
        case ACCESS_POSITION:
        	accessor.testArrayAccess(evaluator.pop());
        	String position = token.getLexeme();
	        if(flagexp) { //FLAG PARA SABER SE O VETOR TA DO LADO ESQUERDO OU DIREITO (ATRIBUINDO OU SENDO ATRIBUIDO)
        		if(position == '0') {
	        		instructionSection.loadImmediate(position);
	        		instructionSection.storeIndex();
	        		instructionSection.loadVector("vet");
							instructionSection.store(name_id_atrib);
	        	} else {
							instructionSection.loadImmediate(position);
							instructionSection.storeIndex();
							instructionSection.loadVector("vet");
							instructionSection.store(name_id_atrib);
							//não sei se a parte de cima deve estar dentro desse ELSE
	        		instructionSection.store("temp1");
	        		instructionSection.load(position);
	        		instructionSection.storeIndex();
	        		instructionSection.loadVector("vet");
	        		instructionSection.store("temp2");
	        		instructionSection.load("temp1");
	        		//ADD OU SUB em temp2 (NÃO SEI O QUE FAZER AQUI E NEM SE É DE FATO AQUI QUE EU TRATO ISSO TUDO)
	        	}
	        } else {
	        	instructionSection.loadImmediate(position);
	        	instruction.store("temp1");
	        	instructionSection.load(name_id_atrib);
	        	instructionSection.store("temp2");
	        	instructionSection.load("temp1");
	        	instructionSection.storeIndex();
	        	instructionSection.load("temp2");
	        	instructionSection.storeVector("vet");
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
