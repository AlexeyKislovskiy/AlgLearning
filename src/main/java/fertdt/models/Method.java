package fertdt.models;

import java.util.Objects;

public class Method {
    private int id;
    private String name;
    private int cubeId;
    private int numberOfSituation;
    private String description;
    private int learning;
    private int learned;
    private String image;
    private State state;
    private int numOfNew;
    private int numOfForgot;
    private int numOfRepeat;

    public enum State {
        UNLEARNED,
        LEARNING,
        LEARNED
    }

    public Method(int id, String name, int cubeId, int numberOfSituation, String description, int learning, int learned, String image) {
        this.id = id;
        this.name = name;
        this.cubeId = cubeId;
        this.numberOfSituation = numberOfSituation;
        this.description = description;
        this.learning = learning;
        this.learned = learned;
        this.image = image;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCubeId() {
        return cubeId;
    }

    public int getNumberOfSituation() {
        return numberOfSituation;
    }

    public String getDescription() {
        return description;
    }

    public int getLearning() {
        return learning;
    }

    public int getLearned() {
        return learned;
    }

    public String getImage() {
        return image;
    }

    public State getState() {
        return state;
    }

    public int getNumOfNew() {
        return numOfNew;
    }

    public int getNumOfForgot() {
        return numOfForgot;
    }

    public int getNumOfRepeat() {
        return numOfRepeat;
    }

    public void setNumOfNew(int numOfNew) {
        this.numOfNew = numOfNew;
    }

    public void setNumOfForgot(int numOfForgot) {
        this.numOfForgot = numOfForgot;
    }

    public void setNumOfRepeat(int numOfRepeat) {
        this.numOfRepeat = numOfRepeat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Method)) return false;
        Method method = (Method) o;
        return id == method.id && cubeId == method.cubeId && numberOfSituation == method.numberOfSituation && learning == method.learning && learned == method.learned && numOfNew == method.numOfNew && numOfForgot == method.numOfForgot && numOfRepeat == method.numOfRepeat && name.equals(method.name) && description.equals(method.description) && image.equals(method.image) && state == method.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cubeId, numberOfSituation, description, learning, learned, image, state, numOfNew, numOfForgot, numOfRepeat);
    }
}
