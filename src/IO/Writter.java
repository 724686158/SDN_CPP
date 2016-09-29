package IO;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import Graph.Graph;
import MIP.Solution;

public class Writter {
	public static void recordSolution(Solution s, int costLow)
	{
		try{
			OutputStream os = new FileOutputStream("./result/output_.txt",true);
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);

			int n=s.getTopologyName().split("\\\\").length;
			String topoNameShort=s.getTopologyName().split("\\\\")[n-1];

			OutputStream os2 = new FileOutputStream("./result/"+topoNameShort,true);
			OutputStreamWriter osw2 = new OutputStreamWriter(os2);
			BufferedWriter bw2 = new BufferedWriter(osw2);

			int controllerHigh=0;
			int controllerLow=0;
			for(double cost:s.getCostPerController())
			{
				if(cost==costLow)
					controllerLow++;
				else if(cost>costLow)
					controllerHigh++;
			}

			bw.write(topoNameShort+" "+Math.round(s.getTotalCost())+" "+s.getDelayMax()+" "+
					(controllerHigh+controllerLow)+" "+controllerLow+" "+controllerHigh+" "+s.isBoolFixedCost()+" "+s.getControllerCapacity()+"\n");

			bw2.write("Scenario:"+" DelayMax:"+s.getDelayMax()+" boolCost:"+s.isBoolFixedCost()+" CapacityController:"+s.getControllerCapacity()+"\n");
			for(int j=0;j<s.getController().length;j++)
				if(s.getCostPerController()[j]!=0)
				{
					int switchToControl=0;
					for(int i=0;i<s.getController().length;i++)
					{
						if(s.getSwitchToController()[i][j]==1)
							switchToControl++;
					}
					bw2.write("controler:"+j+" switches:"+switchToControl+" cost:"+s.getCostPerController()[j]+" w:"+s.getCostWeigth()[j]+"\n");
				}
			bw2.close();
			bw.close();
		}catch(Exception e){
			e.printStackTrace();
		}


	}

	public static void recordMatrixDelay(Graph g)
	{
		try{
			OutputStream os = new FileOutputStream("./result/matrixDelay.txt",true);
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);
			
			int n=g.getName().split("\\\\").length;
			String topoNameShort=g.getName().split("\\\\")[n-1];
			int k=g.getGraph().vertexSet().size();
			double[][] matrix=g.distance();
			bw.write(topoNameShort+"->"+g.getGraph().vertexSet().size()+"\n");
			for(int i=0;i<k;i++)
			{	for(int j=0;j<k;j++)
				{
				bw.write(matrix[i][j]+" ");
				}
				bw.write("\n");
			}

			


			bw.close();
		}catch(Exception e){
			e.printStackTrace();
		}


	}


}
