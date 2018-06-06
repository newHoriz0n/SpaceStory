package topview.pbLib;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;

public class PBSoundEngine {

	public static void playClip(String url, float lautstaerke) {
		File yourFile = new File(url);
		AudioInputStream stream;
		AudioFormat format;
		DataLine.Info info;
		Clip clip;

		try {
			stream = AudioSystem.getAudioInputStream(yourFile);
			format = stream.getFormat();
			info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			float range = volume.getMaximum() - volume.getMinimum();
			float gain = (range * lautstaerke) + volume.getMinimum();
			volume.setValue(gain);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void generateWaveFromString(String s) {
		for (int i = 0; i < s.length(); i++) {
			generateWaveFromChar(s.charAt(i));
		}
	}

	public static void generateWaveFromChar(Character c) {
		double f = 20 * (double) c;
		if (c == ' ') {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			playTone(f);
		}
	}

	private static byte[] getSinusTone(double frequency, AudioFormat af) {
		byte sample_size = (byte) (af.getSampleSizeInBits() / 8);
		byte[] data = new byte[(int) af.getSampleRate() * sample_size];
		double step_width = (2 * Math.PI) / af.getSampleRate();
		double x = 0;

		for (int i = 0; i < data.length; i += sample_size) {
			int sample_max_value = (int) Math.pow(2, af.getSampleSizeInBits()) / 2 - 1;
			int value = (int) (sample_max_value * Math.sin(frequency * x));
			for (int j = 0; j < sample_size; j++) {
				byte sample_byte = (byte) ((value >> (8 * j)) & 0xff);
				data[i + j] = sample_byte;
			}
			x += step_width;
		}
		return data;
	}

	private static void playTone(double frequenzy) {
		AudioFormat af = new AudioFormat(44100, 16, 1, true, false);
		byte[] data = getSinusTone(frequenzy, af);

		try {
			Clip c = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));

			c.open(af, data, 0, data.length);
			c.start();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			c.stop();

		} catch (LineUnavailableException ex) {
			ex.printStackTrace();
		}
	}

	class StringPlayThread extends Thread {
		
		public StringPlayThread(String string) {
			generateWaveFromString(string);
		}
		
	}

}
