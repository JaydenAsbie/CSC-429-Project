// specify the package
package userinterface;

// system imports

import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.Book;

import java.util.Properties;

/** The class containing the Account View  for the ATM application */
//==============================================================
public class BookView extends View
{

    // GUI components
    protected TextField bookTitle;
    protected TextField author;
    protected TextField pubYear;
    protected Button done;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public BookView(IModel book)
    {
        super(book, "BookView");
        System.out.println("Test 2");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());

        container.getChildren().add(createStatusLog("             "));

        getChildren().add(container);

        //populateFields();

    }


    // Create the title container
    //-------------------------------------------------------------
    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(" Book Insert ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);
        container.getChildren().add(titleText);

        return container;
    }

    // Create the main form content
    //-------------------------------------------------------------
    private GridPane createFormContent()
    {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

       /* Label nameL = new Label("Name:");
        name = new TextField();*/



        /*Label addressL = new Label("Address:");
        address = new TextField();

        Label cityL = new Label("City:");
        city = new TextField();

        Label stateCodeL = new Label("State Code:");
        stateCode = new TextField();

        Label zipL = new Label("Zip Code:");
        zip = new TextField();

        Label emailL = new Label("Email:");
        email = new TextField();

        Label dateOfBirthL = new Label("Date of Birth (YYYY-MM-DD):");
        dateOfBirth = new TextField();

        Label statusL = new Label("Status:");
        ComboBox<String> statusCB = new ComboBox<>();
        statusCB.getItems().addAll("Active", "Inactive");*/

        Label titleL = new Label("Title");
        bookTitle = new TextField();

        Label authorL = new Label("Author");
        author = new TextField();

        Label yearL = new Label("Publish Year");
        pubYear = new TextField();

        Button submit = new Button("Submit");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Properties insertProp = new Properties();
                insertProp.setProperty("bookTitle", bookTitle.getText());
                insertProp.setProperty("author", author.getText());
                insertProp.setProperty("pubYear", pubYear.getText());
                insertProp.setProperty("status", "active");
                myModel.stateChangeRequest("InsertBook", insertProp);

                myModel.stateChangeRequest("LibrarianView", null);
            }
        });
        Button done = new Button("Done");
        done.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myModel.stateChangeRequest("LibrarianView", null);
            }
        });

        // Add all variables and labels to the grid in their respective columns and rows
        grid.add(titleL, 0, 0);
        grid.add(bookTitle, 1, 0);
        grid.add(authorL, 0, 1);
        grid.add(author, 1, 1);
        grid.add(yearL, 0, 2);
        grid.add(pubYear, 1, 2);
        grid.add(done, 0, 8);
        grid.add(submit, 1, 8);

        return grid;
    }


    // Create the status log field
    //-------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    //-------------------------------------------------------------
    public void populateFields()
    {
        bookTitle.setText((String)myModel.getState("Title"));
        author.setText((String)myModel.getState("Author"));
        pubYear.setText((String)myModel.getState("Publish Year"));
    }

    // Process Action
    //public void processAction(Event e){

    //
    //}

    /**
     * Update method
     */
    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {
        /**
         clearErrorMessage();

         if (key.equals("ServiceCharge") == true)
         {
         String val = (String)value;
         serviceCharge.setText(val);
         displayMessage("Service Charge Imposed: $ " + val);
         }
         */
    }

    /**
     * Display error message
     */
    //----------------------------------------------------------
    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
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

}

//---------------------------------------------------------------
//	Revision History:
//



