package com.Model.Movies;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Formalism of a DVD
 */
public class DvD implements Serializable {
	/**
	 * autoId use for auto increment dvd id
	 */
	private static int autoId;

	/**
	 * dvd id that are increment by {@link DvD#autoId}
	 */
	private int id;

	/**
	 * the film in the dvd
	 */
	private Film film;

	/**
	 * The state of the dvd
	 */
	private State state;

	/**
	 * the price of the dvd (
	 */
	private float dvdPrice;
	
	public DvD(Film film, State state, float dvdPrice) {
		this.film = film;
		this.state = state;
		autoId++;
		this.id = autoId;
		this.dvdPrice = dvdPrice;
	}

	/**
	 * return the price of the dvd
	 * @return float price
	 */
	public float getDvdPrice() {
		return dvdPrice;
	}

	/**
	 * set the state of the dvd
	 * Useful when client return a dvd
	 *
	 * @param state the new state
	 */
	public void setState(State state) {
        this.state = state;
    }

	/**
	 * Return the film that contain the dvd
	 * @return the Film
	 */
	public Film getFilm() {
		return this.film;
	}


	/**
	 * Return the state of the dvd
	 * @return the state of the dvd
	 */
	public State getState() {
		return this.state;
	}

	/**
	 * Return the id of the dvd
	 * @return int id
	 */
    public int getId() {
        return id;
    }

	/**
	 * Return Category tagged for this dvd
	 * @return ArrayList<Category>
	 */
	public ArrayList<Category> getCategory(){
		return this.film.getCategory();
	}

}

