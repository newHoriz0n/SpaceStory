package spacefight.schiff;

import java.awt.Color;
import java.awt.Graphics2D;

import spacefight.Traeger;
import spacefight.Zerstoerbares;

public class SystemBucht implements Zerstoerbares {

	protected Traeger traeger;

	private ESystemTyp systemTyp;
	protected int aktStufe;
	protected int maxStufe;
	protected double relPosition; // in rad relativ zum Traeger

	private double aktHuelle;
	private double maxHuelle;

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

	public int getRadius() {
		return (int) Math.pow(2, maxStufe);
	}

	public void draw(Graphics2D g2d) {
		int maxRadius = (int) Math.pow(2, maxStufe);
		int aktRadius = (int) Math.pow(2, aktStufe);
		int absPosX = (int) (traeger.getPosition().getX() + Math.cos(relPosition + traeger.getAusrichtung()) * traeger.getRadius());
		int absPosY = (int) (traeger.getPosition().getY() - Math.sin(relPosition + traeger.getAusrichtung()) * traeger.getRadius());
		g2d.setColor(Color.DARK_GRAY);
		g2d.fillOval(absPosX - maxRadius, absPosY - maxRadius, maxRadius * 2, maxRadius * 2);
		g2d.setColor(farbe);
		g2d.fillOval(absPosX - aktRadius, absPosY - aktRadius, aktRadius * 2, aktRadius * 2);
		if (aktStufe > 5) {
			g2d.setColor(traeger.getTeamFarbe());
			g2d.fillOval(absPosX - maxRadius + 2, absPosY - maxRadius + 2, (maxRadius - 2) * 2, (maxRadius - 2) * 2);
		}
	}

	public ESystemTyp getSystemTyp() {
		return systemTyp;
	}

	public int getStufe() {
		return aktStufe;
	}

}
