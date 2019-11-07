package KAL2000;

import java.io.Serializable;

import java.util.ArrayList;

import Util.Category;
import Util.Human;

public class Film implements Serializable {
	private String titre;
	private String synopsis;
	private ArrayList<Human> actors;
	private Human realisator;
	private Category category;

	public Film(String s, ArrayList<Human> h, Human r, Category c) {
		this.synopsis = s;
		this.actors = h;
		this.realisator = r;
		this.category = c;
	}

	public String getTitre() {
		return this.titre;
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
}

