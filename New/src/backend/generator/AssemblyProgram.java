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

public class AssemblyProgram {
	private List<DataItem> dataSection = new ArrayList<>();
	private InstructionSection text = new InstructionSection();
	
	public void store(Symbol symbol) {
		text.store(generateName(symbol));
	}
	
	private static String generateName(Symbol symbol) {
		return String.format("%s_d%d__%d", symbol.getIdentifier(), symbol.getDepth(), symbol.getScope());
	}
	
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
	
	public String generateData() {
		StringBuilder builder = new StringBuilder(".data\n");
		for (DataItem item : dataSection) {
			builder.append(String.format("  %s : %s\n", item.getName(), item.getValue()));
		}
		
		return builder.toString();
	}
	
	public String generateText() {
		List<Instruction> instructions = text.getInstructions();
		Instruction last = instructions.get(instructions.size() - 1);
		
		if (!last.getOperation().equals("HLT")) {
			text.halt();
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

	public void add(LanguageData a, LanguageData b) {
		// TODO make this better. What about temporaries?
		if (a.getVariant() == DataVariant.SYMBOL) {
			text.add(generateName((Symbol) a));
		} else if (a.getVariant() == DataVariant.LITERAL) {
			Literal<Integer> l = (Literal<Integer>) a;
			text.addImediate(l.getValue());
		}
		
		if (b.getVariant() == DataVariant.SYMBOL) {
			text.add(generateName((Symbol) b));
		} else if (b.getVariant() == DataVariant.LITERAL) {
			Literal<Integer> l = (Literal<Integer>) b;
			text.addImediate(l.getValue());
		}
	}
}
