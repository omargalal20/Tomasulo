import java.util.Vector;

public class InstructionQueue {

	Vector<Vector<String>> queue;
	
	public InstructionQueue() {
		queue = new Vector<Vector<String>>();
		Vector<String> firstRow = new Vector<String>();
		firstRow.add("Instruction Name");
		firstRow.add("Output");
		firstRow.add("J");
		firstRow.add("K");
		firstRow.add("Issue");
		firstRow.add("Start Exec");
		firstRow.add("End Exec");
		firstRow.add("WriteBack");
		queue.add(firstRow);
	}
	
}
