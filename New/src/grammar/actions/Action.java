package grammar.actions;

public enum Action {
	DECLARE_TYPE,
	STORE_ID_FUNCTION,
	STORE_ID_DECLARATION,
	STORE_ID_GENERAL,
	PUSH_OPERATOR,
	PUSH_LITERAL,
	SET_ARRAY_SIZE,
	EVALUATE,
	COMPLETE_DECLARATION,
	COMPLETE_ASSIGNMENT,
	RESET_DECLARER,
	DESCEND_SCOPE,
	ASCEND_SCOPE,
	PUSH_SCOPE,
	POP_SCOPE,
	BEGIN_PARAMETERS,
	ACCESS_POSITION,
	STORE_ID_ASSIGNMENT,
	ASSIGNMENT_FROM_DECLARATION,
	ASSIGNMENT_FROM_ACCESS,
	PUSH_SYMBOL;
	
	public static Action parse(int code) {
		switch (code) {
		case 1:
			return DECLARE_TYPE;
		case 10:
			return ASSIGNMENT_FROM_DECLARATION;
		case 11:
			return ASSIGNMENT_FROM_ACCESS;
		case 15:
			return DESCEND_SCOPE;
		case 16:
			return ASCEND_SCOPE;
		case 25:
			return ACCESS_POSITION;
		case 30:
			return STORE_ID_GENERAL;
		case 31:
			return STORE_ID_DECLARATION;
		case 32:
			return STORE_ID_FUNCTION;
		case 33:
			return STORE_ID_ASSIGNMENT;
		case 39:
			return BEGIN_PARAMETERS;
		case 4:
			return COMPLETE_ASSIGNMENT;
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
			return EVALUATE;
		case 300:
			return PUSH_OPERATOR;
		case 555:
			return PUSH_SCOPE;
		case 556:
			return POP_SCOPE;
		case 2000:
			return COMPLETE_DECLARATION;
		case 2001:
			return RESET_DECLARER;
		}
		
		return null;
	}
}
