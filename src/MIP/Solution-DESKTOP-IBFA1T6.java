package MIP;

public class Solution {
	String topologyName;
	double delayMaxSwitchToController;
	int[][] controllerK_J;
	int[][] switchToController;
	double[] staticCostPerController;
	double totalCost;
	double[] controllerCapacity;
	int switchFlowLoad;
	int delayMaxBetweenCOntrollers;
	
	
	
	public Solution(String topologyName, double delayMax,
			int[][] controllerK_J, int[][] switchToController,
			double[] costPerController, double totalCost,
			double[] controllerCapacity, int switchFlowLoad, int delayMaxBetweenCOntrollers) {
		super();
		this.topologyName = topologyName;
		this.delayMaxSwitchToController = delayMax;
		this.controllerK_J = controllerK_J;
		this.switchToController = switchToController;
		this.staticCostPerController = costPerController;
		this.totalCost = totalCost;
		this.controllerCapacity = controllerCapacity;
		this.switchFlowLoad = switchFlowLoad;
		this.delayMaxBetweenCOntrollers=delayMaxBetweenCOntrollers;
	}
	public String getTopologyName() {
		return topologyName;
	}
	public void setTopologyName(String topologyName) {
		this.topologyName = topologyName;
	}

	public void setDelayMax(double delayMax) {
		this.delayMaxSwitchToController = delayMax;
	}
	public int[][] getControllerK_J() {
		return controllerK_J;
	}
	public void setControllerK_J(int[][] controllerK_J) {
		this.controllerK_J = controllerK_J;
	}
	public int[][] getSwitchToController() {
		return switchToController;
	}
	public void setSwitchToController(int[][] switchToController) {
		this.switchToController = switchToController;
	}
	public double[] getStaticCostPerController() {
		return staticCostPerController;
	}

	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	public double[] getControllerCapacity() {
		return controllerCapacity;
	}
	public void setControllerCapacity(double[] controllerCapacity) {
		this.controllerCapacity = controllerCapacity;
	}

	
	public double getDelayMaxSwitchToController() {
		return delayMaxSwitchToController;
	}
	public void setDelayMaxSwitchToController(double delayMaxSwitchToController) {
		this.delayMaxSwitchToController = delayMaxSwitchToController;
	}

	
	public int getDelayMaxBetweenCOntrollers() {
		return delayMaxBetweenCOntrollers;
	}
	public void setDelayMaxBetweenCOntrollers(int delayMaxBetweenCOntrollers) {
		this.delayMaxBetweenCOntrollers = delayMaxBetweenCOntrollers;
	}
	public int getSwitchFlowLoad() {
		return switchFlowLoad;
	}
	public void setSwitchFlowLoad(int switchFlowLoad) {
		this.switchFlowLoad = switchFlowLoad;
	}
	
	
}
