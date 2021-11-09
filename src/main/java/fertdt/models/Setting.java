package fertdt.models;

import java.util.Objects;

public class Setting {
    private int id;
    private String value;

    public Setting(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Setting)) return false;
        Setting setting = (Setting) o;
        return id == setting.id && value.equals(setting.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value);
    }
}
