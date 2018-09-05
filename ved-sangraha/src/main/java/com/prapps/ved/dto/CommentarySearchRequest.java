package com.prapps.ved.dto;

public class CommentarySearchRequest {
    private String language;
    private Integer chapterNo;
    private Integer sutraNo;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getChapterNo() {
        return chapterNo;
    }

    public void setChapterNo(Integer chapterNo) {
        this.chapterNo = chapterNo;
    }

    public Integer getSutraNo() {
        return sutraNo;
    }

    public void setSutraNo(Integer sutraNo) {
        this.sutraNo = sutraNo;
    }
}
