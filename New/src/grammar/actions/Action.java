package grammar.actions;

public enum Action {
	DECLARE_TYPE,
	STORE_ID,
	PUSH_OPERATOR,
	PUSH_INTEGER,
	PUSH_REAL,
	PUSH_STRING,
	PUSH_BOOLEAN,
	EVALUATE,
	COMPLETE_DECLARATION,
	COMPLETE_ASSIGNMENT;
	
	public static Action parse(int code) {
		switch (code) {
		case 1:
			return DECLARE_TYPE;
		case 2:
			return STORE_ID;
		case 4:
			return COMPLETE_ASSIGNMENT;
		case 601:
			return PUSH_INTEGER;
		case 602:
			return PUSH_REAL;
		case 605:
			return PUSH_STRING;
		case 606:
		case 607:
			return PUSH_BOOLEAN;
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
