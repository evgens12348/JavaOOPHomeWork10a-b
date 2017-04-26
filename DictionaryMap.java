package com.gmail.s12348.evgen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class DictionaryMap implements Serializable {

	private static final long serialVersionUID = 1L;
	HashMap<String, String> dist = new HashMap<>();

	public DictionaryMap() {
		super();
	}

	public DictionaryMap(HashMap<String, String> dist) {
		super();
		this.dist = dist;
	}

	public HashMap<String, String> getDist() {
		return dist;
	}

	public void setDist(HashMap<String, String> dist) {
		this.dist = dist;
	}

	public void fillingDictionary() {
		for (;;) {
			int s = JOptionPane.showConfirmDialog(null, "Do you want to add a word to the dictionary?");
			if (s == 0) {
				dist.put(enterEnglishWord(), enterUkrainianWord());
			} else {
				break;
			}
		}
	}

	public String enterEnglishWord() {
		String word = "";
		for (;;) {
			word = String.valueOf(JOptionPane.showInputDialog("Enter the English word."));
			if (word == "null") {
				JOptionPane.showMessageDialog(null, "Canseled set as default");
			}
			if (checkString(word) != true) {
				JOptionPane.showMessageDialog(null, "Input Error");
			} else {
				break;
			}

		}
		return word;
	}

	public String enterUkrainianWord() {
		String word = "";
		for (;;) {
			word = String.valueOf(JOptionPane.showInputDialog("Enter the Ukrainian translation."));
			if (word == "null") {
				JOptionPane.showMessageDialog(null, "You need to enter a translation");
				continue;
			}
			if (checkStringUkr(word) != true) {
				JOptionPane.showMessageDialog(null, "Input Error");
			} else {
				break;
			}

		}
		return word;
	}

	public boolean checkString(String string) {
		if (string.length() == 0) {
			return false;
		} else {
			Pattern p = Pattern.compile("^([a-zA-Z- ¹]+)");
			Matcher m = p.matcher(string);

			return m.matches();
		}
	}

	public boolean checkStringUkr(String string) {
		if (string.length() == 0) {
			return false;
		} else {
			Pattern p = Pattern.compile("^([à-ÿÀ-ß- ¹³²¿¯ªº`'[^¸¨ýÝûÛúÚ]]+)");
			Matcher m = p.matcher(string);

			return m.matches();
		}
	}

	public void saveToFile(File file) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
			oos.writeObject(dist);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public HashMap<String, String> loadFromFile(File file) {
		if (file.exists() == true) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
				dist = (HashMap<String, String>) ois.readObject();
			} catch (IOException | ClassNotFoundException e) {
				System.out.println(e);
			}
		}
		return dist;
	}
}
