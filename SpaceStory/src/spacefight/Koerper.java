package spacefight;

import java.awt.Color;
import java.awt.Graphics2D;

import topview.pbLib.Koordinate3D;

public abstract class Koerper extends WeltObjekt {

	private Koordinate3D speed;
	protected double radius;
	private double masse;
	protected Color farbe;

	public Koerper(double masse, double radius, Koordinate3D position, Color farbe) {
		super(position);
		this.masse = masse;
		this.radius = radius;
		this.farbe = farbe;
	}

	public boolean checkKollision(Koerper k2) {
		double dist = Koordinate3D.getDistance(position.getX(), position.getY(), k2.position.getX(), k2.position.getY());
		return dist < radius + k2.radius;
	}

	@Override
	public abstract void draw(Graphics2D g2d);

	@Override
	public void update(long frameDauer) {
		// TODO: Update K�rper
	}

}
