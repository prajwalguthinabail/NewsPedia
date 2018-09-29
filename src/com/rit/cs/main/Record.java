package com.rit.cs.main;

import java.util.Date;

/**
 * Record.java is an entity class to store the Record ID, file name, and tokens
 * processed from the text.
 * 
 * @author Prajwal Guthinabail Nov 08, 2017
 */
public class Record {
	private int id;
	private String title;
	private String publication;
	private String author;
	private Date date;
	private String content;
	private int score;

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @param id
	 * @param title
	 * @param publication
	 * @param author
	 * @param date
	 * @param content
	 */
	public Record(int id, String title, String publication, String author,
			Date date, String content) {
		super();
		this.id = id;
		this.title = title;
		this.publication = publication;
		this.author = author;
		this.date = date;
		this.content = content;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the publication
	 */
	public String getPublication() {
		return publication;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

}
