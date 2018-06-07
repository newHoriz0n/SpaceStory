package spacefight;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import spacefight.schiff.SystemBucht;
import topview.pbLib.Koordinate3D;

public class Traeger extends Koerper implements Zerstoerbares {

	protected double ausrichtung;
	protected List<SystemBucht> systemBuchten;
	private double maxHuelle;
	private double aktHuelle;

	private Color teamFarbe;

	public Traeger(Koordinate3D position, double radius, List<SystemBucht> buchten, double huelle, double masse, Color farbe, Color teamFarbe) {
		super(masse, radius, position, farbe);
		this.ausrichtung = 0;
		this.systemBuchten = new ArrayList<>();
		this.systemBuchten.addAll(buchten);
		this.maxHuelle = huelle;
		this.aktHuelle = maxHuelle;
		this.teamFarbe = teamFarbe;

		for (SystemBucht sb : buchten) {
			sb.systemEinbauen(this);
		}

	}

	public void getroffenWerden(double schaden) {
		aktHuelle -= schaden;
	}

	public boolean checkAmLeben() {
		return aktHuelle > 0;
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(farbe);
		g2d.fillOval((int) (position.getX() - radius), (int) (position.getY() - radius), (int) (2 * radius), (int) (2 * radius));
		g2d.setColor(teamFarbe);
		g2d.drawOval((int) (position.getX() - radius + 2), (int) (position.getY() - radius + 2), (int) (2 * (radius - 2)), (int) (2 * (radius - 2)));
		for (SystemBucht s : systemBuchten) {
			s.draw(g2d);
		}

	}

	public Koordinate3D getPosition() {
		return position;
	}

	public double getRadius() {
		return radius;
	}

	public double getAusrichtung() {
		return ausrichtung;
	}

	public Color getTeamFarbe() {
		return teamFarbe;
	}

}
