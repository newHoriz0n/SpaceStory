package spacefight;

import java.awt.Graphics2D;

import topview.pbLib.Koordinate3D;

public abstract class WeltObjekt {

	protected Koordinate3D position;

	public WeltObjekt(Koordinate3D pos) {
		this.position = new Koordinate3D(pos);
	}

	public abstract void draw(Graphics2D g2d);

	public abstract void update(long frameDauer);

	public Koordinate3D getPosition() {
		return position;
	}

}
