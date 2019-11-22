package com.Controleur;

import com.Model.Cards.Card;
import com.Model.Client.Client;
import com.Controleur.Kal2000;
import com.Model.Exception.CardException;
import com.Model.Exception.PasswordException;


public interface Loggable {

    void connect(int id, String password, Kal2000 kal2000) throws PasswordException, CardException;
    void connect(Client client, Card card);
    void disconnect();
}
