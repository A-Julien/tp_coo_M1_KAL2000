package Ui;

import Cards.Card;
import Cards.CreditCard;
import Cards.SlaveCard;
import Exception.CardException;
import Exception.PasswordException;
import Exception.SystemException;
import KAL2000.Client;
import KAL2000.Kal2000;

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
                             Kal2000 kal2000) throws SystemException {

        CreditCard creditCard = new CreditCard(idCreditCard);
        Client client = new Client(firstName, lastName, password,creditCard);
        kal2000.addCLient(client);

    }

    public boolean isClientConnected(){
        return this.connectedClient != null;
    }

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

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isSlave(){
        return this.connectedCard instanceof SlaveCard;
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
        this.isAdmin=false;
    }


}
