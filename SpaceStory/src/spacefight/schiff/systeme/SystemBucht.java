package spacefight.schiff.systeme;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import spacefight.Traeger;
import spacefight.Zerstoerbares;
import topview.pbLib.Koordinate3D;
import topview.welt.collision.Collidable;

public abstract class SystemBucht implements Zerstoerbares, Collidable {

	protected Traeger traeger;

	private ESystemTyp systemTyp;
	protected int aktStufe;
	protected int maxStufe;
	protected double relPosition; // in rad relativ zum Traeger
	private double aktHuelle;
	private double maxHuelle;

	protected Koordinate3D aktAbsolutePosition; // Absolute Position im Raum (in update immer aktualisiert)

	private Color farbe;

	public SystemBucht(ESystemTyp systemTyp, int aktStufe, int maxStufe, double relPosition) {
		super();
		this.systemTyp = systemTyp;
		this.aktStufe = aktStufe;
		this.maxStufe = maxStufe;
		this.relPosition = relPosition;
		this.maxHuelle = calcMaxHuelle(aktStufe);

		this.aktHuelle = maxHuelle;
		this.farbe = calcSystemColor();
		
		this.aktAbsolutePosition = new Koordinate3D();
	}

	public void update(long frameDauer) {
		
		this.aktAbsolutePosition.setKoordinaten(traeger.getPosition().getX() + Math.cos(relPosition + traeger.getAusrichtung()) * traeger.getRadius(),
				traeger.getPosition().getY() - Math.sin(relPosition + traeger.getAusrichtung()) * traeger.getRadius(), 0);
	}

	private static double calcMaxHuelle(int aktStufe) {
		return aktStufe * 10;
	}

	public void systemEinbauen(Traeger t) {
		this.traeger = t;
	}

	private Color calcSystemColor() {
		if (systemTyp.equals(ESystemTyp.Antrieb)) {
			return Color.CYAN;
		} else if (systemTyp.equals(ESystemTyp.Laser)) {
			return Color.RED;
		} else if (systemTyp.equals(ESystemTyp.Schild)) {
			return Color.BLUE;
		} else if (systemTyp.equals(ESystemTyp.Steuerung)) {
			return Color.ORANGE;
		}
		return Color.MAGENTA;
	}

	public void getroffenWerden(double schaden) {
		aktHuelle -= schaden;
	}

	public boolean checkAmLeben() {
		return aktHuelle > 0;
	}

	public double getRadius() {
		return (int) Math.pow(2, maxStufe);
	}

	public void draw(Graphics2D g2d) {
		int maxRadius = (int) Math.pow(2, maxStufe);
		int aktRadius = (int) Math.pow(2, aktStufe);
		g2d.setColor(Color.DARK_GRAY);
		g2d.fillOval((int) (aktAbsolutePosition.getX() - maxRadius), (int) (aktAbsolutePosition.getY() - maxRadius), maxRadius * 2, maxRadius * 2);
		g2d.setColor(farbe);
		g2d.fillOval((int) (aktAbsolutePosition.getX() - aktRadius), (int) (aktAbsolutePosition.getY() - aktRadius), aktRadius * 2, aktRadius * 2);
		if (aktStufe > 5) {
			g2d.setColor(traeger.getTeamFarbe());
			g2d.fillOval((int) (aktAbsolutePosition.getX() - maxRadius + 2), (int) (aktAbsolutePosition.getY() - maxRadius + 2), (maxRadius - 2) * 2,
					(maxRadius - 2) * 2);
		}
	}

	public ESystemTyp getSystemTyp() {
		return systemTyp;
	}

	public int getStufe() {
		return aktStufe;
	}

	@Override
	public Koordinate3D getPosition() {
		return new Koordinate3D(traeger.getPosition().getX() + Math.cos(relPosition + traeger.getAusrichtung()) * traeger.getRadius(),
				traeger.getPosition().getY() - Math.sin(relPosition + traeger.getAusrichtung()) * traeger.getRadius(), 0);
	}

	@Override
	public Koordinate3D getSpeed() {
		return traeger.getSpeed();
	}

	@Override
	public double getMasse() {
		return traeger.getMasse();
	}

	@Override
	public boolean isFixiert() {
		return false;
	}

	@Override
	public Collidable getTraeger() {
		return traeger;
	}

	@Override
	public void getCollidableTeile(List<Collidable> cTeile) {
		cTeile.add(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(aktHuelle);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + aktStufe;
		result = prime * result + ((farbe == null) ? 0 : farbe.hashCode());
		temp = Double.doubleToLongBits(maxHuelle);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + maxStufe;
		temp = Double.doubleToLongBits(relPosition);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((systemTyp == null) ? 0 : systemTyp.hashCode());
		result = prime * result + ((traeger == null) ? 0 : traeger.hashCode());
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
		SystemBucht other = (SystemBucht) obj;
		if (Double.doubleToLongBits(aktHuelle) != Double.doubleToLongBits(other.aktHuelle))
			return false;
		if (aktStufe != other.aktStufe)
			return false;
		if (farbe == null) {
			if (other.farbe != null)
				return false;
		} else if (!farbe.equals(other.farbe))
			return false;
		if (Double.doubleToLongBits(maxHuelle) != Double.doubleToLongBits(other.maxHuelle))
			return false;
		if (maxStufe != other.maxStufe)
			return false;
		if (Double.doubleToLongBits(relPosition) != Double.doubleToLongBits(other.relPosition))
			return false;
		if (systemTyp != other.systemTyp)
			return false;
		if (traeger == null) {
			if (other.traeger != null)
				return false;
		} else if (!traeger.equals(other.traeger))
			return false;
		return true;
	}

	@Override
	public void setSpeedNachKollision(Koordinate3D neueSpeed) {
	}

}
