package evoliris.com.hamid_books_final_project.app;

import android.app.Application;

import java.util.ArrayList;

import evoliris.com.hamid_books_final_project.model.AppUser;

/**
 * Created by temp on 15/09/2016.
 */
public class LoginApplication extends Application {

    private ArrayList<AppUser> users;

    @Override
    public void onCreate() {
        super.onCreate();
        users= new ArrayList<>();
    }

    public ArrayList<AppUser> getUsers(){
        return users;
    }
}
