package com.Model.Movies;

import java.io.Serializable;

import java.util.ArrayList;

import com.Model.Client.Human;

/**
 * Film
 */
public class Film implements Serializable {
	/**
	 * autoId use for auto increment dvd id
	 */
	private static int autoId;

	/**
	 * dvd id that are increment by {@link DvD#autoId}
	 */
    private int id;

	/**
	 * title of film
	 */
	private String title;

	/**
	 * synopsis of film
	 */
	private String synopsis;

	/**
	 * Actors of film
	 */
	private ArrayList<Human> actors;

	/**
	 * realisator of film
	 */
	private Human realisator;

	/**
	 * realisator of film
	 */
	private ArrayList<Category> category;

	/**
	 * For history
	 * Number of film rentals
	 */
	private int nbRented;

	public Film(String title, String synopsis, ArrayList<Human> actors, Human realisator, ArrayList<Category> category) {
		this.title = title;
		this.synopsis = synopsis;
		this.actors = actors;
		this.realisator = realisator;
		this.category = category;
		autoId++;
		this.id = autoId;
		this.nbRented = 0;
	}


	public String getTitle() {
		return this.title;
	}

	public String getSynopsis() {
		return this.synopsis;
	}

	public ArrayList<Human> getActors() {
		return this.actors;
	}

	public Human getRealisator() {
		return this.realisator;
	}

	public ArrayList<Category> getCategory() {
		return this.category;
	}

    public int getId() {
        return id;
    }

	/**
	 * For history, add a number rent
	 */
	public void rented(){
		this.nbRented++;
	}

	public int getNbRented() {
		return nbRented;
	}

	@Override
	public String toString() {
		return "id=" + id +
				", title='" + title;
	}
}

