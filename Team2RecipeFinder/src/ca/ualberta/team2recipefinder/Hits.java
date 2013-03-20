package ca.ualberta.team2recipefinder;

import java.util.Collection;

/**
 * This class, based on Chenlei's similar class,
 * encapsulates the response from ElasticSearch
 * 
 * @author chenlei
 *
 * @param <T>
 */
public class Hits<T> {
    int total;
    double max_score;
    Collection<ElasticSearchResponse<T>> hits;
    public Collection<ElasticSearchResponse<T>> getHits() {
        return hits;
    }
    public String toString() {
        return (super.toString()+","+total+","+max_score+","+hits);
    }
}