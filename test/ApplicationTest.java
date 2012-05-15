import java.util.HashMap;
import java.util.Map;

import models.Contact;

import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;

import play.mvc.Http.Response;
import play.test.Fixtures;
import play.test.FunctionalTest;

public class ApplicationTest extends FunctionalTest {

	@Before
	public void cleanDB() {
		Fixtures.deleteDatabase();
	}

	@Test
	public void testThatIndexPageWorks() {
		Response response = GET("/");
		assertIsOk(response);
		assertContentType("text/html", response);
		assertCharset(play.Play.defaultWebEncoding, response);
	}

	@Test
	public void shouldBeFetchedByEmailPartWhenOnlyOneContactExist() {
		new Contact("bob.job@gmail.com", "secret").save();

		Response response = searchForContactsWith("job");

		assertOKJsonResponse(response);
		Assertions.assertThat(getContent(response)).contains("bob.job@gmail.com");
	}

	@Test
	public void shouldBeFetchedByEmailPartWhenOtherContactsExist() {
		new Contact("bob@gmail.com", "secret").save();
		new Contact("joe@gmail.com", "secret").save();

		Response response = searchForContactsWith("bob");

		assertOKJsonResponse(response);
		Assertions.assertThat(getContent(response)).contains("bob@gmail.com");
		Assertions.assertThat(getContent(response)).excludes("joe@gmail.com");
	}

	@Test
	public void shouldInviteFriends() {
		new Contact("bob@gmail.com", "secret").save();
		Assertions.assertThat(((Contact) Contact.findAll().get(0)).invitationSent).isFalse();

		String friendsJson = "[{\"email\":\"bob@gmail.com\"}]";
		// TODO // check how // to easily // generate // JSOn in // java code

		POST("/application/invite", map("friends", friendsJson));

		System.out.println(Contact.findAll());
		Assertions.assertThat(((Contact) Contact.findAll().get(0)).invitationSent).isTrue();
	}

	private Map<String, String> map(final String key, final String value) {
		return new HashMap<String, String>() {
			{
				put(key, value);
			}
		};
	}

	private void assertOKJsonResponse(Response response) {
		assertIsOk(response);
		assertContentType("application/json", response);
		assertCharset(play.Play.defaultWebEncoding, response);
	}

	private Response searchForContactsWith(String searchTerm) {
		return GET("/application/contactsbyemail?term=" + searchTerm);
	}

}