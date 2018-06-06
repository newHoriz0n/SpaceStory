package spacefight;

import java.awt.Color;
import java.awt.Graphics2D;

import topview.pbLib.Koordinate3D;

public class Schuss extends Koerper{

	private double energie;
	private long startZeit;
	private long lebensZeit;

	public Schuss(double masse, double radius, Koordinate3D position, Color farbe) {
		super(masse, radius, position, farbe);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}
}
