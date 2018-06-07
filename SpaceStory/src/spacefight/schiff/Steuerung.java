package spacefight.schiff;

public class Steuerung extends SystemBucht {

	public Steuerung(int aktStufe, int maxStufe, double relPosition) {
		super(ESystemTyp.Steuerung, aktStufe, maxStufe, relPosition);
		// TODO Auto-generated constructor stub
	}

	public double getSteuerKraft() {
		return Math.pow(1.5, aktStufe);
	}

}
