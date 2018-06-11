package spacefight.schiff.systeme;

import java.awt.Color;

import java.awt.Graphics2D;

import topview.pbLib.Koordinate3D;

public class AntriebsStoss {

	private Koordinate3D position;
	private double radius;
	private long startZeit;

	public AntriebsStoss(Koordinate3D pos, double radius) {
		this.position = new Koordinate3D(pos);
		this.radius = radius;
		this.startZeit = System.currentTimeMillis();
	}

	public AntriebsStoss(AntriebsStoss neuerAntriebStoss) {
		this.position = new Koordinate3D(neuerAntriebStoss.position);
		this.radius = neuerAntriebStoss.radius;
		this.startZeit = neuerAntriebStoss.startZeit;
	}

	public void draw(Graphics2D g2d) {
		long zeit = System.currentTimeMillis() - startZeit;
		double rad = radius / ((zeit / 500.0) + 1);
		g2d.setColor(Color.CYAN);
		g2d.drawOval((int) (position.getX() - rad), (int) (position.getY() - rad), (int) (rad * 2), (int) (rad * 2));
	}

	public long getLifeTime() {
		return System.currentTimeMillis() - startZeit;
	}

}
