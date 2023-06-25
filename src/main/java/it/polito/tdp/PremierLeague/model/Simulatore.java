package it.polito.tdp.PremierLeague.model;

import java.util.PriorityQueue;

import it.polito.tdp.PremierLeague.model.Evento.Tipologia;

public class Simulatore {
	
	private Model model;
	private Match match;
	private Integer N;
	
	private Boolean bestPlayerPlaysHome;
	
	private Integer goalA;
	private Integer goalB;
	private Integer espulsiA;
	private Integer espulsiB;
	private PriorityQueue<Evento> queue;
	
	public Simulatore(Model model, Match match, Integer n) {
		super();
		this.model = model;
		this.match = match;
		N = n;
		this.goalA = 0;
		this.goalB = 0;
		this.espulsiA = 0;
		this.espulsiB = 0;
		this.queue = new PriorityQueue<>();
		
		Player bestPlayer = model.bestPlayer().getPlayer();
		Integer homeID = match.getTeamHomeID();
		Integer bestPlayerTeamID = model.getDao().getTeamPlayerMatch(match, bestPlayer);
		
		if (homeID == bestPlayerTeamID) {
			this.bestPlayerPlaysHome = true;
		}
		else {
			this.bestPlayerPlaysHome = false;
		}
		
	}
	
	public Tipologia naturaEvento() {
		double random = Math.random();
		if (random < 0.5) {
			return Tipologia.GOAL;
		}
		else if (random >= 0.5 && random < 0.8) {
			return Tipologia.ESPULSIONE;
		}
		else {
			return Tipologia.INFORTUNIO;
		}
	}
	
	public void inizializza() {
		for (int i = 1; i <= this.N; i++) {
			Tipologia naturaEvento = this.naturaEvento();
			this.queue.add(new Evento(i, naturaEvento));
		}
	}
	
	public void run() {
		while (!this.queue.isEmpty()) {
			Evento e = this.queue.poll();
			Tipologia tipologia = e.getTipologia();
			
			switch(tipologia) {
				
				case GOAL:
					Integer inCampoA = 11 - this.espulsiA;
					Integer inCampoB = 11 - this.espulsiB;
					if (inCampoA > inCampoB) {
						this.goalA++;
						System.out.println("GOAL casa");
					}
					else if (inCampoB > inCampoA) {
						this.goalB++;
						System.out.println("GOAL ospite");
					}
					else {
						if (bestPlayerPlaysHome) {
							this.goalA++;
							System.out.println("GOAL casa");
						}
						else {
							this.goalB++;
							System.out.println("GOAL ospite");
						}
					}
					break;
					
				case ESPULSIONE:
					double random = Math.random();
					if (random < 0.6) {
						if (bestPlayerPlaysHome) {
							this.espulsiA++;
							System.out.println("ESPULSO casa");
						}
						else {
							this.espulsiB++;
							System.out.println("ESPULSO ospite");
						}
					}
					else {
						if (bestPlayerPlaysHome) {
							this.espulsiB++;
							System.out.println("ESPULSO ospite");
						}
						else {
							this.espulsiA++;
							System.out.println("ESPULSO casa");
						}
					}
					break;
					
				case INFORTUNIO:
					System.out.println("INFORTUNIO");
					double random1 = Math.random();
					if (random1 <= 0.5) {
						System.out.println("AGGIUNTE 2 AZIONI SALIENTI");
						for (int i = 0; i < 2; i++) {
							this.N++;
							Tipologia t = this.naturaEvento();
							this.queue.add(new Evento(this.N, t));
						}
					}
					else {
						System.out.println("AGGIUNTE 3 AZIONI SALIENTI");
						for (int i = 0; i < 3; i++) {
							this.N++;
							Tipologia t = this.naturaEvento();
							this.queue.add(new Evento(this.N, t));
						}
					}
					break;
			}
		}
	}
	
	public String risultatoFinale() {
		String result = "Risultato finale: \n" +
						this.match.getTeamHomeNAME() + " " + this.goalA + " - " + this.match.getTeamAwayNAME() + " " + this.goalB + "\n"
						+ "Espulsi " + this.match.getTeamHomeNAME() + " : " + this.espulsiA + "\n"
						+ "Espulsi " + this.match.getTeamAwayNAME() + " : " + this.espulsiB + "\n";
		return result;
	}
}
