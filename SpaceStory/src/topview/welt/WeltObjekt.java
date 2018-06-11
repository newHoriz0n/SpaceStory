package topview.welt;

import java.awt.Graphics2D;

import spacefight.Schuss;
import topview.pbLib.Koordinate3D;

public abstract class WeltObjekt{

	protected Koordinate3D position;

	public WeltObjekt(Koordinate3D pos) {
		this.position = new Koordinate3D(pos);
	}

	public abstract void draw(Graphics2D g2d);

	public abstract void update(long frameDauer);

	public Koordinate3D getPosition() {
		return position;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WeltObjekt other = (WeltObjekt) obj;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}
	
	
}
