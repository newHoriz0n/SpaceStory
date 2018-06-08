package topview.pbLib;

public class Koordinate3D {
	private double x;
	private double y;
	private double z;

	public Koordinate3D() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public Koordinate3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Koordinate3D(double XYrichtung, double laenge) {
		this.x = Math.cos(XYrichtung) * laenge;
		this.y = -Math.sin(XYrichtung) * laenge;
		this.z = 0;
	}

	public Koordinate3D(Koordinate3D bezug, Koordinate3D neu) {
		this.x = neu.getX() - bezug.getX();
		this.y = neu.getY() - bezug.getY();
		this.z = neu.getZ() - bezug.getZ();
	}

	public Koordinate3D(Koordinate3D copy) {
		this.x = copy.getX();
		this.y = copy.getY();
		this.z = copy.getZ();
	}

	/**
	 * @param string
	 *            Koordinate im Format x,y,z
	 */
	public Koordinate3D(String string) {
		String[] infos = string.split(",");
		this.x = Double.parseDouble(infos[0]);
		this.y = Double.parseDouble(infos[1]);
		this.z = Double.parseDouble(infos[2]);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public void setX(double d) {
		this.x = d;
	}

	public void setY(double d) {
		this.y = d;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void add(Koordinate3D summand) {
		this.x += summand.getX();
		this.y += summand.getY();
		this.z += summand.getZ();
	}

	public void scale(double d) {
		this.x *= d;
		this.y *= d;
		this.z *= d;
	}

	public double getXYVektorRichtung() {

		double angle = 0;
		if (x > 0) {
			angle = Math.atan(y / x);
		} else {
			if (x < 0) {
				angle = Math.PI - Math.atan(y / -x);
			}
		}

		return -angle;
	}

	public double getVektorLaenge() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	public String toString() {
		return "" + x + "," + y + "," + z;
	}

	public int getIntX() {
		return (int) x;
	}

	public int getIntY() {
		return (int) y;
	}

	public int getIntZ() {
		return (int) z;
	}

	public void setPosition(Koordinate3D neuePos) {
		this.x = neuePos.getX();
		this.y = neuePos.getY();
		this.z = neuePos.getZ();
	}

	public void setKoordinaten(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void normieren() {
		double laenge = getVektorLaenge();
		this.x = x / laenge;
		this.y = y / laenge;
		this.z = z / laenge;
	}

	public static double getDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Koordinate3D other = (Koordinate3D) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}

}