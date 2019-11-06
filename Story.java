
package com.active.chdating.net;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class Story {

    @SerializedName("ga_prefix")
    private String gaPrefix;
    @Expose
    private String hint;
    @Expose
    private Long id;
    @SerializedName("image_hue")
    private String imageHue;
    @Expose
    private List<String> images;
    @Expose
    private String title;
    @Expose
    private Long type;
    @Expose
    private String url;

    public String getGaPrefix() {
        return gaPrefix;
    }

    public void setGaPrefix(String gaPrefix) {
        this.gaPrefix = gaPrefix;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageHue() {
        return imageHue;
    }

    public void setImageHue(String imageHue) {
        this.imageHue = imageHue;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
