package spacefight.schiff.systeme;

import java.awt.Color;
import java.awt.Graphics2D;

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
		this.ladezustand = 1;
		this.energie = 1.0;
		
		this.schaden = Math.pow(aktStufe, 1.3) * 1000;
	}

	@Override
	public void update(long frameDauer) {
		
		super.update(frameDauer);
		
		calcEnergieRegeneration(frameDauer);
		calcLadeRegeneration(frameDauer);
	}

	/**
	 * Bestimmt die Schussfrequenz.
	 * 
	 * @param frameDauer
	 *            in ms
	 */
	private void calcLadeRegeneration(long frameDauer) {
		double plus = frameDauer / (double) (500 - (40 * 10)) * energie;
		ladezustand += plus; // 2 Schuss pro Sekunde bei Stufe 1 bis 10 bei Stufe 10 (bei voller Energie)
		ladezustand = Math.min(1, ladezustand);
	}

	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);

	}

	private void calcEnergieRegeneration(long frameDauer) {
		energie += frameDauer * 0.00001 * aktStufe; // 1.0 Prozent pro Sekunde pro Stufe
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
		if (ladezustand >= 1.0) {
			Koordinate3D absEigenpos = new Koordinate3D(traeger.getPosition());

			double absRichtung = traeger.getAusrichtung() + relPosition; // Absolute Richtung des Lasers bzg. Schiff

			absEigenpos.add(new Koordinate3D(absRichtung, traeger.getRadius()));

			Koordinate3D ziel = new Koordinate3D(traeger.getPosition());
			ziel.add(new Koordinate3D(x, y, 0));

			Koordinate3D differenz = new Koordinate3D(absEigenpos, ziel);

			double richtung = differenz.getXYVektorRichtung(); // Absolute Richtung des Schusses

			Koordinate3D schussSpeed = new Koordinate3D(richtung, calcLaserSpeed());
			schussSpeed.add(traeger.getSpeed());

			if (Math.cos(richtung - absRichtung) > 0) {
				neuerSchuss = new Schuss(schaden, aktStufe, absEigenpos, schussSpeed, Color.RED);
			}
			ladezustand = 0;
			energie *= 0.95;
		}
	}

}
