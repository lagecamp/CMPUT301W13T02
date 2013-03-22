package ca.ualberta.team2recipefinder.controller;

public class SearchResult {	
	public final static int SOURCE_LOCAL = 0;
	public final static int SOURCE_REMOTE = 1;
	
	private String name;
	private String serverId;
	private long localId;
	private int source;	
	
	public SearchResult(String name,  long localId, String serverId, int source) {
		this.name = name;
		this.serverId = serverId;
		this.localId = localId;
		this.source = source;
	}
	
	public String getName() {
		return name;
	}

	public String getServerId() {
		return serverId;
	}

	public long getLocalId() {
		return localId;
	}

	public int getSource() {
		return source;
	}	
	
	@Override
	public String toString() {
		String origin = this.source == SOURCE_LOCAL ? "L" : "S";
		return origin + " " + name;
	}
}
