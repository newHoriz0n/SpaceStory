package spacefight.schiff.systeme;

public class Schild extends SystemBucht {

	private double energie;
	private double maxEnergie;

	public Schild(int aktStufe, int maxStufe, double relPosition) {
		super(ESystemTyp.Schild, aktStufe, maxStufe, relPosition);

		ladeSchildEnergie();
	}

	private void ladeSchildEnergie() {
		this.maxEnergie = 10000 * Math.pow(aktStufe, 2);
		this.energie = maxEnergie;
	}

	private void regeneriere(double frameDauer) {

		energie += frameDauer * 0.3;
		energie = Math.min(maxEnergie, energie);

	}
	
	@Override
	public void getroffenWerden(double schaden) {
		energie -= schaden;
		energie = Math.max(0, energie);
	}

	@Override
	public void update(long frameDauer) {
		super.update(frameDauer);
		regeneriere(frameDauer);
	}

	public double getEnergie() {
		return energie;
	}

}
