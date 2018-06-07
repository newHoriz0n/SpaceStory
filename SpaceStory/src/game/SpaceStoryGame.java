package game;

import java.awt.Graphics2D;
import java.util.List;

import spacefight.schiff.Schiff;
import topview.exe.TVExe;
import topview.game.TopViewGame;

public class SpaceStoryGame extends TopViewGame {

	private Welt welt;

	private Spieler spieler;

	public SpaceStoryGame() {

		super();
		this.welt = new Welt();

		this.spieler = new Spieler();
		this.spieler.setSchiff((Schiff) welt.getSpieler());

		startGame();
	}

	public static void main(String[] args) {
		SpaceStoryGame ssg = new SpaceStoryGame();
		TVExe tvge = new TVExe(ssg, 0.5, 0.5);
		tvge.requestFocus();
	}

	@Override
	public void update(long frameDauer) {
		welt.updateWelt(frameDauer);
	}

	@Override
	protected void drawWelt(Graphics2D g2d) {
		welt.drawWelt(g2d);
	}

	@Override
	protected void drawHUD(Graphics2D g2d, int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void drawSpielerAktionsHilfen(Graphics2D g2d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleMouseRelease(List<Integer> dragXs, List<Integer> dragYs, int taste, long linkeDrueckzeit) {
		// TODO Auto-generated method stub

		spieler.schussAufZielPosition(dragXs.get(dragXs.size() - 1), dragYs.get(dragYs.size() - 1));

	}

	@Override
	public void handleMouseWheelAktion(int i) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSpielerPosition() {
		spielerX = spieler.getSpielerPosition().getX();
		spielerY = spieler.getSpielerPosition().getY();
	}

	@Override
	public void handleKeyInputs(boolean[] keys) {
		if (keys[87]) {
			// vorwärts

		} else if (keys[83]) {
			// rückwärts
		} else {
			// stoppen

		}

		if (keys[65]) {
			// links
			spieler.setLenkrichtung(1);
		} else if (keys[68]) {
			// rechts
			spieler.setLenkrichtung(-1);
		} else {
			// geradeaus
			spieler.setLenkrichtung(0);
		}

	}

}
