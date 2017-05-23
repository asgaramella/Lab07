package it.polito.tdp.dizionario.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;
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
	
	public List<String> trovaTuttiViciniAmpiezza(String radice){
		GraphIterator<String,DefaultEdge> albero= new BreadthFirstIterator<String,DefaultEdge>(grafo,radice);
		ArrayList<String> ltemp=new ArrayList<String>();
		while(albero.hasNext()){
			ltemp.add(albero.next());
		}
		return ltemp;
	}
	
	public List<String> trovaTuttiViciniProfondita(String radice){
		GraphIterator<String,DefaultEdge> dfi= new DepthFirstIterator<String,DefaultEdge>(grafo,radice);
		ArrayList<String> ltemp=new ArrayList<String>();
		while(dfi.hasNext()){
			ltemp.add(dfi.next());
		}
		return ltemp;
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
