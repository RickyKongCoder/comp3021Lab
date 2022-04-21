package base;
import java.util.*;
import java.io.*;
import java.lang.*;
public class TableNote {
	/*
	 * 
	 * goals:
	 * load file into java
	 * 
	 * 
	 * */
	List<String> columns = null;
	List<List<String>> data = null;
	
   TableNote(String absolutepath){
    data=new ArrayList<List<String>>();  
    columns = new ArrayList<String>();
	this.loadTable(absolutepath); 
   }
   
   boolean loadTable(String name ) {
	   data.clear();
	   columns.clear();
	   FileReader frd = null;
	   BufferedReader bff = null;
	   try {
		  frd = new FileReader(name);
		  bff = new BufferedReader(frd);
		 int i=0;
		 String row = "";

		 while((row=bff.readLine())!=null) {
           if(row.length()<=0)
        	   continue;
			 if(i==0) {
			   String[] columnName = row.split(",",0);
			   for(String temp:columnName) {
				   columns.add(temp);
				   data.add(new ArrayList<String>());
			   }
			 }
			 else {
			  String[] entries = row.split(",",0);
			  for(int index=0;index<entries.length;index++) {
				  data.get(index).add(entries[index]);
			  }
			 }
			 
			 i++;
		 }
	   }
	   catch(Exception e) {
		   return false;
	   }
	   System.out.println("Resultant Table:");
	   for(String column:columns) {
		   System.out.print(column+"\t\t");
	   }
	   System.out.print("\n");
	  for(int j=0;j<data.size();j++) {
	   for(int i=0;i<columns.size();i++) {
	       System.out.print(data.get(i).get(j)+"\t\t");
	   }
	   System.out.print("\n");
	  }	   
	  return  true;
   }
	
   public boolean exportTable(String absolutepath) {
	   File f = null;
	   FileWriter fw = null;
	   try {
		   f = new File(absolutepath);
		   fw = new FileWriter(f);
		   f.createNewFile();
		   String result="";
		   for(int i=0;i<columns.size();i++)
			   result+=columns.get(i)+"\t";
		   result+="\n";
		   for(int j=0;j<data.size();j++)
		   {
			   for(int i=0;i<columns.size();i++) {
				   result+=data.get(i).get(j)+"\t";
			   }
			   result+="\n";
		   }
		   fw.write(result);
		   fw.flush();
		   fw.close();
	   }catch(Exception e) {
		   return  false;
	   }
			   
	   return true;
	   
   }

}
