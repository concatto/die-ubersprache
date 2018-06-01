package backend.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import backend.DataVariant;
import backend.LanguageData;
import backend.Literal;
import backend.Symbol;
import backend.SymbolTable;
import backend.Temporary;
import backend.Type;
import backend.operators.BinaryOperator;

public class AssemblyProgram {
	private List<DataItem> dataSection = new ArrayList<>();
	private InstructionSection text = new InstructionSection();
	
	public void store(Symbol symbol) {
		text.store(generateName(symbol));
	}
	
	private static String generateName(Symbol symbol) {
		return String.format("%s_d%d__%d", symbol.getIdentifier(), symbol.getDepth(), symbol.getScope());
	}
	
	private static String generateName(Temporary temporary) {
		return "temp" + temporary.getIndex();
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
		for (Instruction instruction : text.getInstructions()) {
			builder.append(String.format("  %s %s\n", instruction.getOperation(), instruction.getOperand()));
		}
		
		return builder.toString();
	}
	
	public String generateProgram() {
		return generateData() + generateText();
	}
	
	@SuppressWarnings("unchecked")
	public Temporary evaluateBinary(LanguageData lhs, LanguageData rhs, BinaryOperator op, Type resultType) {
		// We're missing arrays. TODO		
		// First, retrieve the left hand side.
		switch (lhs.getVariant()) {
		case LITERAL:
			// If it's a literal, just load it.
			int value = ((Literal<Integer>) lhs).getValue();
			text.loadImmediate(String.valueOf(value));
			
			break;
		case SYMBOL:
			// If it's a variable, we need its assembly name.
			Symbol symbol = (Symbol) lhs;
			text.load(generateName(symbol));
			
			break;
		case TEMPORARY:
			// If it's a temporary, first we must retrieve it by name then release the index it holds.
			Temporary temp = (Temporary) lhs;
			text.load(generateName(temp));
			
			Temporary.release(temp);
			
			break;
		}
		
		// Then, execute the operation with the right hand side.
		// We'll assume it's an addition for now.
		// It's really similar to the left hand side section, the only thing that is different
		// is the function called on the InstructionSection object
		switch (rhs.getVariant()) {
		case LITERAL:
			// If it's a literal, simply invoke an immediate instruction
			int value = ((Literal<Integer>) rhs).getValue();
			text.addImmediate(value);
			
			break;
		case SYMBOL:
			// If it's a variable, we need its assembly name.
			Symbol symbol = (Symbol) rhs;
			text.add(generateName(symbol));
			
			break;
		case TEMPORARY:
			// If it's a temporary, first we must retrieve it by name then release the index it holds.
			Temporary temp = (Temporary) rhs;
			text.add(generateName(temp));
			
			Temporary.release(temp);
			
			break;
		}
		
		// When everything is said and done, store the resultant temporary.
		Temporary result = Temporary.reserve(resultType);
		text.store(generateName(result));
		return result;
	}
}
