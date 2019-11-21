package KAL2000;

import java.io.Serializable;

import java.util.ArrayList;

import Util.Category;
import Util.Human;

public class Film implements Serializable {
	private static int autoId;
    private int id;
	private String title;
	private String synopsis;
	private ArrayList<Human> actors;
	private Human realisator;
	private ArrayList<Category> category;
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

