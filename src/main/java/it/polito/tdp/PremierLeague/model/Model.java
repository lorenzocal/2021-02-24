package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private PremierLeagueDAO dao;
	private SimpleDirectedWeightedGraph<Player, DefaultWeightedEdge> grafo;
	
	public Model() {
		super();
		this.dao = new PremierLeagueDAO();
	}

	public PremierLeagueDAO getDao() {
		return dao;
	}

	public SimpleDirectedWeightedGraph<Player, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}
	
	public List<Match> listAllMatches(){
		return this.dao.listAllMatches();
	}
	
	public void creaGrafo(Match match) {
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, this.dao.listAllPlayersMatch(match));
		for (Player p1 : this.grafo.vertexSet()) {
			for (Player p2 : this.grafo.vertexSet()) {
				Integer teamID1 = dao.getTeamPlayerMatch(match, p1);
				Integer teamID2 = dao.getTeamPlayerMatch(match, p2);
				if (p1.getPlayerID() < p2.getPlayerID() && teamID1 != teamID2) {
					double eff1 = this.dao.getEfficiencyPlayerMatch(match, p1);
					double eff2 = this.dao.getEfficiencyPlayerMatch(match, p2);
					if (eff1 >= eff2) {
						Graphs.addEdgeWithVertices(this.grafo, p1, p2, eff1 - eff2);
					}
					else {
						Graphs.addEdgeWithVertices(this.grafo, p2, p1, eff2 - eff1);
					}
				}
			}
		}
	}
	
	public PlayerDelta bestPlayer() {
		List<PlayerDelta> result = new ArrayList<>();
		for (Player p : this.grafo.vertexSet()) {
			double delta = 0;
			Set<DefaultWeightedEdge> entranti = this.grafo.incomingEdgesOf(p);
			Set<DefaultWeightedEdge> uscenti = this.grafo.outgoingEdgesOf(p);
			
			for (DefaultWeightedEdge uscente : uscenti) {
				delta += this.grafo.getEdgeWeight(uscente);
			}
			
			for (DefaultWeightedEdge entrante : entranti) {
				delta += this.grafo.getEdgeWeight(entrante);
			}
			
			result.add(new PlayerDelta(p, delta));
		}
		Collections.sort(result);
		return result.get(0);
	}
}
