package topview.ctrl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import topview.game.TopViewGame;

public class TVKeyControl implements KeyListener {

	private TopViewGame tvg;
	private boolean[] keys;

	public TVKeyControl(TopViewGame tvg) {
		keys = new boolean[256];
		for (int i = 0; i < keys.length; i++) {
			keys[i] = false;
		}
		this.tvg = tvg;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
//		System.out.println(arg0.getKeyCode());
		if (arg0.getKeyCode() < keys.length) {
			keys[arg0.getKeyCode()] = true;
		}
		tvg.handleKeyInputs(keys);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyCode() < keys.length) {
			keys[arg0.getKeyCode()] = false;
		}
		tvg.handleKeyInputs(keys);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}
