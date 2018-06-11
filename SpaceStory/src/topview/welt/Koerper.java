package topview.welt;

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

	public Koordinate3D getPosition() {
		return position;
	}
	
	public Koordinate3D getSpeed() {
		return speed;
	}

	public double getMasse() {
		return masse;
	}
	
	public double getRadius() {
		return radius;
	}


	@Override
	public abstract void draw(Graphics2D g2d);

	@Override
	public void update(long frameDauer) {
		Koordinate3D frameSpeed = new Koordinate3D(speed);
		frameSpeed.scale(frameDauer / 1000.0);
		position.add(frameSpeed);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((farbe == null) ? 0 : farbe.hashCode());
		long temp;
		temp = Double.doubleToLongBits(masse);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(radius);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((speed == null) ? 0 : speed.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Koerper other = (Koerper) obj;
		if (farbe == null) {
			if (other.farbe != null)
				return false;
		} else if (!farbe.equals(other.farbe))
			return false;
		if (Double.doubleToLongBits(masse) != Double.doubleToLongBits(other.masse))
			return false;
		if (Double.doubleToLongBits(radius) != Double.doubleToLongBits(other.radius))
			return false;
		if (speed == null) {
			if (other.speed != null)
				return false;
		} else if (!speed.equals(other.speed))
			return false;
		return true;
	}
	
	
	

}
