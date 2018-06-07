package spacefight.schiff;

import java.awt.Color;
import java.util.List;

import spacefight.Traeger;
import topview.pbLib.Koordinate3D;

public class Schiff extends Traeger {

	private double zielAusrichtung;
	private String bezeichnung;
	private double wendigkeit;

	private double rotSpeed; // rad pro Sekunde

	public Schiff(Koordinate3D position, String bezeichnung, double radius, double wendigkeit, double huelle,
			double masse, List<SystemBucht> buchten, Color teamFarbe) {
		super(position, radius, buchten, huelle, masse, Color.GRAY, teamFarbe);

		this.bezeichnung = bezeichnung;
		this.wendigkeit = wendigkeit;
		this.zielAusrichtung = 2.0;
	}

	@Override
	public void update(long frameDauer) {
		lenken(frameDauer);
		super.update(frameDauer);
	}

	/**
	 * 
	 * @param richtung in rad
	 */
	public void setZielAusrichtung(double richtung) {
		this.zielAusrichtung = richtung;
	}

	public void calcWendigkeit() {
		// TODO: calcWendigkeit abhaengig von Schiffsteuersystemen
	}

	public void beschleunigen() {
		// TODO: beschleunigen
	}

	public void bremsen() {
		// TODO: bremsen
	}

	/**
	 * 
	 * @param richtung: Genordete Richtung zur Zielausrichtung
	 */
	public void lenken(long frameDauer) {

		double zeitAnteil = frameDauer / 1000.0;
		double differenz = zielAusrichtung - ausrichtung;
		double wahreWendigkeit = wendigkeit / masse;

		// Ist
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
		ausrichtung += rotSpeed * zeitAnteil;

	}
}
