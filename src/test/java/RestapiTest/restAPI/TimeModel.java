package RestapiTest.restAPI;

public class TimeModel {
    private String name;
    private String job;
    public TimeModel() {
    }

    public TimeModel(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }
}
