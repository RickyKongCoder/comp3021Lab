package base;
import java.util.*;
import base.Note;
public class Folder {

	private ArrayList<Note> notes;
	private String name;
	public Folder(String name) {
		notes = new  ArrayList<Note>();
		this.name = name;
		
	}
	public void addNote(Note n) {
	   notes.add(n);	 
	}
	public String getName() {
		return name;
	}
	public ArrayList<Note> getNotes(){
		return notes;
	}
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;//check the class above
		Folder other = (Folder) obj;
		return Objects.equals(name, other.name);
	}
	
	@Override
	public String toString() {
		int nText=0;
		int nImage=0;
		for(int i=0;i<notes.size();i++) {
		 if( notes.get(i) instanceof ImageNote) { 
		 	nImage++;
		 }else {
			 nText++;
		 }
		}
		return name +":"+nText+":"+nImage;
	}
	
}
