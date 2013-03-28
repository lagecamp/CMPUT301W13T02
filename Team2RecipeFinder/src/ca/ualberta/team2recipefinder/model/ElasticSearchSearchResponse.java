package ca.ualberta.team2recipefinder.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class, based on Chenlei's similar class,
 * encapsulates the response from ElasticSearch
 * 
 * @author chenlei
 *
 * @param <T>
 */
public class ElasticSearchSearchResponse<T> {
	int took;
	boolean timed_out;
	transient Object _shards;
	Hits<T> hits;
	boolean exists;    

	public Collection<ElasticSearchResponse<T>> getHits() {
		if (hits != null) {
			return hits.getHits();
		}
		else {
			return new ArrayList<ElasticSearchResponse<T>>();
		}
	}

	public Collection<T> getSources() {
		Collection<T> out = new ArrayList<T>();
		for (ElasticSearchResponse<T> essrt : getHits()) {
			out.add( essrt.getSource() );
		}
		return out;
	}

	public String toString() {
		return (super.toString() + ":" + took + "," + _shards + "," + exists + ","  + hits);     
	}
}
