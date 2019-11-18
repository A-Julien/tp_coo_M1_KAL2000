package KAL2000;

import java.io.Serializable;

import java.util.ArrayList;

import Util.Category;
import Util.Human;

public class Film implements Serializable {
    private int id;
	private String title;
	private String synopsis;
	private ArrayList<Human> actors;
	private Human realisator;
	private Category category;
	private int nbRented;

	public Film(int filmId, String synopsis, ArrayList<Human> actors, Human realisator, Category category) {
		this.synopsis = synopsis;
		this.actors = actors;
		this.realisator = realisator;
		this.category = category;
		this.id = filmId;
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

	public Category getCategory() {
		return this.category;
	}

    public int getId() {
        return id;
    }

    public void rented(){
		this.nbRented++;
	}

	public int getNbRented() {
		return nbRented;
	}
}

