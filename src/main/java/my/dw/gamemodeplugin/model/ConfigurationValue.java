package my.dw.gamemodeplugin.model;

import java.util.List;

public class ConfigurationValue<T> {

    private T value;

    private List<T> valueRange;

    private T defaultValue;

    public T getValue() {
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

    public void setValueRange(final List<T> valueRange) {
        this.valueRange = valueRange;
    }

    public void setDefaultValue(final T defaultValue) {
        this.defaultValue = defaultValue;
    }

}
