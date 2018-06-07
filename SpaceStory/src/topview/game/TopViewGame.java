package topview.game;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.List;

public abstract class TopViewGame {

	@SuppressWarnings("unused")
	private double mausRichtung;
	@SuppressWarnings("unused")
	private boolean[] keys;
	
	protected double spielerX;
	protected double spielerY;

	TopViewUpdateThread tvut = new TopViewUpdateThread();
	
	public TopViewGame() {
		this.mausRichtung = 0;
		this.keys = new boolean[256];	
	}
	
	
	/**
	 * Startet den UpdateThread
	 */
	public void startGame() {
		tvut.start();
	}

	public abstract void update(long frameDauer);

	public void draw(Graphics2D g2d, int width, int height, int centerX, int centerY) {
		
		setSpielerPosition();
		
		drawHUD(g2d, width, height);

		g2d.setClip(0, 40, width, height - 40);
		AffineTransform af = new AffineTransform();
		af.translate(centerX - spielerX, centerY - spielerY);
		g2d.transform(af);

		drawWelt(g2d);

		drawSpielerAktionsHilfen(g2d);
	}

	/**
	 * Setze spielerX und spielerY
	 */
	public abstract void setSpielerPosition();

	protected abstract void drawWelt(Graphics2D g2d);

	protected abstract void drawHUD(Graphics2D g2d, int width, int height);

	protected abstract void drawSpielerAktionsHilfen(Graphics2D g2d);

	class TopViewUpdateThread extends Thread {

		private long lastUpdate;

		public TopViewUpdateThread() {
			lastUpdate = System.currentTimeMillis();
		}

		@Override
		public void run() {
			while (true) {
				try {
					long frameDauer = System.currentTimeMillis() - lastUpdate;
					update(frameDauer);
					lastUpdate = System.currentTimeMillis();
					sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	// **************** SPIELER Kontrolle *****************

	public void setMausRichtungAusrichtung(double richtung) {
		this.mausRichtung = richtung;
	}

	public void handleKeyInputs(boolean[] keys) {
		this.keys = keys;
	}

	public abstract void handleMouseRelease(List<Integer> dragXs, List<Integer> dragYs, int i, long linkeDrueckzeit);

	public abstract void handleMouseWheelAktion(int i);

}
