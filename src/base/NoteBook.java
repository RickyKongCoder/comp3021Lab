package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoteBook {

	private ArrayList<Folder> folders;
	public NoteBook() {
		folders = new ArrayList<Folder>();
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
	
}
