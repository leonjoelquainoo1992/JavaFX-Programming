/* @author leonjoel
 * JOEL QUAINOO
 * ID: 110688594
 * EMAIL: JOEL.QUAINOO@STONYBROOK.EDU
 * CSE 214 ASSIGNMENT: 5
 * SECTION: 6
 *
 */
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

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}


	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}


	/**
	 * @return the cc
	 */
	public String getCc() {
		return cc;
	}


	/**
	 * @param cc the cc to set
	 */
	public void setCc(String cc) {
		this.cc = cc;
	}


	/**
	 * @return the bcc
	 */
	public String getBcc() {
		return bcc;
	}


	/**
	 * @param bcc the bcc to set
	 */
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}


	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}


	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}


	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}


	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}


	/**
	 * @return the timestamp
	 */
	public GregorianCalendar getTimestamp() {
		return timestamp;
	}


	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(GregorianCalendar timestamp) {
		this.timestamp = timestamp;
	}
	
	@SuppressWarnings("deprecation")
	public String toString(){
		String str = "";
		return str +="   " + this.timestamp.getTime().getMonth() + "/" + this.timestamp.getTime().getDate() + "/2016 " +"          " + this.timestamp.getTime().getHours()+":" +this.timestamp.getTime().getMinutes() +":" +this.timestamp.getTime().getSeconds() + "                                         " + this.subject;
	}

}
