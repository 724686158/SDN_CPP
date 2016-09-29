package Graph;

import java.util.HashMap;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph ;

public class Graph {
	String dateObtained;
	HashMap<String,Node> hashNodes=new HashMap<String, Node>();
	HashMap<String,Link> hashLinks=new HashMap<String, Link>();
	ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> graph =
            new ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge>(
                DefaultWeightedEdge.class);	String name;
	public Graph(String name,String dateObtained, HashMap<String, Node> hashNodes,
			HashMap<String, Link> hashLinks) {
		super();
		this.name=name;
		this.dateObtained = dateObtained;
		this.hashNodes = hashNodes;
		this.hashLinks = hashLinks;
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
		int i=0;
		int j=0;
		for(String node1:graph.vertexSet())
		{
			j=0;
			for(String node2:graph.vertexSet())
			{
				solution=new DijkstraShortestPath<String, DefaultWeightedEdge>(graph, node1, node2);
				distance[i][j]=solution.getPathLength();
				j++;
				//System.out.println(name);
				//System.out.println(node1+" "+node2);
				//System.out.println("distance:"+solution.getPathLength());
				//System.out.println("i:"+i+" j:"+j);
				
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
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
