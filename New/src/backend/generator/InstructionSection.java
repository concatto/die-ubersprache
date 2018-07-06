package backend.generator;

import java.util.ArrayList;
import java.util.List;

public class InstructionSection {
	private List<Instruction> instructions = new ArrayList<>();
	private List<Instruction> recordedInstructions = new ArrayList<>();
	private boolean recording = false;

	public void add(String value) {
		addInstruction("ADD", value);
	}

	public void addImmediate(int value) {
		addInstruction("ADDI", Integer.toString(value));
	}

	public void subtract(String value) {
		addInstruction("SUB", value);
	}

	public void subtractImmediate(int value) {
		addInstruction("SUBI", Integer.toString(value));
	}
	
	public void and(String value) {
		addInstruction("AND", value);
	}
	
	public void andImmediate(int value) {
		addInstruction("ANDI", Integer.toString(value));
	}
	
	public void or(String value) {
		addInstruction("OR", value);
	}
	
	public void orImmediate(int value) {
		addInstruction("ORI", Integer.toString(value));
	}

	public void branchGreaterThan(String label) {
		addInstruction("BGT", label);
	}
	
	public void branchLessThan(String label) {
		addInstruction("BLT", label);
	}
	
	public void branchGreaterThanOrEqual(String label) {
		addInstruction("BGE", label);
	}
	
	public void branchLessThanOrEqual(String label) {
		addInstruction("BLE", label);
	}
	
	public void branchEqual(String label) {
		addInstruction("BEQ", label);
	}
	
	public void branchNotEqual(String label) {
		addInstruction("BNE", label);
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
	
	public void jump(String label) {
		addInstruction("JMP", label);
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
		Instruction instruction = new Instruction(string, value);
		
		if (recording) {
			recordedInstructions.add(instruction);
		} else {			
			instructions.add(instruction);
		}
	}

	public List<Instruction> getInstructions() {
		return instructions;
	}

	public void startRecording() {
		recording = true;
	}

	public void endRecording() {
		recording = false;		
	}

	public void insertRecording() {
		instructions.addAll(recordedInstructions);
	}
}
