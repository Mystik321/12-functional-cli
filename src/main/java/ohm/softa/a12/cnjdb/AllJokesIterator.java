package ohm.softa.a12.cnjdb;

import ohm.softa.a12.model.JokeDto;
import org.apache.commons.lang3.NotImplementedException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Iterator to retrieve all jokes of the ICNDB until collision.
 */
public final class AllJokesIterator implements Iterator<JokeDto> {

    /* ICNDB API proxy to retrieve jokes */
    private final CNJDBApi icndbApi;
	List<JokeDto> jokeDtoList = new ArrayList<>();
	JokeDto current;

    public AllJokesIterator() {
        icndbApi = CNJDBService.getInstance();
		try {
			current = icndbApi.getRandomJoke().get();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }

	@Override
	public boolean hasNext() {
		return !jokeDtoList.contains(current);
	}

	@Override
	public JokeDto next() {
		JokeDto temp = current;
		try {
			current = icndbApi.getRandomJoke().get();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		jokeDtoList.add(temp);
		return temp;
	}
}
