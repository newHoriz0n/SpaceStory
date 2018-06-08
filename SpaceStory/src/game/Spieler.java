package game;

import spacefight.schiff.Schiff;
import topview.pbLib.Koordinate3D;

public class Spieler {

	private Schiff schiff;

	public Spieler() {

	}

	public void setSchiff(Schiff s) {
		this.schiff = s;
	}

	public Koordinate3D getSpielerPosition() {
		return schiff.getPosition();
	}

	public void setZielAusrichtung(double richtung) {
		schiff.setZielAusrichtung(richtung);
	}

	public void setLenkrichtung(int richtung) {
		schiff.steuern(richtung);
	}

	public void schussAufZielPosition(int x, int y) {
		schiff.schiesseAufPosition(x, y);
	}

	public void beschleunigen() {
		schiff.beschleunigen();
	}

	public void bremsen() {
		schiff.bremsen();
	}

	public void gleiten() {
		schiff.gleiten();
	}

	
	
}
