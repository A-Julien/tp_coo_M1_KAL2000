package KAL2000;

import Cards.*;
import Errors.ClientError;
import sun.rmi.rmic.Main;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class Kal2000 {


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


    public Client getClient(String password) throws Exception {

        for(Client client : this.clients)
            if(Objects.equals(client.getPassword(), password)) return client;

        throw new ClientError("Incorrect Password");
    }

    public Card getCard(Client client, int idCard){
        /*if(card instanceof CreditCard){
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
        throw new Exception("404 not fount");*/
        return null;
    }

    public void boot() throws InterruptedException, ClassNotFoundException, IOException {
        System.out.print("Booting ");

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

        for(int i = 0; i < 10; i++){
            System.out.print(".");
            Thread.sleep(100);
        }
        Thread.sleep(100);
        System.out.println("TADA");
        System.out.println("Welcome to KAL2000 !");
    }

    public void powerOff() throws InterruptedException, IOException {
		System.out.print("PowerOff ");

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


        for(int i = 0; i < 10; i++){
            System.out.print(".");
            Thread.sleep(100);
        }
        Thread.sleep(100);
        System.out.println("GoodBye");
    }

}
