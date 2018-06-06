package topview.exe;

import java.awt.Color;

import javax.swing.JFrame;

import topview.ctrl.TVKeyControl;
import topview.game.TopViewGame;
import topview.gfx.PTopViewPanel;

public class TVExe extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TVExe(TopViewGame tvg, double relCenterX, double relCenterY) {

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUndecorated(true);
		setExtendedState(MAXIMIZED_BOTH);
		setVisible(true);

		addKeyListener(new TVKeyControl(tvg));

		add(new PTopViewPanel(tvg, Color.BLACK, relCenterX, relCenterY));
	}

}
