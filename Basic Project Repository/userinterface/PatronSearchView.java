package userinterface;
// system imports
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

// project imports
import impresario.IModel;

public class PatronSearchView extends View {

	protected TextField titleInput;
	protected Button submitButton;

	protected MessageView statusLog;

	public PatronSearchView(IModel bookSearch) {
		super (bookSearch, "PatronSearchView");

		VBox container = new VBox();
		container.setPadding(new Insets(15, 5, 5, 5));

		// Add title for panel
		container.getChildren().add(createTitle());
		container.getChildren().add(createFormContent());

		// create our GUI components, add them to vBox
		container.getChildren().add(createStatusLog("             "));
		getChildren().add(container);
		// Subscriptions
		// myModel.subscribe();
	}

	private Node createStatusLog(String initialMessage) {
		statusLog = new MessageView(initialMessage);
		return statusLog;
	}

	private Node createTitle()
	{
		HBox container = new HBox();
		container.setAlignment(Pos.CENTER);

		Text titleText = new Text(" Patron Search (Zip code) ");
		titleText.setWrappingWidth(300);
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);
		container.getChildren().add(titleText);

		return container;
	}

	private VBox createFormContent() {
		VBox vBox = new VBox(10);
		vBox.setAlignment(Pos.CENTER);
		vBox.setPadding(new Insets(20));

		// Insert text field for searching book titles
		titleInput = new TextField();
		titleInput.setPromptText("Enter ZIP Code:");

		submitButton = new Button("Search");
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					processInput();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
		vBox.getChildren().addAll(titleInput, submitButton);
		return vBox;
	}

	private void processInput() throws Exception {
		myModel.stateChangeRequest("PatronSearch", titleInput.getText());
		titleInput.clear();
	}

	public void updateState(String key, Object value) {
	}
}