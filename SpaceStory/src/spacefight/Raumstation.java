package spacefight;

import java.awt.Color;
import java.util.List;

import spacefight.schiff.systeme.SystemBucht;
import topview.pbLib.Koordinate3D;

public class Raumstation extends Traeger {

	public Raumstation(Koordinate3D position, double radius, List<SystemBucht> buchten, double huelle, double masse, Color farbe, Color teamFarbe) {
		super(position, radius, buchten, huelle, masse, farbe, teamFarbe);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isFixiert() {
		return false;
	}

	@Override
	public void setSpeedNachKollision(Koordinate3D neueSpeed) {
		
	}

}
