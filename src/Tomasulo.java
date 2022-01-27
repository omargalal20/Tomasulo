import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

public class Tomasulo {

	static AddReservationStation ARS;
	static MulReservationStation MRS;
	static InstructionQueue IQ;
	static Memory mem;
	static LoadBuffer LB;
	static StoreBuffer SB;
	static double commonDataBus;
	static int clock = 0;
	static FPR[] registerFile = new FPR[32];
	static int instructionIndexTobeIssued = 1;
//	static int ARScounter = 0;
//	static int MRScounter = 0;
//	static int LBcounter = 0;
//	static int SBcounter = 0;
	static int addlat = 0;
	static int sublat = addlat;
	static int mullat = 0;
	static int divlat = mullat;
	static int storelatency = 0;
	static int loadlatency = 0;
	static int instructionIndexTobeExec = 1;
	static boolean[] flags;
	static boolean isDependent = false;
	static int wroteBack = 0;
	static boolean writeBoolean = false;
	static int endLoad = 0;
	static int endStore = 0;
	static int endAdd = 0;
	static int endSub = 0;
	static int endMul = 0;
	static int endDiv = 0;

	public Tomasulo() {
		ARS = new AddReservationStation();
		MRS = new MulReservationStation();
		IQ = new InstructionQueue();
		mem = new Memory();
		mem.dataMemory[100] = 10;
		mem.dataMemory[150] = 15;
		mem.dataMemory[600] = 200;
		mem.dataMemory[700] = 250;
		mem.dataMemory[300] = 300;
		mem.dataMemory[50] = 50;

		LB = new LoadBuffer();
		SB = new StoreBuffer();
		for (int i = 0; i < 32; i++) {
			registerFile[i] = new FPR("F" + (i));
			registerFile[i].value = i;
		}
	}

	public static void PrintMemory() {
		System.out.println("Memory : ");
		for (int i = 0; i < mem.dataMemory.length; i++) {
			System.out.print(mem.dataMemory[i] + " ");
		}
		System.out.println();
		System.out
				.println("--------------------------------------------------------------");
	}

	public static void IssueInstruction() {
		Vector<String> Instruction = IQ.queue.get(instructionIndexTobeIssued);
		String j = Instruction.get(2);
		String k = Instruction.get(3);
		for (int i = 1 + instructionIndexTobeIssued - 1; i < instructionIndexTobeIssued; i++) {
			if (IQ.queue.get(i).get(1).equals(j)
					|| IQ.queue.get(i).get(1).equals(k)) {
				isDependent = true;
			}
		}
		switch (Instruction.get(0)) {
		case "ADD.D":
				for(int i = 0; i < ARS.flagsForEmptySlots.length; i++){
					if(ARS.flagsForEmptySlots[i] == false){
						ARS.RStation[i + 1][1] = "1";
						ARS.RStation[i + 1][2] = "ADD.D";
						if (registerFile[Integer.parseInt(Instruction.get(2).substring(
								1, Instruction.get(2).length()))].Qi == "") {
							ARS.RStation[i + 1][3] = registerFile[Integer
									.parseInt(Instruction.get(2).substring(1,
											Instruction.get(2).length()))].value
									+ "";
						} else {
							ARS.RStation[i + 1][5] = registerFile[Integer
									.parseInt(Instruction.get(2).substring(1,
											Instruction.get(2).length()))].Qi
									+ "";
						}
						if (registerFile[Integer.parseInt(Instruction.get(3).substring(
								1, Instruction.get(3).length()))].Qi == "") {
							ARS.RStation[i + 1][4] = registerFile[Integer
									.parseInt(Instruction.get(3).substring(1,
											Instruction.get(3).length()))].value
									+ "";
						} else {
							ARS.RStation[i + 1][6] = registerFile[Integer
									.parseInt(Instruction.get(3).substring(1,
											Instruction.get(3).length()))].Qi
									+ "";
						}
						int index = Integer.parseInt(Instruction.get(1).substring(1,
								Instruction.get(1).length()));
						registerFile[index].Qi = "A" + (i + 1);
						IQ.queue.get(instructionIndexTobeIssued).set(4, clock + "");
						ARS.IssueDate[i] = clock;
						ARS.flagsForEmptySlots[i] = true;
						instructionIndexTobeIssued++;
						break;
					}
				}
			break;
		case "SUB.D":
			for(int i = 0; i < ARS.flagsForEmptySlots.length; i++){
				if(ARS.flagsForEmptySlots[i] == false) {
				ARS.RStation[i + 1][1] = "1";
				ARS.RStation[i + 1][2] = "SUB.D";
				if (registerFile[Integer.parseInt(Instruction.get(2).substring(
						1, Instruction.get(2).length()))].Qi == "") {
					ARS.RStation[i + 1][3] = registerFile[Integer
							.parseInt(Instruction.get(2).substring(1,
									Instruction.get(2).length()))].value
							+ "";
				} else {
					ARS.RStation[i + 1][5] = registerFile[Integer
							.parseInt(Instruction.get(2).substring(1,
									Instruction.get(2).length()))].Qi
							+ "";
				}
				if (registerFile[Integer.parseInt(Instruction.get(3).substring(
						1, Instruction.get(3).length()))].Qi == "") {
					ARS.RStation[i + 1][4] = registerFile[Integer
							.parseInt(Instruction.get(3).substring(1,
									Instruction.get(3).length()))].value
							+ "";
				} else {
					ARS.RStation[i + 1][6] = registerFile[Integer
							.parseInt(Instruction.get(3).substring(1,
									Instruction.get(3).length()))].Qi
							+ "";
				}

				int index = Integer.parseInt(Instruction.get(1).substring(1,
						Instruction.get(1).length()));
				registerFile[index].Qi = "A" + (i + 1);
				IQ.queue.get(instructionIndexTobeIssued).set(4, clock + "");
				ARS.IssueDate[i] = clock;
				ARS.flagsForEmptySlots[i] = true;
				instructionIndexTobeIssued++;
			}
			}
			break;
		case "MUL.D":
			for(int i = 0; i < MRS.flagsForEmptySlots.length; i++){
				if(MRS.flagsForEmptySlots[i] == false) {
				MRS.RStation[i + 1][1] = "1";
				MRS.RStation[i + 1][2] = "MUL.D";
				if (registerFile[Integer.parseInt(Instruction.get(2).substring(
						1, Instruction.get(2).length()))].Qi == "") {
					MRS.RStation[i + 1][3] = registerFile[Integer
							.parseInt(Instruction.get(2).substring(1,
									Instruction.get(2).length()))].value
							+ "";
				} else {
					MRS.RStation[i + 1][5] = registerFile[Integer
							.parseInt(Instruction.get(2).substring(1,
									Instruction.get(2).length()))].Qi
							+ "";
				}
				if (registerFile[Integer.parseInt(Instruction.get(3).substring(
						1, Instruction.get(3).length()))].Qi == "") {
					MRS.RStation[i + 1][4] = registerFile[Integer
							.parseInt(Instruction.get(3).substring(1,
									Instruction.get(3).length()))].value
							+ "";
				} else {
					MRS.RStation[i + 1][6] = registerFile[Integer
							.parseInt(Instruction.get(3).substring(1,
									Instruction.get(3).length()))].Qi
							+ "";
				}

				int index = Integer.parseInt(Instruction.get(1).substring(1,
						Instruction.get(1).length()));
				registerFile[index].Qi = "M" + (i + 1);
				IQ.queue.get(instructionIndexTobeIssued).set(4, clock + "");
				MRS.IssueDate[i] = clock;
				MRS.flagsForEmptySlots[i] = true;
				instructionIndexTobeIssued++;
				break;
			}
			}
			break;
		case "DIV.D":
			for(int i = 0; i < MRS.flagsForEmptySlots.length; i++){
				if(MRS.flagsForEmptySlots[i] == false) {
				MRS.RStation[i + 1][1] = "1";
				MRS.RStation[i + 1][2] = "DIV.D";
				if (registerFile[Integer.parseInt(Instruction.get(2).substring(
						1, Instruction.get(2).length()))].Qi == "") {
					MRS.RStation[i + 1][3] = registerFile[Integer
							.parseInt(Instruction.get(2).substring(1,
									Instruction.get(2).length()))].value
							+ "";
				} else {
					MRS.RStation[i + 1][5] = registerFile[Integer
							.parseInt(Instruction.get(2).substring(1,
									Instruction.get(2).length()))].Qi
							+ "";
				}
				if (registerFile[Integer.parseInt(Instruction.get(3).substring(
						1, Instruction.get(3).length()))].Qi == "") {
					MRS.RStation[i + 1][4] = registerFile[Integer
							.parseInt(Instruction.get(3).substring(1,
									Instruction.get(3).length()))].value
							+ "";
				} else {
					MRS.RStation[i + 1][6] = registerFile[Integer
							.parseInt(Instruction.get(3).substring(1,
									Instruction.get(3).length()))].Qi
							+ "";
				}

				int index = Integer.parseInt(Instruction.get(1).substring(1,
						Instruction.get(1).length()));
				registerFile[index].Qi = "M" + (i + 1);
				IQ.queue.get(instructionIndexTobeIssued).set(4, clock + "");
				MRS.IssueDate[i] = clock;
				MRS.flagsForEmptySlots[i] = true;
				instructionIndexTobeIssued++;
				break;
			}
			}
			break;
		case "L.D":
			for(int i = 0; i < LB.flagsForEmptySlots.length; i++){
				if(LB.flagsForEmptySlots[i] == false)  {
				LB.loadBuff[i + 1][1] = "1";
				LB.loadBuff[i + 1][2] = Instruction.get(2) + "";
				int index = Integer.parseInt(Instruction.get(1).substring(1,
						Instruction.get(1).length()));
				registerFile[index].Qi = "L" + (i + 1);
				IQ.queue.get(instructionIndexTobeIssued).set(4, clock + "");
				LB.IssueDate[i] = clock;
				LB.flagsForEmptySlots[i] = true;
				instructionIndexTobeIssued++;
				break;
			}
			}
			break;
		case "S.D":
			for(int i = 0; i < SB.flagsForEmptySlots.length; i++){
				if(SB.flagsForEmptySlots[i] == false) {
				int index = Integer.parseInt(Instruction.get(1).substring(1,
						Instruction.get(1).length()));
				SB.storeBuff[i + 1][1] = "1";
				SB.storeBuff[i + 1][2] = Instruction.get(2) + "";
				if (registerFile[Integer.parseInt(Instruction.get(1).substring(
						1, Instruction.get(1).length()))].Qi == "") {
					SB.storeBuff[i + 1][3] = registerFile[index].Qi
					                							+ "";
				} else {
					SB.storeBuff[i + 1][4] = registerFile[index].Qi
					                							+ "";
				}
				//registerFile[index].Qi = "S" + (SBcounter + 1);
				IQ.queue.get(instructionIndexTobeIssued).set(4, clock + "");
				SB.IssueDate[i] = clock;
				SB.flagsForEmptySlots[i] = true;
				instructionIndexTobeIssued++;
				break;
			}
			}
			break;
		}
	}

	public static int indexOfSmallest(int[] arr) {
		int imin = 0;
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] < arr[imin]) {
				imin = i;
			}
		}
		return imin;
	}

	public static int findIndex(int arr[], int t) {
		int index = Arrays.binarySearch(arr, t);
		int stored = -1;
		if(index < 0){
			for(int i = 0; i < arr.length; i++){
				if(arr[i] == t){
					stored = i;
					break;
				}
			}
			return stored;
		}
		else{
			return (index < 0) ? -1 : index;
		}
		
	}

	public static void startExecution() {
		boolean isDep = false;
		for (int i = 1; i < IQ.queue.size(); i++) {
			if (IQ.queue.get(i).get(4) != "-1"
					&& IQ.queue.get(i).get(5).equals("-1") && isDep == false) {
				switch (IQ.queue.get(i).get(0)) {
				case "ADD.D":
					int index = findIndex(ARS.IssueDate,
							Integer.parseInt(IQ.queue.get(i).get(4)));
					if (ARS.RStation[index + 1][3] != (null)
							&& ARS.RStation[index + 1][4] != (null)) {
						IQ.queue.get(i).set(5, clock + "");
						IQ.queue.get(i).set(6, (clock + addlat - 1) + "");
					}
					break;
				case "SUB.D":
					int index2 = findIndex(ARS.IssueDate,
							Integer.parseInt(IQ.queue.get(i).get(4)));
					if (ARS.RStation[index2 + 1][3] != (null)
							&& ARS.RStation[index2 + 1][4] != (null)) {
						IQ.queue.get(i).set(5, clock + "");
						IQ.queue.get(i).set(6, (clock + addlat - 1) + "");
					}
					break;
				case "MUL.D":
					int index3 = findIndex(MRS.IssueDate,
							Integer.parseInt(IQ.queue.get(i).get(4)));
					if (MRS.RStation[index3 + 1][3] != (null)
							&& MRS.RStation[index3 + 1][4] != (null)) {
						IQ.queue.get(i).set(5, clock + "");
						IQ.queue.get(i).set(6, (clock + mullat - 1) + "");
					}
					break;
				case "DIV.D":
					int index4 = findIndex(MRS.IssueDate,
							Integer.parseInt(IQ.queue.get(i).get(4)));
					if (MRS.RStation[index4 + 1][3] != (null)
							&& MRS.RStation[index4 + 1][4] != (null)) {
						IQ.queue.get(i).set(5, clock + "");
						IQ.queue.get(i).set(6, (clock + mullat - 1) + "");
						endDiv = clock + mullat + 1;
					}
					break;
				case "S.D":
					int index5 = findIndex(SB.IssueDate,
							Integer.parseInt(IQ.queue.get(i).get(4)));
					if (SB.storeBuff[index5 + 1][3] != (null)) {
						IQ.queue.get(i).set(5, clock + "");
						IQ.queue.get(i).set(6, (clock + storelatency - 1) + "");
						endStore = clock + storelatency + 1;
					}
					break;
				case "L.D":
					IQ.queue.get(i).set(5, clock + "");
					IQ.queue.get(i).set(6, (clock + loadlatency - 1) + "");
					endLoad = clock + loadlatency + 1;
					break;
				}
				isDep = true;
			} 
			
			if (IQ.queue.get(i).get(4) != "-1"
					&& IQ.queue.get(i).get(5).equals("-1") && isDep == true) {
				switch (IQ.queue.get(i).get(0)) {
				case "ADD.D":
					int index = findIndex(ARS.IssueDate,
							Integer.parseInt(IQ.queue.get(i).get(4)));
					if (ARS.RStation[index + 1][3] != (null)
							&& ARS.RStation[index + 1][4] != (null)) {
						IQ.queue.get(i).set(5, (clock+1) + "");
						IQ.queue.get(i).set(6, ((clock+1) + addlat - 1) + "");
					}
					break;
				case "SUB.D":
					int index2 = findIndex(ARS.IssueDate,
							Integer.parseInt(IQ.queue.get(i).get(4)));
					if (ARS.RStation[index2 + 1][3] != (null)
							&& ARS.RStation[index2 + 1][4] != (null)) {
						IQ.queue.get(i).set(5, (clock+1) + "");
						IQ.queue.get(i).set(6, ((clock+1) + addlat - 1) + "");
					}
					break;
				case "MUL.D":
					int index3 = findIndex(MRS.IssueDate,
							Integer.parseInt(IQ.queue.get(i).get(4)));
					if (MRS.RStation[index3 + 1][3] != (null)
							&& MRS.RStation[index3 + 1][4] != (null)) {
						IQ.queue.get(i).set(5, (clock+1) + "");
						IQ.queue.get(i).set(6, ((clock+1) + mullat - 1) + "");
					}
					break;
				case "DIV.D":
					int index4 = findIndex(MRS.IssueDate,
							Integer.parseInt(IQ.queue.get(i).get(4)));
					if (MRS.RStation[index4 + 1][3] != (null)
							&& MRS.RStation[index4 + 1][4] != (null)) {
						IQ.queue.get(i).set(5, (clock+1) + "");
						IQ.queue.get(i).set(6, ((clock+1) + mullat - 1) + "");
						endDiv = clock + mullat + 1;
					}
					break;
				case "S.D":
					int index5 = findIndex(SB.IssueDate,
							Integer.parseInt(IQ.queue.get(i).get(4)));
					if (SB.storeBuff[index5 + 1][3] != (null)) {
						IQ.queue.get(i).set(5, (clock+1) + "");
						IQ.queue.get(i).set(6, ((clock+1) + storelatency - 1) + "");
						endStore = clock + storelatency + 1;
					}
					break;
				case "L.D":
					IQ.queue.get(i).set(5, (clock+1) + "");
					IQ.queue.get(i).set(6, ((clock+1) + loadlatency - 1) + "");
					endLoad = clock + loadlatency + 1;
					break;
				}
				//_flag = true;
			}
			
			else if (IQ.queue.get(i).get(4) != "-1"
					&& !IQ.queue.get(i).get(5).equals("-1")) {
				if (IQ.queue.get(i).get(0).equals("ADD.D")
						|| IQ.queue.get(i).get(0).equals("SUB.D")) {
					if (Integer.parseInt(IQ.queue.get(i).get(5)) + addlat == clock
							&& IQ.queue.get(i).get(5) != null) {

						int index = findIndex(ARS.IssueDate,
								Integer.parseInt(IQ.queue.get(i).get(4)));

						endExecution(IQ.queue.get(i), (index + 1));
					}
				}

				if (IQ.queue.get(i).get(0).equals("MUL.D")
						|| IQ.queue.get(i).get(0).equals("DIV.D")) {
					if (Integer.parseInt(IQ.queue.get(i).get(5)) + mullat == clock
							&& IQ.queue.get(i).get(5) != null) {

						int index = findIndex(MRS.IssueDate,
								Integer.parseInt(IQ.queue.get(i).get(4)));
						endExecution(IQ.queue.get(i), (index + 1));

					}
				}

				if (IQ.queue.get(i).get(0).equals("S.D")) {
					if (Integer.parseInt(IQ.queue.get(i).get(5)) + storelatency == clock
							&& IQ.queue.get(i).get(5) != null) {

						int index = findIndex(SB.IssueDate,
								Integer.parseInt(IQ.queue.get(i).get(4)));

						endExecution(IQ.queue.get(i), (index + 1));
					}
				}

				if (IQ.queue.get(i).get(0).equals("L.D")) {
					if (Integer.parseInt(IQ.queue.get(i).get(5)) + loadlatency == clock
							&& IQ.queue.get(i).get(5) != null) {
						int index = findIndex(LB.IssueDate,
								Integer.parseInt(IQ.queue.get(i).get(4)));

						endExecution(IQ.queue.get(i), (index + 1));
					}
				}
			}
		}
	}

	public static void endExecution(Vector<String> instruction, int index) {
		switch (instruction.get(0)) {
		case "ADD.D":
			double value = registerFile[Integer.parseInt(instruction.get(2)
					.substring(1, instruction.get(2).length()))].value
					+ registerFile[Integer.parseInt(instruction.get(3)
							.substring(1, instruction.get(3).length()))].value;
			String header = ARS.RStation[index][0];
			// if (clock >= 3)
			System.out.println("INDEXXX " + index);
			System.out.println("HEADERR " + header);
			writeBack(value, instruction, header);
			ARS.RStation[index][1] = null;
			ARS.RStation[index][2] = null;
			ARS.RStation[index][3] = null;
			ARS.RStation[index][4] = null;
			ARS.RStation[index][5] = null;
			ARS.RStation[index][6] = null;
			int temp = findIndex(ARS.IssueDate, Integer.parseInt(instruction.get(4)));
			ARS.flagsForEmptySlots[temp] = false;
			break;
		case "SUB.D":
			double value1 = registerFile[Integer.parseInt(instruction.get(2)
					.substring(1, instruction.get(2).length()))].value
					- registerFile[Integer.parseInt(instruction.get(3)
							.substring(1, instruction.get(3).length()))].value;
			String header2 = ARS.RStation[index][0];
			// if (clock >= 3)
			
			writeBack(value1, instruction, header2);
			ARS.RStation[index][1] = null;
			ARS.RStation[index][2] = null;
			ARS.RStation[index][3] = null;
			ARS.RStation[index][4] = null;
			ARS.RStation[index][5] = null;
			ARS.RStation[index][6] = null;
			int temp2 = findIndex(ARS.IssueDate, Integer.parseInt(instruction.get(4)));
			ARS.flagsForEmptySlots[temp2] = false;
			break;
		case "MUL.D":
			double value2 = registerFile[Integer.parseInt(instruction.get(2)
					.substring(1, instruction.get(2).length()))].value
					* registerFile[Integer.parseInt(instruction.get(3)
							.substring(1, instruction.get(3).length()))].value;
			String header3 = MRS.RStation[index][0];
			// if (clock >= 3)
			writeBack(value2, instruction, header3);
			MRS.RStation[index][1] = null;
			MRS.RStation[index][2] = null;
			MRS.RStation[index][3] = null;
			MRS.RStation[index][4] = null;
			MRS.RStation[index][5] = null;
			MRS.RStation[index][6] = null;
			int temp3 = findIndex(MRS.IssueDate, Integer.parseInt(instruction.get(4)));
			MRS.flagsForEmptySlots[temp3] = false;
			break;
		case "DIV.D":
			double value3 = registerFile[Integer.parseInt(instruction.get(2)
					.substring(1, instruction.get(2).length()))].value
					/ registerFile[Integer.parseInt(instruction.get(3)
							.substring(1, instruction.get(3).length()))].value;
			String header4 = MRS.RStation[index][0];
			
			writeBack(value3, instruction, header4);
			MRS.RStation[index][1] = null;
			MRS.RStation[index][2] = null;
			MRS.RStation[index][3] = null;
			MRS.RStation[index][4] = null;
			MRS.RStation[index][5] = null;
			MRS.RStation[index][6] = null;
			int temp4 = findIndex(MRS.IssueDate, Integer.parseInt(instruction.get(4)));
			MRS.flagsForEmptySlots[temp4] = false;
			break;
		case "S.D":
			mem.StoreData(
					Integer.parseInt(instruction.get(2).substring(0,
							instruction.get(2).length())),
					registerFile[Integer.parseInt(instruction.get(1).substring(
							1, instruction.get(1).length()))].value);
			
			SB.storeBuff[index][1] = null;
			SB.storeBuff[index][2] = null;
			SB.storeBuff[index][3] = null;
			SB.storeBuff[index][4] = null;
			int temp5 = findIndex(SB.IssueDate, Integer.parseInt(instruction.get(4)));
			SB.flagsForEmptySlots[temp5] = false;
			break;
		case "L.D":
			double value5 = mem.LoadData(Integer.parseInt(instruction.get(2)
					.substring(0, instruction.get(2).length())));
			String tag = LB.loadBuff[index][0];
			writeBack(value5, instruction, tag);
			LB.loadBuff[index][1] = null;
			LB.loadBuff[index][2] = null;
			
			int temp6 = findIndex(LB.IssueDate, Integer.parseInt(instruction.get(4)));
			LB.flagsForEmptySlots[temp6] = false;
			break;
		}
	}

	public static boolean fullStations() {
		boolean flag = false;
		boolean flag2 = false;
		boolean flag3 = false;
		boolean flag4 = false;
		for (int i = 1; i < ARS.RStation.length; i++) {
			if (ARS.RStation[i][1] == (null)) {
				flag = false;
			} else {
				flag = true;
				break;
			}
		}
		for (int i = 1; i < MRS.RStation.length; i++) {
			if (MRS.RStation[i][1] == (null)) {
				flag2 = false;
			} else {
				flag2 = true;
				break;
			}
		}
		for (int i = 1; i < LB.loadBuff.length; i++) {
			if (LB.loadBuff[i][1] == (null)) {
				flag3 = false;
			} else {
				flag3 = true;
				break;
			}
		}
		for (int i = 1; i < SB.storeBuff.length; i++) {
			if (SB.storeBuff[i][1] == (null)) {
				flag4 = false;
			} else {
				flag4 = true;
				break;
			}
		}
		return flag || flag2 || flag3 || flag4;
	}

	public static void writeBack(double value, Vector<String> instruction,
			String tag) {
		commonDataBus = value;
		for (int i = 1; i < ARS.RStation.length; i++) {
			if (ARS.RStation[i][5] != null && ARS.RStation[i][5].equals(tag)) {
				ARS.RStation[i][3] = value + "";
				ARS.RStation[i][5] = null;
			}
			if (ARS.RStation[i][6] != null && ARS.RStation[i][6].equals(tag)) {
				ARS.RStation[i][4] = value + "";
				ARS.RStation[i][6] = null;
			}
		}
		for (int i = 1; i < MRS.RStation.length; i++) {
			if (MRS.RStation[i][5] != null && MRS.RStation[i][5].equals(tag)) {
				MRS.RStation[i][3] = value + "";
				MRS.RStation[i][5] = null;
			}
			if (MRS.RStation[i][6] != null && MRS.RStation[i][6].equals(tag)) {
				MRS.RStation[i][4] = value + "";
				MRS.RStation[i][6] = null;
			}
		}
		//Check Indexes
		for (int i = 1; i < SB.storeBuff.length; i++) {
			if (SB.storeBuff[i][4] != null && SB.storeBuff[i][4].equals(tag)) {
				SB.storeBuff[i][3] = value + "";
				SB.storeBuff[i][4] = null;
			}
		}
		for (int i = 1; i < registerFile.length; i++) {
			if (registerFile[i].Qi.equals(tag)) {
				registerFile[i].value = value;
				registerFile[i].Qi = "";
				break;
			}
		}
		writeBoolean = true;
		wroteBack = clock;
		System.out.println("Clock : " + clock);
		System.out.println("Instruction Queue");
		for(int i = 0; i < IQ.queue.size(); i++){
			System.out.print(IQ.queue.get(i));
		}
		System.out.println("AddReservationStation");
		print2D(ARS.RStation);
		System.out.println("MulReservationStation");
		print2D(MRS.RStation);
		System.out.println("LoadBuffer");
		print2D(LB.loadBuff);
		System.out.println("StoreBuffer");
		print2D(SB.storeBuff);
		System.out.println("Register File");
		for (int i = 0; i < registerFile.length; i++) {
			System.out.println(registerFile[i].name + " " + registerFile[i].Qi
					+ " " + registerFile[i].value);
		}
		System.out
				.println("----------------------------------------------------------------------");
		clock++;
		for (int i = 1; i < IQ.queue.size(); i++) {
			if (IQ.queue.get(i).get(4).equals(instruction.get(4))) {
				IQ.queue.get(i).set(7, (clock - 1) + "");
			}
		}
	}

	public static int Fetch(String fileName) throws Exception {
		String line = "";
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		int instrCount = 0;
		while ((line = br.readLine()) != null) {
			Vector<String> vec = new Vector<String>();
			String[] array = line.split(" ");
			for (String s : array) {
				vec.add(s);
			}
			if (vec.size() == 3) {
				vec.add("-1");
				vec.add("-1");
				vec.add("-1");
				vec.add("-1");
				vec.add("-1");
			}
			if (vec.size() == 4) {
				vec.add("-1");
				vec.add("-1");
				vec.add("-1");
				vec.add("-1");
			}
			IQ.queue.add(vec);
			instrCount++;
		}
		br.close();
		return instrCount;
	}

	public static void tomasulo(String fileName) throws Exception {
		int instructionCount = Fetch(fileName);
		System.out.println("Clock : " + clock);
		System.out.println("Instruction Queue");
		System.out.println(IQ.queue);
		System.out
		.println("________________________________________________________________________________");
		clock = 1;
		do {
			if(clock == 40)
				break;
			if (instructionIndexTobeIssued <= instructionCount ) {
				System.out.println("ICCCCC " + instructionCount);
				System.out.println("IITBIIIII " + instructionIndexTobeIssued);
				IssueInstruction();
			}
			if (isDependent) {
				if (clock > wroteBack && writeBoolean) {
					startExecution();
					isDependent = false;
					writeBoolean = false;
				}
			} else if (!isDependent && clock >= 2) {
				startExecution();
			}
			System.out.println("Clock : " + (clock));
			System.out.println("Instruction Queue");
			System.out.println(IQ.queue);
			System.out.println("AddReservationStation");
			print2D(ARS.RStation);
			System.out.println("MulReservationStation");
			print2D(MRS.RStation);
			System.out.println("LoadBuffer");
			print2D(LB.loadBuff);
			System.out.println("StoreBuffer");
			print2D(SB.storeBuff);
			System.out.println("Register File");
			for (int i = 0; i < registerFile.length; i++) {
				System.out.println(registerFile[i].name + " "
						+ registerFile[i].Qi + " " + registerFile[i].value);
			}
			System.out
					.println("----------------------------------------------------------------------");
			clock++;
		} while (fullStations());
	}

	public static void print2D(String mat[][]) {
		for (int i = 0; i < mat.length; i++)
			for (int j = 0; j < mat[i].length; j++)
				System.out.print(mat[i][j] + " ");
		System.out.println();
	}

	public static void main(String[] args) throws Throwable {
		Tomasulo jerry = new Tomasulo();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter ADD Latency : ");
		addlat = sc.nextInt();
		System.out.println("Enter MUL Latency : ");
		mullat = sc.nextInt();
		System.out.println("Enter Store Latency : ");
		storelatency = sc.nextInt();
		System.out.println("Enter Load Latency : ");
		loadlatency = sc.nextInt();
		tomasulo("C:/Users/Omar Galal/workspace/Micro/src/testCases.txt");
		System.out.println("_________________________________");
		System.out.println(mem.dataMemory[900] + " Final Result ");
	}
}