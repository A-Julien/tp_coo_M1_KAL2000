package com.Controller;

import com.Model.Cards.Card;
import com.Model.Cards.CreditCard;
import com.Model.Cards.SlaveCard;
import com.Model.Exception.CardException;
import com.Model.Exception.PasswordException;
import com.Model.Exception.SystemException;
import com.Model.Client.Client;

/**
 * 
 * @author feydelh
 *
 */
public class UserInterface implements Loggable {
    private static int adminId = 12345;
    private Card connectedCard;
    private Client connectedClient;
    private boolean isAdmin;

    public UserInterface() {
        this.connectedCard = null;
        this.connectedClient = null;
        this.isAdmin = false;
    }

    /**
     * 
     * @return connectedCard
     */
    public Card getConnectedCard() {
        return connectedCard;
    }
    
    /**
     * 
     * @return connectedClient
     */
    public Client getConnectedClient() {
        return connectedClient;
    }


    /**
     * Create a client with the informations given.
     * @param firstName client's first name
     * @param lastName client's last name
     * @param password client's password
     * @param idCreditCard client's credit card number
     * @param kal2000 KAL2000 engine
     * @throws SystemException when the client already exists
     */
    public void createClient(String firstName,
                             String lastName,
                             String password,
                             int idCreditCard,
                             Kal2000 kal2000) throws SystemException {

        CreditCard creditCard = new CreditCard(idCreditCard);
        Client client = new Client(firstName, lastName, password,creditCard);
        kal2000.addCLient(client);

    }

    /**
     * 
     * @return <code>true</code> if a client is connect, <code>false<code> otherwise.
     */
    public boolean isClientConnected(){
        return this.connectedClient != null;
    }
    
	/**
	 * Connect the client, if it's possible according informations he gives.
	 * @param id client's card number
	 * @param password client's password
	 * @param kal2000 KAL2000 engine
	 * @throws PasswordException when password doesn't match with id
	 * @throws CardException when no card have been found
	 */
    @Override
    public void connect(int idCard, String password, Kal2000 kal2000) throws PasswordException, CardException {
        if (idCard == adminId && password.equals(String.valueOf(adminId))) {
            this.isAdmin = true;
            return;
        }
        System.out.println(idCard);

        this.connectedClient = kal2000.getClient(password, idCard);
        this.connectedCard = kal2000.getCard(this.connectedClient, idCard);
    }

    /**
     * 
     * @return <code>true</code> if the person connecter is admin, <code>false<code> otherwise.
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * 
     * @return <code>true</code> if the connected card is a slave card, <code>false<code> otherwise.
     */
    public boolean isSlave(){
        return this.connectedCard instanceof SlaveCard;
    }
    
    /**
     * Connect the client and his card.
     * @param client the client
     * @param card client's card
     */
    @Override
    public void connect(Client client, Card card) {
        this.connectedCard = card;
        this.connectedClient = client;
    }
    
    /**
     * Disconnect the connected client.
     */
    @Override
    public void disconnect(){
        this.connectedClient = null;
        this.connectedCard = null;
        this.isAdmin=false;
    }


}
