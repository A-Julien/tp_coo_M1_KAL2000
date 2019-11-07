package KAL2000;
import java.util.ArrayList;
import util.State;

<<<<<<< Updated upstream
public class DvD {
	private Film film;
	private State state;
	
	public DvD(Film f, State s) {
		this.film = f;
		this.state = s;
		
	}
	
	public Film getFilm() {
		return this.film;
	}
	
	public State getState() {
		return this.state;
	}
}
=======
import java.io.Serializable;

public class DvD implements Serializable {

}
>>>>>>> Stashed changes
