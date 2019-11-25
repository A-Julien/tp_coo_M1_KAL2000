package com.View;
import com.Controller.Kal2000;
import com.Controller.UserInterface;
import com.Model.Exception.CardException;
import com.Model.Exception.PasswordException;
import com.Model.Exception.SystemException;

import java.util.NoSuchElementException;
import java.util.Scanner;

public abstract class ConnectionView {
	
	/**
	 * Allow client (or admin) to connect.
	 * @param system kal2000 engine
	 * @param ui user interface
	 * @param sc scanner
	 * @return <code>true</code> if logging ok, <code>false</code> otherwise
	 */
    public static boolean logging(Kal2000 system, UserInterface ui, Scanner sc){
        String pw;
        int id;
        System.out.println("Entrez votre identifiant :");
        id = Integer.parseInt(sc.nextLine());
        System.out.println("Entrez votre mot de passe :");
        pw = sc.nextLine();
        try {
            ui.connect(id, pw, system);
        } catch (PasswordException | CardException e) {
            System.out.println("ERROR : " + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Allow a new client to create his account.
	 * @param system kal2000 engine
	 * @param ui user interface
	 * @param sc scanner
     * @return <code>true</code> if create client is5 ok, <code>false</code> otherwise
     */
    public static boolean creatClient(Kal2000 system, UserInterface ui, Scanner sc){
        String pw, firstName, name;
        int id;
        System.out.println("Entrez votre prénom :");
        firstName = sc.nextLine();
        System.out.println("Entrez votre nom :");
        name = sc.nextLine();
        System.out.println("Entrez votre mot de passe :");
        pw = sc.nextLine();
        System.out.println("Entrez le numéro de votre carte :");
        id = Integer.parseInt(sc.nextLine());
        try {
            ui.createClient(firstName, name, pw, id, system);
            ui.connect(id, pw, system);
        } catch (PasswordException | CardException | SystemException | NoSuchElementException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
