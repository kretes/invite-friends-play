import models.Contact;

import org.fest.assertions.Assertions;
import org.junit.Test;

import play.mvc.Http.Response;
import play.test.FunctionalTest;

public class ApplicationTest extends FunctionalTest {

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
		Assertions.assertThat(getContent(response)).contains(
				"bob.job@gmail.com");
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

	private void assertOKJsonResponse(Response response) {
		assertIsOk(response);
		assertContentType("application/json", response);
		assertCharset(play.Play.defaultWebEncoding, response);
	}

	private Response searchForContactsWith(String searchTerm) {
		Response response = GET("/application/contactsbyemail?term="
				+ searchTerm);
		return response;
	}

}