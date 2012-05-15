package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Message extends Model {

	@ManyToOne
	public MessageKey messageKey;

	@ManyToOne
	public Language language;

	public String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Message(MessageKey messageKey, Language language, String message) {
		this.messageKey = messageKey;
		this.language = language;
		this.message = message;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result
				+ ((messageKey == null) ? 0 : messageKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (messageKey == null) {
			if (other.messageKey != null)
				return false;
		} else if (!messageKey.equals(other.messageKey))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Message [messageKey=" + messageKey + ", language=" + language
				+ ", message=" + message + "]";
	}

}
