package backend.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import backend.LanguageData;
import backend.Literal;
import backend.Symbol;
import backend.SymbolTable;
import backend.Temporary;
import backend.Type;
import backend.operators.Add;
import backend.operators.BinaryOperator;
import backend.operators.BitwiseAnd;
import backend.operators.BitwiseOr;
import backend.operators.DifferentThan;
import backend.operators.EqualThan;
import backend.operators.GreaterOrEqualThan;
import backend.operators.GreaterThan;
import backend.operators.LessOrEqualThan;
import backend.operators.LessThan;
import backend.operators.RelationalOperator;
import backend.operators.Subtract;

public class AssemblyProgram {
	public static final String RETURN_ADDRESS = "1071";
	private List<DataItem> dataSection = new ArrayList<>();
	private Map<Integer, List<String>> labels = new HashMap<>(); //Might have two or more labels in sequence
	private InstructionSection text = new InstructionSection();
	
	public AssemblyProgram() {
		text.jump("_MAIN");
	}
	
	public void store(Symbol symbol) {
		text.store(generateName(symbol));
	}
	
	public static String generateName(Symbol symbol) {
		if (symbol.isFunction()) {
			return "_" + symbol.getIdentifier().toUpperCase();
		} else {
			return String.format("%s_d%d__%d", symbol.getIdentifier(), symbol.getDepth(), symbol.getScope());
		}
	}
	
	public static String generateName(Temporary temporary) {
		return "100" + temporary.getIndex();
	}
	
	// Transforms a Symbol instance into a data item with the symbol's properties.
	private static DataItem parseSymbol(Symbol symbol) {
		String value = Stream.generate(() -> "0")
			.limit(symbol.getSize())
			.collect(Collectors.joining(","));
		
		return new DataItem(generateName(symbol), value);
	}
	
	public void parseSymbolTable(SymbolTable table) {
		dataSection = table.getSymbols().stream()
			.filter(symbol -> !symbol.isFunction())
			.map(AssemblyProgram::parseSymbol)
			.collect(Collectors.toList());
	}
	
	// Generates the data section of the assembly program.
	public String generateData() {
		StringBuilder builder = new StringBuilder(".data\n");
		for (DataItem item : dataSection) {
			builder.append(String.format("  %s : %s\n", item.getName(), item.getValue()));
		}
		
		return builder.toString();
	}

	
	// Generates the text section of the assembly program.
	public String generateText() {
		List<Instruction> instructions = text.getInstructions();
		
		if (instructions.size() > 0) {
			Instruction last = instructions.get(instructions.size() - 1);
			
			if (!last.getOperation().equals("HLT")) {
				text.halt();
			}
		}
		
		StringBuilder builder = new StringBuilder(".text\n");
		for (int i = 0; i < instructions.size(); i++) {
			if (labels.containsKey(i)) {
				// Adds the label(s) in the current line
				for (String label : labels.get(i)) {
					builder.append(label + ":\n");
				}
			}
			
			Instruction instruction = instructions.get(i);
			builder.append(String.format("  %s %s\n", instruction.getOperation(), instruction.getOperand()));
		}
		
		return builder.toString();
	}
	
	public String generateProgram() {
		return generateData() + generateText();
	}
	
	@SuppressWarnings("unchecked")
	public void load(LanguageData ld) {
		switch (ld.getVariant()) {
		case LITERAL:
			int value = ((Literal<Integer>) ld).getValue();
			text.loadImmediate(String.valueOf(value));
			
			break;
		case SYMBOL:
			Symbol symbol = (Symbol) ld;
			
			if (symbol.isFunction() && symbol.getType() != Type.VOID) {
				call(symbol);
				text.load(RETURN_ADDRESS);
			} else {
				text.load(generateName(symbol));
			}
			
			break;
		case TEMPORARY:
			Temporary temp = (Temporary) ld;
			text.load(generateName(temp));
			Temporary.release(temp);
			
			break;
		}
	}
	public Temporary evaluateVector(LanguageData index, Symbol symbol, boolean afterErhalt, LanguageData literal) {
		//load(index);
		text.storeIndex();
		if(afterErhalt) {
			text.loadVector(generateName(symbol));
		}else {
			//int value = ((Literal<Integer>) literal).getValue();
			//text.add(String.valueOf(value));
			//text.storeVector(generateName(symbol));
		}
		
		Temporary temp = Temporary.reserve(symbol.getType());
		//text.store(generateName(temp));
		return temp;
	}
	
	private void generateImmediateOperation(int value, BinaryOperator op) {
		if (op instanceof Add) {
			text.addImmediate(value);
		} else if (op instanceof Subtract) {
			text.subtractImmediate(value);
		} else if (op instanceof BitwiseAnd) {
			text.andImmediate(value);
		} else if (op instanceof BitwiseOr) {
			text.orImmediate(value);
		} else if (op instanceof RelationalOperator) {
			text.subtractImmediate(value);
		}
	}
	
	private void generateOperation(String name, BinaryOperator op) {
		if (op instanceof Add) {
			text.add(name);
		} else if (op instanceof Subtract) {
			text.subtract(name);
		} else if (op instanceof BitwiseAnd) {
			text.and(name);
		} else if (op instanceof BitwiseOr) {
			text.or(name);
		} else if (op instanceof RelationalOperator) {
			text.subtract(name);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Temporary evaluateBinary(LanguageData lhs, LanguageData rhs, BinaryOperator op, Type resultType) {
		// We're missing arrays. TODO
		// First, retrieve the left hand side.
		load(lhs);
		
		// Then, execute the operation with the right hand side.
		// It's really similar to the left hand side section, the only thing that is different
		// is the function called on the InstructionSection object
		switch (rhs.getVariant()) {
		case LITERAL:
			// If it's a literal, simply invoke an immediate instruction
			int value = ((Literal<Integer>) rhs).getValue();
			generateImmediateOperation(value, op);
			
			break;
		case SYMBOL:
			// If it's a variable, we need its assembly name.
			Symbol symbol = (Symbol) rhs;
			
			// If it's a function and has a return value, it needs special treatment.
			if (symbol.isFunction() && symbol.getType() != Type.VOID) {
				Temporary temp = Temporary.reserve();
				text.store(generateName(temp));
				call(symbol);
				load(temp);
				
				generateOperation(RETURN_ADDRESS, op);
			} else {
				generateOperation(generateName(symbol), op);
			}
			
			break;
		case TEMPORARY:
			// If it's a temporary, first we must retrieve it by name then release the index it holds.
			Temporary temp = (Temporary) rhs;
			Temporary.release(temp);
			generateOperation(generateName(temp), op);
			
			break;
		}
		
		// When everything is said and done, store the resultant temporary.
		Temporary result = Temporary.reserve(resultType);
		text.store(generateName(result));
		return result;
	}
	
	public void read(Symbol symbol) {
		text.read();
		text.store(generateName(symbol));
	}
	
	public void write(LanguageData data) {
		load(data);
		text.write();
	}
	
	public void branch(RelationalOperator op, String label) {
		if (op instanceof GreaterThan) {
			text.branchLessThanOrEqual(label);
		} else if (op instanceof LessThan) {
			text.branchGreaterThanOrEqual(label);
		} else if (op instanceof DifferentThan) {
			text.branchEqual(label);
		} else if (op instanceof EqualThan) {
			text.branchNotEqual(label);
		} else if (op instanceof GreaterOrEqualThan) {
			text.branchLessThan(label);
		} else if (op instanceof LessOrEqualThan) {
			text.branchGreaterThan(label);
		}
		
	}
	
	public void branchDoWhile(RelationalOperator op, String label) {
		if (op instanceof GreaterThan) {
			text.branchGreaterThan(label);
		} else if (op instanceof LessThan) {
			text.branchLessThan(label);
		} else if (op instanceof DifferentThan) {
			text.branchNotEqual(label);
		} else if (op instanceof EqualThan) {
			text.branchEqual(label);
		} else if (op instanceof GreaterOrEqualThan) {
			text.branchGreaterThanOrEqual(label);
		} else if (op instanceof LessOrEqualThan) {
			text.branchLessThanOrEqual(label);
		}

	}		

	public void insertLabel(String label) {
		List<String> list;
		
		int index = text.getInstructions().size();
		
		if (labels.containsKey(index)) {
			list = labels.get(index);
		} else {
			list = new ArrayList<>();
		}
		
		list.add(label);
		labels.put(index, list);
	}

	public void jump(String label) {
		text.jump(label);
	}
	
	public void startRecording() {
		text.startRecording();
	}
	
	public void endRecording() {
		text.endRecording();
	}
	
	public void insertRecording() {
		text.insertRecording();
	}

	public void returnToCaller() {
		text.returnToCaller();
	}
	
	public void storeReturnValue(LanguageData value) {
		load(value);
		text.store(RETURN_ADDRESS);
	}
	
	public void call(Symbol function) {		
		text.call(generateName(function));
	}
}
