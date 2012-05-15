import java.util.List;

import models.Language;
import models.Message;
import models.MessageKey;

import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class MessagesTest extends UnitTest {

	@Before
	public void cleanDB() {
		Fixtures.deleteDatabase();
	}

	@Test
	public void messageCanBeStored() {
		Language en = new Language("English").save();
		MessageKey key = new MessageKey("welcome").save();

		new Message(key, en, "Welcome!").save();

		Assertions.assertThat(Message.find("byMessageKeyAndLanguage", key, en).fetch()).onProperty("message")
				.containsOnly("Welcome!");
	}

	@Test
	public void canFindKeysWithNoMessageForLanguage() {
		Language en = new Language("English").save();
		MessageKey keyForWhichMessageDoesNotExistsForEN = new MessageKey("unhandledKey").save();
		MessageKey keyForWhichMessageExistsForEN = new MessageKey("handledKey").save();

		new Message(keyForWhichMessageExistsForEN, en, "Welcome!").save();

		Language fr = new Language("French").save();
		new Message(keyForWhichMessageExistsForEN, fr, "Welcome!").save();
		new Message(keyForWhichMessageDoesNotExistsForEN, fr, "Welcome!").save();

		List<Object> keys = MessageKey.find(
				"select k from MessageKey k where k not in "
						+ "(select message.messageKey from Message message where message.language = ?)", en).fetch();

		Assertions.assertThat(keys).onProperty("messageKey").containsOnly("unhandledKey");
	}

}