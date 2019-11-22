package com.Model.Movies;

import java.io.Serializable;
import java.util.ArrayList;

public class DvD implements Serializable {
	private static int autoId;
	private int id;
	private Film film;
	private State state;
	private float dvdPrice;
	
	public DvD(Film film, State state, float dvdPrice) {
		this.film = film;
		this.state = state;
		autoId++;
		this.id = autoId;
		this.dvdPrice = dvdPrice;
	}

	public float getDvdPrice() {
		return dvdPrice;
	}

	public void setState(State state) {
        this.state = state;
    }
    public Film getFilm() {
		return this.film;
	}
	
	public State getState() {
		return this.state;
	}

    public int getId() {
        return id;
    }

    public ArrayList<Category> getCategory(){
		return this.film.getCategory();
	}

}

