package orbyt.starry.models;

/**
 * Created by calebchiesa on 3/24/17.
 */

public class Item {


    private String name;
    private String stars;
    private String description;
    private String url;
    private String profileImg;
    private String profileUrl;


    public Item() {
    }

    public Item(String name, String stars, String description, String url, String profileImg, String profileUrl) {
        this.name = name;
        this.stars = stars;
        this.description = description;
        this.url = url;
        this.profileImg = profileImg;
        this.profileUrl = profileUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
