package KAL2000;

import Cards.*;
import Exception.CardException;
import Exception.PasswordException;
import Exception.FilmException;
import Exception.RentException;
import Exception.SystemException;
import Util.MetaDataFormatter;

import java.io.*;
import java.util.*;

public class Kal2000 {
    private final String path = ".";
    private final int maxDvd = 100;

    private ArrayList<Film> films6beerVideo;
    private ArrayList<Client> clients;
    private HashMap<DvD, Integer> dvds;


    public Kal2000() {
        this.films6beerVideo = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.dvds = new HashMap<>();
    }

    public void addCLient(Client client) throws SystemException {
        if(this.cointainClient(client)) throw new SystemException("client already exist");;
        this.clients.add(client);
    }

    public void addCLients(ArrayList<Client> clients){
        this.clients.addAll(clients);
    }

    public void addFilms(ArrayList<Film> films){
        this.films6beerVideo.addAll(films);
    }

    public void addDvds(HashMap<DvD, Integer> dvds){
        this.ensureDvdCapacity(dvds.size());
        this.dvds.putAll(dvds);
    }

    //TODO add dvds && add Films
    public Client getClient(String password) throws PasswordException {

        for(Client client : this.clients)
            if(Objects.equals(client.getPassword(), password)) return client;

        throw new PasswordException("Incorrect Password");
    }

    public Card getCard(Client client, int idCard) throws NoSuchElementException {

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
			inputStream = new ObjectInputStream(new FileInputStream(path+"/saveVideos"));
			this.films6beerVideo = (ArrayList<Film>) inputStream.readObject();
			inputStream.close();

			inputStream = new ObjectInputStream(new FileInputStream(path+"/saveClients"));
			this.clients = (ArrayList<Client>) inputStream.readObject();
			inputStream.close();

			inputStream = new ObjectInputStream(new FileInputStream(path+"/saveDvDs"));
			this.dvds = (HashMap<DvD, Integer>) inputStream.readObject();
			inputStream.close();
		} catch (IOException e) {
			System.out.println("/!\\ save files not found");
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

		ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(path+"/saveVideos"));
		outputStream.writeObject(this.films6beerVideo);
		outputStream.close();

		outputStream = new ObjectOutputStream(new FileOutputStream(path+"/saveClients"));
		outputStream.writeObject(this.clients);
		outputStream.close();

		outputStream = new ObjectOutputStream(new FileOutputStream(path+"/saveDvDs"));
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
        for(Film f : this.films6beerVideo){
            if(f.getId() == film.getId()) throw new FilmException("film with id " + film.getId() + " already exist");
        }
        this.films6beerVideo.add(film);
    }

    private void ensureDvdCapacity(){
        if(this.getNbDvd() >= maxDvd) throw new RuntimeException("Can not add dvd, capacity exceed");
    }

    private void ensureDvdCapacity(int nbDvd){
        if(this.getNbDvd() + nbDvd >= maxDvd) throw new RuntimeException("Can not add dvd, capacity exceed");
    }

    private int getNbDvd(){
        int nbDvd = 0;
        for(int nb : this.dvds.values()) nbDvd +=nb;
        return nbDvd;
    }

    public Film getFilmdById(int id) throws SystemException {
        for (Film film : films6beerVideo){
            if(film.getId() == id) return film;
        }
        throw new SystemException("Dvd Film " + id + "not found");
    }

    public DvD getDvdById(int id) throws SystemException {
        for (Map.Entry<DvD, Integer> dvdList : this.dvds.entrySet()) {
            if(dvdList.getKey().getId() == id) return dvdList.getKey();
        }

        throw new SystemException("Dvd with " + id + "not found");
    }

    public HashMap<Film, Integer> getFilmStat(){
        HashMap<Film, Integer> statsFilm = new HashMap<>();
        ArrayList<MetaDataFormatter> metaDataFormatters = this.getClientMetaData();
        for (MetaDataFormatter metaDataFormatter : metaDataFormatters){
            for (Map.Entry<Film, Integer> cardArrayListEntry : metaDataFormatter.getFilmStat().entrySet()) {
                Map.Entry elem = cardArrayListEntry;
                Integer nbRented = (Integer) elem.getValue();
                statsFilm.put((Film) elem.getKey(), nbRented);
            }
        }
        return statsFilm;
    }

    public ArrayList<MetaDataFormatter> getClientMetaData(){
        ArrayList<MetaDataFormatter> metaDatumFormatters = new ArrayList<>();
        for(Client client : this.clients){
            metaDatumFormatters.add(new MetaDataFormatter(client));
        }
        return metaDatumFormatters;
    }

    public boolean cointainClient(Client client){
        for (Client client1 : clients){
            if(client1.equals(client)) return true;
        }
        return false;
    }

    public String printDvds() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<DvD, Integer> dvdList : this.dvds.entrySet()) {
            Map.Entry<DvD, Integer> elem = dvdList;
            stringBuilder.append(elem.getKey().getFilm().getTitle()).append(" nb dvd : ").append(elem.getValue()).append("\n");
        }
        return stringBuilder.toString();
    }

    public String printClients(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Client client : this.clients){
            stringBuilder.append(client.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

}
