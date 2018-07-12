package backend;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Temporary extends LanguageData {
	private static boolean[] available = new boolean[10];
	private int index;
	
	static {
		Arrays.fill(available, true);
	}
	
	public Temporary(Type type) {
		this(type, -1);
	}
	
	private Temporary(Type type, int index) {
		super(DataVariant.TEMPORARY, type);
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}

	public static Temporary reserve(Type type) {
		for (int i = 0; i < available.length; i++) {
			if (available[i]) {
				available[i] = false;
				return new Temporary(type, i);
			}
		}
		
		throw new IllegalStateException("No temporaries left!");
	}
	
	public static Temporary reserve() {
		return Temporary.reserve(Type.INTEGER);
	}
	
	public static void release(Temporary temp) {
		available[temp.getIndex()] = true;
	}
}
