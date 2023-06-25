package it.polito.tdp.PremierLeague.model;

public class Evento implements Comparable<Evento>{

	public enum Tipologia{
		GOAL,
		ESPULSIONE,
		INFORTUNIO
	}
	
	private Integer id;
	private Tipologia tipologia;
	
	
	
	public Evento(Integer id, Tipologia tipologia) {
		super();
		this.id = id;
		this.tipologia = tipologia;
	}



	public Tipologia getTipologia() {
		return tipologia;
	}



	public int compareTo(Evento o) {
		return this.id.compareTo(o.id);
	}



	public void setId(Integer id) {
		this.id = id;
	}

}
