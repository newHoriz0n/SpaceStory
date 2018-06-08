package spacefight;

import java.awt.Color;
import java.awt.Graphics2D;

import topview.pbLib.Koordinate3D;

public abstract class Koerper extends WeltObjekt {

	protected Koordinate3D speed; // in pixel pro Sekunde
	protected double radius;
	protected double masse;
	protected Color farbe;

	public Koerper(double masse, double radius, Koordinate3D position, Color farbe) {
		super(position);
		this.masse = masse;
		this.radius = radius;
		this.farbe = farbe;
		this.speed = new Koordinate3D();
	}

	public boolean checkKollision(Koerper k2) {
		double dist = Koordinate3D.getDistance(position.getX(), position.getY(), k2.position.getX(),
				k2.position.getY());
		return dist < radius + k2.radius;
	}

	public void setSpeed(Koordinate3D speed) {
		this.speed = speed;
	}

	public Koordinate3D getSpeed() {
		return speed;
	}

	@Override
	public abstract void draw(Graphics2D g2d);

	@Override
	public void update(long frameDauer) {
		Koordinate3D frameSpeed = new Koordinate3D(speed);
		frameSpeed.scale(frameDauer / 1000.0);
		position.add(frameSpeed);
	}

}
