package team5468.plugin2020.widget;

import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.scene.image.ImageView;

@Description(name = "Hood Indicator", dataTypes = Number.class, summary = "Displays the position of the Hood with a fun icon")
@ParametrizedController("HoodPosition.fxml")
public final class HoodPosition extends SimpleAnnotatedWidget<Number> {

    @FXML
    private Pane root;

    @FXML
    private ImageView background;

    @FXML
    private ImageView turret;

    @FXML
	private void initialize() {
        
        //sets up images
        background.fitHeightProperty().bind(root.heightProperty().multiply(.96));
        turret.fitHeightProperty().bind(root.heightProperty().multiply(0.96));
        turret.toFront();

        //makes rotation transformation
        Rotate r = new Rotate();
        //binds the angle of rotation to the input number for the widget
        r.angleProperty().bind(dataProperty());
        

        //whenever the height of the box the image is in changes we need to adj the rotation axis beacuse its dumb and relitive
        root.heightProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                turret.getTransforms().remove(r);

                double nv = newValue.doubleValue();
                double x = (nv/3)*2.2518;
                double y = (nv/3)*2.0218;

                r.setPivotX(x);
                r.setPivotY(y);

                //circle made at the point of rotation for aligning it to the image
                // Circle c = new Circle(0, 0, 20, Color.RED);
                // c.getTransforms().add(new Translate(x-turret.getLayoutBounds().getCenterX(), y-turret.getLayoutBounds().getCenterY()));
                // root.getChildren().add(c);

                turret.getTransforms().add(r);

            }
        });   
		
	}
	
    @Override
    public Pane getView() {
		return root;
	}
	
}