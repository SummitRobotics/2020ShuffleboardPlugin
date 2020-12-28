package team5468.plugin2020.widget;

import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;

@Description(name = "Turret Indicator", dataTypes = Number.class, summary = "Displays the position of the turret with a fun icon")
@ParametrizedController("TurretPosition.fxml")
public final class TurretPosition extends SimpleAnnotatedWidget<Number> {
	
	
	@FXML
    private Pane root;

    @FXML
    private ImageView background;
    
    @FXML
    private ImageView turret;
    
	@FXML
	private void initialize() {
        
        background.fitHeightProperty().bind(root.heightProperty().multiply(1));
        turret.fitHeightProperty().bind(root.heightProperty().multiply(0.707));
        turret.toFront();

        dataProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Double rotate = newValue.doubleValue();
                    turret.setRotate(rotate-90);   
            }
        });

        
		
	}
	
    @Override
    public Pane getView() {
		return root;
	}
	
}