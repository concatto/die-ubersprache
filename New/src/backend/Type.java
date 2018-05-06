package backend;

public enum Type {
	INTEGER,
	REAL,
	BOOLEAN,
	CHARACTER,
	STRING,
	VOID;
	
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
		case "leer":
			return VOID;
		}
		
		return null;
	}

	public static Type deduceLiteral(int code) {
		switch (code) {
		case 601: // Integer literal e.g. 10
			return INTEGER;
		case 602: // Floating point literal e.g. 5.3
			return REAL;
		case 605: // String literal e.g. "Hello world"
			return STRING;
		case 606: // true
		case 607: // false
			return BOOLEAN;
		}
		
		return null;
	}
	
	
}
