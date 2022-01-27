public class AddReservationStation {

	String[][] RStation;
	int[] IssueDate;
	boolean[] flagsForEmptySlots;

	public AddReservationStation() {
		IssueDate = new int[3];
		flagsForEmptySlots = new boolean[3];
		IssueDate[0] = Integer.MAX_VALUE;
		IssueDate[1] = Integer.MAX_VALUE;
		IssueDate[2] = Integer.MAX_VALUE;

		RStation = new String[4][7];
		RStation[0][0] = "Inst #";
		RStation[0][1] = "Busy";
		RStation[0][2] = "OP";
		RStation[0][3] = "Vj";
		RStation[0][4] = "Vk";
		RStation[0][5] = "Qj";
		RStation[0][6] = "Qk";

		RStation[1][0] = "A1";
		RStation[2][0] = "A2";
		RStation[3][0] = "A3";

	}
}
