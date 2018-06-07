package topview.ctrl;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

import topview.game.TopViewGame;
import topview.pbLib.Koordinate3D;

public class TVMouseControl implements MouseMotionListener, MouseListener, MouseWheelListener {

	private int mausX;
	private int mausY;

	private long rechteDrueckzeit;
	private long linkeDrueckzeit;

	private int centerX;
	private int centerY;

	private TopViewGame tvg;

	private List<Integer> dragXs;
	private List<Integer> dragYs;

	private long lastDragPosTime;

	public TVMouseControl(TopViewGame tvg, int centerX, int centerY) {
		this.tvg = tvg;
		this.centerX = centerX;
		this.centerY = centerY;
		this.dragXs = new ArrayList<>();
		this.dragYs = new ArrayList<>();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int posX = e.getX() - centerX;
		int posY = e.getY() - centerY;
		double dist = Double.MAX_VALUE;
		if (dragXs.size() > 0) {
			dist = Koordinate3D.getDistance(posX, posY, dragXs.get(dragXs.size() - 1), dragYs.get(dragYs.size() - 1));
		}
		if (System.currentTimeMillis() > lastDragPosTime + 30 && dist > 20 && dragXs.size() < 5) {
			dragXs.add(posX);
			dragYs.add(posY);
			lastDragPosTime = System.currentTimeMillis();
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		this.mausX = arg0.getX();
		this.mausY = arg0.getY();

		calcMausAusrichtung();

	}

	private void calcMausAusrichtung() {
		Koordinate3D dif = new Koordinate3D(new Koordinate3D(centerX, centerY, 0), new Koordinate3D(mausX, mausY, 0));
		tvg.setMausRichtungAusrichtung(dif.getXYVektorRichtung());

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		resetDrag();

		int posX = e.getX() - centerX;
		int posY = e.getY() - centerY;
		dragXs.add(posX);
		dragYs.add(posY);
		lastDragPosTime = System.currentTimeMillis();

		if (e.getButton() == 1) {
			linkeDrueckzeit = System.currentTimeMillis();
		}
		if (e.getButton() == 3) {
			rechteDrueckzeit = System.currentTimeMillis();
		}
	}

	private void resetDrag() {
		this.dragXs.clear();
		this.dragYs.clear();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == 1) {
			tvg.handleMouseRelease(dragXs, dragYs, 1, linkeDrueckzeit);
		}
		if (e.getButton() == 3) {
			tvg.handleMouseRelease(dragXs, dragYs, 3, rechteDrueckzeit);
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		tvg.handleMouseWheelAktion(-arg0.getWheelRotation());
	}

}
