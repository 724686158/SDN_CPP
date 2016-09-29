package IO;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import Graph.Graph;
import MIP.Solution;
public class Writter {

	public static void recordSolution(Solution s, String path, long executionTime)
	{
		//milliseconds to seconds (execution time)
		//double time=executionTIme/1000;


		try{
			OutputStream os = new FileOutputStream(path+"summarized/result_geral.txt",true);
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);
			//System.out.println(s.getTopologyName());
			int n=s.getTopologyName().split("\\\\").length;
			String topoNameShort=s.getTopologyName().split("\\\\")[n-1].replace("gml", "rst");

			OutputStream os2 = new FileOutputStream(path+topoNameShort,true);
			OutputStreamWriter osw2 = new OutputStreamWriter(os2);
			BufferedWriter bw2 = new BufferedWriter(osw2);

			//	("Topology TotalControllerCost DelayConstraintSwitchToController NumberTotalOfControllers "+typeController+"\n");


			int k=s.getStaticCostPerController().length;
			int j=s.getControllerK_J()[0].length;

			int[] totalControllerByType=new int [k];
			
			for(int index=0;index<k;index++)
				totalControllerByType[index]=0;

			int[][] controllerK_J=s.getControllerK_J();

			for(int index_k=0;index_k<k;index_k++)
				for(int index_j=0;index_j<j;index_j++)
				{
					if(controllerK_J[index_k][index_j]==1)
						totalControllerByType[index_k]++;
				}

			int totalControllers=0;

			String stringTotalControllerByType="";

			for(int index=0;index<k;index++)
			{
				totalControllers+=totalControllerByType[index];
				stringTotalControllerByType+=totalControllerByType[index]+" ";
			}


			bw.write(topoNameShort+" "+Math.round(s.getTotalCost())+" "+s.getDelayMaxSwitchToController()+" "+
					totalControllers+" "+stringTotalControllerByType+"\n");

			bw2.write("Scenario:"+" DelayMaxSwitchToController:"+s.getDelayMaxSwitchToController()+"\n");

			int[] switchsAssignedToControl_K=new int[k];
			for(int index_k=0;index_k<k;index_k++)
				switchsAssignedToControl_K[index_k]=0;

			for(int index_i=0;index_i<j;index_i++)
			{
				String stringControllerCapacity="undefined";

				for(int index_j=0;index_j<j;index_j++)
				{
					if(s.getSwitchToController()[index_i][index_j]==1)
					{					
						for(int index_k=0;index_k<k;index_k++)
						{
							if(s.getControllerK_J()[index_k][index_j]==1)
							{
								stringControllerCapacity=""+s.getControllerCapacity()[index_k];
							}
							
						}
						bw2.write("switch"+index_i+":"+index_j+" "+stringControllerCapacity+"\n");

						break;
					}
				}
			}

			bw2.close();
			bw.close();
		}catch(Exception e){
			e.printStackTrace();
		}


	}
	
	public static void recordNullSolution(String path, String gName,int delay)
	{
		try{
			OutputStream os = new FileOutputStream(path+"summarized/solutionNull.txt",true);
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);
			
			bw.write(gName+" "+"delay:"+delay);
			bw.write("\n");
			
			bw.close();
			
		}catch(Exception  ex)
		{
			ex.printStackTrace();		}

	}

	public static void processHashResults(HashMap<String, ArrayList<Solution>> hashResults, int[] cCapacity,int[] delayConstraint, boolean[] isFixedCost)
	{

		//Hash = controllerCapacity+"-"+delayConstraint+"-"+differentCapacity;
		//for(controllerCapacity:cCapacity)
		//for()


	}

	public static double[][] recordMatrixDelay(Graph g,String path)
	{
		try{
			OutputStream os = new FileOutputStream(path+"summarized/matrixDelay.txt",true);
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);

			int n=g.getName().split("\\\\").length;
			String topoNameShort=g.getName().split("\\\\")[n-1];
			int k=g.getGraph().vertexSet().size();
			double[][] matrix=g.distance();
			bw.write(topoNameShort+"->"+g.getGraph().vertexSet().size()+"\n");
			for(int i=0;i<k;i++){	
				for(int j=0;j<k;j++)
				{
					bw.write(matrix[i][j]+" ");
				}
				bw.write("\n");
			}

			bw.close();
			return matrix;
		}catch(Exception e){
			e.printStackTrace();

		}
		return null;
	}


}
