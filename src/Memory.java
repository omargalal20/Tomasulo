public class Memory {

	double[] dataMemory;

	public Memory() {
		dataMemory = new double[2048];
	}

	public double LoadData(int address) {
		return dataMemory[address];
	}

	public void StoreData(int address, double data) {
		dataMemory[address] = data;
	}
}
