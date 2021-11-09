package fertdt.models;

import java.util.Objects;

public class Algorithm {
    private int id;
    private String text;
    private int situationId;
    private int numberOfUses;
    private boolean verified;
    private int addCuberId;
    private State state;
    private String situationName;
    private String situationImage;
    private String methodName;

    public enum State {
        USING,
        NOT_USING
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setSituationName(String situationName) {
        this.situationName = situationName;
    }

    public void setSituationImage(String situationImage) {
        this.situationImage = situationImage;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Algorithm(int id, String text, int situationId, int numberOfUses, boolean verified, int addCuberId) {
        this.id = id;
        this.text = text;
        this.situationId = situationId;
        this.numberOfUses = numberOfUses;
        this.verified = verified;
        this.addCuberId = addCuberId;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getSituationId() {
        return situationId;
    }

    public int getNumberOfUses() {
        return numberOfUses;
    }

    public boolean isVerified() {
        return verified;
    }

    public int getAddCuberId() {
        return addCuberId;
    }

    public State getState() {
        return state;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getSituationName() {
        return situationName;
    }

    public String getSituationImage() {
        return situationImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Algorithm)) return false;
        Algorithm algorithm = (Algorithm) o;
        return id == algorithm.id && situationId == algorithm.situationId && numberOfUses == algorithm.numberOfUses && verified == algorithm.verified && addCuberId == algorithm.addCuberId && text.equals(algorithm.text) && state == algorithm.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, situationId, numberOfUses, verified, addCuberId, state);
    }
}
