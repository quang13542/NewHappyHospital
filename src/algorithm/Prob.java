package algorithm;

import algorithm.ProbTS.*;

public class Prob {
	private double _rng01, _rng11;
	public Prob() {
		double rng = Math.random();
		
		this._rng01 = rng;
		this._rng11 = rng * 2.0;//   this._rng11 = ((rng() * 0x100000000) | 0) / 0x100000000 * 2;
	}
		
	ProbTS x = new ProbTS();
	
	public UniformDistribution uniform(double min, double max){
		return x.new UniformDistribution(this._rng01, min, max);
	}

	public NormalDistribution normal(double mean, double sd){
		return x.new NormalDistribution(this._rng11, mean, sd);
	}

	public ExponentialDistribution exponential(double lambda){
		return x.new ExponentialDistribution(this._rng01, lambda);
	}

	public LogNormalDistribution logNormal(double mu, double sigma){
		return x.new LogNormalDistribution(this._rng11, mu, sigma);
	}

	public PoissonDistribution poisson(double lambda){
		return x.new PoissonDistribution(this._rng01, lambda);
	}
	
    public BimodalDistribution bimodal(double lambda){
        return x.new BimodalDistribution(this._rng01, lambda);
    }
}
