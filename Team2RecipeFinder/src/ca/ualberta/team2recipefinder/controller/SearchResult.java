package ca.ualberta.team2recipefinder.controller;

/**
 * This class encapsulates a search result, either from the
 * server or the client
 * @author CMPUT 301 Team 2
 *
 */
public class SearchResult {	
	public final static int SOURCE_LOCAL = 0;
	public final static int SOURCE_REMOTE = 1;
	
	private String name;
	private String serverId;
	private long localId;
	private int source;	
	
	/**
	 * Constructor
	 * @param name name of the recipe
	 * @param localId the id in the local database
	 * @param serverId the id on the server
	 * @param source the constant the designates the source (server or client)
	 */
	public SearchResult(String name,  long localId, String serverId, int source) {
		this.name = name;
		this.serverId = serverId;
		this.localId = localId;
		this.source = source;
	}
	
	/**
	 * Returns the recipe name
	 * @return recipe name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the server id
	 * @return server id
	 */
	public String getServerId() {
		return serverId;
	}

	/**
	 * Returns the local id
	 * @return local id
	 */
	public long getLocalId() {
		return localId;
	}

	/**
	 * Returns the source
	 * @return the integer the identifies the source of the result
	 */
	public int getSource() {
		return source;
	}	
	
	/**
	 * Converts this SearchResult to string format
	 * @return the string representation of this SearchResult
	 */
	@Override
	public String toString() {
		String origin = this.source == SOURCE_LOCAL ? "L" : "S";
		return origin + " " + name;
	}
}
