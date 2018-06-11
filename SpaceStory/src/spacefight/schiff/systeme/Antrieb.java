package spacefight.schiff.systeme;

import topview.pbLib.Koordinate3D;

public class Antrieb extends SystemBucht {

	private long lastAntriebstossZeit;
	private AntriebsStoss neuerAntriebStoss;

	public Antrieb(int aktStufe, int maxStufe, double relPosition) {
		super(ESystemTyp.Antrieb, aktStufe, maxStufe, relPosition);
	}

	@Override
	public void update(long frameDauer) {

		super.update(frameDauer);
		
		if (System.currentTimeMillis() - lastAntriebstossZeit > 100) {
			this.neuerAntriebStoss = new AntriebsStoss(aktAbsolutePosition, Math.pow(2, aktStufe));
			lastAntriebstossZeit = System.currentTimeMillis();
		}

	}

	public AntriebsStoss getNeuerAntriebStoss() {
		if (neuerAntriebStoss != null) {
			AntriebsStoss a = new AntriebsStoss(neuerAntriebStoss);
			neuerAntriebStoss = null;
			return a;
		}
		return null;
	}

	public double getAntriebsKraft() {
		return Math.pow(aktStufe, 2) * 10;
	}

}
