package fertdt.models;


import java.sql.Date;
import java.util.Objects;

public class Cuber {
    private int id;
    private String nickname;
    private String email;
    private String password;
    private Date registrationDate;
    private int visitedDays;
    private int visitedDaysRow;
    private Date lastVisited;
    private Status status;

    public enum Status {
        USER,
        MODERATOR,
        ADMIN
    }

    public Cuber(int id, String nickname, String email, String password, Date registrationDate, int visitedDays,
                 int visitedDaysRow, Date lastVisited) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
        this.visitedDays = visitedDays;
        this.visitedDaysRow = visitedDaysRow;
        this.lastVisited = lastVisited;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Cuber(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public int getVisitedDays() {
        return visitedDays;
    }

    public int getVisitedDaysRow() {
        return visitedDaysRow;
    }

    public Date getLastVisited() {
        return lastVisited;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cuber)) return false;
        Cuber cuber = (Cuber) o;
        return id == cuber.id && visitedDays == cuber.visitedDays && visitedDaysRow == cuber.visitedDaysRow && nickname.equals(cuber.nickname) && email.equals(cuber.email) && password.equals(cuber.password) && registrationDate.equals(cuber.registrationDate) && lastVisited.equals(cuber.lastVisited) && status == cuber.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickname, email, password, registrationDate, visitedDays, visitedDaysRow, lastVisited, status);
    }
}
