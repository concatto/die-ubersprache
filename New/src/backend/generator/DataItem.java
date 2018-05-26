package backend.generator;

public class DataItem {
	private String name;
	private String value;
	
	public DataItem(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
}
