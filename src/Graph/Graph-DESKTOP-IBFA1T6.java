package Graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph ;

public class Graph {
	String dateObtained;
	String networkName;
	Boolean isBackbone;
	Boolean isLayerIP;
	
	HashMap<String,Node> hashNodes=new HashMap<String, Node>();
	HashMap<String,Link> hashLinks=new HashMap<String, Link>();
	ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> graph =
            new ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge>(
                DefaultWeightedEdge.class);	
	String pathName;
	public Graph(String name,String dateObtained, HashMap<String, Node> hashNodes,
			HashMap<String, Link> hashLinks, Boolean isBackbone, Boolean isLayerIP, String networkName) {
		super();
		this.pathName=name;
		this.dateObtained = dateObtained;
		this.hashNodes = hashNodes;
		this.hashLinks = hashLinks;
		this.networkName=networkName;
		this.isBackbone=isBackbone;
		this.isLayerIP=isLayerIP;
	}

	public void insertNodes()
	{
		for(String key:hashNodes.keySet())
			graph.addVertex(key);
	}
	
	public void insertlinks()
	{	
		for(String key:hashLinks.keySet())
		{
			Link link=hashLinks.get(key);
		//	System.out.println(name);
		//	System.out.println(link.getSource()+"-"+link.getDestination()+"-");
		//	System.out.println(hashNodes.containsKey(link.getSource()));
		//	System.out.println(hashNodes.containsKey(link.getDestination()));
			if(!link.getSource().equals(link.getDestination()))
			{
				DefaultWeightedEdge e1 = graph.addEdge(link.getSource(), link.getDestination());
				graph.setEdgeWeight(e1, link.getWeigth());
			}
			
		}
	}
	
	public double[][] distance()
	{
		int size=graph.vertexSet().size();
		//System.out.println("size:"+size);
		double distance[][]=new double[size][size];
		DijkstraShortestPath<String, DefaultWeightedEdge> solution;
		
		
		ArrayList<String> listOfNodes=new ArrayList<String>();
		
		for(String node1:graph.vertexSet())
			listOfNodes.add(node1);
		
		Collections.sort(listOfNodes);
		
		int i=0;
		for(String node1:listOfNodes)
		{
			int j=0;
			for(String node2:listOfNodes)
			{
				
				solution=new DijkstraShortestPath<String, DefaultWeightedEdge>(graph, node1, node2);
			/*	System.out.println(i+" "+j);
				System.out.println(networkName);
				System.out.println("Vertex:"+graph.vertexSet().size());*/
				distance[i][j]=solution.getPathLength();
				
				/*if(Double.isInfinite(distance[i][j]))
				{
					System.out.println("!!!!!!!!!!!!!!!!!!");
					System.out.println(distance[i][j]);

					System.out.println(networkName);
					System.out.println("Infinity");
					System.out.println(node1+" "+node2);
					System.out.println("Edges:"+graph.edgeSet().size());
					
				}*/
				//System.out.println(name);
				//System.out.println(node1+" "+node2);
				//System.out.println("distance:"+solution.getPathLength());
				//System.out.println("i:"+i+" j:"+j);
				j++;
			}
			i++;
		}
		return distance;
	}

	public String getDateObtained() {
		return dateObtained;
	}

	public void setDateObtained(String dateObtained) {
		this.dateObtained = dateObtained;
	}

	public HashMap<String, Node> getHashNodes() {
		return hashNodes;
	}

	public void setHashNodes(HashMap<String, Node> hashNodes) {
		this.hashNodes = hashNodes;
	}

	public HashMap<String, Link> getHashLinks() {
		return hashLinks;
	}

	public void setHashLinks(HashMap<String, Link> hashLinks) {
		this.hashLinks = hashLinks;
	}

	public ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> getGraph() {
		return graph;
	}

	public void setGraph(
			ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> graph) {
		this.graph = graph;
	}

	public String getName() {
		return pathName;
	}

	public void setName(String name) {
		this.pathName = name;
	}

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public Boolean getIsBackbone() {
		return isBackbone;
	}

	public void setIsBackbone(Boolean isBackbone) {
		this.isBackbone = isBackbone;
	}

	public Boolean getIsLayerIP() {
		return isLayerIP;
	}

	public void setIsLayerIP(Boolean isLayerIP) {
		this.isLayerIP = isLayerIP;
	}
	
	
}
