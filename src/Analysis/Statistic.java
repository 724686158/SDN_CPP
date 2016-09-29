package Analysis;

public class Statistic {
	double mean;
	double ci;
	double var;
	double sd;
	double sum;
	public Statistic(double mean, double ci, double var, double sd,double sum) {
		super();
		this.mean = mean;
		this.ci = ci;
		this.var = var;
		this.sd = sd;
		this.sum=sum;
	}
	
}
