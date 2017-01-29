package com.alinturbut.olxCrawl.domain;

import javax.persistence.*;

@Entity
@Table(name = "site_status")
public class SiteStatus {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "no_of_posts")
    private int noOfPosts;

    @Column(name = "last_post_url")
    private String lastPostUrl;

    @Column(name = "related_post_url")
    private String relatedPostUrl;

    public String getLastPostUrl() {
        return lastPostUrl;
    }

    public void setLastPostUrl(String lastCarTitle) {
        this.lastPostUrl = lastCarTitle;
    }

    public int getNoOfPosts() {
        return noOfPosts;
    }

    public void setNoOfPosts(int noOfPosts) {
        this.noOfPosts = noOfPosts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRelatedPostUrl() {
        return relatedPostUrl;
    }

    public void setRelatedPostUrl(String relatedPostUrl) {
        this.relatedPostUrl = relatedPostUrl;
    }
}
