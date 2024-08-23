package RestapiTest.restAPI;

public class ResponceTime extends TimeModel {

    private String updatedAt;

    public ResponceTime(String name, String job, String updatedAt) {
        super(name, job);
        this.updatedAt = updatedAt;
    }

    public ResponceTime() {
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
