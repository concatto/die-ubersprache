package grammar.actions;

public enum Action {
	DECLARE_TYPE,
	DECLARE_ID,
	PUSH_OPERATOR,
	PUSH_LITERAL,
	EVALUATE,
	COMPLETE_DECLARATION;
	
	public static Action parse(int code) {
		switch (code) {
		case 1:
			return DECLARE_TYPE;
		case 2:
			return DECLARE_ID;
		case 400:
			return PUSH_LITERAL;
		case 401:
		case 402:
			return EVALUATE;
		case 300:
			return PUSH_OPERATOR;
			
		}
		
		return null;
	}
}
