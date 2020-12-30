package team5468.plugin2020.widget;

import org.fxmisc.easybind.EasyBind;
import org.fxmisc.easybind.monadic.MonadicBinding;

import edu.wpi.first.shuffleboard.api.sources.DataSource;
import edu.wpi.first.shuffleboard.api.sources.DataSourceUtils;
import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

@Description(name = "LED Button", dataTypes = Number.class, summary = "a clone of the led buttons on the button box")
@ParametrizedController("LEDButtonClone.fxml")
public final class LEDButtonClone extends SimpleAnnotatedWidget<Number> {

	@FXML
	private Pane root;
	// hell from example widjet i understand nothing that is happning
	@FXML
	private ToggleButton button;
	private MonadicBinding<String> simpleSourceName;

	private final Property<Color> trueColor = new SimpleObjectProperty<>(this, "colorWhenTrue", Color.LAWNGREEN);
	private final Property<Color> falseColor = new SimpleObjectProperty<>(this, "colorWhenFalse", Color.DARKGREEN);

	@FXML
	//this is 100000000% BAD and PAIN and SIN and retroactivly caused 2020
	//this HSOULD be done with a custom data type with 2+ bools in it and not this
	//but i couldent make that work at all despite my mediocure efforts
	private void initialize() {
		//the input to this is a intager
		//the intager can be 00, 01, 10, 11
		//the first bit is if it is pressed or not and the sencond is if the led is on or off

		// my brain is bad™
		simpleSourceName = EasyBind.monadic(sourceProperty()).map(DataSource::getName).map(DataSourceUtils::baseName)
				.orElse("");

		//sets the deafult value for the button
		button.selectedProperty().set(false);

		//sets the button clicked state when the networktables value changes
		dataProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				//if pressed is true
				boolean newPressedState = (dataProperty().get().intValue() & 0b00000000000000000000000000000001) == 1;
				button.selectedProperty().set(newPressedState);
			}
		} );
		//chsnges the data when the selected state changes from user input (and maby the binding i dont know but if it does it should still be fine)
		button.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					//gets just the led bit so we dont change that
					try{
						int oldNumb = dataProperty().get().intValue() & 0b00000000000000000000000000000010;
						if(newValue){
							//takes the led bit from the origonal data and adds a 1 last bit to it to make the button on
							dataProperty().setValue(oldNumb | 0b00000000000000000000000000000001);
						}
						else{
							//takes the led bit and adds nothing to it (not nessary but good to have to show what is happning)
							dataProperty().setValue(oldNumb | 0b00000000000000000000000000000000);
						}
					}
					catch(NullPointerException e){
						return;
					}
			}
		} );
		
		//hellllllllllllllllll to bind backround color
		button.backgroundProperty().bind(Bindings.createObjectBinding(
			() -> createSolidColorBackground(getColor()),dataProperty(), trueColor, falseColor)
			);
		//makes button text
		button.textProperty().bind(simpleSourceName);
		//i KNOW there is an event loop somehwere
		//GIVE IT TO MMMMMMEEEEEEEEEEEE      EVENT LOOOOOOOPPPPPPPPPPPPPP I HATE THIS BINDINGS STUFFFFFFFF ITS BBBBBAAAAADDDDDDD
	}


	//stuff i coppyed from boolean box
	private Color getColor() {
		//BITWISE OPRATIONS WHOOOOOOOOOOOOOOOOOO
		//this masks all bits accept the second and then checks if the second bit is on bu checking if the result is 2 (10 = 2)
		try{
			int firstmask = 0b00000000000000000000000000000010;
			final boolean data = (dataProperty().get().intValue() & firstmask) == 2; 

			if (data) {
				return trueColor.getValue();
			} else {
				return falseColor.getValue();
			}
		}
		catch(NullPointerException e){
			return Color.RED;
		}
	}

	private Background createSolidColorBackground(Color color) {
		return new Background(new BackgroundFill(color, null, null));
	}

	public Color getTrueColor() {
		return trueColor.getValue();
	}

	public Property<Color> trueColorProperty() {
		return trueColor;
	}

	public void setTrueColor(Color trueColor) {
		this.trueColor.setValue(trueColor);
	}

	public Color getFalseColor() {
		return falseColor.getValue();
	}

	public Property<Color> falseColorProperty() {
		return falseColor;
	}

	public void setFalseColor(Color falseColor) {
		this.falseColor.setValue(falseColor);
	}

	//important for reasons
	@Override
	public Pane getView() {
		return root;
	}
}
