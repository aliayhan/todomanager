CREATE DATABASE todo_manager 
	CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;
    
USE todo_manager;

CREATE TABLE users (
	id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT, 
	user_name VARCHAR(30) NOT NULL, 
	email VARCHAR(50) NOT NULL, 
	pwhash BINARY(60) NOT NULL, 
	PRIMARY KEY (id),  
	UNIQUE KEY user_name_uk (user_name),
    UNIQUE KEY email_uk (email))
ENGINE = InnoDB;

CREATE TABLE todo_entries (
	id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT, 
	user_id INT(11) UNSIGNED NOT NULL,
    todo_text VARCHAR(100) NOT NULL, 
	PRIMARY KEY (id),
	CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE)
ENGINE = InnoDB;