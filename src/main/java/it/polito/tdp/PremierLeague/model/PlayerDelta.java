package it.polito.tdp.PremierLeague.model;

public class PlayerDelta implements Comparable<PlayerDelta>{

	Player player;
	Double delta;
	
	
	public PlayerDelta(Player player, Double delta) {
		super();
		this.player = player;
		this.delta = delta;
	}
	
	public Player getPlayer() {
		return player;
	}
	public Double getDelta() {
		return delta;
	}

	@Override
	public int compareTo(PlayerDelta o) {
		return o.delta.compareTo(this.delta);
	}
	
	
}
