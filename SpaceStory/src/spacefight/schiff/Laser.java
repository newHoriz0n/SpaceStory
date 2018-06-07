package spacefight.schiff;

import java.awt.Color;

import spacefight.Schuss;
import topview.pbLib.Koordinate3D;

public class Laser extends SystemBucht {

	private double schaden;
	private double ladezustand; // in %, Feuert nur wenn Ladezustand = 100%
	private double energie; // in %

	private Schuss neuerSchuss; // zeit des letzten in Auftrag gegebenen Schusses;

	public Laser(int aktStufe, int maxStufe, double relPosition) {
		super(ESystemTyp.Laser, aktStufe, maxStufe, relPosition);

		this.neuerSchuss = null;
	}

	/**
	 * Bestimmt die Schussfrequenz.
	 * 
	 * @param frameDauer
	 *            in ms
	 */
	private void calcLadeRegeneration(long frameDauer) {
		// TODO: Calc LadeRegeneration
		ladezustand += frameDauer / (500 - (40 * aktStufe)) * energie; // 2 Schuss bei Stufe 1 bis 10 bei Stufe 10 (bei voller Energie)
		ladezustand = Math.min(1, ladezustand);
	}

	private void calcEnergieRegeneration(long frameDauer) {
		energie += frameDauer * 0.00001; // 1.0 Prozent pro Sekunde
		energie = Math.min(1, energie);
	}

	/**
	 * 
	 * @param Distanz
	 *            zum Ziel in pixel
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
		return 200 * Math.pow(1.2, aktStufe);
	}

	public Schuss getNeuerSchuss() {
		if (neuerSchuss != null) {
			Schuss s = new Schuss(neuerSchuss);
			neuerSchuss = null;
			return s;
		}
		return null;
	}

	public void schiesseAufPosition(int x, int y) {
		Koordinate3D absEigenpos = new Koordinate3D(traeger.getPosition());

		double absRichtung = traeger.getAusrichtung() + relPosition; // Absolute Richtung des Lasers bzg. Schiff
		
		absEigenpos.add(new Koordinate3D(absRichtung, traeger.getRadius()));

		Koordinate3D ziel = new Koordinate3D(traeger.getPosition());
		ziel.add(new Koordinate3D(x, y, 0));

		Koordinate3D differenz = new Koordinate3D(absEigenpos, ziel);

		double richtung = differenz.getXYVektorRichtung();			// Absolute Richtung des Schusses
		
		if (Math.cos(richtung - absRichtung) > 0) {
			neuerSchuss = new Schuss(schaden, aktStufe, absEigenpos, new Koordinate3D(richtung, calcLaserSpeed()), Color.RED);
		}
	}

}
