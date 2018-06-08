package topview.pbLib.collision;

import java.util.List;

import topview.pbLib.Koordinate3D;

public interface Collidable {

	public Koordinate3D getPosition();
	
	public Koordinate3D getSpeed();

	public double getRadius();

	public double getMasse();

	public int getTeam();
	
	public boolean isFixiert();
	
	public Collidable getTraeger();
	
	
	/**
	 * Rekursiv alle Koerperteile holen
	 * 
	 * @param teile
	 */
	public void getCollidableTeile(List<Collidable> cTeile);

}
