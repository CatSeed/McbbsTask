package cc.baka9.mcbbs.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public abstract class Config {
	public void load() {
		createFile();
		onLoad();
	}

	public abstract void onLoad();

	public abstract String getName();

	private File file;

	public File getFile() {
		return file;
	}

	public void createFile() {
		file = new File(new File("").getAbsolutePath(), getName());
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public List<String> read() {
		List<String> list = new ArrayList<>();

		try (FileInputStream fileInputStream = new FileInputStream(file);
				InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
			String text = null;
			while ((text = bufferedReader.readLine()) != null) {
				list.add(text);
			}
		} catch (Exception e) {

		}

		return list;
	}

	public void write(String text, boolean append) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, append))) {
			bw.write(text);
			bw.newLine();
		} catch (Exception e) {
		}
	}
}
