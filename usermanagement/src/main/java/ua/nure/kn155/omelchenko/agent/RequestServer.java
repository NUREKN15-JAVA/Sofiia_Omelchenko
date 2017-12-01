package ua.nure.kn155.omelchenko.agent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import ua.nure.kn155.omelchenko.User;
import ua.nure.kn155.omelchenko.db.DaoFactory;
import ua.nure.kn155.omelchenko.db.DatabaseException;

public class RequestServer extends CyclicBehaviour {

	@Override
	public void action() {
		ACLMessage message = myAgent.receive();
		if ( message != null) {
			if(message.getPerformative() == ACLMessage.REQUEST) {
				myAgent.send(createReply(message));
			}
			if(message.getPerformative() == ACLMessage.INFORM) {
				Collection<User> users = parseMessage(message);
				((SearchAgent) myAgent).showUsers(users);
			}
		}else {
			block();
		}
	}

	private Collection<User> parseMessage(ACLMessage message) {
		Collection<User> users = new LinkedList<User>();
		String content = message.getContent();
		if (content != null) {
			StringTokenizer tokenizer = new StringTokenizer(content, ";");
			while (tokenizer.hasMoreTokens()) {
				String userInfo = tokenizer.nextToken();
				StringTokenizer stringTokenizer= new StringTokenizer(userInfo, ",");
				String id = stringTokenizer.nextToken();
				String firstName = stringTokenizer.nextToken();
				String lastName = stringTokenizer.nextToken();
				users.add(new User(new Long(id), firstName, lastName, null));
			}
		}
		return users;
	}

	private ACLMessage createReply(ACLMessage message) {
		ACLMessage reply = message.createReply();
		String content = message.getContent();
		StringTokenizer tokenizer = new StringTokenizer(content, ",");
		if (tokenizer.countTokens() == 2) {
			String firstName = tokenizer.nextToken();
			String lastName = tokenizer.nextToken();
			Collection<User> users = null;
			try {
				users = DaoFactory.getInstance().getUserDao().find(firstName, lastName);
			} catch (DatabaseException e) {
				e.printStackTrace();
				users = new ArrayList(0);
			}
			StringBuffer buffer = new StringBuffer();
			for (Iterator it = (Iterator) users.iterator(); it.hasNext();) {
				User user = (User) it.next();
				buffer.append(user.getId()).append(",");
				buffer.append(user.getFirstName()).append(",");
				buffer.append(user.getLastName()).append(";");
			}
			reply.setContent(buffer.toString());
		}
		return reply;
	}

}
