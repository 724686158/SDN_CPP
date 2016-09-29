package Heuristic;


import java.util.ArrayList;
import java.util.HashMap;

public class LoadBalancing {
	public void  runLoadBalacing()
	{
		HashMap<String, Solutions> hashResults= ReadFiles.readSolutions();

		//meanDelay(hashmatrix);

		//Topologies
		for(String topology: hashResults.keySet())
		{
			//Cases in each topology
			double matrixDelay[][]=hashmatrix.get(topology);
			HashMap<String, ArrayList<Controller>> solution= hashResults.get(topology).getHashMapsolution();
			for(String idSolution: solution.keySet())
			{

				int numberOfSwitches=matrixDelay[0].length; //Switches
				String[] info=idSolution.split("-");
				double delayMax=Double.parseDouble(info[0]);

				int switchesMapped=0;

				ArrayList<Controller> listNewControllers=new ArrayList<Controller>();

				for(Controller c: solution.get(idSolution))
				{
					Controller newController=new Controller(c.id, c.capacity, 0, c.w, c.cost);
					listNewControllers.add(newController);
				}
				//Switch, Controller
				HashMap<Integer,Integer> listSwitchesMapped=new HashMap<Integer, Integer>();

				for(Controller c:listNewControllers)
				{

					listSwitchesMapped.put(c.id,c.id);
					c.listSwitches.put(c.id,0.0);
					switchesMapped++;
					c.swtiches++;
				}

				int infiniteLoop=0;
				while(switchesMapped<numberOfSwitches)
				{
					infiniteLoop++;
					//System.out.println(topology+":"+switchesMapped+"--"+numberOfSwitches+" solution:"+idSolution+" ");

					for(Controller c:listNewControllers)
					{

						double numberOfDeploys=c.w+1;

						for(int i=0;i<numberOfDeploys;i++)
						{
							if(c.swtiches<c.finalCapacity && switchesMapped<numberOfSwitches)
							{
								int node=-1;
								double delay=100000;
								//Find a switch
								for(int j=0;j<numberOfSwitches;j++)
								{

									if(!listSwitchesMapped.containsKey(j) && matrixDelay[c.id][j]<=delayMax)
									{
										//System.out.println("entrou");
										if(delay>=matrixDelay[c.id][j])
										{
											delay=matrixDelay[c.id][j];
											node=j;

										}
									}

								}
								if(node!=-1)
								{
									listSwitchesMapped.put(node,c.id);
									c.listSwitches.put(node,delay);
									switchesMapped++;
									c.swtiches++;
									node=-1;
								}
							}	
						}


						//System.out.println(c.id+":"+c.swtiches+" --->"+listSwitchesMapped.size());
					}

					if(infiniteLoop>10000)
					{
						for(int k=0;k<numberOfSwitches;k++)
							loop:
							{

							if(!listSwitchesMapped.containsKey(k))
							{
								for(Controller c1:listNewControllers)
								{
									if(delayMax>=matrixDelay[c1.id][k])
									{
										for(Integer s:c1.listSwitches.keySet())
											for(Controller c2:listNewControllers)
											{
												if(delayMax>=matrixDelay[s][c2.id] && c2.finalCapacity>c2.swtiches)
												{
													c1.listSwitches.remove(s);
													listSwitchesMapped.remove(s);
													c1.listSwitches.put(k, matrixDelay[c1.id][k]);
													listSwitchesMapped.remove(s);
													break loop;
												}
											}
									}
								}

							}
							}
					}
				}
				//System.out.println(idSolution);
				solution.put(idSolution, listNewControllers);
			}
		}

		//computeLoadBalacing(hashResults);
		//computeAverageCapacity(hashResults);
		computeAverageControllers(hashResults);
	}
	
	public static void meanDelay(HashMap<String, double[][]> hashMatrix)
	{
		for(String topology:hashMatrix.keySet())
		{
			double mean=0;
			double[][] matrix=hashMatrix.get(topology);
			double size=matrix[0].length;
			for(int i=0;i<size;i++)
				for(int j=0;j<size;j++)
					mean+=matrix[i][j];
			mean=mean/(size*size);

			double var=0;

			for(int i=0;i<size;i++)
				for(int j=0;j<size;j++)
					var+=Math.pow((matrix[i][j]-mean), 2);
			var=var/(size*size);

			double sd=Math.sqrt(var);

			System.out.println(topology+" "+mean+" "+sd);

		}
	}

	public static void computeLoadBalacing(HashMap<String, Solutions> hashResults)
	{
		String[] capacity={"20","30","50","100","1000"};
		String[] delay={"50.0","100.0","150.0","200.0","250.0","300.0","350.0","400.0"};

		for(String s:capacity)
			System.out.print(" Capacity="+s+" ");
		System.out.println();

		String fixed="-true-";

		for(String de:delay)
		{
			String line=de+" ";
			for(String cap:capacity)
			{

				HashMap<String, ArrayList<String>> hashTopologies=new HashMap<String, ArrayList<String>>();
				for(String topology:hashResults.keySet())
				{
					Solutions sls=hashResults.get(topology);
					ArrayList<Controller> listCtrl=sls.hashPlacementControllers.get(de+fixed+cap);


					//if(listCtrl.size()>2 )
					for(Controller c1:listCtrl)
					{
						for(Controller c2:listCtrl)
							if(((c1.swtiches/c1.finalCapacity)-(c2.swtiches/c2.finalCapacity))>0.1)
							{

								if(!hashTopologies.containsKey(topology))
								{
									ArrayList<String> listCases=new ArrayList<String>();
									listCases.add(de+fixed+cap);
									hashTopologies.put(topology, listCases);
								}else
								{
									if(!hashTopologies.get(topology).contains(de+fixed+cap))
										hashTopologies.get(topology).add(de+fixed+cap);

								}

							}

					}
				}
				int total=0;
				for(String key:hashTopologies.keySet())
					total+=hashTopologies.get(key).size();
				line+=(total+" ");


			}

			System.out.print(line+"\n");
		}
	}

	public static void computeAverageCapacity (HashMap<String, Solutions> hashResults)
	{
		String[] capacity={"20","30","50","100","1000"};
		String[] delay={"50.0","100.0","150.0","200.0","250.0","300.0","350.0","400.0"};

		for(String s:capacity)
			System.out.print(" Capacity="+s+" ");
		System.out.println();

		String fixed="-false-";

		for(String de:delay)
		{
			String line=de+" ";
			for(String cap:capacity)
			{
				ArrayList<Double> listData=new ArrayList<Double>();
				for(String topology:hashResults.keySet())
				{
					Solutions sls=hashResults.get(topology);
					ArrayList<Controller> listCtrl=sls.hashPlacementControllers.get(de+fixed+cap);

					for(Controller c1:listCtrl)
					{
						listData.add(c1.finalCapacity);
					}
				}

				Double mean= AnalyseData.computeMetrics(listData).mean;
				line+=(mean+" ");


			}

			System.out.print(line+"\n");
		}
	}

	public static void computeAverageControllers (HashMap<String, Solutions> hashResults)
	{
		String[] capacity={"20","30","50","100","1000"};
		String[] delay={"50.0","100.0","150.0","200.0","250.0","300.0","350.0","400.0"};

		for(String s:capacity)
			System.out.print(" Capacity="+s+" ");
		System.out.println();

		String fixed="-true-";

		for(String de:delay)
		{
			String line=de+" ";
			for(String cap:capacity)
			{
				ArrayList<Double> listData=new ArrayList<Double>();
				for(String topology:hashResults.keySet())
				{
					Solutions sls=hashResults.get(topology);
					ArrayList<Controller> listCtrl=sls.hashPlacementControllers.get(de+fixed+cap);

					listData.add(listCtrl.size()*1.0);
				}

				Double total= AnalyseData.computeMetrics(listData).sum;
				line+=(total+" ");


			}

			System.out.print(line+"\n");
		}
	}
}
