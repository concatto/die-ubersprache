package backend;

public class Symbol extends LanguageData {
	private String identifier;
	private boolean function;
	private boolean initialized;
	private boolean parameter;
	private boolean used;
	private int size; // 1 for regular variables, n for arrays
	private int scope;
	private int depth;
	private int parameterPosition;
	private int count;

	public int getParameterPosition() {
		return parameterPosition;
	}

	public void setParameterPosition(int parameterPosition) {
		this.parameterPosition = parameterPosition;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public Symbol() {
		super(DataVariant.SYMBOL);
		
		this.size = 1;
		this.function = false;
		this.initialized = false;
		this.used = false;
		this.parameter = false;
	}

	public Symbol(String identifier, Type type, boolean function, boolean initialized, boolean used, boolean parameter, int size, int scope, int depth, int parameterPosition) {
		this();
		
		this.identifier = identifier;
		this.type = type;
		this.function = function;
		this.initialized = initialized;
		this.used = used;
		this.parameter = parameter;
		this.size = size;
		this.scope = scope;
		this.depth = depth;
		this.parameterPosition = parameterPosition;
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

	public boolean isParameter() {
		return parameter;
	}

	public boolean isUsed() {
		return used;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setFunction(boolean function) {
		this.function = function;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	public void setParameter(boolean parameter) {
		this.parameter = parameter;
	}

	public void setScope(int scope) {
		this.scope = scope;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setUsed(boolean used) {
		this.used = used;
		System.out.println("This one is now used \\/");
		System.out.println(this.toString());
		
	}

	public int getDepth() {
		return depth;
	}

	@Override
	public String toString() {
		return String.format("%s %s. Func: %b; Arr: %b; Size: %d; Init: %b; Used: %b; Param: %b; Scope: %d; Depth: %d; P. pos: %d",
				type, identifier, function, isArray(), size, initialized, used, parameter, scope, depth, parameterPosition
		);
	}

	public void setCount(int count) {
		this.count = count;
		// TODO Auto-generated method stub
		
	}
	
	public int getCount() {
		return count;
	}
}
