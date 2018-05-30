package backend;

public abstract class LanguageData {
	protected DataVariant variant;
	protected Type type;
	
	public LanguageData(DataVariant variant) {
		this.variant = variant;
	}
	
	public LanguageData(DataVariant variant, Type type) {
		this(variant);
		this.type = type;
	}

	public Type getType() {
		return type;
	}
	
	public DataVariant getVariant() {
		return variant;
	}
}
