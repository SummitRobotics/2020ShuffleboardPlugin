package team5468.plugin2020.widget;

import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

@Description(name = "Status Display", dataTypes = String.class, summary = "Displays the robot status big and in colors")
@ParametrizedController("StatusDisplay.fxml")
public final class StatusDisplay extends SimpleAnnotatedWidget<String> {
	
	
	@FXML
	private Pane root;
	@FXML
	private TextField textField;
	
	@FXML
	//this is bad and should be done with a custom data type with 2 seprate strings as well beacuse this is bad and verry error prone
	private void initialize() {
		//makes it corect
		textField.visibleProperty().set(true);
		textField.editableProperty().set(false);
		textField.setFont(Font.font("Roboto", FontWeight.BOLD, 30));
		textField.setAlignment(Pos.CENTER);

		//whenever the data changes parse that data from the color and show it
		dataProperty().addListener((__, prev, cur) -> {
			//if if the data is bad display an error
			if (cur != null && cur instanceof String) {
				//splits the color form the data
				String[] parts = cur.split(":");
				String status = parts[0];
				textField.setText(status);
				//if there is no color in the string make the text white
				try{
					String color = parts[1];
					textField.setStyle("-fx-text-fill: "+color+";");
				}
				catch(IndexOutOfBoundsException e){
					textField.setStyle("-fx-text-fill: white;");
				}
			}
			else{
				//if the data is not null but is not a string the datatype is wrong
				if(cur != null){
					textField.setText("input data type not string");
					textField.setStyle("-fx-text-fill: red;");
				}
				//if the data is null the robot is not conected
				else{
					textField.setText("no robot status to display");
					textField.setStyle("-fx-text-fill: white;");
				}
				
			}
		});
		
	}
	
	@Override
	public Pane getView() {
		return root;
	}
	
}