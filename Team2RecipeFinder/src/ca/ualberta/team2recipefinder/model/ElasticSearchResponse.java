package ca.ualberta.team2recipefinder.model;

/**
 * This class, based on Chenlei's similar class,
 * encapsulates the response from ElasticSearch
 * 
 * @author chenlei
 *
 * @param <T>
 */
public class ElasticSearchResponse<T> {
    String _index;
    String _type;
    String _id;
    int _version;
    boolean exists;
    T _source;
    double max_score;
    
    public T getSource() {
        return _source;
    }
}
