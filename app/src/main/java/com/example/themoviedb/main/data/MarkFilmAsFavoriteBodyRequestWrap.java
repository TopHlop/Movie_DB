package com.example.themoviedb.main.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MarkFilmAsFavoriteBodyRequestWrap {
    @SerializedName("media_type")
    @Expose
    private String mediaType;
    @SerializedName("media_id")
    @Expose
    private int mediaId;
    @SerializedName("favorite")
    @Expose
    private boolean favorite;

    public MarkFilmAsFavoriteBodyRequestWrap(String mediaType, int mediaId, boolean favorite) {
        this.mediaType = mediaType;
        this.mediaId = mediaId;
        this.favorite = favorite;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
