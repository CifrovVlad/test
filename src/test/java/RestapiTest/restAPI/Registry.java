package RestapiTest.restAPI;

public class Registry {
    private String email;
    private String password;

    public Registry(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
