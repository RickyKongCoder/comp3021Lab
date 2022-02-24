package base;

import java.util.ArrayList;

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
	public ArrayList<Folder> getFolders(){
		return  folders;
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
