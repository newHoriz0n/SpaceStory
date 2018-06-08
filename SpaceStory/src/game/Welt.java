package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import spacefight.Schuss;
import spacefight.WeltObjekt;
import spacefight.schiff.ESchiffTyp;
import spacefight.schiff.Laser;
import spacefight.schiff.Schiff;
import spacefight.schiff.SchiffDB;
import topview.gfx.Hintergrund;
import topview.pbLib.Koordinate3D;

public class Welt {

	private List<WeltObjekt> objekte;
	private List<Schuss> schuesse;

	private Hintergrund weltall;

	public Welt() {

		objekte = new ArrayList<>();
		schuesse = new ArrayList<>();

		objekte.add(SchiffDB.createSchiffVonPrototyp(ESchiffTyp.Bomber, new Koordinate3D(), Color.GREEN));

		weltall = new Hintergrund();
		weltall.addHintergrundBild(1, -2000, -2000, "gfx/hintergrund/weltraum1.jpg");	
		
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
			try {
				s.draw(g2d);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public WeltObjekt getSpieler() {
		return objekte.get(0);
	}

	public void drawHintergrund(Graphics2D g2d, int centerX, int centerY) {
		weltall.drawHintergrund(g2d, getSpieler().getPosition().getX(), getSpieler().getPosition().getY(), centerX,
				centerY);
	}

}
