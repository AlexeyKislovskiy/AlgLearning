package fertdt.models;

import java.util.Objects;

public class Situation {
    private int id;
    private String name;
    private int methodId;
    private int mirrorId;
    private int reverseId;
    private int mirrorReverseId;
    private String image;
    private State state;
    private TrainingState trainingState;
    private LearningState learningState;

    public enum State {
        UNLEARNED,
        LEARNING,
        LEARNED
    }

    public enum TrainingState {
        TRAINING,
        NOT_TRAINING
    }

    public enum LearningState {
        NEW,
        FORGOT,
        REPEAT,
        AWAIT,
        NOT_LEARNING
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setTrainingState(TrainingState trainingState) {
        this.trainingState = trainingState;
    }

    public void setLearningState(LearningState learningState) {
        this.learningState = learningState;
    }

    public Situation(int id, String name, int methodId, int mirrorId, int reverseId, int mirrorReverseId, String image) {
        this.id = id;
        this.name = name;
        this.methodId = methodId;
        this.mirrorId = mirrorId;
        this.reverseId = reverseId;
        this.mirrorReverseId = mirrorReverseId;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMethodId() {
        return methodId;
    }

    public int getMirrorId() {
        return mirrorId;
    }

    public int getReverseId() {
        return reverseId;
    }

    public int getMirrorReverseId() {
        return mirrorReverseId;
    }

    public String getImage() {
        return image;
    }

    public State getState() {
        return state;
    }

    public TrainingState getTrainingState() {
        return trainingState;
    }

    public LearningState getLearningState() {
        return learningState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Situation)) return false;
        Situation situation = (Situation) o;
        return id == situation.id && methodId == situation.methodId && mirrorId == situation.mirrorId && reverseId == situation.reverseId && mirrorReverseId == situation.mirrorReverseId && name.equals(situation.name) && image.equals(situation.image) && state == situation.state && trainingState == situation.trainingState && learningState == situation.learningState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, methodId, mirrorId, reverseId, mirrorReverseId, image, state, trainingState, learningState);
    }
}
