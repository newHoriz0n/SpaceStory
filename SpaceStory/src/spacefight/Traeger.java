package spacefight;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import spacefight.schiff.systeme.Antrieb;
import spacefight.schiff.systeme.ESystemTyp;
import spacefight.schiff.systeme.Laser;
import spacefight.schiff.systeme.Schild;
import spacefight.schiff.systeme.Steuerung;
import spacefight.schiff.systeme.SystemBucht;
import topview.pbLib.Koordinate3D;
import topview.welt.Koerper;
import topview.welt.collision.Collidable;

public abstract class Traeger extends Koerper implements Zerstoerbares, Collidable {

	protected double ausrichtung;
	protected List<SystemBucht> systemBuchten;
	private double maxHuelle;
	private double aktHuelle;
	private double aktSchild;
	
	
	protected double wendigkeit;
	protected double beschleunigung;

	protected List<Laser> lasers;
	protected List<Antrieb> antriebe;
	protected List<Schild> schilde;

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

		ladeSysteme();

	}

	private void ladeSysteme() {

		this.lasers = new ArrayList<>();
		this.antriebe = new ArrayList<>();
		this.schilde = new ArrayList<>();

		double aktBeschl = 0;
		double aktWend = 0;

		for (SystemBucht s : systemBuchten) {
			if (s.getSystemTyp().equals(ESystemTyp.Laser)) {
				lasers.add((Laser) s);
			} else if (s.getSystemTyp().equals(ESystemTyp.Antrieb)) {
				antriebe.add((Antrieb) s);
				aktBeschl += ((Antrieb) s).getAntriebsKraft();
			} else if (s.getSystemTyp().equals(ESystemTyp.Schild)) {
				schilde.add((Schild) s);
			} else if (s.getSystemTyp().equals(ESystemTyp.Steuerung)) {
				aktWend += ((Steuerung) s).getSteuerKraft();
			}
		}

		beschleunigung = aktBeschl / masse;
		wendigkeit = aktWend;
		
		aktSchild = 0;
		
		for (Schild s : schilde) {
			aktSchild += s.getEnergie();
		}

	}

	@Override
	public void update(long frameDauer) {
		super.update(frameDauer);

		for (SystemBucht s : systemBuchten) {
			s.update(frameDauer);
		}

		// berechne aktuelle Schildenergie
		aktSchild = 0;
		for (Schild s : schilde) {
			aktSchild += s.getEnergie();
		}
		
		
	}

	public void getroffenWerden(double schaden) {
		
		double schildSchaden = schaden / (double)schilde.size();

		for (Schild s : schilde) {
			s.getroffenWerden(schildSchaden);
		}
		
		aktHuelle += Math.min(aktSchild - schaden, 0);	//  von hülle abziehen was nicht von schild abgefangen wird

		
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
		
		 g2d.setColor(Color.WHITE);
		 g2d.drawString("" + aktSchild, getPosition().getIntX() + 100,
		 getPosition().getIntY());
		 g2d.drawString("" + aktHuelle, getPosition().getIntX() + 100,
		 getPosition().getIntY() + 20);

	}

	public double getAusrichtung() {
		return ausrichtung;
	}

	public Color getTeamFarbe() {
		return teamFarbe;
	}

	@Override
	public abstract boolean isFixiert();

	@Override
	public Collidable getTraeger() {
		return this;
	}

	@Override
	public void getCollidableTeile(List<Collidable> cTeile) {
		cTeile.add(this);
		cTeile.addAll(systemBuchten);
	}

}
