package evoliris.com.hamid_books_final_project.model;

/**
 * Created by temp on 15/09/2016.
 */
public class AppUser {

    private String userName;
    private String email;
    private String password;

    public AppUser(){
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
