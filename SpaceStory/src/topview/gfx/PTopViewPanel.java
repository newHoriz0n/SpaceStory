package topview.gfx;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import topview.ctrl.TVMouseControl;
import topview.game.TopViewGame;

public class PTopViewPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TopViewGame tvg;

	private Color bgColor;

	private double relCenterX;
	private double relCenterY;

	private int centerX; // Absolute Position der Zentralen X Position
	private int centerY; // Absolute Position der Zentralen Y Position

	private boolean mouseControlSet;

	/**
	 * 
	 * @param tvg
	 * @param bgColor
	 * @param relCenterX
	 *            X Center Position relativ zur Bildgröße (1/3 = Links, 1/2 = Mitte,
	 *            2/3 = Rechts)
	 * @param relCenterY
	 *            Y Center Position relativ zur Bildgröße (1/3 = Oben, 1/2 = Mitte,
	 *            2/3 = Unten)
	 */
	public PTopViewPanel(TopViewGame tvg, Color bgColor, double relCenterX, double relCenterY) {
		this.tvg = tvg;
		this.bgColor = bgColor;

		this.relCenterX = relCenterX;
		this.relCenterY = relCenterY;

		PanelUpdateThread put = new PanelUpdateThread();
		put.start();
	}

	@Override
	public void paint(Graphics g) {
		if (!mouseControlSet) {
			this.centerX = (int) (getWidth() * relCenterX);
			this.centerY = (int) (getHeight() * relCenterY);
			TVMouseControl tvmc = new TVMouseControl(tvg, centerX, centerY);
			addMouseMotionListener(tvmc);
			addMouseListener(tvmc);
			addMouseWheelListener(tvmc);
			mouseControlSet = true;
		}

		// if (!bildFormatGesetzt) {
		// kmc.amSpielerAusrichten(new int[] { getWidth(), getHeight() });
		// }

		BufferedImage bufferedImage = new BufferedImage(2000, 1100, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bufferedImage.createGraphics();

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);

		g2d.setColor(bgColor);
		g2d.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
		updatePanel(g2d);

		Graphics2D g2dComponent = (Graphics2D) g;
		g2dComponent.drawImage(bufferedImage, null, 0, 0);
	}

	public void updatePanel(Graphics2D g2d) {
		tvg.draw(g2d, getWidth(), getHeight(), centerX, centerY);
	}

	class PanelUpdateThread extends Thread {

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				updateUI();
			}
		}

	}

}
