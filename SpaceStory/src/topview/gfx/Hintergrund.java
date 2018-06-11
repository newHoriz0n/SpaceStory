package topview.gfx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Hintergrund {

	private List<HintergrundBild> bilder;
	
	public Hintergrund() {
		this.bilder = new ArrayList<>();
	}
	
	public void addHintergrundBild(int abstand, int offsetX, int offsetY, String URL) {
		bilder.add(new HintergrundBild(abstand, offsetX, offsetY, URL));
	}
	
	public void drawHintergrund(Graphics2D g2d, double viewPointX, double viewPointY, int centerX, int centerY) {
		for (HintergrundBild b : bilder) {
			b.drawHintegrundBild(g2d, viewPointX, viewPointY, centerX, centerY);
		}
	}
	
	class HintergrundBild {
		
		private double abstand;
		private BufferedImage bild;
		private int offsetX, offsetY;

		public HintergrundBild(int abstand, int offsetX, int offsetY, String URL) {

			this.abstand = abstand;
			this.offsetX = offsetX;
			this.offsetY = offsetY;

			try {
				bild = ImageIO.read(new File(URL));
			} catch (IOException e) {
			}

		}

		public void drawHintegrundBild(Graphics2D g2d, double viewPointX, double viewPointY, int centerX, int centerY) {
			g2d.drawImage(bild, (int) (offsetX + centerX - (viewPointX * (1.0 / abstand))),
					(int) (offsetY + centerY - (viewPointY * (1.0 / abstand))), null);
		}
		
		
	}
}
