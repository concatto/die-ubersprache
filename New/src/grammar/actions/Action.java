package grammar.actions;

public enum Action {
	DECLARE_TYPE, DECLARE_ID; //et al
	
	public static Action parse(int code) {
		switch (code) {
		case 1:
			return DECLARE_TYPE;
		case 2:
			return DECLARE_ID;
		}
		
		return null;
	}
}
