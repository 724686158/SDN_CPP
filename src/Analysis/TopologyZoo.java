package Analysis;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import Graph.Graph;
import Graph.Link;

public class TopologyZoo {
	
	ArrayList<Graph> listGraphs;
	String path;
	
	
	public TopologyZoo(ArrayList<Graph> listGraphs, String path) {
		this.listGraphs = listGraphs;
		this.path = path;
	}



	public void writeSummarizedStatics()
	{
		try{
			OutputStream os_tSize = new FileOutputStream(path+"topologySizeData.txt");
			OutputStream os_tMeanDelay = new FileOutputStream(path+"topologyMeanDelayData.txt");
			OutputStream os_tLinkDelay = new FileOutputStream(path+"topologyLinkDelayData.txt");
			
			OutputStreamWriter osw_tSize = new OutputStreamWriter(os_tSize);
			OutputStreamWriter osw_tMeanDelay = new OutputStreamWriter(os_tMeanDelay);
			OutputStreamWriter osw_tLinkDelay = new OutputStreamWriter(os_tLinkDelay);
			
			//topology size
			BufferedWriter bw_tSize = new BufferedWriter(osw_tSize);
			//topology mean delay
			BufferedWriter bw_tMeanDelay = new BufferedWriter(osw_tMeanDelay);
			//topology delay
			BufferedWriter bw_tLinkDelay = new BufferedWriter(osw_tLinkDelay);
			//**********************Essential Analyze***********************************
			
			for(Graph g:listGraphs)
			{
				
				bw_tSize.write(g.getName()+" "+g.getGraph().vertexSet().size()+"\n");
				double linkMean=0;
				for(String keyLink:g.getHashLinks().keySet())
				{
					Link l=g.getHashLinks().get(keyLink);
					bw_tLinkDelay.write(g.getName()+" "+l.getWeigth()+"\n");
					linkMean+=l.getWeigth(); //Delay
					
				}
				linkMean=linkMean/g.getHashLinks().size();
				bw_tMeanDelay.write(g.getName()+" "+linkMean+"\n");
				
			}
			
			//*************************************************************************

			bw_tSize.close();
			bw_tMeanDelay.close();
			bw_tLinkDelay.close();
		}catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
	
	
}
