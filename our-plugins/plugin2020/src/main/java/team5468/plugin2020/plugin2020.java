package team5468.plugin2020;

import edu.wpi.first.shuffleboard.api.data.DataType;
import edu.wpi.first.shuffleboard.api.plugin.Description;
import edu.wpi.first.shuffleboard.api.plugin.Plugin;
import edu.wpi.first.shuffleboard.api.theme.Theme;
import edu.wpi.first.shuffleboard.api.widget.ComponentType;
import edu.wpi.first.shuffleboard.api.widget.WidgetType;
import team5468.plugin2020.widget.HoodPosition;
import team5468.plugin2020.widget.LEDButtonClone;
import team5468.plugin2020.widget.StatusDisplay;
import team5468.plugin2020.widget.TurretPosition;

import java.util.List;
import java.util.Map;

/**
 * An example plugin that provides a custom data type (a 2D point) and a simple widget for viewing such data.
 */
@Description(
    group = "team5468",
    name = "2020 plugin",
    version = "1.0.3",
    summary = "it works™"
)
public final class plugin2020 extends Plugin {

  //gets the css file for the theme and makes it a Theme that can be loaded
  private final Theme customTheme = new Theme(getClass(), "5468Theme", "theme/5468Theme.css");

  @Override
  public List<Theme> getThemes() {
    return List.of(customTheme);
  }

  @Override
  public List<DataType> getDataTypes() {
    return List.of(
        
    );
  }

  @Override
  public List<ComponentType> getComponents() {
    return List.of(
        WidgetType.forAnnotatedWidget(LEDButtonClone.class), 
        WidgetType.forAnnotatedWidget(StatusDisplay.class), 
        WidgetType.forAnnotatedWidget(TurretPosition.class), 
        WidgetType.forAnnotatedWidget(HoodPosition.class)
    );
  }

  @Override
  public Map<DataType, ComponentType> getDefaultComponents() {
    return Map.of(
        
    );
  }
}
