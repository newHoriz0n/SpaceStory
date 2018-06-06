package spacefight.schiff;

public class Antrieb extends SystemBucht {

	public Antrieb(int aktStufe, int maxStufe, double relPosition) {
		super(ESystemTyp.Antrieb, aktStufe, maxStufe, relPosition);
		// TODO Auto-generated constructor stub
	}

	public double getAntriebsKraft() {
		return Math.pow(2, 3);
	}

}
