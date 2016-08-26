
import java.io.Serializable;
import java.util.*;

public class Mailbox implements Serializable {
	private static final long serialVersionUID = 6745241773071622730L;
	private Folder inbox;
	private Folder trash;
	private ArrayList<Folder> folders;
	public static Mailbox mailbox;


	public Mailbox() {

	}


	/**
	 * @return the inbox
	 */
	public Folder getInbox() {
		if(inbox == null)
			inbox = new Folder("Inbox");
		return inbox;
	}


	/**
	 * @param inbox the inbox to set
	 */
	public void setInbox(Folder inbox) {
		this.inbox = inbox;
	}


	/**
	 * @return the trash
	 */
	public Folder getTrash() {
		if(trash == null)
			trash = new Folder("Trash");
		return trash;
	}

	/**
	 * @param trash the trash to set
	 */
	public void setTrash(Folder trash) {
		this.trash = trash;
	}


	/**
	 * @return the folders
	 */
	public ArrayList<Folder> getFolders() {
		if(folders == null)
			folders = new ArrayList<Folder>();
		return folders;
	}

	/**
	 * @param folders the folders to set
	 */
	public void setFolders(ArrayList<Folder> folders) {
		this.folders = folders;
	}


	/**
	 * @return the mailbox
	 */
	public static Mailbox getMailbox() {
		return mailbox;
	}

	/**
	 * @param mailbox the mailbox to set
	 */
	public static void setMailbox(Mailbox mailbox) {
		Mailbox.mailbox = mailbox;
	}

	public void addFolder(Folder folder){
		for(int i = 0; i < getFolders().size(); i++){
			if(getFolders().get(i).getName().equalsIgnoreCase(folder.getName())){
				System.out.println("Folder already exists.");
				return;
			}
		}
		folders.add(folder);
		System.out.println("Folder added");
	}

	public void deleteFolder(String name){
		for(int i = 0; i < getFolders().size(); i++){
			if((getFolders().get(i).getName()).equalsIgnoreCase(name)){
				getFolders().remove((getFolders().get(i)));
				System.out.printf("Folder \"%s\" deleted.\n", name);
				return;
			}
		}
		System.out.println("Folder not found!");

	}

	@SuppressWarnings("resource")
	public void composeEmail(){
		Scanner keyboard = new Scanner(System.in);
		System.out.println("To: ");
		String to = keyboard.nextLine();
		System.out.println("CC: ");
		String cc = keyboard.nextLine();
		System.out.println("Bcc: ");
		String bcc = keyboard.nextLine();
		System.out.println("Subject: ");
		String subject = keyboard.nextLine();
		System.out.println("Body: ");
		String body = keyboard.nextLine();
		Email email = new Email(to, subject, body);
		email.setBcc(bcc);
		email.setCc(cc);
		GregorianCalendar timestamp = new GregorianCalendar();
		email.setTimestamp(timestamp);
		getInbox().addEmail(email);
		System.out.println("Email successfully added to Inbox");
	}

	public void deleteEmail(Email email){
		getTrash().addEmail(email);
		System.out.println("Email moved to trash");
	}

	public void clearTrash(){
		getTrash().setEmails(new ArrayList<Email>());
		System.out.println("Trash is emptied");
	}

	public void moveEmail(Email email, Folder target){
		for(int index = 0; index < getFolders().size(); index++){
			if(getFolders().get(index).getName().equalsIgnoreCase(target.getName())){
				getFolders().get(index).addEmail(email);
				System.out.println("Email moved to "+ getFolders().get(index).getName());
				return;
			}
		}
		System.out.println("Folder not found. Moving email to inbox");
		getInbox().addEmail(email);
	}

	public Folder getFolder(String name){
		for(int i = 0; i < getFolders().size(); i++){
			Folder folder = new Folder(getFolders().get(i).getName());
			if(folder.getName().equalsIgnoreCase(name))
				return folder;
		}
		System.out.println("No such folder");
		return null;	
	}
	
	
	public void getFolderNameInString(){
		for(int i = 0; i < getFolders().size(); i++){
			System.out.println(getFolders().get(i).getName());
		}
		System.out.println();
	}
	
	
	

}
