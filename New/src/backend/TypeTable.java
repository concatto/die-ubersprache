package backend;

public class TypeTable {
	private Type[][] matrix;
	
	public TypeTable(int... configuration) {
		Type[] vals = Type.values();
		
		matrix = new Type[vals.length][vals.length];
		
		for (int i = 0; i < vals.length; i++) {
			for (int j = 0; j < vals.length; j++) {
				int index = configuration[i * vals.length + j];
				
				matrix[i][j] = index == 0 ? null : vals[index - 1]; 
			}
		}
	}
	
	public Type apply(Type a, Type b) {
		int i = a.ordinal();
		int j = b.ordinal();
		
		return matrix[i][j];
	}
}
