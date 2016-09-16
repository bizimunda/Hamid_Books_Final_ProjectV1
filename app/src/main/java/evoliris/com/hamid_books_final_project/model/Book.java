package evoliris.com.hamid_books_final_project.model;

/**
 * Created by temp on 15/09/2016.
 */
public class Book {

    private long id;
    private String image;
    private String title;


    public Book() {
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
