package game;

import java.awt.Graphics2D;
import java.util.List;

import topview.exe.TVExe;
import topview.game.TopViewGame;

public class SpaceStoryGame extends TopViewGame {

	private Welt welt;

	public SpaceStoryGame() {

		super();
		this.welt = new Welt();

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
	public void handleMouseRelease(List<Integer> dragXs, List<Integer> dragYs, int i, long linkeDrueckzeit) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleMouseWheelAktion(int i) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSpielerPosition() {
		spielerX = welt.getSpieler().getPosition().getX();
		spielerY = welt.getSpieler().getPosition().getY();
	}

}
