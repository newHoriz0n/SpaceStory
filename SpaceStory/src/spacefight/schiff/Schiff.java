package spacefight.schiff;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;

import spacefight.Traeger;
import spacefight.schiff.systeme.Antrieb;
import spacefight.schiff.systeme.Laser;
import spacefight.schiff.systeme.SystemBucht;
import topview.pbLib.Koordinate3D;

public class Schiff extends Traeger {

	private boolean autoPilot;
	private double zielAusrichtung;
	private double lenkRichtung;
	private double aktSchub;

	private String bezeichnung;
	private double radSpeed;

	private double rotSpeed; // rad pro Sekunde

	private Koordinate3D kollisionsSpeed;
	private double kollisionSpeedAnteil;

	private BufferedImage bild;

	public Schiff(Koordinate3D position, String bezeichnung, double radius, double huelle, double masse, List<SystemBucht> buchten, Color teamFarbe,
			BufferedImage bild) {
		super(position, radius, buchten, huelle, masse, new Color(0, 0, 0, 0), teamFarbe);

		this.bezeichnung = bezeichnung;
		this.zielAusrichtung = 2.0;

		this.autoPilot = false;
		this.lenkRichtung = 0;

		this.kollisionsSpeed = new Koordinate3D();
		this.kollisionSpeedAnteil = 0;

		this.bild = bild;

	}

	@Override
	public void update(long frameDauer) {

		calcAusrichtung(frameDauer);
		calcSpeed(frameDauer);

		super.update(frameDauer);

		kollisionSpeedAnteil *= Math.pow(0.97, frameDauer / 1000.0);
	}

	private void calcSpeed(long frameDauer) {
		radSpeed += aktSchub * beschleunigung * (frameDauer / 1000.0);
		Koordinate3D newSpeed = new Koordinate3D(ausrichtung, radSpeed);
		kollisionsSpeed.scale(kollisionSpeedAnteil);
		newSpeed.add(kollisionsSpeed);
		setSpeed(newSpeed);
	}

	/**
	 * 
	 * @param richtung
	 *            in rad
	 */
	public void setZielAusrichtung(double richtung) {
		this.zielAusrichtung = richtung;
	}

	public void steuern(double richtung) {
		this.lenkRichtung = richtung;
	}

	public void beschleunigen() {
		this.aktSchub = 1.0;
	}

	public void bremsen() {
		this.aktSchub = -1.0;
	}

	public void gleiten() {
		this.aktSchub = 0;
	}

	/**
	 * 
	 * @param richtung:
	 *            Genordete Richtung zur Zielausrichtung
	 */
	private void calcAusrichtung(long frameDauer) {

		double zeitAnteil = frameDauer / 1000.0;
		double wahreWendigkeit = wendigkeit / masse;

		if (autoPilot) {
			double differenz = zielAusrichtung - ausrichtung;
			if (Math.abs(differenz) < rotSpeed * zeitAnteil && rotSpeed < wendigkeit) {
				rotSpeed = 0;
				ausrichtung = zielAusrichtung;
			} else {
				if (Math.abs(differenz) - (0 * wendigkeit * zeitAnteil) > ((rotSpeed * rotSpeed) / (2 * wahreWendigkeit))) {
					rotSpeed += wahreWendigkeit * zeitAnteil * Math.signum(differenz);
				} else {
					rotSpeed -= wahreWendigkeit * zeitAnteil * Math.signum(differenz);
				}
			}
		} else {
			if (lenkRichtung != 0) {
				rotSpeed += wahreWendigkeit * zeitAnteil * Math.signum(lenkRichtung);
			} else {
				if (Math.abs(rotSpeed) > wahreWendigkeit * zeitAnteil) {
					if (rotSpeed > 0) {
						rotSpeed -= wahreWendigkeit * zeitAnteil;
					} else {
						rotSpeed += wahreWendigkeit * zeitAnteil;
					}
				} else {
					rotSpeed = 0;
				}
			}
		}
		ausrichtung += rotSpeed * zeitAnteil;

	}

	public void schiesseAufPosition(int x, int y) {
		for (Laser l : lasers) {
			l.schiesseAufPosition(x, y);
		}
	}

	public List<Laser> getLaser() {
		return lasers;
	}

	public List<Antrieb> getAntriebe() {
		return antriebe;
	}

	@Override
	public boolean isFixiert() {
		return false;
	}

	@Override
	public void draw(Graphics2D g2d) {

		AffineTransform at = new AffineTransform();
		at.translate(position.getX() - radius, position.getY() - radius);
		at.rotate(-ausrichtung, radius, radius);
		g2d.drawImage(bild, at, null);
		
		super.draw(g2d);
	}

	@Override
	public void setSpeedNachKollision(Koordinate3D neuePos) {
		kollisionSpeedAnteil = 1;
		neuePos.substract(speed);
		kollisionsSpeed.setPosition(neuePos);
	}

}
