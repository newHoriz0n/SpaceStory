package spacefight.schiff;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import spacefight.Traeger;
import topview.pbLib.Koordinate3D;

public class Schiff extends Traeger {

	private boolean autoPilot;
	private double zielAusrichtung;
	private double lenkRichtung;
	private double aktSchub;

	private String bezeichnung;
	private double wendigkeit;
	private double beschleunigung;
	private double radSpeed;

	private double rotSpeed; // rad pro Sekunde

	private List<Laser> lasers;

	public Schiff(Koordinate3D position, String bezeichnung, double radius, double huelle, double masse,
			List<SystemBucht> buchten, Color teamFarbe) {
		super(position, radius, buchten, huelle, masse, Color.GRAY, teamFarbe);

		this.bezeichnung = bezeichnung;
		this.zielAusrichtung = 2.0;

		this.autoPilot = false;
		this.lenkRichtung = 0;

		calcWendigkeit();
		calcAntriebskraft();
		ladeWaffen();

	}

	private void ladeWaffen() {

		this.lasers = new ArrayList<>();

		for (SystemBucht s : systemBuchten) {
			if (s.getSystemTyp().equals(ESystemTyp.Laser)) {
				lasers.add((Laser) s);
			}
		}

	}

	@Override
	public void update(long frameDauer) {

		calcAusrichtung(frameDauer);
		calcSpeed(frameDauer);
		super.update(frameDauer);
	}

	private void calcSpeed(long frameDauer) {
		radSpeed += aktSchub * beschleunigung * (frameDauer / 1000.0);
		setSpeed(new Koordinate3D(ausrichtung, radSpeed));
	}

	/**
	 * 
	 * @param richtung in rad
	 */
	public void setZielAusrichtung(double richtung) {
		this.zielAusrichtung = richtung;
	}

	public void steuern(double richtung) {
		this.lenkRichtung = richtung;
	}

	private void calcAntriebskraft() {
		double aktBeschl = 0;

		for (SystemBucht s : systemBuchten) {
			if (s.getSystemTyp().equals(ESystemTyp.Antrieb)) {
				aktBeschl += ((Antrieb) s).getAntriebsKraft();
			}
		}

		beschleunigung = aktBeschl / masse;
	}

	public void calcWendigkeit() {

		double aktWend = 0;

		for (SystemBucht s : systemBuchten) {
			if (s.getSystemTyp().equals(ESystemTyp.Steuerung)) {
				aktWend += ((Steuerung) s).getSteuerKraft();
			}
		}

		wendigkeit = aktWend;

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
	 * @param richtung: Genordete Richtung zur Zielausrichtung
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
				if (Math.abs(differenz)
						- (0 * wendigkeit * zeitAnteil) > ((rotSpeed * rotSpeed) / (2 * wahreWendigkeit))) {
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
}
