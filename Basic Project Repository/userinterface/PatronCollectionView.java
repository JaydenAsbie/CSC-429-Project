package userinterface;

// system imports

import javafx.beans.property.SimpleStringProperty;
import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Patron;
import model.PatronCollection;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

//==============================================================================
public class PatronCollectionView extends View
{
	protected TableView<PatronTableModel> tableOfPatrons;
	protected Button doneButton;

	protected MessageView statusLog;


	//--------------------------------------------------------------------------
	public PatronCollectionView(IModel wsc)
	{
		super(wsc, "PatronCollectionView");
		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));
		container.setPrefWidth(931);
		container.setPrefHeight(500);

		// create our GUI components, add them to this panel
		container.getChildren().add(createTitle());
		container.getChildren().add(createFormContent());

		// Error message area
		container.getChildren().add(createStatusLog("                                            "));

		getChildren().add(container);
		
		populateFields();
	}

	//--------------------------------------------------------------------------
	protected void populateFields()
	{
		getEntryTableModelValues();
	}

	//--------------------------------------------------------------------------
	protected void getEntryTableModelValues()
	{
		
		ObservableList<AccountTableModel> tableData = FXCollections.observableArrayList();
		try
		{
			//AccountCollection accountCollection = (AccountCollection)myModel.getState("AccountList");

	 		//Vector entryList = (Vector)accountCollection.getState("Accounts");
			//Enumeration entries = entryList.elements();

			//while (entries.hasMoreElements() == true)
			{
				//Account nextAccount = (Account)entries.nextElement();
				//Vector<String> view = nextAccount.getEntryListView();

				// add this list entry to the list
				//AccountTableModel nextTableRowData = new AccountTableModel(view);
				//tableData.add(nextTableRowData);
				
			}
			
			//tableOfAccounts.setItems(tableData);
		}
		catch (Exception e) {//SQLException e) {
			// Need to handle this exception
		}
	}

	// Create the title container
	//-------------------------------------------------------------
	private Node createTitle()
	{
		HBox container = new HBox();
		container.setAlignment(Pos.CENTER);	

		Text titleText = new Text(" Brockport Bank ATM ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setWrappingWidth(300);
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);
		container.getChildren().add(titleText);
		
		return container;
	}

	// Create the main form content
	//-------------------------------------------------------------
	private VBox createFormContent()
	{
		VBox vbox = new VBox(10);

		GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
       	grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Text prompt = new Text("LIST OF PATRONS");
        prompt.setWrappingWidth(350);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

		tableOfPatrons = new TableView<PatronTableModel>();
		tableOfPatrons.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	
		TableColumn patronIdColumn = new TableColumn("Patron Id") ;
		patronIdColumn.setMinWidth(100);
		patronIdColumn.setCellValueFactory(
	                new PropertyValueFactory<PatronTableModel, String>("patronId"));
		
		TableColumn nameColumn = new TableColumn("Name") ;
		nameColumn.setMinWidth(100);
		nameColumn.setCellValueFactory(
	                new PropertyValueFactory<PatronTableModel, String>("name"));
		  
		TableColumn addressColumn = new TableColumn("Address") ;
		addressColumn.setMinWidth(100);
		addressColumn.setCellValueFactory(
	                new PropertyValueFactory<PatronTableModel, String>("address"));
		
		TableColumn cityColumn = new TableColumn("City") ;
		cityColumn.setMinWidth(100);
		cityColumn.setCellValueFactory(
	                new PropertyValueFactory<PatronTableModel, String>("city"));

		TableColumn stateCodeColumn = new TableColumn("State Code") ;
		stateCodeColumn.setMinWidth(100);
		stateCodeColumn.setCellValueFactory(
				new PropertyValueFactory<PatronTableModel, String>("stateCode"));

		TableColumn zipColumn = new TableColumn("Zip") ;
		zipColumn.setMinWidth(100);
		zipColumn.setCellValueFactory(
				new PropertyValueFactory<PatronTableModel, String>("zip"));

		TableColumn emailColumn = new TableColumn("Email") ;
		emailColumn.setMinWidth(100);
		emailColumn.setCellValueFactory(
				new PropertyValueFactory<PatronTableModel, String>("email"));

		TableColumn dateOfBirthColumn = new TableColumn("Date of Birth") ;
		dateOfBirthColumn.setMinWidth(100);
		dateOfBirthColumn.setCellValueFactory(
				new PropertyValueFactory<PatronTableModel, String>("dateOfBirth"));

		TableColumn statusColumn = new TableColumn("Status");
		statusColumn.setMinWidth(100);
		statusColumn.setCellValueFactory(
				new PropertyValueFactory<PatronTableModel, String>("status"));

		tableOfPatrons.getColumns().addAll(patronIdColumn, nameColumn, addressColumn, cityColumn, stateCodeColumn,
				zipColumn, emailColumn, dateOfBirthColumn, statusColumn);

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPrefSize(115, 400);
		scrollPane.setContent(tableOfPatrons);

		doneButton = new Button("Done");
 		doneButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	clearErrorMessage(); 
					// do the inquiry
                    myModel.stateChangeRequest("LibrarianView", null);
					//tableOfPatrons.getItems().clear();
					tableOfPatrons.refresh();
            	 }
        	});

		HBox btnContainer = new HBox(100);
		btnContainer.setAlignment(Pos.CENTER);
		btnContainer.getChildren().add(doneButton);
		
		vbox.getChildren().add(grid);
		vbox.getChildren().add(scrollPane);
		vbox.getChildren().add(btnContainer);
	
		return vbox;
	}

	//--------------------------------------------------------------------------
	public void updateState(String key, Object value)
	{
	}

	//--------------------------------------------------------------------------
	public void clearTable() { tableOfPatrons.getItems().clear(); }

	//--------------------------------------------------------------------------
	public void updateTable(PatronCollection patronCollection){
		tableOfPatrons.getItems().clear();

		Vector<Patron> patrons = (Vector<Patron>) patronCollection.getState("Patrons");
		System.out.println("Test 2: " + patrons.size());

		for(Patron patron : patrons){
			Vector<String> entry = patron.getEntryListView();
			PatronTableModel model = new PatronTableModel(entry);
			tableOfPatrons.getItems().add(model);
		}
	}

	//--------------------------------------------------------------------------
	protected MessageView createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}


	/**
	 * Display info message
	 */
	//----------------------------------------------------------
	public void displayMessage(String message)
	{
		statusLog.displayMessage(message);
	}

	/**
	 * Clear error message
	 */
	//----------------------------------------------------------
	public void clearErrorMessage()
	{
		statusLog.clearErrorMessage();
	}
	/*
	//--------------------------------------------------------------------------
	public void mouseClicked(MouseEvent click)
	{
		if(click.getClickCount() >= 2)
		{
			processAccountSelected();
		}
	}
   */
	
}
