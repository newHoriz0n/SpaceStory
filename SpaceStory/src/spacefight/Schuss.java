package spacefight;

import java.awt.Color;
import java.awt.Graphics2D;

import topview.pbLib.Koordinate3D;
import topview.welt.Koerper;

public class Schuss extends Koerper {

	private double energie;
	private long startZeit;
	private long lebensZeit;

	public Schuss(double energie, double radius, Koordinate3D startPosition, Koordinate3D speed, Color farbe) {
		super(0, radius, startPosition, farbe);

		this.energie = energie;
		this.lebensZeit = 1500;
		this.startZeit = System.currentTimeMillis();

		setSpeed(speed);
	}

	public Schuss(Schuss schuss) {
		super(0, schuss.radius, schuss.position, schuss.farbe);
		this.energie = schuss.energie;
		this.startZeit = schuss.startZeit;
		this.lebensZeit = schuss.lebensZeit;
		setSpeed(schuss.speed);
	}

	public boolean fliegt() {
		return System.currentTimeMillis() - startZeit < lebensZeit;
	}

	public double getEnergie() {
		return energie;
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(farbe);
		g2d.fillOval((int) (position.getIntX() - radius), (int) (position.getIntY() - radius), (int) (2 * radius), (int) (2 * radius));
	}

	public boolean isScharf() {
		if (System.currentTimeMillis() - startZeit > 100) {
			return true;
		}
		return false;
	}
}
