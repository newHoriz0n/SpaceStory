package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import spacefight.Schuss;
import spacefight.WeltObjekt;
import spacefight.schiff.Laser;
import spacefight.schiff.Schiff;
import topview.pbLib.Koordinate3D;

public class Welt {

	private List<WeltObjekt> objekte;
	private List<Schuss> schuesse;

	public Welt() {

		objekte = new ArrayList<>();
		schuesse = new ArrayList<>();

		objekte.add(SchiffDB.createSchiffVonPrototyp(ESchiffTyp.Kreuzer, new Koordinate3D(), Color.GREEN));

	}

	public void updateWelt(long frameDauer) {
		for (WeltObjekt w : objekte) {
			w.update(frameDauer);
			if (w.getClass().equals(Schiff.class)) {
				for (Laser l : ((Schiff) w).getLaser()) {
					Schuss s = l.getNeuerSchuss();
					if (s != null) {
						schuesse.add(s);
					}
				}
			}
		}

		for (int i = schuesse.size() - 1; i >= 0; i--) {
			if (schuesse.get(i).fliegt()) {
				schuesse.get(i).update(frameDauer);
			} else {
				schuesse.remove(i);
			}
		}

	}

	public void drawWelt(Graphics2D g2d) {
		for (WeltObjekt w : objekte) {
			w.draw(g2d);
		}
		for (Schuss s : schuesse) {
			s.draw(g2d);
		}
	}

	public WeltObjekt getSpieler() {
		return objekte.get(0);
	}

}
