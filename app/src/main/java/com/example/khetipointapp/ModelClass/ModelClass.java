package com.example.khetipointapp.ModelClass;

import java.util.Comparator;

public class ModelClass {
    private String authorName, repoName, about, imageUrl, repoUrl, languageUsed, currStars, currForks;
    private boolean expandable;

    public ModelClass(String authorName, String repoName, String about, String imageUrl, String repoUrl, String languageUsed, String currStars, String currForks) {
        this.authorName = authorName;
        this.repoName = repoName;
        this.about = about;
        this.imageUrl = imageUrl;
        this.repoUrl = repoUrl;
        this.languageUsed = languageUsed;
        this.currStars = currStars;
        this.currForks = currForks;
        this.expandable = false;
    }
    
    public ModelClass(){}                   //.....Empty constructor

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }


    //......................................................................Getter and Setter functions

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }

    public String getLanguageUsed() {
        return languageUsed;
    }

    public void setLanguageUsed(String languageUsed) {
        this.languageUsed = languageUsed;
    }

    public String getCurrStars() {
        return currStars;
    }

    public void setCurrStars(String currStars) {
        this.currStars = currStars;
    }

    public String getCurrForks() {
        return currForks;
    }

    public void setCurrForks(String currForks) {
        this.currForks = currForks;
    }

    @Override
    public String toString() {
        return "ModelClass{" +
                "authorName='" + authorName + '\'' +
                ", repoName='" + repoName + '\'' +
                ", about='" + about + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", repoUrl='" + repoUrl + '\'' +
                ", languageUsed='" + languageUsed + '\'' +
                ", currStars='" + currStars + '\'' +
                ", currForks='" + currForks + '\'' +
                '}';
    }


    
    
    //....Class for sorting by STARS
    public static Comparator<ModelClass> byStars = new Comparator<ModelClass>() {
        @Override
        public int compare(ModelClass o1, ModelClass o2) {
            return - Integer.valueOf(o1.currStars).compareTo(Integer.valueOf(o2.currStars));
        }
    };

    
    //....Class for sorting by AUTHOR NAME
    public static Comparator<ModelClass> byAuthorName = new Comparator<ModelClass>() {
        @Override
        public int compare(ModelClass o1, ModelClass o2) {
            return o1.getAuthorName().compareTo(o2.getAuthorName());
        }
    };
}
