package algorithm;

import algorithm.ProbTS.*;

public class Random {
	private String _name;
	
	public Random() {
	}
	
	public double getProbability() {
		Prob per = new Prob();
        double ran = Math.random();
        ran *= 5;
        int tmp = (int)ran;
        
        //switch(Math.floor(ran*4)){
        switch(tmp)	{
            case 0:
                PoissonDistribution poisson = per.poisson(1); //Math.random();
                this._name = "Poisson";
                return poisson.random();
            case 1:
                UniformDistribution uniform = per.uniform(0, 1);
                this._name = "Uniform";
                return uniform.random();
            case 2:
                this._name = "Bimodal";
                BimodalDistribution bimodal = per.bimodal(1);
                return bimodal.random();
        }
        this._name = "Normal";
        NormalDistribution normal = per.normal(0,1);
        return normal.random();
	}
}
