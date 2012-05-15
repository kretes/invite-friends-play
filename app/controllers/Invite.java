package controllers;


import java.util.ArrayList;
import java.util.List;

import models.Contact;
import play.mvc.Controller;

public class Invite extends Controller {

	public static void index() {
		render();
	}
	
	
	public static void inviteGmail() {
		Invite.index();
	}

	public static void inviteLinkedIn() {
		Invite.index();
	}

	public static void inviteFacebook() {
		Invite.index();
	}

}