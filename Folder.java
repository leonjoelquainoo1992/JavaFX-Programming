
 *  public void addEmail(Email email): Adds an email to the folder according to the current sorting method.
·   public Email removeEmail(int index): Removes an email from the folder by index.
·  	public void sortBySubjectAscending(): Sorts the emails alphabetically by subject in ascending order.
·   public void sortBySubjectDescending(): Sorts the emails alphabetically by subject in descending order.
·   public void sortByDateAscending():  Sorts the emails by date in ascending order.
·   public void sortByDateDescending(): Sorts the emails by date in descending order. 	
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Folder implements Serializable{
	private static final long serialVersionUID = 8685728182584557749L;
	private ArrayList<Email> emails;
	private String name;
	private String currentSortingMethod;

	
	public Folder(String name) {
		this.emails = new ArrayList<Email>();
		this.name = name;
		this.currentSortingMethod = "";
	}

	/**
	 * @return the emails
	 */
	public ArrayList<Email> getEmails() {
		return emails;
	}

	/**
	 * @param emails the emails to set
	 */
	public void setEmails(ArrayList<Email> emails) {
		this.emails = emails;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the currentSortingMethod
	 */
	public String getCurrentSortingMethod() {
		return currentSortingMethod;
	}

	/**
	 * @param currentSortingMethod the currentSortingMethod to set
	 */
	public void setCurrentSortingMethod(String currentSortingMethod) {
		this.currentSortingMethod = currentSortingMethod;
	}

	public void addEmail(Email email){
		emails.add(email);
	}

	public Email removeEmail(int index){
		return emails.remove(index - 1);
	}

	public void sortBySubjectAscending(){
		Comparator<Email> comparator = new Comparator<Email>() {			
			@Override
			public int compare(Email o1, Email o2) {				
				return o1.getSubject().compareTo(o2.getSubject());				
			}
		};
		Collections.sort(emails, comparator);

	}

	public void sortBySubjectDescending(){
		Comparator<Email> comparator = new Comparator<Email>() {			
			@Override
			public int compare(Email o1, Email o2) {				
				return o2.getSubject().compareTo(o1.getSubject());				
			}
		};
		Collections.sort(emails, comparator);
	}

	public void sortByDateAscending(){
		Comparator<Email> comparator = new Comparator<Email>() {			
			@Override
			public int compare(Email o1, Email o2) {				
				return o1.getTimestamp().compareTo(o2.getTimestamp());				
			}
		};
		Collections.sort(emails, comparator);
	}

	public void sortByDateDescending(){
		Comparator<Email> comparator = new Comparator<Email>() {			
			@Override
			public int compare(Email o1, Email o2) {				
				return o2.getTimestamp().compareTo(o1.getTimestamp());				
			}
		};
		Collections.sort(emails, comparator);

	}
	
	public void getFolderNameInString(){
		for(int i = 0; i < Mailbox.mailbox.getFolders().size(); i++){
			System.out.println(Mailbox.mailbox.getFolders().get(i).getName());
		}
		System.out.println();
	}
	
	public static void main(String[] args){
		Email e1 = new Email("me", "You", "Him");
		Email e2 = new Email("His", "Thems", "Her");
		Mailbox mail = new Mailbox();
		mail.getInbox().addEmail(e1);
		mail.getInbox().addEmail(e2);
		mail.getInbox().sortBySubjectAscending();
		
		
	}
}
