package spacefight.schiff;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import topview.pbLib.Koordinate3D;

public class SchiffDB {

	private static HashMap<ESchiffTyp, SchiffPrototyp> prototypen = loadPrototypen();

	private static HashMap<ESchiffTyp, SchiffPrototyp> loadPrototypen() {

		HashMap<ESchiffTyp, SchiffPrototyp> prototypen = new HashMap<>();

		loadJaeger(prototypen);
		LoadKreuzer(prototypen);
		LoadBomber(prototypen);
		return prototypen;

	}

	private static void LoadBomber(HashMap<ESchiffTyp, SchiffPrototyp> prototypen2) {
		List<SystemBucht> buchten = new ArrayList<>();

		buchten.add(new Steuerung(3, 4, 0));
		buchten.add(new Antrieb(2, 3, Math.PI - 0.35));
		buchten.add(new Antrieb(2, 3, Math.PI - 0.65));
		buchten.add(new Antrieb(2, 3, Math.PI + 0.35));
		buchten.add(new Antrieb(2, 3, Math.PI + 0.65));

		buchten.add(new Laser(3, 4, 1));
		buchten.add(new Laser(3, 4, -1));
		buchten.add(new Laser(3, 3, Math.PI - 1));
		buchten.add(new Laser(3, 3, Math.PI + 1));
		buchten.add(new Laser(2, 3, Math.PI));

		buchten.add(new Schild(2, 2, Math.PI / 2));
		buchten.add(new Schild(2, 2, -Math.PI / 2));

		SchiffPrototyp sp = new SchiffPrototyp("Bomber", buchten, 10, 50, 2500);

		prototypen2.put(ESchiffTyp.Bomber, sp);
	}

	private static void LoadKreuzer(HashMap<ESchiffTyp, SchiffPrototyp> prototypen2) {
		List<SystemBucht> buchten = new ArrayList<>();

		buchten.add(new Steuerung(3, 3, 0));
		buchten.add(new Antrieb(2, 3, Math.PI));
		buchten.add(new Antrieb(2, 3, Math.PI - 0.5));
		buchten.add(new Antrieb(2, 3, Math.PI + 0.5));
		buchten.add(new Laser(2, 3, 1));
		buchten.add(new Laser(2, 3, -1));
		buchten.add(new Laser(2, 2, Math.PI - 1));
		buchten.add(new Laser(2, 2, Math.PI + 1));
		buchten.add(new Schild(2, 2, Math.PI / 2));
		buchten.add(new Schild(2, 2, -Math.PI / 2));

		SchiffPrototyp sp = new SchiffPrototyp("Kreuzer", buchten, 3, 25, 500);

		prototypen2.put(ESchiffTyp.Kreuzer, sp);
	}

	private static void loadJaeger(HashMap<ESchiffTyp, SchiffPrototyp> prototypen) {

		List<SystemBucht> buchten = new ArrayList<>();

		buchten.add(new Steuerung(2, 2, 0));
		buchten.add(new Antrieb(0, 1, Math.PI));
		buchten.add(new Antrieb(2, 2, Math.PI - 0.5));
		buchten.add(new Antrieb(2, 2, Math.PI + 0.5));
		buchten.add(new Laser(1, 2, 1));
		buchten.add(new Laser(1, 2, -1));
		buchten.add(new Schild(1, 1, Math.PI - 1));
		buchten.add(new Schild(1, 1, Math.PI + 1));

		SchiffPrototyp sp = new SchiffPrototyp("Jäger", buchten, 0.8, 15, 100);

		prototypen.put(ESchiffTyp.Jaeger, sp);

	}

	public static Schiff createSchiffVonPrototyp(ESchiffTyp typ, Koordinate3D position, Color teamFarbe) {

		if (prototypen.containsKey(typ)) {
			return new Schiff(position, prototypen.get(typ).getBezeichnung(), prototypen.get(typ).getRadius(), prototypen.get(typ).getHuelle(),
					prototypen.get(typ).getMasse(), prototypen.get(typ).getBuchten(), teamFarbe);
		} else {
			throw new IllegalArgumentException();
		}

	}

}
