package ua.nure.kn155.omelchenko.agent;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class SearchRequestBehavior extends Behaviour {
	private AID[] aids;
	private String firstName;
	private String lastName;

	public SearchRequestBehavior(AID[] aids, String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.aids = aids;
	}

	@Override
	public void action() {
		ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
		aclMessage.setContent(firstName + ',' + lastName);
		if (aids != null) {
			for (AID aid : aids) {
				aclMessage.addReceiver(aid);
			}
			myAgent.send(aclMessage);
		}
	}

	@Override
	public boolean done() {
		return true;
	}

}
