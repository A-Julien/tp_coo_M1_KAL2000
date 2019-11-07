package KAL2000;
import java.text.DateFormat;
import java.util.Date;
import java.util.ArrayList;

public class Kal2000 {
	
	private Date dateActuelle;
	private ArrayList<Rent> listRent;
	private ArrayList<DvD> dvdsKAL2000;
	private ArrayList<Client> clients;
	private ArrayList<Film> filmsCyberVideo;
	
	public Kal2000(Date d) {
		this.dateActuelle = d;
		this.listRent = new ArrayList();
		this.dvdsKAL2000 = new ArrayList();
		this.clients = new ArrayList();
		this.filmsCyberVideo = new ArrayList();
	}
	
	public Date getDateActuelle() {
		return this.dateActuelle;
	}
	public ArrayList<Rent> getRents() {
		return this.listRent;
	}
	public ArrayList<DvD> getDvds() {
		return this.dvdsKAL2000;
	}
	public ArrayList<Client> getClients() {
		return this.clients;
	}
	public ArrayList<Film> getFilms() {
		return this.filmsCyberVideo;
	}
}