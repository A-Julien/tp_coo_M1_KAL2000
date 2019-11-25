package com.Controller;

import com.Model.Cards.Card;
import com.Model.Client.Client;
import com.Model.Exception.CardException;
import com.Model.Exception.PasswordException;


public interface Loggable {
	
	/**
	 * Connect the client, if it's possible according informations he gives.
	 * @param id client's card number
	 * @param password client's password
	 * @param kal2000 KAL2000 engine
	 * @throws PasswordException when password doesn't match with id
	 * @throws CardException when no card have been found
	 */
    void connect(int id, String password, Kal2000 kal2000) throws PasswordException, CardException;
    
    /**
     * Connect the client and his card.
     * @param client the client
     * @param card client's card
     */
    void connect(Client client, Card card);
    
    /**
     * Disconnect the connected client.
     */
    void disconnect();
}
