package com.Controller;

import com.Model.Cards.Card;
import com.Model.Cards.CreditCard;
import com.Model.Cards.MainCard;
import com.Model.Cards.SlaveCard;
import com.Model.Client.Client;

import com.Model.Exception.SystemException;
import com.Utils.MetaDataFormatter;
import com.Model.Exception.FilmException;
import com.Model.Exception.PasswordException;
import com.Model.Exception.RentException;
import com.Model.Movies.DvD;
import com.Model.Movies.Film;

import java.io.*;
import java.util.*;

/**
 * Kal2000 is the controller.
 * Contain "dataBase" :
 *     private ArrayList<Film> films6beerVideo;
 *     private ArrayList<Client> clients;
 *     private HashMap<DvD, Integer> dvds;
 *
 * Can save en load data from files
 */
public class Kal2000 {
    /**
     * path of save file
     */
    private final String path = ".";

    /**
     * number of dvd that engine can contain
     */
    private final int maxDvd = 100;

    /**
     * List of films in CyberVideo
     */
    private ArrayList<Film> films6beerVideo;

    /**
     * List of client register in Al2000
     */
    private ArrayList<Client> clients;

    /**
     * List of Dvd in Al2000, the integer is the number of exemplary
     */
    private HashMap<DvD, Integer> dvds;


    public Kal2000() {
        this.films6beerVideo = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.dvds = new HashMap<>();
    }

    /**
     * Register a client in Al2000
     * @param client the client to register
     * @throws SystemException throws if client already exist
     */
    public void addCLient(Client client) throws SystemException {
        if(this.containClient(client)) throw new SystemException("client already exist");;
        this.clients.add(client);
    }

    /**
     * Allow to add an ArrayList of client, useful when load the database from file
     * @param clients ArrayList of client
     */
    public void addCLients(ArrayList<Client> clients){
        this.clients.addAll(clients);
    }

    /**
     * Allow to add an ArrayList of films, useful when load the database from file
     * @param films ArrayList of films
     */
    public void addFilms(ArrayList<Film> films){
        this.films6beerVideo.addAll(films);
    }

    /**
     * Allow to add an HashMap of DvD, useful when load the database from file
     * @param dvds the HashMap of DvD
     */
    public void addDvds(HashMap<DvD, Integer> dvds){
        this.ensureDvdCapacity(dvds.size());
        this.dvds.putAll(dvds);
    }

    /**
     * Return a Client by compare password and idCard,
     * This method is used to connect a client.
     *
     * @param password the client password
     * @param idCard the id of the card insert in Al2000
     * @return the found client or throw <code>PasswordException</code>
     * @throws PasswordException if Incorrect Password
     */
    public Client getClient(String password, int idCard) throws PasswordException {
        for(Client client : this.clients){
            if(Objects.equals(client.getPassword(), password)){
                try{
                    this.getCard(client, idCard);
                }catch (NoSuchElementException e){
                    continue;
                }
                return client;
            }
        }
        throw new PasswordException("Incorrect Password");
    }

    /**
     * Return the card from a Client and the idCard (idCard given by card reader in Al200)
     *
     * @param client the client
     * @param idCard the id of the card
     * @return the <code>Card</code> found
     * @throws NoSuchElementException if no card found raise this exception
     */
    public Card getCard(Client client, int idCard) throws NoSuchElementException {

        for (CreditCard creditcard : client.getCreditCards()) { // check if the desired card is a CreditCard
            if(idCard == creditcard.getNumCard())
                return creditcard;
        }

        for (MainCard mainCard : client.getMainCards()) { // check if the desired card is a MainCard
            if(idCard == mainCard.getId())
                return mainCard;
        }

        for (MainCard mainCard : client.getMainCards()) { // check if the desired card is a SlaveCard
            for (SlaveCard slaveCard : mainCard.getSlaveCards()) {
                if(idCard == slaveCard.getId())
                    return slaveCard;
            }
        }

        throw new NoSuchElementException("card not found"); // not card Found
    }

    /**
     * This method allow Al2000 to boot
     *
     * Load database from Saving File
     *
     * @throws InterruptedException
     * @throws ClassNotFoundException if doesn't find class in classpath
     * @throws IOException if file not found, print : "/!\ save files not found"
     */
    public void boot() throws InterruptedException, ClassNotFoundException, IOException {
        System.out.print("Booting ");

		ObjectInputStream inputStream;
		try {
			inputStream = new ObjectInputStream(new FileInputStream(path+"/saveVideos")); //load films
			this.films6beerVideo = (ArrayList<Film>) inputStream.readObject();
			inputStream.close();

			inputStream = new ObjectInputStream(new FileInputStream(path+"/saveClients")); //load clients
			this.clients = (ArrayList<Client>) inputStream.readObject();
			inputStream.close();

			inputStream = new ObjectInputStream(new FileInputStream(path+"/saveDvDs")); //load dvds
			this.dvds = (HashMap<DvD, Integer>) inputStream.readObject();
			inputStream.close();
		} catch (IOException e) { // catch files not found exception
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

    /**
     * Power off All2000
     *
     * Save all data in file
     *
     * @throws InterruptedException
     * @throws IOException if file not found, print : "/!\ save files not found"
     */
    public void powerOff() throws InterruptedException, IOException {
		System.out.print("PowerOff ");

		ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(path+"/saveVideos")); //save films
		outputStream.writeObject(this.films6beerVideo);
		outputStream.close();

		outputStream = new ObjectOutputStream(new FileOutputStream(path+"/saveClients"));//save client
		outputStream.writeObject(this.clients);
		outputStream.close();

		outputStream = new ObjectOutputStream(new FileOutputStream(path+"/saveDvDs"));//save dvds
		outputStream.writeObject(this.dvds);
		outputStream.close();


        for(int i = 0; i < 10; i++){
            System.out.print(".");
            Thread.sleep(100);
        }
        Thread.sleep(100);
        System.out.println("GoodBye");
    }

    /**
     * "Physically" give a dvd (when rent one for example)
     *
     * @param dvd the dvd
     * @throws RentException if they are not dvd exemplary
     */
    public void giveDvd(DvD dvd) throws RentException {
        if(this.dvds.get(dvd) == 0) throw new RentException("Dvd not available");
        this.dvds.put(dvd, this.dvds.get(dvd) - 1); // give dvd, to remove 1 exemplary from database
    }

    /**
     * When insert "Physically" a dvd to All2000
     *
     * @param dvd the insert dvd
     */
    public void returnDvd(DvD dvd){
        this.dvds.put(dvd, this.dvds.get(dvd)+1);
    }

    /**
     * Get all dvd in All2000
     * @return HashMap<DvD, Integer>
     */
    public HashMap<DvD, Integer> getDvds() {
        return this.dvds;
    }

    /**
     * Admin Method
     * "Physically" a new dvd
     * if dvd already exist upload the number of exemplary
     * @param dvd the dvd
     * @param nbDvd nb exemplary of dvd
     */
    public void addDvd(DvD dvd, int nbDvd) {
        this.ensureDvdCapacity();
        this.dvds.put(dvd,nbDvd);
    }

    /**
     * Admin Method
     * "Physically" an exemplary of a dvd present in All2000
     *
     * @param dvd the dvd
     * @throws NoSuchElementException dvd not found in kal2000
     */
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

    /**
     * Admin Method
     * Remove all exemplary (and the dvd) from ALl2000
     * @param dvd the dvd to remove
     */
    public void removeDvd(DvD dvd){
         this.dvds.remove(dvd);
    }

    /**
     * Admin Method
     *
     * Remove a Film from dataBase
     * @param film
     * @return <code>true</code> if film remove, <code>false</code> otherwise
     */
    public boolean removeFilm(Film film){
        return this.films6beerVideo.remove(film);
    }

    /**
     * get all Film in CyberVideo
     * @return ArrayList<Film>
     */
    public ArrayList<Film> getFilms6beerVideo() {
        return films6beerVideo;
    }

    /**
     * add a film in database
     *
     * @param film the film to add
     * @throws FilmException film already exist
     */
    public void addFilm(Film film) throws FilmException {
        for(Film f : this.films6beerVideo){
            if(f.getId() == film.getId()) throw new FilmException("film with id " + film.getId() + " already exist");
        }
        this.films6beerVideo.add(film);
    }

    /**
     * ensure that don't overflow dvd capacity
     */
    private void ensureDvdCapacity() throws RuntimeException{
        if(this.getNbDvd() >= maxDvd) throw new RuntimeException("Can not add dvd, capacity exceed");
    }

    /**
     * ensure that don't overflow dvd capacity
     * @param nbDvd number of dvd that will be added
     */
    private void ensureDvdCapacity(int nbDvd){
        if(this.getNbDvd() + nbDvd >= maxDvd) throw new RuntimeException("Can not add dvd, capacity exceed");
    }

    /**
     * Return number of dvd in kall2000
     * @return number of dvd in kall2000
     */
    private int getNbDvd(){
        int nbDvd = 0;
        for(int nb : this.dvds.values()) nbDvd +=nb;
        return nbDvd;
    }

    /**
     * Get A Film By id
     * @param id id of film
     * @return the film found
     * @throws SystemException if Film not found
     */
    public Film getFilmdById(int id) throws SystemException {
        for (Film film : films6beerVideo){
            if(film.getId() == id) return film;
        }
        throw new SystemException("Film " + id + "not found");
    }

    /**
     * Get a dvd by id
     * @param id id of the dvd
     * @return dvd found
     * @throws SystemException if Dvd not found
     */
    public DvD getDvdById(int id) throws SystemException {
        for (Map.Entry<DvD, Integer> dvdList : this.dvds.entrySet()) {
            if(dvdList.getKey().getId() == id) return dvdList.getKey();
        }

        throw new SystemException("Dvd with " + id + "not found");
    }

    /**
     * Return an HashMap<Film, Integer>,
     * Where the Integer is the number of film rentals
     *
     * For each film the number of rentals
     *
     * @return HashMap<Film, Integer>,
     */
    public HashMap<Film, Integer> getFilmStat(){
        HashMap<Film, Integer> statsFilm = new HashMap<>();
        ArrayList<MetaDataFormatter> metaDataFormatters = this.getClientMetaData();
        for (MetaDataFormatter metaDataFormatter : metaDataFormatters){
            for (Map.Entry<Film, Integer> cardArrayListEntry : metaDataFormatter.getFilmStat().entrySet()) {
                Integer nbRented = cardArrayListEntry.getValue();
                statsFilm.put(cardArrayListEntry.getKey(), nbRented);
            }
        }
        return statsFilm;
    }

    /**
     * Return MetaData of client
     * MetaDataFormatter allow to get calculated information related to a client.
     *
     * This method build a MetaDataFormatter by client
     * @return ArrayList<MetaDataFormatter>
     */
    public ArrayList<MetaDataFormatter> getClientMetaData(){
        ArrayList<MetaDataFormatter> metaDatumFormatters = new ArrayList<>();
        for(Client client : this.clients){
            metaDatumFormatters.add(new MetaDataFormatter(client));
        }
        return metaDatumFormatters;
    }

    /**
     * If All2000 contain the client passing in param
     *
     * @param client
     * @return <code>True</code> if exist, <code>False</code> otherwise
     */
    public boolean containClient(Client client){
        for (Client client1 : clients){
            if(client1.equals(client)) return true;
        }
        return false;
    }

    /**
     * Print details for all Dvds
     * @return the string to display
     */
    public String printDvds() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<DvD, Integer> dvdList : this.dvds.entrySet()) {
            stringBuilder.append(dvdList.getKey().getFilm().getTitle()).append(" nb dvd : ").append(dvdList.getValue()).append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Print details for all client
     * @return  the string to display
     */
    public String printClients(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Client client : this.clients){
            stringBuilder.append(client.toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}
