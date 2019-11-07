package KAL2000;
import Util.State;

import java.io.Serializable;

public class DvD implements Serializable {
	private int id;
	private Film film;
	private State state;
	
	public DvD(Film film, State state, int idDvd) {
		this.film = film;
		this.state = state;
		this.id = idDvd;
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
}

