package spacefight.schiff;

public class Schild extends SystemBucht {

	private double energie;
	private double maxEnergie;

	public Schild(int aktStufe, int maxStufe, double relPosition) {
		super(ESystemTyp.Schild, aktStufe, maxStufe, relPosition);
		// TODO Auto-generated constructor stub
	}

	private void regeneriere(double frameDauer) {

		energie += frameDauer;
		energie = Math.min(maxEnergie, energie);

	}

}
