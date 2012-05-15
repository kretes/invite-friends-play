import java.util.List;

import models.Language;
import models.Message;
import models.MessageKey;

import org.fest.assertions.Assertions;
import org.junit.Test;

import play.test.UnitTest;

public class MessagesTest extends UnitTest {

	@Test
	public void messageCanBeStored() {
		Language en = new Language("English").save();
		MessageKey key = new MessageKey("welcome").save();

		new Message(key, en, "Welcome!").save();

		Assertions
				.assertThat(
						Message.find("byMessageKeyAndLanguage", key, en)
								.fetch()).onProperty("message")
				.containsOnly("Welcome!");
	}

	@Test
	public void canFindKeysWithNoMessageForLanguage() {
		Language en = new Language("English").save();
		MessageKey key = new MessageKey("welcome").save();

		List<Object> keys = MessageKey.find("select k from MessageKey k")
				.fetch();

		Assertions.assertThat(keys).onProperty("messageKey")
				.containsOnly("welcome");
	}

}