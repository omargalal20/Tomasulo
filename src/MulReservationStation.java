public class MulReservationStation {

	String[][] RStation;
	int [] IssueDate;
	boolean[] flagsForEmptySlots;

	public MulReservationStation() {
		IssueDate = new int [2];
		flagsForEmptySlots = new boolean[2];
		IssueDate[0] = Integer.MAX_VALUE;
		IssueDate[1] = Integer.MAX_VALUE;
		RStation = new String[3][7];
		RStation[0][0] = "Inst #";
		RStation[0][1] = "Busy";
		RStation[0][2] = "OP";
		RStation[0][3] = "Vi";
		RStation[0][4] = "Vk";
		RStation[0][5] = "Qi";
		RStation[0][6] = "Qk";

		RStation[1][0] = "M1";
		RStation[2][0] = "M2";

	}
}
