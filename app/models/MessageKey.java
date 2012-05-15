package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class MessageKey extends Model {

	public String messageKey;

	public MessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	public String getMessageKey() {
		return messageKey;
	}

}
