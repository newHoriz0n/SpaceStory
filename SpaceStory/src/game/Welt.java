package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import spacefight.WeltObjekt;
import topview.pbLib.Koordinate3D;

public class Welt {

	private List<WeltObjekt> objekte;

	public Welt() {

		objekte = new ArrayList<>();

		objekte.add(SchiffDB.createSchiffVonPrototyp(ESchiffTyp.Bomber, new Koordinate3D(), Color.GREEN));

	}

	public void updateWelt(long frameDauer) {

		for (WeltObjekt w : objekte) {
			w.update(frameDauer);
		}

	}

	public void drawWelt(Graphics2D g2d) {
		for (WeltObjekt w : objekte) {
			w.draw(g2d);
		}
	}

	public WeltObjekt getSpieler() {
		return objekte.get(0);
	}

}
