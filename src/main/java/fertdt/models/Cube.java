package fertdt.models;

import java.util.Objects;

public class Cube {
    private int id;
    private String name;
    private String image;
    private String description;

    public Cube(int id, String name, String image, String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cube)) return false;
        Cube cube = (Cube) o;
        return id == cube.id && name.equals(cube.name) && image.equals(cube.image) && description.equals(cube.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, image, description);
    }
}
