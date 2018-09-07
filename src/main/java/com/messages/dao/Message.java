package com.messages.dao;

public class Message{
	
	String message;
	Integer user_id;
	long time;
	Integer replied_to;
	Integer post_id;
	String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPostId() {
		return post_id;
	}
	public void setPostId(Integer post_id) {
		this.post_id = post_id;
	}
	public Integer getRepliedTo() {
		return replied_to;
	}
	public void setRepliedTo(Integer replied_to) {
		this.replied_to = replied_to;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getUserId() {
		return user_id;
	}
	public void setUserId(Integer user_id) {
		this.user_id = user_id;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
}