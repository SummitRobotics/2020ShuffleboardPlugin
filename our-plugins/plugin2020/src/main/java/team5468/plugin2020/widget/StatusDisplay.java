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
	private void initialize() {
		//makes it visable
		textField.visibleProperty().set(true);
		textField.editableProperty().set(false);
		textField.setFont(Font.font("Roboto", FontWeight.BOLD, 30));
		textField.setAlignment(Pos.CENTER);

		
		dataProperty().addListener((__, prev, cur) -> {
			if (cur != null && cur instanceof String) {
				String[] parts = cur.split(":");
				String status = parts[0];
				textField.setText(status);
				try{
					String color = parts[1];
					textField.setStyle("-fx-text-fill: "+color+";");
				}
				catch(IndexOutOfBoundsException e){
					textField.setStyle("-fx-text-fill: white;");
				}
				
				
			}
			else{
				textField.setText("no robot status to display");
				textField.setStyle("-fx-text-fill: white;");
			}
		});
		
	}
	
	@Override
	public Pane getView() {
		return root;
	}
	
}