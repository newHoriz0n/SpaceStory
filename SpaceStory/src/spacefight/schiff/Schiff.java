package spacefight.schiff;

import java.awt.Color;
import java.util.List;

import spacefight.Traeger;
import topview.pbLib.Koordinate3D;

public class Schiff extends Traeger {

	private String bezeichnung;
	private double wendigkeit;

	public Schiff(Koordinate3D position, String bezeichnung, double radius, double wendigkeit, double huelle, double masse, List<SystemBucht> buchten,
			Color teamFarbe) {
		super(position, radius, buchten, huelle, masse, Color.GRAY, teamFarbe);
		
		this.bezeichnung = bezeichnung;
		this.wendigkeit = wendigkeit;
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
	 * @param richtung:
	 *            Genordete Richtung zur Zielausrichtung
	 */
	public void lenken(double richtung) {
		// TODO: lenken
	}
}
