public class FPR {

	String name;
	double value;
	String Qi = "";
	int count;

	public FPR(String name) {
		if (count == 32)
			return;
		else {
			this.name = name;
			count++;
		}
	}
}
