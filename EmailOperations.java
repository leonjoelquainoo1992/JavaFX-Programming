/* @author leonjoel
 * JOEL QUAINOO
 * ID: 110688594
 * EMAIL: JOEL.QUAINOO@STONYBROOK.EDU
 * CSE 214 ASSIGNMENT: 5
 * SECTION: 6
 *
 */
import java.io.*;
import java.util.*;

public class EmailOperations {
	static Scanner keyboard = new Scanner(System.in);
	static File file = new File("mailbox.obj");

	public static void printMenu(){
		System.out.println("A - Add folder ");
		System.out.println("R - Remove folder ");
		System.out.println("C - Compose ");
		System.out.println("F - View folder ");
		System.out.println("I - Inbox ");
		System.out.println("T - Trash ");
		System.out.println("E - Empty trash ");
		System.out.println("Q - Quit \n");
		System.out.print("Enter a user option: ");
	}

	public static void printSubMenu(){
		System.out.println("M – Move email ");
		System.out.println("D – Delete email ");
		System.out.println("V – View email contents ");
		System.out.println("SA – Sort by subject ascending ");
		System.out.println("SD – Sort by subject descending ");
		System.out.println("DA – Sort by date ascending ");
		System.out.println("DD – Sort by date descending ");
		System.out.println("R – Return to main menu \n");
		System.out.print("Enter a user option: ");
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try{
			if(file.exists()){
				FileInputStream stream = new FileInputStream(file);
				ObjectInputStream inFile = new ObjectInputStream(stream);
				Mailbox.mailbox = (Mailbox)inFile.readObject();
			}
			else{
				file.createNewFile();
				Mailbox.mailbox = new Mailbox();
				System.out.println("Previous save not found, starting with an empty mailbox.");
				System.out.println("Mailbox: \n");
				Mailbox.mailbox.getFolderNameInString();
			}

			boolean isDone = false;
			while(!isDone){
				printMenu();
				String options = keyboard.nextLine().toUpperCase();
				switch(options){
				case "A": {
					String folder;
					System.out.print("Enter folder name: ");
					folder = keyboard.nextLine();
					Folder newFolder = new Folder(folder);
					Mailbox.mailbox.addFolder(newFolder);
					System.out.println("Mailbox:");
					Mailbox.mailbox.getFolderNameInString();
					break;
				}
				case "R":{
					System.out.println("Enter folder name: ");
					String folder = keyboard.nextLine();
					Mailbox.mailbox.deleteFolder(folder);
					System.out.println("Mailbox:");
					Mailbox.mailbox.getFolderNameInString();
					break;
				}
				case "C":{
					Mailbox.mailbox.composeEmail();
					System.out.println("Mailbox:");
					Mailbox.mailbox.getFolderNameInString();
					break;
				}
				case "F":{
					System.out.println("Enter folder name: ");
					String folder = keyboard.nextLine();
					Folder newFolder = new Folder(folder);
					if(newFolder != null){
						subMenu(newFolder);
					}
					break;
				}
				case "I":{
					subMenu(Mailbox.mailbox.getInbox());
					break;
				}
				case "T":{
					subMenu(Mailbox.mailbox.getTrash());
					break;
				}
				case "E":{
					Mailbox.mailbox.clearTrash();
					break;
				}
				case "Q":{
					isDone = true;
					FileOutputStream stream;
					stream = new  FileOutputStream(file);
					ObjectOutputStream output = new ObjectOutputStream(stream);
					output.writeObject(Mailbox.mailbox);
					System.out.println("Program successfully exited and mailbox saved.");
					break;
				}
				default:{
					System.out.println("Invalid choice\n");
					break;
				}
				}
			}
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
			if(file.exists()){
				file.delete();
			}
		}

	}

	public static void subMenu(Folder folder){
		boolean done = false;
		while(!done){
			printSubMenu();
			String options = keyboard.nextLine().toUpperCase();
			switch(options){
			case "M":{
				System.out.println("Enter email index: ");
				String index = keyboard.nextLine();
				Email email = folder.getEmails().get(Integer.parseInt(index) - 1);
				if(email == null){
					System.out.println("Email does not exists");
				}
				else{
					System.out.println("Select a folder to move " + email.getSubject() + " to: ");
					String foldername = keyboard.nextLine();
					if(foldername.equalsIgnoreCase("Inbox")){
						Mailbox.mailbox.getInbox().addEmail(email);
						folder.removeEmail(Integer.parseInt(index));
						System.out.println("Mail added to Inbox  successfully");
					}
					else if(foldername.equalsIgnoreCase("Trash")){
						Mailbox.mailbox.deleteEmail(email);
						System.out.println("Email moved to trash successfully");
						folder.removeEmail(Integer.parseInt(index));
					}
					else{
						Folder newFolder = new Folder(foldername);
						newFolder.addEmail(email);
						System.out.println("Email added to "+ folder.getName() + " successfully");
						newFolder.removeEmail(Integer.parseInt(index));
					}

				}
				break;
			}
			case "D":{
				System.out.println("Enter email index: ");
				String index = keyboard.nextLine();
				Email email = folder.getEmails().get(Integer.parseInt(index) - 1);
				if(email == null){
					System.out.println("Email does not exist");
					break;
				}
				else{
					folder.removeEmail(Integer.parseInt(index));
					System.out.println(email +" successfully  to trash");
					break;
				}
			}
			case "V":{
				System.out.print("Enter email index: ");
				String index = keyboard.nextLine();
				if(Integer.parseInt(index) > folder.getEmails().size()){
					System.out.println("Index is out of range " + folder.getEmails().size());
					break;
				}
				else{
					Email email = folder.getEmails().get(Integer.parseInt(index) - 1);
					if(email == null){
						System.out.println("Email does not exist");
						break;
					}
					else{
						System.out.println("To: " + email.getTo());
						System.out.println("CC: " + email.getCc());
						System.out.println("BCC: " + email.getBcc());
						System.out.println("Subject: "+ email.getSubject());
						System.out.println("Body: "+ email.getBody());
						break;
					}	
				}
			}
			case "SA":{
				folder.sortBySubjectAscending();
				folder.getFolderNameInString();
				break;
			}
			case "SD":{
				folder.sortBySubjectDescending();
				folder.getFolderNameInString();
				break;
			}
			case "DA": {
				folder.sortByDateAscending();
				folder.getFolderNameInString();
				break;
			}
			case "DD":{
				folder.sortByDateDescending();
				folder.getFolderNameInString();
				break;
			}
			case "R": {
				done = true;
				break;
			}
			default: {
				System.out.println("Invalid choice");
			}

			}
		}
	}
}
