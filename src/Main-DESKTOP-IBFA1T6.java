
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import Analysis.TopologyZoo;
import Graph.Graph;
import IO.ReadTopology;
import IO.Writter;
import MIP.FacilityLocation;
import MIP.Solution;

public class Main {


	public static void main(String[] args) {

		System.out.println("Starting ...");

		int[] d={25,50,75,100,125,150,175,200};
		//int[] capacityController={20,40,60,100,1000};
		int delayConstraintBetweenControllers=200;

		double[] controllerCapacityArray={1000.0,2000.0,4000.0};
		//double[] controllerCapacityArray={1000.0};
		double controllerCost[]={10000.0, 15000.0, 20000.0}; //Dollars
		//double controllerCost[]={10000.0}; //Dollars

		int switchFlows=70; //K flows 

	/*	startCplex(d, delayConstraintBetweenControllers, controllerCapacityArray, controllerCost, switchFlows);
		//****************************************************************************************************
		controllerCapacityArray= new double[] {4000.0};
		//double[] controllerCapacityArray={1000.0};
		controllerCost=new double[]{20000.0}; //Dollars

		startCplex(d, delayConstraintBetweenControllers, controllerCapacityArray, controllerCost, switchFlows);
		//****************************************************************************************************
		controllerCapacityArray= new double[] {2000.0};
		//double[] controllerCapacityArray={1000.0};
		controllerCost=new double[]{15000.0}; //Dollars

		startCplex(d, delayConstraintBetweenControllers, controllerCapacityArray, controllerCost, switchFlows);
		//****************************************************************************************************
*/
		controllerCapacityArray= new double[] {1000.0};
		//double[] controllerCapacityArray={1000.0};
		controllerCost=new double[]{10000.0}; //Dollars

		startCplex(d, delayConstraintBetweenControllers, controllerCapacityArray, controllerCost, switchFlows);
		//****************************************************************************************************



	}

	public static void startCplex(int[] d, int delayConstraintBetweenControllers, double[] controllerCapacityArray, double controllerCost[], int switchFlows)
	{

		String outPutPath = null;

		//*********Variables*************************************************************************************************


		//HashMap<String, ArrayList<Solution>> hashSolutions=new HashMap<String, ArrayList<Solution>>();
		//********************************Initialize output file

		outPutPath=init(controllerCapacityArray,switchFlows);



		//FacilityLocation.calculate("",delayMatrix, 4, 400, costConstant, false, costLow, costHigh, 1);
		//int problems=0;
		//***********************************Read and Filter Topologies********************************************************************
		try{
			HashMap<String, Graph> hashOfGraphs=ReadTopology.readFiles("D:/Dropbox/academic/topology-data/topology-zoo");
			ArrayList<Graph> listofGraphs=new ArrayList<Graph>();


			for(String key:hashOfGraphs.keySet())
			{
				Graph g=hashOfGraphs.get(key);
				//network filter
				if(g.getIsBackbone() && g.getIsLayerIP() && g.getGraph().vertexSet().size()>=15)
					listofGraphs.add(g);
			}

			//Generate 3 files about topology-zoo statistics (delay and size)
			TopologyZoo analyzeZoo=new TopologyZoo(listofGraphs, outPutPath);
			analyzeZoo.writeSummarizedStatics();

			//Sorting
			Collections.sort(listofGraphs, new Comparator<Graph>() {
				@Override
				public int compare(Graph  graph1, Graph  graph2)
				{
					if(graph1.getGraph().vertexSet().size()>=graph2.getGraph().vertexSet().size())
						return 1;
					else
						return -1;

				}
			});

			//System.out.println(listofGraphs.size());
			int x=0;
			for(Graph g:listofGraphs) //Read
			{

				//*************Execute Cplex******************************************************************
				//loop count 
				x++;
				//System.out.println(g.getIsBackbone()+" "+g.getIsLayerIP()+" "+g.getHashNodes().size()+" "+g.getName());


				double matrixDelay[][]=Writter.recordMatrixDelay(g, outPutPath);

				// Date date = new Date();

				// display time and date using toString()
				System.out.println(g.getName()+" "+g.getGraph().vertexSet().size());
				//
				//for(int controllerCapacity:controllerCapacityArray)
				for(int delayConstraintSwitchController:d)
				{

					System.out.print("Start:");
					System.out.println(g.getName());
					//initial execution time
					long start = System.currentTimeMillis();
					//Solution s=FacilityLocation.calculate(g.getName(),matrixDelay, g.getGraph().vertexSet().size(), delayConstraint, isFixedCost, costBase, Additionalcost, controllerCapacity,  delayConstraintBetweenControllers);
					Solution s=FacilityLocation.calculate(g.getName(),matrixDelay, g.getGraph().vertexSet().size(), delayConstraintSwitchController,delayConstraintBetweenControllers,
							controllerCost	, controllerCapacityArray, switchFlows );
					//Final execution time
					long elapsedTime = System.currentTimeMillis() - start;
					if(s!=null)
						Writter.recordSolution(s, outPutPath, elapsedTime);
					else
						Writter.recordNullSolution(outPutPath, g.getName(),delayConstraintSwitchController);
					/*
					//******************Organize Solutions according attributes *********************************
					String key=""+controllerCapacity+"-"+delayConstraintSwitchController+"-"+isFixedCost;
					if(!hashSolutions.containsKey(key))
					{
						ArrayList<Solution> listSolutions=new ArrayList<Solution>();
						listSolutions.add(s);
						hashSolutions.put(key,listSolutions);
					}else {
						ArrayList<Solution> listSolutions = hashSolutions.get(key);
						listSolutions.add(s);							
					}*/

				}

			}


			System.out.println("topologies:"+x+" ...");
			System.out.println("Finished CPLEX, processing solutions obtained....");
			//Writter.processHashResults(hashSolutions,capacityController,d,fixedCost);
			System.out.println("End");

		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	public static String init(double[] arrayCapacityControllers, int switchFlows)
	{
		String outPutPath="";
		try{
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			Date date = new Date();
			String dateFolder=(dateFormat.format(date)); 
			outPutPath="./result/"+dateFolder+"/";
			File directory=new File(outPutPath);
			if(!directory.exists())
			{
				if (directory.mkdir()) {
					File directory2=new File(outPutPath+"/summarized");
					directory2.mkdir();
					System.out.println("Directory is created!");
					OutputStream os = new FileOutputStream(outPutPath+"summarized/result_geral.txt");
					OutputStreamWriter osw = new OutputStreamWriter(os);
					BufferedWriter bw = new BufferedWriter(osw);

					bw.write("Controller Capacity\n");
					for(int i=0;i<arrayCapacityControllers.length;i++)
						bw.write("ControllerType"+i+":"+arrayCapacityControllers[i]+"\n");

					bw.write("switchDemand:"+switchFlows+"\n");
					String typeController="";
					for(int i=0;i<arrayCapacityControllers.length;i++)
						typeController+="ControllerType"+i+" ";

					bw.write("Topology TotalControllerCost DelayConstraintSwitchToController NumberTotalOfControllers "+typeController+"\n");
					bw.close();
				} else {
					System.out.println("Failed to create directory!");
				}
			}

		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return outPutPath;
	}

}
