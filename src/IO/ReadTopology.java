package IO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Stream;


import Utils.Util;
import Graph.Graph;
import Graph.Node;
import Graph.Link;
public class ReadTopology {

	public static ArrayList<Graph> readFiles() throws IOException
	{

		ArrayList<Graph> listGraph=new ArrayList<Graph>();
		HashMap<String,Double> listTopo=new HashMap<String, Double>();
		Files.walk(Paths.get("D:/Dropbox/academic/topology-data/topology-zoo")).forEach(filePath -> {
			if (Files.isRegularFile(filePath) && filePath.toString().contains(".gml") ) {

				//System.out.println(filePath);
				Stream<String> lines;

				String date="";

				Graph graph;
				HashMap<String,Node> hashNodes=new HashMap<String, Node>();
				HashMap<String,Link> hashLinks=new HashMap<String, Link>();

				try {
					lines = Files.lines(filePath);
					Iterator<String> i=lines.iterator();					
					
					while(i.hasNext())
					{
						String line=i.next();
						if(line.contains("DateObtained"))
						{
							date=line.split(" ")[1].replace("\"", "");
						}else if(line.contains("node"))
						{
							String id=null;
							String label=null;
							String country=null;
							Double longitude=null;
							Double latitude=null;

							
							while(!line.contains("]"))
							{
								if(line.contains("id ") && !line.contains("code") && !line.contains("\""))
								{
									id=line.split("id")[1].trim();
									//System.out.println(id);
								}else if(line.contains("label"))
								{
									label=line.split("label")[1].replace("\"", "").trim();
								}else if(line.contains("Country"))
								{
									country=line.split("Country")[1].replace("\"", "").trim();
								}else if(line.contains("Longitude"))
								{
									longitude=Double.parseDouble(line.split("Longitude")[1].replace("\"", ""));
								}else if(line.contains("Latitude"))
								{
									latitude=Double.parseDouble(line.split("Latitude")[1].replace("\"", ""));
								}
								line=i.next();
							}
							hashNodes.put(id, new Node(id, label, country, longitude, latitude));
						}else if(line.contains("edge"))
						{
							String source=null;;
							String destination=null;
							String linkLabel=null;
							String linkNote=null;
							String linkType=null;


							while(!line.contains("]"))
							{
								if(line.contains("source"))
								{
									source=line.split("source")[1].replace("\"", "").trim();
								}else if(line.contains("target"))
								{
									destination=line.split("target")[1].replace("\"", "").trim();
								}else if(line.contains("LinkType"))
								{
									linkLabel=line.split("LinkType")[1].replace("\"", "");
								}else if(line.contains("LinkLabel"))
								{
									linkNote=line.split("LinkLabel")[1].replace("\"", "");
								}else if(line.contains("LinkNote"))
								{
									linkType=line.split("LinkNote")[1].replace("\"", "");
								}
								line=i.next();
							}
							Node node1=hashNodes.get(source);
							Node node2=hashNodes.get(destination);
							double delay=0;
							if(node1.getLatitude()!=null && node1.getLongitude()!=null && node2.getLatitude()!=null && node2.getLongitude()!=null)
							{
								
								 delay=Util.distance(node1.getLatitude(), node1.getLongitude(), node2.getLatitude(), node2.getLongitude(), "K");
								 delay=delay*0.02;
							}else
							{
								//System.out.println("s:"+source+" d:"+destination+" "+filePath+" ");
								delay=15;
							}
							
							
							if(!listTopo.containsKey(filePath.toString()))
							{
								listTopo.put(filePath.toString(),delay);
								//System.out.println("s:"+source+" d:"+destination+" "+filePath+" ");

							}else
							{
								double value=listTopo.get(filePath.toString());
								value+=delay;
								listTopo.put(filePath.toString(),value);
							}

							
							hashLinks.put(source+"-"+destination, new Link(source, destination, linkLabel, linkNote, linkType, (int)(delay+0.5)) );
						}
					}
					//if(listTopo.get(filePath.toString())/hashNodes.size()>20 )
						//System.out.println(filePath.toString()+":"+listTopo.get(filePath.toString())/hashNodes.size()+" "+hashNodes.size()+" "+listTopo.get(filePath.toString()));

					graph=new Graph(filePath.toString(),date, hashNodes, hashLinks);
					listGraph.add(graph);	

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}
	
		});
		
		
			
			
		
		return listGraph;
	}
}
