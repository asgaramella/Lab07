package it.polito.tdp.dizionario.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

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
			this.creaCollegamenti(parola);
		}

		/*for(DefaultEdge e: grafo.edgeSet())
			System.out.println(e);*/
		
		return new ArrayList<String>(grafo.vertexSet());
	}

	private void creaCollegamenti(String parola) {
	
	     for(String stemp: grafo.vertexSet()){
	    	 int diverse=0;
	    	 for(int i=0;i< parola.length();i++){
	    		 if(stemp.charAt(i)!=parola.charAt(i))
	    			 diverse++;
	    	 }
	    	 if(diverse==1)
	    		 grafo.addEdge(parola, stemp);
	    		 
	     }
		
	}

	public List<String> displayNeighbours(String parolaInserita) {

		
		return Graphs.neighborListOf(grafo, parolaInserita);
	}
	
	public List<String> trovaTuttiVicini(String radice){
		List<String> visitati= new ArrayList<String>();
		recursive(radice,visitati);
		return visitati;
	}
	
	private void recursive(String parola, List<String> visitati){
		visitati.add(parola);
		for(String stemp: Graphs.neighborListOf(grafo,parola)){
			if(!visitati.contains(stemp))
				recursive(stemp,visitati);
			}
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
