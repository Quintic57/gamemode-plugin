package my.dw.gamemodeplugin.model;

import java.util.List;

public class GameModeConfigurationElement<T> {

    private final String name;

    private T value;

    private final List<T> valueRange;

    private final T defaultValue;

    public GameModeConfigurationElement(final String name, final T defaultValue, final List<T> valueRange) {
        if (valueRange.size() > 8) {
            throw new IllegalArgumentException("Config value range must have less than nine elements");
        }
        this.name = name;
        value = defaultValue;
        this.valueRange = valueRange;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public T getCurrentValue() {
        return value;
    }

    public List<T> getValueRange() {
        return valueRange;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public void setValue(final T value) {
        this.value = value;
    }

    public void resetToDefault() {
        value = defaultValue;
    }

}
