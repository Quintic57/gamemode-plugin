package my.dw.gamemodeplugin.model;

import java.util.List;

public class ConfigurationValue<T> {

    private T data;

    private List<T> valueRange;

    private T defaultValue;

    public T getData() {
        return data;
    }

    public List<T> getValueRange() {
        return valueRange;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public void setValueRange(final List<T> valueRange) {
        this.valueRange = valueRange;
    }

    public void setDefaultValue(final T defaultValue) {
        this.defaultValue = defaultValue;
    }

}
