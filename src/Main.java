
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import Graph.Graph;
import IO.ReadTopology;
import IO.Writter;
import MIP.FacilityLocation;
import MIP.Solution;

public class Main {

	public static void main(String[] args) {

		System.out.println("Teste");
//********************************Initialize output file
		try{
			OutputStream os = new FileOutputStream("./result/output_.txt");
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write("Topology TotalControllerCost DelayMax NumberOfControllers NumberControllerLow NumberControllerHigh BooleanFixCost CapacityController\n");
			bw.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
//********************************
		double delayMatrix[][]={
				{0,100,200,300},
				{100,0,100,200},
				{200,100,0,100},
				{300,200,100,0}
		};
		//*********Variables*************************************************************************************************
		int[] d={50,100,150,200,250,300,350,400};
		boolean[] fixedCost={true};
		int costBase=10000;
		//costHigh = Difference between low and high
		int costHigh=5000;
		int[] capacityController={20,30,50,100,1000};
		ArrayList<Solution> listSolutions=new ArrayList<Solution>();
		//FacilityLocation.calculate("",delayMatrix, 4, 400, costConstant, false, costLow, costHigh, 1);
		//int problems=0;
		//***********************************Read Topology********************************************************************
		try{
			for(Graph g:ReadTopology.readFiles()) //Read
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
				//*************Execute Cplex******************************************************************
				if(g.getGraph().vertexSet().size()<60)
				{
					// Date date = new Date();

				     // display time and date using toString()
					//System.out.println(g.getName()+" "+g.getGraph().vertexSet().size());

					for(int capacity:capacityController)
						for(int delayM:d)
							for(boolean differentCapacity:fixedCost)
							{
								//System.out.print("Start:");
								 //String str = String.format("Current Date/Time : %tc", date );
								// System.out.println(str);
								//listSolutions.add
								Solution s=(FacilityLocation.calculate(g.getName(),g.distance(), g.getGraph().vertexSet().size(), delayM, differentCapacity, costBase, costHigh, capacity));
								Writter.recordSolution(s, costBase);
								//Writter.recordMatrixDelay(g);
								//problems++;
								//str = String.format("Current Date/Time : %tc", date );
								//System.out.println("Problem "+problems+" ok!"+" Finish:"+str);
								System.out.println(g.getName());
							}
				}
				//
			}

			System.out.println("Final");
			System.out.println(listSolutions.size());
			
			
			//****************************Record results in a file************************************
			/*OutputStream os = new FileOutputStream("./result/output_.txt");
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write("Topology TotalCost DelayMax ControllerTotalCost ControllerLowCost ControllerHighCost BooleanFixCost CapacityController\n");
			for(Solution s:listSolutions)   	 
			{
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
			}
			bw.close();*/
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
