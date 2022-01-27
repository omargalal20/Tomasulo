public class Instruction {

	String name;
	String j;
	String k;
	String Output;
	int issue;
	int startExec;
	int endExec;
	int writeBack;
	int latency;

	public Instruction(String name, int issue, int startExec, int endExec, int writeBack, int latency) {
		this.name = name;
		this.startExec = startExec;
		this.endExec = endExec;
		this.issue = issue;
		this.writeBack = writeBack;
		this.latency = latency;
	}

}
