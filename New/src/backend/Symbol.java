package backend;

public class Symbol {
	private String identifier;
	private Type type;
	private boolean function;
	private boolean initialized;
	private int scope;
	
	public Symbol(String identifier, Type type, boolean function, boolean initialized, int scope) {
		this.identifier = identifier;
		this.type = type;
		this.function = function; 
		this.initialized = initialized;
		this.scope = scope;
	}

	public Type getType() {
		return type;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public boolean getFunction() {
		return function;
	}
	
	public Boolean getInitialized() {
		return initialized;
	}	
	
	public int getScope() {
		return scope;
	}
}
