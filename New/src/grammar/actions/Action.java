package grammar.actions;

public enum Action {
	DECLARE_TYPE,
	STORE_ID_FUNCTION,
	STORE_ID_DECLARATION,
	STORE_ID_GENERAL,
	PUSH_OPERATOR,
	PUSH_LITERAL,
	SET_ARRAY_SIZE,
	EVALUATE_BINARY,
	EVALUATE_UNARY,
	COMPLETE_DECLARATION,
	COMPLETE_ASSIGNMENT,
	RESET_DECLARER,
	BEGIN_PARAMETERS,
	ACCESS_POSITION,
	ASSIGNMENT_FROM_DECLARATION,
	ASSIGNMENT_FROM_ACCESS,
	PUSH_SYMBOL,
	COMPLETE_FUNCTION_DECLARATION,
	OPEN_SCOPE,//a partir daqui foi adicionado
	CLOSE_SCOPE,
	OPEN_SCOPE_FUNCTION,
	CLOSE_SCOPE_FUNCTION,
	OPEN_SCOPE_FOR,
	CLOSE_SCOPE_FOR,
	ARITHMETICS;

	public static Action parse(int code) {
		switch (code) {
		case 1:
			return DECLARE_TYPE;
		case 4:
			return COMPLETE_ASSIGNMENT;
		case 10:
			return ASSIGNMENT_FROM_DECLARATION;
		case 11:
			return ASSIGNMENT_FROM_ACCESS;
		case 25:
			return ACCESS_POSITION;
		case 30:
			return STORE_ID_GENERAL;
		case 31:
			return STORE_ID_DECLARATION;
		case 32:
			return STORE_ID_FUNCTION;
		case 39:
			return BEGIN_PARAMETERS;
		case 50:
			return SET_ARRAY_SIZE;
		case 601:
		case 602:
		case 605:
		case 606:
		case 607:
			return PUSH_LITERAL;
		case 610:
			return PUSH_SYMBOL;
		case 401:
		case 402:
		case 403:
		case 404:
		case 405:
		case 406:
		case 407:
		case 408:
		case 409:
			return EVALUATE_BINARY;
		case 410:
			return EVALUATE_UNARY;
		case 300:
		case 302:
			return PUSH_OPERATOR;
		case 1999:
			return COMPLETE_FUNCTION_DECLARATION;
		case 2000:
			return COMPLETE_DECLARATION;
		case 2001:
			return RESET_DECLARER;
		case 1200://if
		case 1300://else
		case 900://while
		case 3000://inicio
			return OPEN_SCOPE;
		case 3001:
			return CLOSE_SCOPE;
		case 40://funcao
			return OPEN_SCOPE_FUNCTION;
		case 700:
			return OPEN_SCOPE_FOR;
		}

		return null;
	}
}
