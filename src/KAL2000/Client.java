package KAL2000;
import java.util.ArrayList;

public class Client {
	private Human person;
	private boolean sub;
	private String password;
	private ArrayList<SubCard> subCards;
	private ArrayList<CreditCard> creditCards;
	
	
	public Client(String fn, String ln,String p,CreditCard c) {
		this.person=new Human(fn,ln);
		this.sub=false;
		this.password = p;
		this.creditCards= new ArrayList();
		this.creditCards.add(c);
		this.subCards=new ArrayList();
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
	
	public ArrayList<SubCards> getSubCards() {
		return this.subCards;
	}
}