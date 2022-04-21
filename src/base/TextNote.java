package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
public class TextNote extends Note {

	public String content;
	String getTextFromFile(String absolutePath) {
		String result="";
		FileInputStream fis=null;
		try {
		fis = new FileInputStream(absolutePath);
		int i=0;
		while((i=fis.read())!=-1) {
			result+=String.valueOf((char)i);
		}
		}catch(Exception e) {
			System.out.println("Error reading text file.");
		}
		return result;
		
	}
	public void exportTextToFile(String pathFolder) {
	
		if(pathFolder.compareTo("")==0) {
			pathFolder =".";
		}
		File file = new File(pathFolder+File.separator+"COMP30213021_syllabus"+".txt");
		FileWriter fw =null;
		try {	
		file.createNewFile();
		fw =  new FileWriter(file);
		fw.write(content);
		fw.close();
		}
		catch(Exception e) {
		e.printStackTrace();
		}
		return;
		
	}
	public TextNote(String title) {
	super(title);

	}
	
	public TextNote(File f) {
		super(f.getName());
		this.content =  getTextFromFile(f.getAbsolutePath());
		
	}
	public TextNote(String title, String content) {
		super(title);
		this.content = content;
	}

}


