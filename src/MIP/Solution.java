package MIP;

public class Solution {
	String topologyName;
	double delayMax;
	int[] controller;
	int[][] switchToController;
	double[] costPerController;
	double totalCost;
	boolean boolFixedCost;
	int controllerCapacity;
	int[] costWeigth;
	public Solution(String topologyName, double delayMax, int[] controller,
			int[][] switchToController, double[] costPerController,
			double totalCost, boolean boolFixedCost, int controllerCapacity,int[] costWeigth) {
		super();
		this.topologyName = topologyName;
		this.delayMax = delayMax;
		this.controller = controller;
		this.switchToController = switchToController;
		this.costPerController = costPerController;
		this.totalCost = totalCost;
		this.boolFixedCost=boolFixedCost;
		this.controllerCapacity=controllerCapacity;
		this.costWeigth=costWeigth;
	}

	public String getTopologyName() {
		return topologyName;
	}

	public void setTopologyName(String topologyName) {
		this.topologyName = topologyName;
	}

	public double getDelayMax() {
		return delayMax;
	}

	public void setDelayMax(double delayMax) {
		this.delayMax = delayMax;
	}

	public int[] getController() {
		return controller;
	}

	public void setController(int[] controller) {
		this.controller = controller;
	}

	public int[][] getSwitchToController() {
		return switchToController;
	}

	public void setSwitchToController(int[][] switchToController) {
		this.switchToController = switchToController;
	}

	public double[] getCostPerController() {
		return costPerController;
	}

	public void setCostPerController(double[] costPerController) {
		this.costPerController = costPerController;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public boolean isBoolFixedCost() {
		return boolFixedCost;
	}

	public void setBoolFixedCost(boolean boolFixedCost) {
		this.boolFixedCost = boolFixedCost;
	}

	public int getControllerCapacity() {
		return controllerCapacity;
	}

	public void setControllerCapacity(int controllerCapacity) {
		this.controllerCapacity = controllerCapacity;
	}

	public int[] getCostWeigth() {
		return costWeigth;
	}

	public void setCostWeigth(int[] costWeigth) {
		this.costWeigth = costWeigth;
	}
	
	
	
}
