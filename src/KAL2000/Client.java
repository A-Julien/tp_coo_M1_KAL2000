package KAL2000;
import Cards.CreditCard;
import Cards.MainCard;
import Cards.SubCard;
import Util.Human;

import java.util.ArrayList;

public class Client {
	private Human person;
	private boolean sub;
	private String password;
	private ArrayList<MainCard> mainCards;
	private ArrayList<CreditCard> creditCards;
	
	
	public Client(String fristName, String lastName,String password,CreditCard creditCard) {
		this.person = new Human(fristName,lastName);
		this.sub = false;
		this.password = password;
		this.creditCards = new ArrayList<>();
		this.creditCards.add(creditCard);
		this.mainCards = new ArrayList<>();
	}
	
	public boolean isSub() {
		return this.sub;
	}
	
	public void setSub(boolean b) {
		this.sub=b;
	}
	
	public Human getPerson() {
		return this.person;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public ArrayList<CreditCard> getCreditCards() {
		return this.creditCards;
	}
	
	public ArrayList<MainCard> getMainCards() {
		return this.mainCards;
	}
}