package backend;

public enum Type {
	INTEGER, REAL, BOOLEAN, CHARACTER, STRING;
	
	public static Type fromLexeme(String lexeme) {
		
		switch (lexeme) {
		case "ganze":
			return INTEGER;
		case "reelle":
			return REAL;
		case "zeichenkette":
			return STRING;
		case "zeichen":
			return CHARACTER;
		case "boolesche":
			return BOOLEAN;
		}
		
		return null;
	}
	
	
}
