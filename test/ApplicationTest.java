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
	public void testThatContactsCanBeFetchedByEmailPart() {
		new Contact("bob@gmail.com", "secret").save();

		Response response = GET("/application/contactsbyemail?term=bob");
		assertIsOk(response);
		assertContentType("application/json", response);
		assertCharset(play.Play.defaultWebEncoding, response);

		Assertions.assertThat(getContent(response)).contains("bob@gmail.com");
	}

}