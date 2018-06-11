package spacefight;

import topview.pbLib.Koordinate3D;

public interface Zerstoerbares {

	public void getroffenWerden(double schaden);

	public boolean checkAmLeben();

	public double getRadius();
	
	public static boolean checkGetroffen(Koordinate3D schussPos, Koordinate3D eigenPos, double eigenRad, double schussRad) {
		Koordinate3D dif = new Koordinate3D(schussPos, eigenPos);
		return dif.getVektorLaenge() <= eigenRad + schussRad;
	}

}
