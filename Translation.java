package com.gmail.s12348.evgen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Translation implements Serializable {
	private static final long serialVersionUID = 1L;
	private DictionaryMap dm = new DictionaryMap();
	private File fileOne = new File("English.in");
	private File fileTwo = new File("Ukrainian.out");
	private String[] engText;
	private ArrayList<String> ukrText = new ArrayList<>();

	public Translation() {
		super();
	}

	public void readFileToArray() {
		String txt = "";
		try (BufferedReader bf = new BufferedReader(new FileReader(fileOne))) {
			while (true) {
				String s = bf.readLine();
				if (s == null) {
					break;
				}
				txt = txt + s;
			}
		} catch (IOException e) {
			System.out.println(e);
		}
		engText = txt.split(" ");
	}

	public void translate() {
		for (int i = 0; i < engText.length; i++) {
			if (dm.getDist().containsKey(engText[i])) {
				ukrText.add(dm.getDist().get(engText[i]));
			} else {
				JOptionPane.showMessageDialog(null, engText[i] + " This word is not in the dictionary.");
				dm.dist.put(engText[i], dm.enterUkrainianWord());
				ukrText.add(dm.getDist().get(engText[i]));
			}
		}
		
	}

	public void writeArrayToFile() {

		try (PrintWriter prwOne = new PrintWriter(fileTwo)) {
			if (fileTwo.exists() == false) {
				fileTwo.createNewFile();
			}
			for (String string : ukrText) {
				prwOne.print(string + " ");
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void print() {
		String txt = "Text translation: ";
		for (String string : ukrText) {
			txt = txt + string + " ";
		}
		System.out.println(txt);
	}

	public void startTranslate() {
		File file = new File("distmap");		
		dm.loadFromFile(file);
		dm.fillingDictionary();
		readFileToArray();
		translate();
		dm.saveToFile(file);
		writeArrayToFile();
		print();
	}
}
