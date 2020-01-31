package networking_server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.UrlValidator;

public class Message {
	
	String URL = new String();
	
	public Message(String inmessage) {
		this.inmessage = inmessage;
	}

	String inmessage;

	public Message() {
		// TODO Auto-generated constructor stub
	}

	public String getInmessage() {
		return inmessage;
	}

	public void setInmessage(String inmessage) {
		this.inmessage = inmessage;
	}
	
	public void validateMessage(String inmessage) {
		String pattern = "GET(.*)";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(inmessage);
		if (m.find()) {
			validateURL(m.group(1));
		}
	}
	
	public boolean validateURL(String url) {
		UrlValidator defaultValidator = new UrlValidator();
		return defaultValidator.isValid(url);
	}

}
