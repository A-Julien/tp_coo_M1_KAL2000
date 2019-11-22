package com.Model.Client;

import com.Model.Cards.CreditCard;
import com.Model.Cards.MainCard;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import com.Model.Utils.*;

public class Client implements Serializable {
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

	private boolean containCreditCard(ArrayList<CreditCard> card){
		for (CreditCard creditCard : creditCards){
			for (CreditCard creditCard1 : card){
				if(creditCard.equals(creditCard1))return true;
			}
		}
		return false;
	}

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