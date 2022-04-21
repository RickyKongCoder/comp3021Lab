package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.FileInputStream;
public class NoteBook implements Serializable{

	private ArrayList<Folder>  folders;
	public NoteBook() {
		folders = new ArrayList<Folder>();
	}
	public NoteBook(String file) {
		FileInputStream fis=null;
		ObjectInputStream in=null;		
		try {
		fis = new FileInputStream(file);
		in = new ObjectInputStream(fis);
	    NoteBook obj = (NoteBook) in.readObject();
	    this.folders = obj.folders;
	    in.close();
		}
		catch(Exception e){
			e.printStackTrace();	
		}
	}
	public boolean addFolder(String folderName) {
		for(Folder f:folders) {
			if(f.getName().compareTo(folderName)==0) {
				return false;
			}
		}
		Folder f = new Folder(folderName);
		folders.add(f);
		return true;
	}
	public boolean createTextNote(String folderName, String title ) {
		TextNote note = new TextNote(title);
		return insertNote(folderName, note);
	}
	public boolean createImageNote(String folderName , String title) {
		ImageNote note =   new ImageNote(title);
		return insertNote(folderName,note);
	}
	public boolean createTextNote(String folderName,String title, String content) {
		TextNote note  = new TextNote(title, content);
		return insertNote(folderName,note);
	}
	public ArrayList<Folder> getFolders(){
		return  folders;
	}
	public void sortFolders() {
		for(Folder folder: folders){
			folder.sortNotes();//reference? 
		}
		Collections.sort(folders);
	}
	public List<Note> searchNotes(String keywords){
	  ArrayList<Note> result= new ArrayList<Note>();
		for(Folder f:folders) {
		  for(Note n:f.searchNotes(keywords)) {
//			  System.out.println(n.getTitle());
			  result.add(n);
		  }
		 		
		}
	return result;
	  
	}
	
	public boolean insertNote(String folderName , Note note) {
		for(int i=0;i<folders.size();i++) {
		   if(folders.get(i).getName()==folderName) {
			   	for(Note n: folders.get(i).getNotes()) {
			   		if(n.equals(note)) {
			         System.out.println("Creating note "+ note.getTitle() + " under folder " + folderName + " failed");
			   			return false;
			   		}
			   	}
	   			folders.get(i).addNote(note);
			    return true;
		   }	
		}
		Folder book  = new Folder(folderName);
		book.addNote(note);
		folders.add(book);
		return true;
	}
	public boolean save(String file) {
		FileOutputStream fos=null;
		ObjectOutputStream out = null;
		try {
		fos =new  FileOutputStream(file);
		out =new ObjectOutputStream(fos);
		NoteBook obj = new NoteBook();
		obj.folders = this.folders;
		out.writeObject(obj);
		out.close();
		}
		catch(Exception e){
			e.printStackTrace();
;			return false;
		}
		return true;
		
		
	}
	
}
