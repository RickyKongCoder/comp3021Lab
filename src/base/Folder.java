package base;
import java.io.Serializable;
import java.util.*;
import base.Note;
public class Folder implements Comparable<Folder>,Serializable{

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
	public void sortNotes() {
		//TO DO 
	Collections.sort(notes);
	}
	public boolean removeNotes(String title) {
		   // TODO
		   // Given the title of the note, delete it from the folder.
		   // Return true if it is deleted successfully, otherwise return false. 
		   for(int i=0;i<notes.size();i++) {
			   if(notes.get(i).getTitle().compareTo(title)==0) {
				   notes.remove(i);
				   return true;
			   }
		   }
		   return false;
	}
	public List<Note> searchNotes(String keywords){
		
		ArrayList<Note> list = new ArrayList<Note>();//List is an interface for ArrayList so 
		//therefore can't not be initiated 
		ArrayList<String> temp = new ArrayList<String>(); 
		for(String w:keywords.split(" ",0)) {
           //if(w.compareTo("OR")!=0 && w.compareTo("or")!=0)
			temp.add(w);

    	}

		ArrayList<String> neccesary = new ArrayList<String>();
		ArrayList<ArrayList<String>> orArray = new ArrayList<ArrayList<String>>();
		boolean withOr = false;
	    ArrayList<String> groupOr = new ArrayList<String>();
		for(int i=0;i<temp.size()-1;i++) {
			if(temp.get(i).toLowerCase().compareTo("or")==0) {
			continue;
			}
			if(temp.get(i+1).compareTo("OR")==0 | temp.get(i+1).compareTo("or")==0 ) {
				if(withOr) {
			    groupOr.add(temp.get(i));

				}else {
				groupOr = new ArrayList<String>();
				groupOr.add(temp.get(i));

				}
			    withOr = true;
			}
			else {

				if(withOr) {
				groupOr.add(temp.get(i));
				orArray.add(groupOr);
				groupOr =null;
				}
				else {
					neccesary.add(temp.get(i));
				}
		    
				withOr =false;

			}
		}
		String last = "";
		if(temp.size()>=2) {
			last = temp.get(temp.size()-2);
		if(last.toLowerCase().compareTo("or")==0) {
			groupOr.add(temp.get(temp.size()-1));
		}
		else {
			neccesary.add(temp.get(temp.size()-1));
		}
		}
		if(temp.size()==1)
			neccesary.add(temp.get(temp.size()-1));
		if(!groupOr.isEmpty()) {
			orArray.add(groupOr);
		}
//		System.out.println(neccesary.toString());
//		System.out.println(orArray.toString());
	    for(Note note:notes)	
	    {
	    	boolean valid = true;
	    	for(int i=0;i<neccesary.size();i++) {
	    		if(note instanceof ImageNote) {
	    		if(!note.getTitle().toLowerCase().contains(neccesary.get(i).toLowerCase())) {
		    		System.out.println(note.getTitle());

	    			valid = false;
	    			break;
	    		}
	    		}else {
	    		if(!note.getTitle().toLowerCase().contains(neccesary.get(i).toLowerCase()) &&  !((TextNote)note).content.toLowerCase().contains(neccesary.get(i).toLowerCase())) {
		    			valid = false;
		    			break;
		    	}
	    		}
	    	}
	    	if(!valid)
	    		continue;
	    	for(int i=0;i<orArray.size();i++) {
	    		boolean subvalid =false;
	    		for(int j=0;j<orArray.get(i).size();j++) {
	    	    String target = orArray.get(i).get(j).toLowerCase();

	    		if(note instanceof ImageNote) {
		    		if(note.getTitle().toLowerCase().contains(target)) {
		    			subvalid = true;
			    		//System.out.println(target+" |"+note.getTitle());
		    			break;
		    		}
	    		}
		    	else if(note instanceof TextNote){
		    		//System.out.println( "Text:"+((TextNote)note).content.toLowerCase());
		    		if(note.getTitle().toLowerCase().contains(target) |  ((TextNote)note).content.toLowerCase().contains(target)) {
		    			subvalid = true;
			    		//System.out.println(target+"|"+note.getTitle());

		    			break;
		    		}		
		    	}
	    		}

	    		if(!subvalid) {
	    			valid =false;
	    			break;
	    		}
	    			
	    	} 	
	    	if(valid)
	    	{
	    		list.add(note);
	    		
	    	}
	    }
		
	    return  list;
		
		
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
	@Override
	public int compareTo(Folder f) {
		if(this.name.compareTo(f.name)<0) {
			return -1;
		}
		else if(this.name.compareTo(f.name)>0) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
}
