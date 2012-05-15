package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Contact;
import play.mvc.Controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Application extends Controller {

	public static void index() {
		List<Contact> contacts = Contact.findAll();
		render(contacts);
	}

	public static void index(List<Contact> contacts) {
		render(contacts);
	}

	public static void inviteCallback() {
		new Contact("jwermuth@gmail.com", "Jesper").save();
		new Contact("whabula@gmail.com", "Whabula").save();
		new Contact("wrinkle@gmail.com", "John Smith").save();

		index();
	}

	// TODO it would be better to have JSON automatically resolved as my Object
	// like Contact
	// maybe there is an easier way than to register TypeBinder for my type
	public static void invite(String friends) {
		JsonElement element = new JsonParser().parse(friends);
		JsonArray arrayOfFriends = element.getAsJsonArray();
		for (int i = 0; i < arrayOfFriends.size(); i++) {
			String email = arrayOfFriends.get(i).getAsJsonObject().get("email").getAsString();
			Contact contact = Contact.find("byEmail", email).first();
			contact.invitationSent = true;
			contact.save();
		}
		System.out.println(Contact.findAll() + " after");
		invited();
	}

	public static void invited() {
		List<Contact> contacts = Contact.find("byInvitationSent", true).fetch();
		render(contacts);
	}

	public static void contactsByEmail(String term) {
		List<Contact> contacts = Contact.find("byEmailLike", "%" + term + "%").fetch();
		List<String> emails = new ArrayList<String>();
		for (Contact contact : contacts) {
			emails.add(contact.email);
		}
		renderJSON(emails);
	}

}