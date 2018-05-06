package grammar.actions;

public enum Action {
	DECLARE_TYPE,
	STORE_ID_FUNCTION,
	STORE_ID_DECLARATION, // Also declaration + assignment
	STORE_ID_GENERAL, // Includes assignment (separate from decl.)
	PUSH_OPERATOR,
	PUSH_LITERAL,
	SET_ARRAY_SIZE,
	EVALUATE,
	COMPLETE_DECLARATION,
	COMPLETE_ASSIGNMENT;
	
	public static Action parse(int code) {
		switch (code) {
		case 1:
			return DECLARE_TYPE;
		case 30:
			return STORE_ID_GENERAL;
		case 31:
			return STORE_ID_DECLARATION;
		case 32:
			return STORE_ID_FUNCTION;
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
		case 401:
		case 402:
		case 403:
		case 404:
		case 405:
		case 406:
			return EVALUATE;
		case 300:
			return PUSH_OPERATOR;
		case 2000:
			return COMPLETE_DECLARATION;
		}
		
		return null;
	}
}
