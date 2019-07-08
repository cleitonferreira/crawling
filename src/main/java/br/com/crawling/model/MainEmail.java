package br.com.crawling.model;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;

public class MainEmail {

	private String userName;
	private String password;
	private String receivingHost;

	public void setAccountDetails(String userName, String password) {

		this.userName = userName;
		this.password = password;

	}

	public static void main(String[] args) {

		Validate.isTrue(args.length != 2,
				"fornecer 2 argumentos: username(String) e o password(String)");

		/*
		 * String senderPassword=new String("!@s3curity"); String senderUserName=new
		 * String("crawlingbrtest");
		 */

		String username = new String(args[1]);
		String password = new String(args[2]);

		/* Crie um objeto GmailClient */
		MainEmail gmailClient = new MainEmail();

		/* Configurando detalhes da conta */
		gmailClient.setAccountDetails(username, password);

		// Envia e-mail
		// newGmailClient.sendGmail(mailFrom, mailTo, mailSubject, mailText);

		// Leitura e-mails
		gmailClient.readGmail();

	}

	public void readGmail() {

		this.receivingHost = "imap.gmail.com";

		Properties props2 = System.getProperties();

		props2.setProperty("mail.store.protocol", "imaps");

		Session session2 = Session.getDefaultInstance(props2, null);

		try {

			Store store = session2.getStore("imaps");

			store.connect(this.receivingHost, this.userName, this.password);

			Folder folder = store.getFolder("INBOX");

			folder.open(Folder.READ_ONLY);

			Message message[] = folder.getMessages();

			
			
			for (int i = 0; i < message.length; i++) {

				System.out.println(message[i].getSubject());

				//Object content = message[i].getContent();

				String result = null;

				if (message[i] instanceof MimeMessage) {
					MimeMessage m = (MimeMessage) message[i];
					Object contentObject = m.getContent();
					if (contentObject instanceof Multipart) {
						BodyPart clearTextPart = null;
						BodyPart htmlTextPart = null;
						Multipart multipart = (Multipart) contentObject;
						int count = multipart.getCount();
						for (int ii = 0; i < count; ii++) {
							BodyPart part = multipart.getBodyPart(ii);
							if (part.isMimeType("text/plain")) {
								clearTextPart = part;
								break;
							} else if (part.isMimeType("text/html")) {
								htmlTextPart = part;
							}
						}

						if (clearTextPart != null) {
							result = (String) clearTextPart.getContent();
						} else if (htmlTextPart != null) {
							String html = (String) htmlTextPart.getContent();
							result = Jsoup.parse(html).text();
						}

					} else if (contentObject instanceof String)
					{
						result = (String) contentObject;
					} else
					{
						result = null;
					}
					
					System.out.println("Mensagem: "+result);

				}
			}

			// close connections
			folder.close(true);

			store.close();

		} catch (Exception e) {

			System.out.println(e.toString());

		}

	}

}
