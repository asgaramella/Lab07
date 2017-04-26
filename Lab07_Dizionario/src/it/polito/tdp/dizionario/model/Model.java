package it.polito.tdp.dizionario.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.dizionario.db.WordDAO;

public class Model {
	UndirectedGraph<String,DefaultEdge> grafo;
	
	public Model() {
		grafo=new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
	}

	public List<String> createGraph(int numeroLettere) {
		WordDAO dao=new WordDAO();
		Graphs.addAllVertices(grafo, dao.getAllWordsFixedLength(numeroLettere));
		
		for(String parola: grafo.vertexSet()){
			for(String s: dao.getAllSimilarWords(parola, numeroLettere))
				grafo.addEdge(s , parola);
		}

		/*for(DefaultEdge e: grafo.edgeSet())
			System.out.println(e);*/
		
		return new ArrayList<String>(grafo.vertexSet());
	}


	public List<String> displayNeighbours(String parolaInserita) {

		
		return Graphs.neighborListOf(grafo, parolaInserita);
	}

	public String findMaxDegree() {
		String massimo=null;
		int gradoMax=-1;
		for(String stemp: grafo.vertexSet()){
			if(grafo.degreeOf(stemp)>gradoMax){
				massimo=stemp;
				gradoMax=grafo.degreeOf(stemp);
			}
		}
		
		return massimo;
	}
	
	public int findDegree(String parola){
		return grafo.degreeOf(parola);
	}
}
