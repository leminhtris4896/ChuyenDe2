package com.example.trile.foodlocation.Models;

public class mdUserStatusPost {
    public String getStrIDPost() {
        return strIDPost;
    }

    public void setStrIDPost(String strIDPost) {
        this.strIDPost = strIDPost;
    }

    public boolean isStrStatusLike() {
        return strStatusLike;
    }

    public void setStrStatusLike(boolean strStatusLike) {
        this.strStatusLike = strStatusLike;
    }

    public boolean isStrStatusUnlikeLike() {
        return isStrStatusUnlikeLike;
    }

    public void setStrStatusUnlikeLike(boolean strStatusUnlikeLike) {
        isStrStatusUnlikeLike = strStatusUnlikeLike;
    }

    private String strIDPost;
    private boolean strStatusLike;
    private boolean isStrStatusUnlikeLike;


    public mdUserStatusPost() {
    }


    public mdUserStatusPost(String strIDPost, boolean strStatusLike, boolean isStrStatusUnlikeLike) {
        this.strIDPost = strIDPost;
        this.strStatusLike = strStatusLike;
        this.isStrStatusUnlikeLike = isStrStatusUnlikeLike;
    }
}
