package com.todomanager.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Entity "TodoList" which represents an row entry of db table "todolists".
 * 
 * @author Ayhan Dardagan
 *
 */
@Entity
@Table(name = "todo_entries")
public class TodoEntry {

	/**
	 * Primary key, todo message ID.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	/**
	 * Todo message text.
	 */
	@NotNull
	private String todoText;

	// Constructors

	/**
	 * Default constructor.
	 */
	public TodoEntry() {
	}

	/**
	 * Constructor.
	 * 
	 * @param todoText
	 *            Todo message text
	 */
	public TodoEntry(final String todoText) {
		this.todoText = todoText;
	}

	// Getters and setters

	/**
	 * Get todo message ID.
	 * 
	 * @return Todo message ID
	 */
	public long getId() {
		return id;
	}

	/**
	 * Set todo message ID.
	 * 
	 * @param id
	 *            Todo message ID
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * Get todo message text.
	 * 
	 * @return Todo message text
	 */
	public String getTodoText() {
		return todoText;
	}

	/**
	 * Set todo message text.
	 * 
	 * @param todoText
	 *            Todo message text
	 */
	public void setTodoText(final String todoText) {
		this.todoText = todoText;
	}
}
