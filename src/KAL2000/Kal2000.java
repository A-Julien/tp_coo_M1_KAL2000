package KAL2000;
import java.text.DateFormat;
import java.util.Date;
import java.util.ArrayList;

import Cards.*;
import sun.rmi.rmic.Main;

import java.io.*;
import java.util.ArrayList;

public class Kal2000 {
<<<<<<< Updated upstream
	
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
=======


    private ArrayList<Film> films6beerVideo;
    private ArrayList<Client> clients;
    private ArrayList<DvD> dvds;


    public Kal2000() {
        this.films6beerVideo = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.dvds = new ArrayList<>();
    }

    public void addCLient(Client client){
        this.clients.add(client);
    }

    public rentDvd(){

    }

    public Client getClient(Card card) throws Exception {
        if(card instanceof CreditCard){
            for(Client client:clients){
                if(client.getCreditCard().getNumCreditCard() == ((CreditCard)card).getNumCreditCard())
                    return client;
            }
        }

        if(card instanceof SubCard){
            if(card instanceof SlaveCard){
                for(Client client:clients){
                    for(SubCard mainCard:client.getSubCard()){
                        if(mainCard.getId() == ((SlaveCard)card).getIdMainCard()) return client;
                    }
                }
            }
        }
        throw new Exception("404 not fount");
    }

    public void boot() throws InterruptedException, ClassNotFoundException, IOException {
        System.out.print("Booting ");
        for(int i = 0; i < 10; i++){
            System.out.print(".");
            Thread.sleep(100);
        }
        Thread.sleep(100);
        System.out.println("TADA");
        System.out.println("Welcome to KAL2000 !");

        ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream(
                    "/Users/julien/Documents/M1NFO/COO/tp_coo_M1_KAL2000/saveVideos"));
            this.films6beerVideo = (ArrayList<Film>) inputStream.readObject();
            inputStream.close();

            inputStream = new ObjectInputStream(new FileInputStream(
                    "/Users/julien/Documents/M1NFO/COO/tp_coo_M1_KAL2000/saveClients"));
            this.clients = (ArrayList<Client>) inputStream.readObject();
            inputStream.close();

            inputStream = new ObjectInputStream(new FileInputStream(
                    "/Users/julien/Documents/M1NFO/COO/tp_coo_M1_KAL2000/saveDvDs"));
            this.dvds = (ArrayList<DvD>) inputStream.readObject();
            inputStream.close();
        } catch (IOException e) {
            System.out.println("no save files found");
        }
    }

    public void powerOff() throws InterruptedException, IOException {
        System.out.print("PowerOff ");
        for(int i = 0; i < 10; i++){
            System.out.print(".");
            Thread.sleep(100);
        }
        Thread.sleep(100);
        System.out.println("GoodBye");
        this.films6beerVideo.add(new Film());

        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(
                                "/Users/julien/Documents/M1NFO/COO/tp_coo_M1_KAL2000/saveVideos"));
        outputStream.writeObject(this.films6beerVideo);
        outputStream.close();

        outputStream = new ObjectOutputStream(new FileOutputStream(
                "/Users/julien/Documents/M1NFO/COO/tp_coo_M1_KAL2000/saveClients"));
        outputStream.writeObject(this.clients);
        outputStream.close();

        outputStream = new ObjectOutputStream(new FileOutputStream(
                "/Users/julien/Documents/M1NFO/COO/tp_coo_M1_KAL2000/saveDvDs"));
        outputStream.writeObject(this.clients);
        outputStream.close();
    }

}
>>>>>>> Stashed changes
