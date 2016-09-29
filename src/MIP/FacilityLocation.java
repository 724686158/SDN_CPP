package MIP;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

public class FacilityLocation {
	
	
	public static Solution calculate(String topologyName,double delayMatrix[][],int numberOfNodes,int delayM,boolean fixedCost,int costBase,int costAdd,int capacity)
	{
		Solution solution=null;
		try {
			
			//*****************************************
			IloCplex cplex = new IloCplex();
			
			IloIntVar[] x = new IloIntVar[numberOfNodes];
			//nodes combination 2x2
			int combination=(numberOfNodes*(numberOfNodes-1))/2;
			
			IloIntVar[] u = new IloIntVar[combination];
			for(int i=0;i<combination;i++)
				u[i]=cplex.boolVar();
			// w={0,1}
			//IloNumVar[] w = new IloIntVar[numberOfNodes];
			IloNumVar[] w=new IloIntVar[numberOfNodes];
			IloNumVar[] c =  cplex.numVarArray(numberOfNodes, 0., Double.MAX_VALUE);
			
			IloNumExpr objective = cplex.numExpr();

			
			for(int j=0;j<numberOfNodes;j++)
			{
				x[j]= cplex.boolVar();
				
				w[j]= cplex.intVar(0, numberOfNodes);
				objective=(cplex.sum(objective, cplex.prod(c[j], x[j])));
			}
			
			IloIntVar[][] y = new IloIntVar[numberOfNodes][numberOfNodes];
			for(int i=0;i<numberOfNodes;i++)
				for(int j=0;j<numberOfNodes;j++)
					y[i][j]=cplex.boolVar();

			//Objective function
			cplex.addMinimize(objective);

			//*********************Constraints***********************************************
			
			//Cost definition
			for(int j=0;j<numberOfNodes;j++)
			{
				if(fixedCost)
				{
					cplex.addEq(c[j], costBase); //Value of each controller
					
					IloNumExpr expr = cplex.numExpr();
					for(int i=0;i<numberOfNodes;i++)
						expr = cplex.sum(expr, y[i][j]);
					cplex.addLe(expr,capacity);
					
					
				}
				else
				{
					
					
					IloNumExpr cost=cplex.sum(costBase,cplex.prod(costAdd, w[j]) );
					cplex.addEq(c[j],cost); //Value of each controller
					
					IloNumExpr expr = cplex.numExpr();
					for(int i=0;i<numberOfNodes;i++)
						expr = cplex.sum(expr, y[i][j]);
					cplex.addLe(expr, cplex.prod(cplex.sum(w[j],1),capacity));
					
					/*IloNumExpr cost=cplex.sum(costLow, cplex.prod(costHigh, cplex.sum(1,cplex.prod(-1, w[j]))));
					cplex.addEq(c[j],cost); //Value of each controller
					
					IloNumExpr expr = cplex.numExpr();
					for(int i=0;i<numberOfNodes;i++)
						expr = cplex.sum(expr, y[i][j]);
					cplex.addLe(expr, cplex.sum(capacityLowC,cplex.prod(numberOfNodes, cplex.sum(1,cplex.prod(-1, w[j])))));*/
					
				}
			}
			
			/*if(fixedCost)
			{
				int indexU=0;
				for(int i=0;i<numberOfNodes;i++)
					for(int j=i+1;j<numberOfNodes;j++)
					{
						IloNumExpr sum1 = cplex.numExpr();
						for(int k=0;k<numberOfNodes;k++)
							sum1 = cplex.sum(sum1,y[k][i]);
						
						IloNumExpr sum2 = cplex.numExpr();
						for(int k=0;k<numberOfNodes;k++)
							sum2 = cplex.sum(sum2,y[k][j]);
						double a=numberOfNodes*0.2;
						
						IloNumExpr part1=cplex.sum(x[j],x[i]);				
						IloNumExpr part2=cplex.sum(1,cplex.prod(4, cplex.diff(1, u[indexU])));
						cplex.addLe(part1,part2);
						
						//cplex.addLe(cplex.prod(cplex.prod(x[j], x[i]), cplex.diff(sum1, sum2)), a);
						cplex.addLe(cplex.diff(sum2, sum1), cplex.sum(a, cplex.prod(u[indexU],numberOfNodes)));
						cplex.addLe(cplex.diff( sum1,sum2), cplex.sum(a, cplex.prod(u[indexU],numberOfNodes)));
						indexU++;
					}	
			}*/
			
			
			//Every switch should be connected to a controller
			
			for(int i=0;i<numberOfNodes;i++)
			{
				IloNumExpr expr = cplex.numExpr();
				for(int j=0;j<numberOfNodes;j++)
					expr = cplex.sum(expr,y[i][j]);
				cplex.addEq(expr,1);
			}

			//One switch can be attached to a controller if and only if this controller was allocated
			for(int i=0;i<numberOfNodes;i++)
				for(int j=0;j<numberOfNodes;j++)
				{
					IloNumExpr expr = cplex.numExpr();
					expr = cplex.sum(y[i][j],cplex.prod(-1.0,x[j]));
					cplex.addLe(expr,0);
				}
					
			//the delay between a switch and controller should be less than D
			for(int i=0;i<numberOfNodes;i++)
				for(int j=0;j<numberOfNodes;j++)
				{
					IloNumExpr expr = cplex.numExpr();
					expr = cplex.prod(y[i][j], delayMatrix[i][j]);
					cplex.addLe(expr,delayM);
				}
			//Load balancing
					
			/*for(int i=0;i<numberOfNodes;i++)
				for(int j=i+1;j<numberOfNodes;j++)
				{
					IloNumExpr sum1 = cplex.numExpr();
					for(int k=0;k<numberOfNodes;k++)
						sum1 = cplex.sum(sum1,y[k][i]);
					
					IloNumExpr sum2 = cplex.numExpr();
					for(int k=0;k<numberOfNodes;k++)
						sum2 = cplex.sum(sum2,y[k][j]);
					
					IloNumExpr cap1 = cplex.numExpr();
					cap1=(cplex.sum(capacity,cplex.prod(w[i], capacity)));
					
					IloNumExpr cap2 = cplex.numExpr();
					cap2=(cplex.sum(capacity,cplex.prod(w[j], capacity)));

					
					IloNumExpr expr1 = cplex.numExpr();
					IloNumExpr expr2 = cplex.numExpr();
					
					expr1= cplex.diff(cplex.prod(sum1, cap1), cplex.prod(sum2, cap2));
					expr2= cplex.diff(cplex.prod(sum2, cap2), cplex.prod(sum1, cap1));
					
					IloNumExpr lessThen = cplex.numExpr();
					lessThen=cplex.prod(0.2, cplex.prod(cap1, cap2));
					
					
					cplex.addLe(expr1, lessThen);
					cplex.addLe(expr2, lessThen);
					//cplex.addLe(cplex.diff(cplex.prod(sum2, cap2), cplex.prod(sum1, cap1)), cplex.prod(0.2, cplex.prod(cap1, cap2)));
				}*/
			
			if ( cplex.solve() ) {
				//*******Solution variables**************************
				double totalCost=0;
				double[] costPerController=new double[numberOfNodes];
				int[][] switchToController=new int[numberOfNodes][numberOfNodes];
				int[] controllerPlacement=new int[numberOfNodes];
				int[] wPerController=new int[numberOfNodes];
				//***************************************************
				
				
				System.out.println("Cost:"+cplex.getObjValue());
				totalCost=cplex.getObjValue();
				
				
				for(int j=0;j<numberOfNodes;j++)
				{
					controllerPlacement[j]=(int) cplex.getValue(x[j]);
					
					if(!fixedCost)
						wPerController[j]=(int) cplex.getValue(w[j]);
					costPerController[j]=cplex.getValue(x[j])*(costBase+wPerController[j]*costAdd);
					//System.out.println("x"+j+" "+cplex.getValue(x[j]));
				}
				
				for(int i=0;i<numberOfNodes;i++)
					for(int j=0;j<numberOfNodes;j++)
					{
						switchToController[i][j]=(int) cplex.getValue(y[i][j]);
						//System.out.println("y["+i+"]["+j+"] "+cplex.getValue(y[i][j]));
					}
				solution=new Solution(topologyName, delayM, controllerPlacement, switchToController, costPerController, totalCost, fixedCost,capacity,wPerController);
				
			}
			cplex.end();
		}
		catch (IloException e) {
			System.err.println("Concert exception ’" + e + "’ caught");
		}
		return solution;
	}
}
