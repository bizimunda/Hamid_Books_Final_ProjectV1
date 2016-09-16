package evoliris.com.hamid_books_final_project.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by temp on 15/09/2016.
 */
public class BookApi {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("photo")
    @Expose
    private String photo;

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    /**
     *
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return
     *     The photo
     */

    public String getPhoto() {
        return photo;
    }

    /**
     *
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @param photo
     *     The photo
     */
    public void setPhoto(String photo) {
        this.title = photo;
    }

    public BookApi withTitle(String title) {
        this.title = title;
        return this;
    }

    public BookApi withPhoto(String photo) {
        this.photo = photo;
        return this;
    }

    /**
     *
     * @return
     *     The id
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @param id
     *     The id
     */
    public void setId(long id) {
        this.id = id;
    }

    public BookApi withId(long id) {
        this.id = id;
        return this;
    }

    /**
     *
     * @return
     *     The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     *     The createdAt
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public BookApi withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    /**
     *
     * @return
     *     The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     *     The updatedAt
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public BookApi withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

}
