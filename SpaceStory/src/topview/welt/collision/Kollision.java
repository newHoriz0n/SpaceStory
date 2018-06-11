package topview.welt.collision;

import java.util.ArrayList;
import java.util.List;

import topview.pbLib.Koordinate3D;

public class Kollision {

	private static boolean computeCollision(Collidable c0, Collidable c1) {

		// l_n: Normalenvektor (Verbindung zwischen den beiden Kugelnn)
		double l_nx = c1.getPosition().getX() - c0.getPosition().getX();
		double l_ny = c1.getPosition().getY() - c0.getPosition().getY();

		// Abstand der beiden Kugeln
		double l_dist = Math.sqrt(l_nx * l_nx + l_ny * l_ny);

		// Kugeln kollidieren, wenn der Abstand kleiner gleich der Summe
		// der Radien beider Kugeln ist.
		if (l_dist <= c0.getRadius() + c1.getRadius()) {
			// Normalenvektor wird normalisiert: |l_n| = 1
			l_nx /= l_dist;
			l_ny /= l_dist;

			// Tangentialvektor (senkrecht zu Normalenvektor, zwischen beiden Kugeln)
			double l_tx = l_ny;
			double l_ty = -l_nx;

			// Summe der Massen beider Kugeln
			double l_sm1m2 = c0.getMasse() + c1.getMasse();

			// Überlappung der beiden Kugeln
			double l_overlap = (c0.getRadius() + c1.getRadius()) - l_dist;

			// Verschieben der beiden Kugeln entlang der Normalen,
			// so dass sie sich nicht mehr überlappen!

			double EPSILON = 0.1;

			c0.getTraeger().getPosition().setKoordinaten(
					c0.getTraeger().getPosition().getX() - l_nx * l_overlap * (c1.getMasse() / l_sm1m2) * (1 + EPSILON),
					c0.getTraeger().getPosition().getY() - l_ny * l_overlap * (c1.getMasse() / l_sm1m2) * (1 + EPSILON), 0);
			c1.getTraeger().getPosition().setKoordinaten(
					c1.getTraeger().getPosition().getX() + l_nx * l_overlap * (c0.getMasse() / l_sm1m2) * (1 + EPSILON),
					c1.getTraeger().getPosition().getY() + l_ny * l_overlap * (c0.getMasse() / l_sm1m2) * (1 + EPSILON), 0);

			// Zerlegung der Geschwindigkeitsvektoren in Normalen- und
			// Tangentialanteil: v=sn*n+st*t, wobei
			// v Vektor, n Normalenvektor, t Tagentialvektor und
			// sn, st zwei skalare Werte sind.
			// Es gilt: v*n = sn*(n*n)+st*(t*n) = sn, da t*n=0 und n*n = 1
			// Es gilt: v*t = sn*(n*t)+st*(t*t) = st, da t*n=0 und t*t = 1
			// Also ist: sn = v*n und st=v*t

			// Ball 1: Zerlegung des Geschwindigkeitsvektors in n- und t-Anteil
			double l_sn1 = l_nx * c0.getTraeger().getSpeed().getX() + l_ny * c0.getTraeger().getSpeed().getY();
			double l_st1 = l_tx * c0.getTraeger().getSpeed().getX() + l_ty * c0.getTraeger().getSpeed().getY();

			double l_n1x = l_nx * l_sn1; // Normalenvektor-Anteil von p_b1.vx
			double l_n1y = l_ny * l_sn1;

			double l_t1x = l_tx * l_st1; // Tangentialvektor-Anteil von p_b1.vx
			double l_t1y = l_ty * l_st1;

			// Ball 2: Zerlegung des Geschwindigkeitsvektors in n- und t-Anteil
			double l_sn2 = l_nx * c1.getTraeger().getSpeed().getX() + l_ny * c1.getTraeger().getSpeed().getY();
			double l_st2 = l_tx * c1.getTraeger().getSpeed().getX() + l_ty * c1.getTraeger().getSpeed().getY();

			double l_n2x = l_nx * l_sn2; // Normalenvektor-Anteil von p_b2.vx
			double l_n2y = l_ny * l_sn2;

			double l_t2x = l_tx * l_st2; // Tangentialvektor-Anteil von p_b2.vx
			double l_t2y = l_ty * l_st2;

			// Der Impulserhaltungssatz
			// m1*v1 + m2*v2 = m1*v1' + m2*v2'
			// (wobei m1, m2 = Massen der Körper
			// und v1, v2, v1', v2' die Geschwindigkeiten)
			// und der Energieerhaltsungssatz
			// 0,5*m1*v1² + 0,5*m2*v2² = 0,5*m1*v1'² + 0,5*m2*v2'²
			// führen nach einfachen mathematischen Umformungen zu
			// folgenden Beziehungen (für den eindimensionalen Fall):
			// v1' = 2*(m1*v1+m2*v2)/(m1+m2) - v1
			// v2' = 2*(m1*v1+m2*v2)/(m1+m2) - v2
			// 2*(m1*v1+m2*v2)/(m1+m2) ist die Geschwindigkeit des
			// gemeinsamen Schwerpunktes.
			// Im zweidimensionalen Fall gilt, dass die Kollision entlang
			// der Normalen erfolgt. Die tangentialen Anteile der der
			// Bewegungsrichtungen werden unverändert übernommen.

			double l_vspx = 2 * (c0.getMasse() * l_n1x + c1.getMasse() * l_n2x) / l_sm1m2;
			double l_vspy = 2 * (c0.getMasse() * l_n1y + c1.getMasse() * l_n2y) / l_sm1m2;

			if (!c0.getTraeger().isFixiert()) {
				c0.getTraeger().setSpeedNachKollision(new Koordinate3D(l_vspx - l_n1x + l_t1x, l_vspy - l_n1y + l_t1y, 0));
			}
			if (!c1.getTraeger().isFixiert()) {
				c1.getTraeger().setSpeedNachKollision(new Koordinate3D(l_vspx - l_n2x + l_t2x, l_vspy - l_n2y + l_t2y, 0));
			}
			return true;
		}

		return false;
	}

	public static boolean checkCollision(Collidable c0, Collidable c1) {
		List<Collidable> cs0 = new ArrayList<Collidable>();
		c0.getCollidableTeile(cs0);
		List<Collidable> cs1 = new ArrayList<Collidable>();
		c1.getCollidableTeile(cs1);

		boolean collision = false;
		for (Collidable ct0 : cs0) {
			for (Collidable ct1 : cs1) {
				if (computeCollision(ct0, ct1)) {
					collision = true;
				}
			}
		}
		return collision;

	}
}