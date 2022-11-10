package com.stackroute.newz.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.stackroute.newz.dao.NewsSource;
import com.stackroute.newz.dao.Reminder;

public class NewsDto {

    private Long newsId;
	
    @NotBlank(message="Title of the news item cannot be blank")
    @NotNull(message="Title of the news item cannot be blank")
	private String title;
	
    @NotBlank(message="Author of the news item cannot be blank")
	private String author;
	
    @NotBlank(message="Description of the news item cannot be blank")
	private String description;
	
	@JsonSerialize(using = ToStringSerializer.class)
	private LocalDateTime publishedAt;
	
    @NotBlank(message="Content of the news item cannot be blank")
	private String content;
	
    @NotBlank(message="Url of the news item cannot be blank")
	private String url;
	
	private String urlToImage;
		
	private ReminderDto reminder;
	
	private NewssourceDto source;
	
	private String userId;

	public Long getNewsId() {
		return newsId;
	}

	public void setNewsId(Long newsId) {
		this.newsId = newsId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(LocalDateTime publishedAt) {
		this.publishedAt = publishedAt;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlToImage() {
		return urlToImage;
	}

	public void setUrlToImage(String urlToImage) {
		this.urlToImage = urlToImage;
	}

	public ReminderDto getReminder() {
		return reminder;
	}

	public void setReminder(ReminderDto reminder) {
		this.reminder = reminder;
	}

	public NewssourceDto getSource() {
		return source;
	}

	public void setSource(NewssourceDto source) {
		this.source = source;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "NewsDto [newsId=" + newsId + ", title=" + title + ", author=" + author + ", description=" + description
				+ ", publishedAt=" + publishedAt + ", content=" + content + ", url=" + url + ", urlToImage="
				+ urlToImage + ", reminder=" + reminder + ", source=" + source + ", userId=" + userId + "]";
	}
	
	

}
