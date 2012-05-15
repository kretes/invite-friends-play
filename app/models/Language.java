package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Language extends Model {

	public String name;

	public Language(String name) {
		this.name = name;
	}

}
