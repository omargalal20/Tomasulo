public class StoreBuffer {

	String[][] storeBuff;
	int [] IssueDate;
	boolean[] flagsForEmptySlots;

	public StoreBuffer() {
		IssueDate = new int [3];
		flagsForEmptySlots = new boolean[3];
		IssueDate[0] = Integer.MAX_VALUE;
		IssueDate[1] = Integer.MAX_VALUE;
		IssueDate[2] = Integer.MAX_VALUE;
		storeBuff = new String[4][5];
		storeBuff[0][0] = "Store #";
		storeBuff[0][1] = "Busy";
		storeBuff[0][2] = "Address";
		storeBuff[0][3] = "V";
		storeBuff[0][4] = "Q";

		storeBuff[1][0] = "S1";
		storeBuff[2][0] = "S2";
		storeBuff[3][0] = "S3";

	}

}
