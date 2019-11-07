package Ui;

import Cards.Card;
import Cards.CreditCard;
import Errors.CardError;
import Errors.PasswordError;
import KAL2000.Client;
import KAL2000.Kal2000;

public class UserInterface implements Logging{
    private Card connectedCard;
    private Client connectedClient;


    public Card getConnectedCard() {
        return connectedCard;
    }
    public Client getConnectedClient() {
        return connectedClient;
    }


    public void createClient(String firstName,
                             String lastName,
                             String password,
                             int idCreditCard,
                             Kal2000 kal2000){

        CreditCard creditCard = new CreditCard(idCreditCard);
        Client client = new Client(firstName, lastName, password,creditCard);
        kal2000.addCLient(client);
        this.connect(client, creditCard);
    }

    @Override
    public boolean connect(int idCard, String password, Kal2000 kal2000){
        try {
            this.connectedClient = kal2000.getClient(password);
            this.connectedCard = kal2000.getCard(this.connectedClient, idCard);
        } catch (PasswordError | CardError e) {
            return false;
        }
        return true;
    }

    @Override
    public void connect(Client client, Card card) {
        this.connectedCard = card;
        this.connectedClient = client;
    }

    @Override
    public void disconnect(){
        this.connectedClient = null;
        this.connectedCard = null;
    }
}
