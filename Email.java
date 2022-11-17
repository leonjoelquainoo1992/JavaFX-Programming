
import java.io.Serializable;
import java.util.GregorianCalendar;

public class Email implements Serializable{
	private String to;
	private String cc;
	private String bcc;
	private String subject;
	private String body;
	private GregorianCalendar timestamp;
	private static final long serialVersionUID = 1L;
	
	public Email(String to, String subject, String body) {
		this.to = to;
		this.subject = subject;
		this.body = body;
		this.cc = "";
		this.bcc = "";
	}
	
	public String getTo() {
		return to;
	}
	
	public void setTo(String to) {
		this.to = to;
	}
	
	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getBcc() {
		return bcc;
	}
	
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public GregorianCalendar getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(GregorianCalendar timestamp) {
		this.timestamp = timestamp;
	}
	
	@SuppressWarnings("deprecation")
	public String toString(){
		String str = "";
		return str +="   " + this.timestamp.getTime().getMonth() + "/" + this.timestamp.getTime().getDate() + "/2016 " +"          " + this.timestamp.getTime().getHours()+":" +this.timestamp.getTime().getMinutes() +":" +this.timestamp.getTime().getSeconds() + "                                         " + this.subject;
	}

}
