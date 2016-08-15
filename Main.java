/* @author leonjoel
 * JOEL QUAINOO
 * ID: 110688594
 * EMAIL: JOEL.QUAINOO@STONYBROOK.EDU
 * CSE 214 ASSIGNMENT: 5
 * SECTION: 6
 *
 */
import java.util.GregorianCalendar;
import java.util.Optional;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


@SuppressWarnings("rawtypes")
public class Main extends Application {
	final TextField to = new TextField();
	final TextField cc = new TextField();
	final TextField bcc = new TextField();
	final TextField sub = new TextField();
	final TextField body = new TextField();
	final Label emailView = new Label();
	final Button composeEmail = new Button("Compose");
	final Button sendEmail = new Button("Send");
	final Button sortbyDateAscending = new Button("Date Ascending");
	final Button addFolder = new Button("Add New Folder");
	final Button inboxFolder = new Button("Inbox");
	final Button trashFolder = new Button("Trash");
	final Button removeFolder = new Button("Remove Folder");
	final Button deleteEmail = new Button("Delete");
	final Button moveEmail = new Button("Move");
	Alert alert = new Alert(AlertType.CONFIRMATION);
	GridPane folderGridPane = new GridPane();
	Mailbox mailbox = new Mailbox();
	ListView listView  = new ListView();
	HBox hBoxTop = new HBox();

	double value = addButtons("Button", 40).getTranslateY();
	boolean folderRemoved = false;
	static double valueX = 10;
	static double valueY = 0;

	ObservableList<Email> inboxData = FXCollections.observableArrayList();
	ObservableList<Email> trashData = FXCollections.observableArrayList();

	@SuppressWarnings("unchecked")
	@Override

	public void start(Stage primaryStage) {
		try {
			BorderPane window = new BorderPane();
			Scene scene = new Scene(window,850,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setMaxHeight(500);
			primaryStage.setMinHeight(500);
			primaryStage.setMaxWidth(850);
			primaryStage.setMinWidth(850);
			primaryStage.setTitle("Email Operations");

			composeEmail.setPrefSize(120, 20);
			composeEmail.setTranslateX(-205);
			composeEmail.setTranslateY(0);
			composeEmail.setOnMousePressed(new EventHandler<MouseEvent>(){
				public void handle(MouseEvent event){
					clear();
					sendEmail.setDisable(false);
					GridPane composePane = new GridPane();
					composePane.setVgap(3);
					composePane.setStyle("-fx-background-color: #0000;");
					Label toLabel = new Label("TO:");
					Label ccLabel = new Label("CC:");
					Label bccLabel = new Label("BCC:");
					Label subjectLabel = new Label("Subject:");
					to.setPromptText("Recipients");
					to.getText();
					to.setEditable(true);
					to.setPrefWidth(580);
					GridPane.setConstraints(to, 0, 0);
					to.setTranslateX(60);
					to.setTranslateY(06);
					toLabel.setTranslateX(30);
					toLabel.setTranslateY(06);

					cc.setPromptText("Recipients");
					cc.getText();
					cc.setEditable(true);
					cc.setPrefWidth(580);
					GridPane.setConstraints(cc, 0, 1);
					cc.setTranslateX(60);
					cc.setTranslateY(06);
					ccLabel.setTranslateX(30);
					ccLabel.setTranslateY(38);

					bcc.setPromptText("Recipients");
					bcc.getText();
					bcc.setEditable(true);
					bcc.setPrefWidth(580);
					GridPane.setConstraints(bcc, 0, 2);
					bcc.setTranslateX(60);
					bcc.setTranslateY(06);
					bccLabel.setTranslateX(25);
					bccLabel.setTranslateY(70);

					sub.setPromptText("");
					sub.getText();
					sub.setEditable(true);
					sub.setPrefWidth(580);
					GridPane.setConstraints(sub, 0, 3);
					sub.setTranslateX(60);
					sub.setTranslateY(06);
					subjectLabel.setTranslateX(10);
					subjectLabel.setTranslateY(100);

					body.getText();
					body.setEditable(true);
					GridPane.setConstraints(body, 0, 4);
					body.setPrefWidth(580);
					body.setPrefHeight(290);
					body.setTranslateX(60);
					body.setTranslateY(06);
					composePane.getChildren().addAll(to, cc, bcc, sub, body, toLabel, ccLabel, bccLabel, subjectLabel);
					window.setCenter(composePane);
					composeEmail.setDisable(true);
					deleteEmail.setDisable(true);
					moveEmail.setDisable(true);
				}
			});

			//Send email button 
			sendEmail.setPrefSize(120, 20);
			sendEmail.setTranslateX(-180);
			sendEmail.setTranslateY(0);
			sendEmail.setDisable(true);
			sendEmail.setOnMousePressed(new EventHandler<MouseEvent>(){
				public void handle(MouseEvent event){
					try{
						if(to.getText() != null && cc.getText() != null && bcc.getText() != null && sub.getText() != null && body.getText() != null){
							Email email = new Email(to.getText(), sub.getText(), body.getText());
							email.setBcc(bcc.getText());
							email.setCc(cc.getText());
							GregorianCalendar timestamp = new GregorianCalendar();
							email.setTimestamp(timestamp);
							mailbox.getInbox().addEmail(email);
							inboxData.add(email);
							clear();
							composeEmail.setDisable(false);
							sendEmail.setDisable(true);
							window.setCenter(listView);
						}
					}
					catch(Exception e){
						window.setCenter(emailView);
						composeEmail.setDisable(false);
						sendEmail.setDisable(true);
					}
				}
			});

			//Table View Getting email content in folders starts

			TableColumn indexCol = new TableColumn("Index");
			TableColumn timeCol = new TableColumn("Time");
			TableColumn subjectCol = new TableColumn("Subject");
			indexCol.prefWidthProperty().bind(listView.widthProperty().multiply(.1));
			timeCol.prefWidthProperty().bind(listView.widthProperty().multiply(.399));
			subjectCol.prefWidthProperty().bind(listView.widthProperty().multiply(.5));
			listView.setTranslateX(12);
			listView.setEditable(false);

			//Drop Down Menu for Sorting 
			final Label sortLabel = new Label("Sort By:");
			sortLabel.setTranslateX(598);
			sortLabel.setTranslateY(5);
			ObservableList<String> options = FXCollections.observableArrayList("Date Ascending", "Date Descending",
					"Subject Ascending", "Subject Descending");
			final ComboBox<String> sortMenu = new ComboBox<>();
			for(int i = 0; i < options.size(); i++){
				sortMenu.getItems().add(options.get(i));
			}
			sortMenu.setPrefWidth(165);
			sortMenu.autosize();
			sortMenu.setTranslateX(600);
			sortMenu.setTranslateY(0);
			sortMenu.setEditable(false);

			sortMenu.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
				@Override
				public void changed(ObservableValue ov, String t, String t1) {
					String sort = sortMenu.getValue();
					sort = t1;
					if(sort.contains("Date Ascending")){
						mailbox.getInbox().sortByDateAscending();
					}
					else if(sort.contains("Date Descending")){
						mailbox.getInbox().sortByDateDescending();
					}
					else if(sort.contains("Subject Ascending")){
						mailbox.getInbox().sortBySubjectAscending();
					}
					else
						mailbox.getInbox().sortBySubjectDescending();
				}    
			});


			hBoxTop.setPadding(new Insets(10, 12, 15,12)); //(top/right/bottom/left)
			hBoxTop.setStyle("-fx-background-color: #8B7B8B");	
			hBoxTop.getChildren().addAll(sortLabel, sortMenu, composeEmail, sendEmail, deleteEmail, moveEmail);
			//Drop down Menu Ends

			if(inboxData.size() > 0){
				int val = listView.getSelectionModel().getSelectedIndex();
				System.out.println(val);
			}




			//Folders Starts
			folderGridPane.setVgap(2);
			window.setLeft(folderGridPane);

			ObservableList<Folder> folders = FXCollections.observableArrayList(new Folder("Inbox"), new Folder("Trash"));
			addFolder.setPrefSize(120, 20);
			addFolder.setTranslateX(10);
			addFolder.setTranslateY(360);
			addFolder.setOnMousePressed(new EventHandler<MouseEvent>(){
				public void handle(MouseEvent event){
					TextInputDialog dialog = new TextInputDialog("");
					dialog.setHeaderText(null);
					dialog.setTitle("Add New Folder");
					dialog.setContentText("Enter folder name:");
					Optional<String> result = dialog.showAndWait();
					if (result.isPresent()){
						Folder folder = new Folder(result.get());
						folders.add(folder);
						mailbox.addFolder(folder);
						value += 31;
						if(folderRemoved == true){
							folderGridPane.getChildren().add(addButtons(result.get(), valueY));
							folderRemoved = false;
						}
						else{
							folderGridPane.getChildren().add(addButtons(result.get(), value));
						}
						window.setCenter(emailView);
						emailView.setText(folder.getName() + " added successfully.");
					}
				}
			});


			removeFolder.setPrefSize(120, 20);
			removeFolder.setTranslateX(10);
			removeFolder.setTranslateY(393);
			removeFolder.setOnMousePressed(new EventHandler<MouseEvent>(){
				public void handle(MouseEvent event){
					window.setCenter(emailView);
					TextInputDialog dialog = new TextInputDialog("");
					dialog.setHeaderText(null);
					dialog.setTitle("Remove Folder");
					dialog.setContentText("Enter folder name:");
					Optional<String> result = dialog.showAndWait();
					if (result.isPresent() && result.get() != null){
						mailbox.deleteFolder(result.get());
						for(int i = 0; i < folderGridPane.getChildren().size(); i++){
							if(((Labeled) folderGridPane.getChildren().get(i)).getText().equalsIgnoreCase(result.get()) && !(((Labeled) folderGridPane.getChildren().get(i)).getText().equalsIgnoreCase("Inbox")) && !(((Labeled) folderGridPane.getChildren().get(i)).getText().equalsIgnoreCase("Trash"))){
									valueY = folderGridPane.getChildren().get(i).getTranslateY();
									folderGridPane.getChildren().remove(i);
									folderRemoved = true;
									emailView.setText(result.get() + " removed successfully.");
							}
							else{
								emailView.setText("Unsuccessful: \n");
								emailView.setText(result.get() + " can not be removed.");
							}
						}


					}
				}
			});

			inboxFolder.setPrefSize(120, 20);
			inboxFolder.setTranslateX(10);
			inboxFolder.setTranslateY(9);
			inboxFolder.setOnMousePressed(new EventHandler<MouseEvent>(){
				public void handle(MouseEvent event){
					window.setCenter(listView);
					deleteEmail.setDisable(false);
					moveEmail.setDisable(false);
					sendEmail.setDisable(true);
					listView.setItems(inboxData);
				}
			});

			trashFolder.setPrefSize(120, 20);
			trashFolder.setTranslateX(10);
			trashFolder.setTranslateY(40);
			trashFolder.setOnMousePressed(new EventHandler<MouseEvent>(){
				public void handle(MouseEvent event){
					window.setCenter(listView);
					listView.setItems(trashData);
					deleteEmail.setDisable(true);
				}
			});

			folderGridPane.getChildren().addAll(removeFolder, addFolder, inboxFolder, trashFolder);
			//Folder Ends

			deleteEmail.setPrefSize(100, 20);
			deleteEmail.setTranslateX(0);
			deleteEmail.setTranslateY(0);
			deleteEmail.setDisable(true);
			deleteEmail.setOnMousePressed(new EventHandler<MouseEvent>(){
				public void handle(MouseEvent event){
					TextInputDialog dialog = new TextInputDialog("");
					dialog.setHeaderText(null);
					dialog.setTitle("Add New Folder");
					dialog.setContentText("Enter  index of mail you want to delete:");
					Optional<String> result = dialog.showAndWait();
					if(result.isPresent() && result.get() != null && inboxData.size() > 0){
						mailbox.getInbox().removeEmail(Integer.parseInt(result.get()));
						trashData.add(inboxData.remove(Integer.parseInt(result.get()) - 1));
					}
					else 
						emailView.setText("Index out of bound");
				}
			});


			moveEmail.setPrefSize(100, 20);
			moveEmail.setTranslateX(-220);
			moveEmail.setTranslateY(0);
			moveEmail.setDisable(true);
			moveEmail.setOnMousePressed(new EventHandler<MouseEvent>(){
				public void handle(MouseEvent event){
					//implement move email

				}
			});


			primaryStage.setScene(scene);
			primaryStage.show();
			window.setTop(hBoxTop);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public Button addButtons(String text,double value){
		Button button = new Button(text);
		button.setPrefSize(120, 20);
		button.setTranslateX(10);
		button.setTranslateY(value);
		return button;
	}

	public void getInbox(){
		mailbox.getInbox();
	}

	public void clear(){
		sub.clear();
		to.clear();
		cc.clear();
		bcc.clear();
		body.clear();
	}


}

