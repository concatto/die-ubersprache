package backend;

public class Symbol {
	private String identifier;
	private Type type;
	private boolean function;
	private boolean initialized;
	private int size; // 1 for regular variables, n for arrays
	private int scope;
	
	public Symbol(String identifier, Type type, boolean function, boolean initialized, int size, int scope) {
		this.identifier = identifier;
		this.type = type;
		this.function = function; 
		this.initialized = initialized;
		this.size = size;
		this.scope = scope;
	}

	public Type getType() {
		return type;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public boolean isFunction() {
		return function;
	}
	
	public boolean isInitialized() {
		return initialized;
	}	
	
	public int getScope() {
		return scope;
	}
	
	public int getSize() {
		return size;
	}
	
	public boolean isArray() {
		return size > 1;
	}
	
	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	public void setScope(int scope) {
		this.scope = scope;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	@Override
	public String toString() {
		return String.format("%s %s. Func: %b; Arr: %b; Size: %d; Init: %b; Scope: %d",
				type, identifier, function, isArray(), size, initialized, scope
		);
	}
}
