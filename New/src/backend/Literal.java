package backend;

public class Literal<T> extends LanguageData {
	private T value;
	
	public Literal(Type type, T value) {
		super(DataVariant.LITERAL, type);
		this.value = value;
	}
	
	public T getValue() {
		return value;
	}
}
