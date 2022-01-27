public class LoadBuffer {

	String[][] loadBuff;
	int[] IssueDate;
	boolean[] flagsForEmptySlots;

	public LoadBuffer() {
		IssueDate = new int[3];
		flagsForEmptySlots = new boolean[3];

		IssueDate[0] = Integer.MAX_VALUE;
		IssueDate[1] = Integer.MAX_VALUE;
		IssueDate[2] = Integer.MAX_VALUE;
		loadBuff = new String[4][3];
		loadBuff[0][0] = "Load #";
		loadBuff[0][1] = "Busy";
		loadBuff[0][2] = "Address";
		loadBuff[1][0] = "L1";
		loadBuff[2][0] = "L2";
		loadBuff[3][0] = "L3";
	}

}
