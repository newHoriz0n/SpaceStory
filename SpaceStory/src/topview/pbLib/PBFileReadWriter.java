package topview.pbLib;

/**
 * Version 1.1 -> 20180311 -> FileReadWriter
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PBFileReadWriter {

	/**
	 * Returs lines in file
	 * 
	 * @param url
	 * @return
	 */
	public static List<String> getLines(String url) {

		List<String> lines = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(url));
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line.trim());
			}
			reader.close();
			return lines;
		} catch (Exception e) {
			System.err.format("Exception occurred trying to read '%s'.", url);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns files in folder
	 * 
	 * @param folderURL
	 * @return
	 */
	public static List<String> getFileURLsInFolder(String folderURL) {

		List<String> files = new ArrayList<String>();
		File test = new File(folderURL);

		File[] DIR = test.listFiles();
		for (int i = 0; i < DIR.length; i++) {
			if (!DIR[i].isDirectory()) {
				files.add(DIR[i].getName());
			}
		}

		return files;
	}

	public static void writeLinesToFile(List<String> lines, String fileURL) throws IOException {

		File f = new File(fileURL);
		f.createNewFile();
		FileWriter fw = new FileWriter(f, false);
		BufferedWriter bw = new BufferedWriter(fw);

		for (String s : lines) {
			bw.write(s);
			bw.write(System.getProperty("line.separator"));
		}

		bw.close();

	}

	public static List<String[]> getAttributeFromLine(String attributSeparator, String elementSeparator, String line) {

		List<String[]> attList = new ArrayList<>();
		String[] atts = line.split(attributSeparator);

		for (String s : atts) {
			String[] c = s.split(elementSeparator);
			attList.add(c);
		}

		return attList;

	}

}
