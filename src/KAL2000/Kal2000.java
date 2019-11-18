package KAL2000;

import Cards.*;
import Exception.CardException;
import Exception.PasswordException;
import Exception.FilmException;
import Exception.RentException;
import Util.MetaData;

import java.io.*;
import java.util.*;

public class Kal2000 {
    private final int maxDvd = 100;

    private ArrayList<Film> films6beerVideo;
    private ArrayList<Client> clients;
    private HashMap<DvD, Integer> dvds;


    public Kal2000() {
        this.films6beerVideo = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.dvds = new HashMap<>();
    }

    public void addCLient(Client client){
        this.clients.add(client);
    }


    public Client getClient(String password) throws PasswordException {

        for(Client client : this.clients)
            if(Objects.equals(client.getPassword(), password)) return client;

        throw new PasswordException("Incorrect Password");
    }

    public Card getCard(Client client, int idCard) throws CardException {

        for (CreditCard credicard : client.getCreditCards()) {
            if(idCard == credicard.getNumCard())
                return credicard;
        }

        for (MainCard mainCard : client.getMainCards()) {
            if(idCard == mainCard.getId())
                return mainCard;
        }

        for (MainCard mainCard : client.getMainCards()) {
            for (SlaveCard slaveCard : mainCard.getSlaveCards()) {
                if(idCard == slaveCard.getId())
                    return slaveCard;
            }
        }

        throw new NoSuchElementException("card not found");
    }

    public void boot() throws InterruptedException, ClassNotFoundException, IOException {
        System.out.print("Booting ");

		ObjectInputStream inputStream;
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
			this.dvds = (HashMap<DvD, Integer>) inputStream.readObject();
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
		outputStream.writeObject(this.dvds);
		outputStream.close();


        for(int i = 0; i < 10; i++){
            System.out.print(".");
            Thread.sleep(100);
        }
        Thread.sleep(100);
        System.out.println("GoodBye");
    }

    public void giveDvd(DvD dvd) throws RentException {
        if(this.dvds.get(dvd) == 0) throw new RentException("Dvd not available");
        this.dvds.put(dvd, this.dvds.get(dvd) - 1);
    }

    public HashMap<DvD, Integer> getDvds() {
        return this.dvds;
    }

    public void addDvd(DvD dvd, int nbDvd) {
        this.ensureDvdCapacity();
        this.dvds.put(dvd,nbDvd);
    }

    public void addDvd(DvD dvd){
        this.ensureDvdCapacity();
        for (DvD d : this.dvds.keySet()){
            if(d.getId() == dvd.getId()){
                this.dvds.put(dvd, this.dvds.get(dvd)+1);
                return;
            }
        }
        throw new NoSuchElementException("dvd not found in kal2000");
    }

    public void removeDvd(DvD dvd){
         this.dvds.remove(dvd);
    }

    public boolean removeFilm(Film film){
        return this.films6beerVideo.remove(film);
    }

    public ArrayList<Film> getFilms6beerVideo() {
        return films6beerVideo;
    }

    public void addFilm(Film film) throws FilmException {
        for(Film f : this.films6beerVideo) if(f.getId() == film.getId()) this.films6beerVideo.add(film);
        throw new FilmException("film with id " + film.getId() + " already exist");
    }

    private void ensureDvdCapacity(){
        if(this.getNbDvd() >= maxDvd) throw new RuntimeException("Can not add dvd, capacity exceed");
    }

    private int getNbDvd(){
        int nbdvd = 0;
        for(int nb : this.dvds.values()) nbdvd +=nb;
        return nbdvd;
    }



    public HashMap<Film, Integer> getFilmStat(){
        HashMap<Film, Integer> statsFilm = new HashMap<>();
        ArrayList<MetaData> metaDatas = this.getClientMetaData();
        for (MetaData metaData : metaDatas){
            for (Map.Entry<Film, Integer> cardArrayListEntry : metaData.getFilmStat().entrySet()) {
                Map.Entry elem = cardArrayListEntry;
                Integer nbRented = (Integer) elem.getValue();
                statsFilm.put((Film) elem.getKey(), nbRented);
            }
        }
        return statsFilm;
    }

    public ArrayList<MetaData> getClientMetaData(){
        ArrayList<MetaData> metaData = new ArrayList<>();
        for(Client client : this.clients){
            metaData.add(new MetaData(client));
        }
        return metaData;
    }
}
