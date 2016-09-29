package IO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Stream;
import Utils.Util;
import Graph.Graph;
import Graph.Node;
import Graph.Link;

public class ReadTopology {

	public static HashMap<String, Graph> readFiles(String path) throws IOException
	{

		//ArrayList<Graph> listGraph=new ArrayList<Graph>();
		HashMap<String, Graph> hashGraph=new HashMap<String, Graph>();

		Files.walk(Paths.get(path)).forEach(filePath -> {
			if (Files.isRegularFile(filePath) && filePath.toString().contains(".gml") ) {

				//System.out.println(filePath);
				Stream<String> lines;

				String date="";
				String networkName=null;
				Boolean isBackbone=null;
				Boolean isLayerIP=null;
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
							date=line.split(" ")[3].replace("\"", "");
							//System.out.println("date"+date);
						}else if(line.contains("Network "))
						{
							networkName=line.split("Network ")[1].replace("\"", "");
							//System.out.println(networkName+"?");
						}
						else if(line.contains("Backbone"))
						{

							//System.out.println("Bk:"+line.split(" ")[3].replace("\"", ""));
							if(line.split(" ")[3].replace("\"", "").equals("1"))
								isBackbone=true;
							else
								isBackbone=false;
						}
						else if(line.contains("Layer "))
						{
							//System.out.println("Layer:"+line.split(" ")[3].replace("\"", ""));
							if(line.split("Layer ")[1].replace("\"", "").equals("IP"))
								isLayerIP=true;
							else
								isLayerIP=false;						
						}
						else if(line.contains("node ["))
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

								//:::::::::::::::::::::::::::::::::---->Delay estimation<-----:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
								delay=Util.distance(node1.getLatitude(), node1.getLongitude(), node2.getLatitude(), node2.getLongitude(), "K");
								delay=delay*0.02;

								//-----------------------------------------------------------------------------------------------------------------------
							}else
							{
								//System.out.println("s:"+source+" d:"+destination+" "+filePath+" ");
								delay=15;
							}

							hashLinks.put(source+"-"+destination, new Link(source, destination, linkLabel, linkNote, linkType, (int)(delay+0.5)) );
						}
					}

					graph=new Graph(filePath.toString(),date, hashNodes, hashLinks, isBackbone, isLayerIP, networkName);

					//******************************Create a object ListenableUndirectedWeightedGraph with Nodes and Links 
					graph.insertNodes();
					graph.insertlinks();
					
					

					//****************************************************************************************************

					//************* Remove nodes with degree=0

					for(String key:graph.getHashNodes().keySet())
					{
						if(graph.getGraph().degreeOf(key)==0)
						{
							//System.out.println(graph.getName());
							//System.out.println(key);

							//Remove vertices without links
							graph.getGraph().removeVertex(key);
							//g.getHashNodes().remove(key);
						}
					}
					//****************************************************************************************************





					//***********Create a hash of Topologies
					if(!hashGraph.containsKey(graph.getNetworkName()))
					{
						hashGraph.put(graph.getNetworkName(), graph);
						//System.out.println("added");
					}
					else
					{
						//
						Graph g2=hashGraph.get(graph.getNetworkName());
						//Replace the small network for a big network (different version, but the same name) 
						if(g2.getHashNodes().size()<graph.getHashNodes().size()) 
							hashGraph.put(graph.getNetworkName(), graph);
						//System.out.println("111111111111111");
					}

					//****************************************************************************************************
					//listGraph.add(graph);	

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}

		});

		/*for(Graph g:listGraph) //
		{
			g.insertNodes();
			g.insertlinks();
			for(String key:g.getHashNodes().keySet())
			{
				if(g.getGraph().degreeOf(key)==0)
				{
					//System.out.println(g.getName());
					//System.out.println(key);

					//Remove vertices without links
					g.getGraph().removeVertex(key);
					//g.getHashNodes().remove(key);
				}
			}

			//
		}

		//Verify the newest version of the network
		for(Graph g:listGraph) //
		{


		}*/

		return hashGraph;
	}
}
