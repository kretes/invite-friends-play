package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Contact extends Model {
	public Contact(String email, String name) {
		this.email = email;
		this.name = name;
	}

	public String email;
	public String name;
	public boolean invitationSent;

	@Override
	public String toString() {
		return "Contact [email=" + email + ", name=" + name + ", invitationSent=" + invitationSent + "]";
	}

}
