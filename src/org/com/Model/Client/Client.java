package org.com.Model.Client;

import org.com.Model.Cards.CreditCard;
import org.com.Model.Cards.MainCard;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Class Client
 *
 */
public class Client implements Serializable {
	/**
	 * informations of the client
	 */
	private Human person;
	
	/**
	 * 1 if he is a sub, 0 otherwise
	 */
	private boolean sub;
	
	/**
	 * client's password
	 */
	private String password;
	
	/**
	 * client's main cards list
	 */
	private ArrayList<MainCard> mainCards;
	
	/**
	 *  client's credit cards list
	 */
	private ArrayList<CreditCard> creditCards;
	
	
	public Client(String fristName, String lastName,String password,CreditCard creditCard) {
		this.person = new Human(fristName,lastName);
		this.sub = false;
		this.password = password;
		this.creditCards = new ArrayList<>();
		this.creditCards.add(creditCard);
		this.mainCards = new ArrayList<>();
	}
	
	/**
	 * Check if the client is a sub.
	 * @return <code>true</code> if he is, <code>false</code> otherwise.
	 */
	public boolean isSub() {
		return this.sub;
	}
	
	public void setSub(boolean b) {
		this.sub=b;
	}
	
	/**
	 * Get human information of the client.
	 * @return human
	 */
	public Human getPerson() {
		return this.person;
	}
	
	/**
	 * Get the password of the client.
	 * @return password
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * Get all credit cards of the client.
	 * @return ArrayList<CreditCard>
	 */	
	public ArrayList<CreditCard> getCreditCards() {
		return this.creditCards;
	}
	
	/**
	 * Get all main cards of the client.
	 * @return ArrayList<MainCard>
	 */		
	public ArrayList<MainCard> getMainCards() {
		return this.mainCards;
	}

	/**
	 * Check if the card given belong to the client.
	 * @param card
	 * @return <code>true</code> if it does, <code>false</code> otherwise.
	 */
	private boolean containCreditCard(ArrayList<CreditCard> card){
		for (CreditCard creditCard : creditCards){
			for (CreditCard creditCard1 : card){
				if(creditCard.equals(creditCard1))return true;
			}
		}
		return false;
	}

	/**
	 * Create a main card linked to the credit card given.
	 * @param creditCard
	 * @return the created main card
	 */
	public MainCard createMainCard(CreditCard creditCard){
		this.mainCards.add(new MainCard(creditCard));
		return this.mainCards.get(this.mainCards.size() - 1);
	}

	@Override
	public String toString() {
		return this.person.toString() + this.creditCards;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Client)) return false;
		Client client = (Client) o;
		return Objects.equals(getPassword(), client.getPassword()) &&
				this.containCreditCard(client.getCreditCards());
	}
}