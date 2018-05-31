package backend.generator;

import java.util.ArrayList;
import java.util.List;

public class InstructionSection {
	private List<Instruction> instructions = new ArrayList<>();

	public void add(String value) {
		addInstruction("ADD", value);
	}

	public void addImediate(int value) {
		addInstruction("ADDI", Integer.toString(value));
	}

	public void subtract(String value) {
		addInstruction("SUB", value);
	}

	public void subtractImediate(String value) {
		addInstruction("SUBI", value);
	}

	public void load(String value) {
		addInstruction("LD", value);
	}

	public void loadImmediate(String value) {
		addInstruction("LDI", value);
	}

	public void loadVector(String value) {
		addInstruction("LDV", value);
	}

	public void store(String value) {
		addInstruction("STO", value);
	}

	public void storeVector(String value) {
		addInstruction("STOV", value);
	}

	public void storeIndex() {
		store("$indr");
	}

	public void write() {
		store("$out_port");
	}

	public void read() {
		load("$in_port");
	}

	public void halt() {
		addInstruction("HLT", "0");
	}

	private void addInstruction(String string, String value) {
		instructions.add(new Instruction(string, value));
	}

	public List<Instruction> getInstructions() {
		return instructions;
	}
}
