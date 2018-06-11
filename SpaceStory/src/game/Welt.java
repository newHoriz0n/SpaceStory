package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import spacefight.Schuss;
import spacefight.Zerstoerbares;
import spacefight.schiff.ESchiffTyp;
import spacefight.schiff.Schiff;
import spacefight.schiff.SchiffDB;
import spacefight.schiff.systeme.Antrieb;
import spacefight.schiff.systeme.AntriebsStoss;
import spacefight.schiff.systeme.Laser;
import topview.gfx.Hintergrund;
import topview.pbLib.Koordinate3D;
import topview.welt.WeltObjekt;
import topview.welt.collision.Collidable;
import topview.welt.collision.Kollision;

public class Welt {

	private List<WeltObjekt> objekte;
	private List<Schuss> schuesse;
	private List<AntriebsStoss> antriebstoesse;

	private Hintergrund weltall;

	public Welt() {

		objekte = new ArrayList<>();
		schuesse = new ArrayList<>();
		antriebstoesse = new ArrayList<>();

		// Spielerschiff
		objekte.add(SchiffDB.createSchiffVonPrototyp(ESchiffTyp.Kreuzer, new Koordinate3D(), Color.GREEN));
		objekte.add(SchiffDB.createSchiffVonPrototyp(ESchiffTyp.Jaeger, new Koordinate3D(500, 00, 0), Color.GREEN));

		// andre

		 weltall = new Hintergrund();
//		 weltall.addHintergrundBild(1, -2000, -2000, "gfx/hintergrund/weltraum1.jpg");

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
				for (Antrieb a : ((Schiff) w).getAntriebe()) {
					AntriebsStoss aS = a.getNeuerAntriebStoss();
					if (aS != null) {
						antriebstoesse.add(aS);
					}
				}
			}
		}

		for (WeltObjekt w1 : objekte) {
			for (WeltObjekt w2 : objekte) {
				if (!w1.equals(w2)) {
					if (w1 instanceof Collidable && w2 instanceof Collidable) {
						Kollision.checkCollision((Collidable) w1, (Collidable) w2);
					}
				}
			}
		}

		for (int i = schuesse.size() - 1; i >= 0; i--) {
			if (schuesse.get(i).fliegt()) {
				schuesse.get(i).update(frameDauer);
				// Check Treffer
				if (schuesse.get(i).isScharf()) {
					for (WeltObjekt w : objekte) {
						if (w instanceof Zerstoerbares) {
							if (Zerstoerbares.checkGetroffen(schuesse.get(i).getPosition(), w.getPosition(), ((Zerstoerbares) w).getRadius(),
									schuesse.get(i).getRadius())) {
								// Schaden
								((Zerstoerbares) w).getroffenWerden(schuesse.get(i).getEnergie());
								// Schuss löschen
								schuesse.remove(i);
								// Aus Schleife raus
								break;
							}
						}
					}
				}
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

		for (int i = antriebstoesse.size() - 1; i >= 0; i--) {
			if (antriebstoesse.get(i).getLifeTime() > 400) {
				antriebstoesse.remove(i);
			} else {
				antriebstoesse.get(i).draw(g2d);
			}
		}
	}

	public WeltObjekt getSpieler() {
		return objekte.get(0);
	}

	public void drawHintergrund(Graphics2D g2d, int centerX, int centerY) {
		 weltall.drawHintergrund(g2d, getSpieler().getPosition().getX(),
		 getSpieler().getPosition().getY(), centerX,
		 centerY);
	}

}
