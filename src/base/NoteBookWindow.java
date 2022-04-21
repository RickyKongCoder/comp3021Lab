package base;

import java.util.ArrayList;
import java.util.List;
import javafx.stage.FileChooser; 
import base.Folder;
import base.Note;
import base.NoteBook;
import base.TextNote;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.File;
import javafx.scene.control.ButtonType;
import java.util.Optional;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.control.TextInputDialog;

import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 
 * NoteBook GUI with JAVAFX
 * 
 * COMP 3021
 * 
 * 
 * @author valerio
 *
 */
public class NoteBookWindow extends Application {

	/**
	 * TextArea containing the note
	 */
	final TextArea textAreaNote = new TextArea("");
	/**
	 * list view showing the titles of the current folder
	 */
	final ListView<String> titleslistView = new ListView<String>();
	/**
	 * 
	 * Combobox for selecting the folder
	 * 
	 */
	final ComboBox<String> foldersComboBox = new ComboBox<String>();
	TextField tf;
	Button bt;
	Button buttonRemove;
	/**
	 * This is our Notebook object
	 */
	NoteBook noteBook = null;
	Folder currentFolderObj=null;
	/**
	 * current folder selected by the user
	 */
	Stage stage;
	String currentFolder = "";
	/**
	 * current search string
	 */
	String currentSearch = "";
	String currentNote= "";
	List<Note>notes=  null;
	private void updateTitleList() {
		  ArrayList<String> titles=  new ArrayList<String>();
		    System.out.println(currentFolderObj.toString());

		   if(currentFolderObj!=null) {
		    notes = currentFolderObj.getNotes();
		    for(Note n:notes) {
		    	titles.add(n.getTitle());
		    }
		   }
		   
			ObservableList<String> obList = FXCollections.observableArrayList(titles);
		    titleslistView.setItems(obList);
	}
	private void searchTitleList() {
		  ArrayList<String> titles=  new ArrayList<String>();
		    System.out.println(currentFolderObj.toString());

		   if(currentFolderObj!=null) {
		    notes = noteBook.searchNotes(currentSearch);
		    for(Note n:notes) {
		    	titles.add(n.getTitle());
		    }
		   }
		   
			ObservableList<String> obList = FXCollections.observableArrayList(titles);
		    titleslistView.setItems(obList);
	}
	public static void main(String[] args) {
		launch(NoteBookWindow.class, args);
	}

	@Override
	public void start(Stage stage) {
		loadNoteBook();
		// Use a border pane as the root for scene
		BorderPane border = new BorderPane();
		// add top, left and center
		border.setTop(addHBox());
		border.setLeft(addVBox());
		border.setCenter(addGridPane());
	
		Scene scene = new Scene(border);
		stage.setScene(scene);
		stage.setTitle("NoteBook COMP 3021");
		this.stage = stage;
		stage.show();
	}
	
	/**
	 * This create the top section
	 * 
	 * @return
	 */
	private HBox addHBox() {

		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10); // Gap between nodes

		Button buttonLoad = new Button("Load");
		buttonLoad.setPrefSize(100, 20);
		FileChooser fileChooser = new FileChooser();
		buttonLoad.setOnAction(
				(e)->{
fileChooser.setTitle("Please Choose As File with notebook object");
FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Serialized Object File(*.ser)", "*.ser");
fileChooser.getExtensionFilters().add(extFilter);
	File selectedFile	= fileChooser.showOpenDialog(stage);
	System.out.println(selectedFile.getName());
	if(selectedFile!=null) {
		loadNoteBook(selectedFile);
	}
	foldersComboBox.getItems().clear();
  	for(int i=0;i<noteBook.getFolders().size();i++) {
		foldersComboBox.getItems().add(noteBook.getFolders().get(i).getName());
	}
  	titleslistView.getItems().clear();
	});
//		buttonLoad.setDisable(false);
		Button buttonSave = new Button("Save");
		buttonSave.setPrefSize(100, 20);
		buttonSave.setOnAction((e)->{
		fileChooser.setTitle("Save File");
		FileChooser.ExtensionFilter extfilter = new FileChooser.ExtensionFilter("Serialized Object File(*.ser)", "*.ser");
		fileChooser.getExtensionFilters().add(extfilter)	;
		File selectedFile = fileChooser.showSaveDialog(stage);
		if(selectedFile!=null) {
		noteBook.save(selectedFile.getAbsolutePath());
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Successfully saved");
		alert.setContentText("You file has been saved to file " + selectedFile.getName());
		alert.showAndWait().ifPresent(rs -> {
		    if (rs == ButtonType.OK) {
		        System.out.println("Pressed OK.");
		    }
		});
		}
		});
//		buttonSave.setDisable(true);
		Text text1 = new Text(900, 20, "Search:");
		text1.setFont(Font.font("Courier", FontWeight.BLACK, 
		      FontPosture.REGULAR, 15));
		tf = new TextField();
	    tf.setAlignment(Pos.BOTTOM_RIGHT);
	    bt = new Button("Search", 
	    		   null);
	    bt.setOnAction((e)->{
	    currentSearch=tf.getText();
	    searchTitleList();
	    });
	    buttonRemove = new Button("Clear Search", 
	    		   null);
	    buttonRemove.setOnAction((e)->{
	    currentSearch="";
	    currentNote="";
	    currentFolder="";
	    tf.setText("");
		textAreaNote.setText("");
	    titleslistView.getItems().clear();
	    }); 
		hbox.getChildren().addAll(buttonLoad, buttonSave,text1,tf,bt,buttonRemove);

		return hbox;
	}

	/**
	 * this create the section on the left
	 * 
	 * @return
	 */
	private VBox addVBox() {

		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10)); // Set all sides to 10
		vbox.setSpacing(8); // Gap between nodes

		// TODO: This line is a fake folder list. We should display the folders in noteBook variable! Replace this with your implementation
		for(int i=0;i<noteBook.getFolders().size();i++) {
			foldersComboBox.getItems().add(noteBook.getFolders().get(i).getName());
		}

		foldersComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue ov, Object t, Object t1) {
				if(t1!=null)
				currentFolder = t1.toString();
				// this contains the name of the folder selected
				// TODO update listview
				updateListView();

			}

		});

		foldersComboBox.setValue("-----");

		titleslistView.setPrefHeight(100);

		titleslistView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue ov, Object t, Object t1) {
				if (t1 == null)
					return;
				String title = t1.toString();
				// This is the selected title
				// TODO load the content of the selected note in
				// textAreNote
				String content = "";
				currentNote  = title;
				System.out.println(currentNote);
				for(Note ntemp:currentFolderObj.getNotes()) {
					if(ntemp.getTitle().compareTo(titleslistView.getSelectionModel().getSelectedItem().toString())==0) {
						if(ntemp instanceof TextNote) {
							content=((TextNote)ntemp).content;
						}
					}

				}
				textAreaNote.setText(content);

			}
		});
		vbox.getChildren().add(new Label("Choose folder: "));
		HBox hbx2 = new HBox();
		Button addFolder = new Button("Add Folder");
		hbx2.getChildren().addAll(foldersComboBox,addFolder);
		hbx2.setSpacing(10); 

		vbox.getChildren().addAll(hbx2);

		addFolder.setOnAction((e)->{
			TextInputDialog dialog = new TextInputDialog("Add a Folder");
		    dialog.setTitle("Input");
		    dialog.setHeaderText("Add a new folder for your notebook:");
		    dialog.setContentText("Please enter the name you want to create:");
		    // Traditional way to get the response value.
		    Optional<String> result = dialog.showAndWait();
		    if (result.isPresent()){
		        // TODO 
		    	if(!noteBook.addFolder(result.get())) {
		    		Alert alert = new Alert(AlertType.WARNING);
		    		alert.setTitle("Warning");
		    		alert.setContentText("Please input valid folder name");
		    		alert.showAndWait().ifPresent(rs -> {
		    		    if (rs == ButtonType.OK) {
		    		        System.out.println("Pressed OK.");
		    		    }
		    		});
		    	}
		    	else {
		    		
		    		foldersComboBox.getItems().add(result.get());
		    	}
		    }
		    }
		);
		vbox.getChildren().add(new Label("Choose note title"));
		vbox.getChildren().add(titleslistView);
		Button addNote = new Button("Add Note");
		vbox.getChildren().add(addNote);
		addNote.setOnAction((e)->{
			 System.out.println(foldersComboBox.getSelectionModel().getSelectedItem().toString());
			 if(foldersComboBox.getSelectionModel().isEmpty())
			   {
				 	Alert alert = new Alert(AlertType.WARNING);
		    		alert.setTitle("Warning");
		    		alert.setContentText("Please choose a folder first.");
		    		alert.showAndWait().ifPresent(rs -> {
		    		    if (rs == ButtonType.OK) {
		    		        System.out.println("Pressed OK.");
		    		    }
		    		});
			    	return;
			  }
			TextInputDialog dialog = new TextInputDialog("Add a Folder");
			
		    dialog.setTitle("Input");
		    dialog.setHeaderText("Add a new Note to current Folder:");
		    dialog.setContentText("Please enter the name you want to create:");
		    // Traditional way to get the response value.
		    Optional<String> result = dialog.showAndWait();
		 
		    if (result.isPresent()){
		        // TODO 
		    		titleslistView.getItems().add(result.get());
		    		noteBook.createTextNote(currentFolder,result.get());
		    		Alert alert = new Alert(AlertType.INFORMATION);
		    		alert.setTitle("Successful!");
		    		alert.setContentText("Insert note "+result.get()+" JavaFx to folder "+currentFolder+" successfully!");
		    		alert.showAndWait().ifPresent(rs -> {
		    		    if (rs == ButtonType.OK) {
		    		        System.out.println("Pressed OK.");
		    		    }
		    		 
		    		});
		    }
		    }
		);
		return vbox;
	}

	private void updateListView() {
		ArrayList<String> list = new ArrayList<String>();

		// TODO populate the list object with all the TextNote titles of the
		// currentFolder
		Folder f = null;
		for(Folder ftemp:noteBook.getFolders()) {
			if(ftemp.getName().compareTo(currentFolder)==0) {
				f  =ftemp;
				currentFolderObj = ftemp;

				break;
			}
		}
		if(f!=null)
		for(int i=0;i<f.getNotes().size();i++) {
			list.add(f.getNotes().get(i).getTitle());
		}
		
		ObservableList<String> combox2 = FXCollections.observableArrayList(list);
		titleslistView.setItems(combox2);
		textAreaNote.setText("");
	}

	/*
	 * Creates a grid for the center region with four columns and three rows
	 */
	private GridPane addGridPane() {

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));
		ImageView i2 = new ImageView("/save.png");
		i2.setFitHeight(15);
		i2.setFitWidth(15);

		ImageView i3 = new ImageView("/delete.png");
	
		i3.setFitHeight(15);
		i3.setFitWidth(15);
		Button SaveNote = new Button("Save Note",null);
		SaveNote.setOnAction((e)->{
			if(foldersComboBox.getSelectionModel().isEmpty() || titleslistView.getSelectionModel().isEmpty())
			{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Successfully saved");
				alert.setContentText("Please select a folder and a note. ");
				alert.showAndWait().ifPresent(rs -> {
				    if (rs == ButtonType.OK) {
				        System.out.println("Pressed OK.");
				    }
				});
			}
			if(currentNote!="") {
				for(Note n:currentFolderObj.getNotes()) {
					if(n.getTitle().compareTo(currentNote)==0 && n instanceof TextNote) {
						TextNote tn = ((TextNote)n);
						tn.content = textAreaNote.getText();
					}
				}
			}
		});
		Button DeleteNote = new Button("Delete Note",null);

		DeleteNote.setOnAction((e)->{
			if(foldersComboBox.getSelectionModel().isEmpty() || titleslistView.getSelectionModel().isEmpty())
			{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Successfully saved");
				alert.setContentText("Please select a folder and a note. ");
				alert.showAndWait().ifPresent(rs -> {
				    if (rs == ButtonType.OK) {
				        System.out.println("Pressed OK.");
				    }
				});
			}
			if(currentNote!="") {
					if(currentFolderObj.removeNotes(currentNote)) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Successful");
					alert.setContentText("The note has been successfuly deleted!");
					alert.showAndWait().ifPresent(rs -> {
					    if (rs == ButtonType.OK) {
					        System.out.println("Pressed OK.");
					    }
					});
				
					}
				    updateTitleList();

			
			}
		});
		textAreaNote.setEditable(true);
		textAreaNote.setMaxSize(450, 400);
		textAreaNote.setWrapText(true);
		textAreaNote.setPrefWidth(450);
		textAreaNote.setPrefHeight(400);
		
		// 0 0 is the position in the grid
		grid.add(i2, 0,0);
		grid.add(SaveNote, 1, 0);
		grid.add(i3, 2, 0);
		grid.add(DeleteNote, 3, 0);
		grid.add(textAreaNote, 0, 1,4,4);

		return grid;
	}

	private void loadNoteBook() {
		NoteBook nb = new NoteBook();
		nb.createTextNote("COMP3021", "COMP3021 syllabus", "Be able to implement object-oriented concepts in Java.");
		nb.createTextNote("COMP3021", "course information",
				"Introduction to Java Programming. Fundamentals include language syntax, object-oriented programming, inheritance, interface, polymorphism, exception handling, multithreading and lambdas.");
		nb.createTextNote("COMP3021", "Lab requirement",
				"Each lab has 2 credits, 1 for attendence and the other is based the completeness of your lab.");

		nb.createTextNote("Books", "The Throwback Special: A Novel",
				"Here is the absorbing story of twenty-two men who gather every fall to painstakingly reenact what ESPN called “the most shocking play in NFL history” and the Washington Redskins dubbed the “Throwback Special”: the November 1985 play in which the Redskins’ Joe Theismann had his leg horribly broken by Lawrence Taylor of the New York Giants live on Monday Night Football. With wit and great empathy, Chris Bachelder introduces us to Charles, a psychologist whose expertise is in high demand; George, a garrulous public librarian; Fat Michael, envied and despised by the others for being exquisitely fit; Jeff, a recently divorced man who has become a theorist of marriage; and many more. Over the course of a weekend, the men reveal their secret hopes, fears, and passions as they choose roles, spend a long night of the soul preparing for the play, and finally enact their bizarre ritual for what may be the last time. Along the way, mishaps, misunderstandings, and grievances pile up, and the comforting traditions holding the group together threaten to give way. The Throwback Special is a moving and comic tale filled with pitch-perfect observations about manhood, marriage, middle age, and the rituals we all enact as part of being alive.");
		nb.createTextNote("Books", "Another Brooklyn: A Novel",
				"The acclaimed New York Times bestselling and National Book Award–winning author of Brown Girl Dreaming delivers her first adult novel in twenty years. Running into a long-ago friend sets memory from the 1970s in motion for August, transporting her to a time and a place where friendship was everything—until it wasn’t. For August and her girls, sharing confidences as they ambled through neighborhood streets, Brooklyn was a place where they believed that they were beautiful, talented, brilliant—a part of a future that belonged to them. But beneath the hopeful veneer, there was another Brooklyn, a dangerous place where grown men reached for innocent girls in dark hallways, where ghosts haunted the night, where mothers disappeared. A world where madness was just a sunset away and fathers found hope in religion. Like Louise Meriwether’s Daddy Was a Number Runner and Dorothy Allison’s Bastard Out of Carolina, Jacqueline Woodson’s Another Brooklyn heartbreakingly illuminates the formative time when childhood gives way to adulthood—the promise and peril of growing up—and exquisitely renders a powerful, indelible, and fleeting friendship that united four young lives.");

		nb.createTextNote("Holiday", "Vietnam",
				"What I should Bring? When I should go? Ask Romina if she wants to come");
		nb.createTextNote("Holiday", "Los Angeles", "Peter said he wants to go next Agugust");
		nb.createTextNote("Holiday", "Christmas", "Possible destinations : Home, New York or Rome");
		noteBook = nb;

	}
	private void loadNoteBook(File file) {
	NoteBook nb = new NoteBook(file.getAbsolutePath());
	//System.out.println(file.getAbsolutePath());
	noteBook = nb;
	}
	

}
