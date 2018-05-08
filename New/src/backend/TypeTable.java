package backend;

public class TypeTable {
	private Type[][] matrix;
	
	public TypeTable(int[][] configuration) {
		Type[] values = Type.values();
		
		int rows = configuration.length;
		int cols = configuration[0].length;
		
		matrix = new Type[rows][cols];
		                  
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				int index = configuration[i][j];
				
				matrix[i][j] = index == 0 ? null : values[index - 1]; 
			}
		}
	}
	
	public Type get(int row, int column) {
		return matrix[row][column];
	}
}
