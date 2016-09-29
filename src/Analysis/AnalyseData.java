package Analysis;
import java.util.ArrayList;


public class AnalyseData {
	public static Statistic computeMetrics(ArrayList<Double> listData)
	{
		double mean=0;
		double ci;
		double var=0;
		double sd;
		double sum=0;
		
		for(double d:listData)
			sum+=d;
		
		
		mean=sum/listData.size();
		
		for(double d:listData)
			var+=(Math.pow((mean-d), 2));
		
		var/=listData.size();
		
				
		sd=Math.sqrt(var);
		//95% of confidence
		ci=1.96*sd/Math.sqrt(listData.size());
		
		Statistic result=new Statistic(mean, ci, var, sd,sum);
		
		return result;
	}
}
