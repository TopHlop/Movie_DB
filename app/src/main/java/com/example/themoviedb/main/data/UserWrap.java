package com.example.themoviedb.main.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserWrap {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("avatar")
    @Expose
    private Avatar avatar;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public class Avatar {
        @SerializedName("gravatar")
        @Expose
        private Gravatar gravatar;

        public Gravatar getGravatar() {
            return gravatar;
        }

        public void setGravatar(Gravatar gravatar) {
            this.gravatar = gravatar;
        }

        public class Gravatar {
            @SerializedName("hash")
            @Expose
            private String hash;

            public String getHash() {
                return hash;
            }

            public void setHash(String hash) {
                this.hash = hash;
            }
        }
    }
}
