package spacefight.schiff;

public class Laser extends SystemBucht {
	
	private double schaden;
	private double ladezustand;		// in %, Feuert nur wenn Ladezustand = 100%
	private double energie;			// in %

	public Laser(int aktStufe, int maxStufe, double relPosition) {
		super(ESystemTyp.Laser, aktStufe, maxStufe, relPosition);
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * Bestimmt die Schussfrequenz.
	 * @param frameDauer in ms
	 */
	private void calcLadeRegeneration(long frameDauer) {
		// TODO: Calc LadeRegeneration
		ladezustand += frameDauer / (500 - (40 * aktStufe)) * energie;			// 2 Schuss bei Stufe 1 bis 10 bei Stufe 10 (bei voller Energie)
		ladezustand = Math.min(1, ladezustand);
	}
	
	private void calcEnergieRegeneration(long frameDauer) {
		energie += frameDauer * 0.00001;			// 1.0 Prozent pro Sekunde
		energie = Math.min(1, energie);
	}
	
	
	/**
	 * 
	 * @param Distanz zum Ziel in pixel
	 * @return Zeit in ms die laser braucht um zeil zu erreichen
	 */
	private double calcZeitBisEinschlag(double dist) {
		return dist / calcLaserSpeed() * 1000;
	}
	
	/**
	 * 
	 * @return laserspeed in pixel pro Sekunde
	 */
	private double calcLaserSpeed() {
		return aktStufe * 300;
	}
	
}
