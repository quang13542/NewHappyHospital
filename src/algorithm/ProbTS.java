package algorithm;


public class ProbTS{
	enum DistributionType{
		Unknown,
		Continuous,
		Discrete
	}
	
	private class Distribution{
		protected double _rng01;
		protected double _rng11;
		protected double _min;
		protected double _max;
		protected double _mean;
		protected double _range;
		protected double _variance;
		protected DistributionType _type;
		
		protected double min(){
			return this._min;
		}

		protected double max(){
			return this._max;
		}

		protected double mean(){
			return this._mean;
		}

		protected double variance(){
			return this._variance;
		}

		protected DistributionType type(){
			return this._type;
		}
	}
	
	public class UniformDistribution extends Distribution{
		public UniformDistribution(double rng01, double min, double max){
			this._rng01 = rng01;
			this._min = min;
			this._max = max;
			this._range = (max - min);
			this._mean = min + this._range / 2;
			this._variance = ((max - min) * (max - min)) / 12;
			this._type = DistributionType.Continuous;
		}

		public double random(){
			return this._min + this._rng01 * this._range;
		}
	}
	
	public class NormalDistribution extends Distribution{
		private double _sd;
		private Double _y1; 	//private _y1: number | null;
		private Double _y2;		//private _y2: number | null;

		public NormalDistribution(double rng11, double mean, double sd){
			this._rng11 = rng11;
			this._min =	Double.NEGATIVE_INFINITY;
			this._max = Double.POSITIVE_INFINITY;
			this._mean = mean;
			this._sd = sd;
			this._variance = sd * sd;
			this._type = DistributionType.Continuous;
			this._y1 = null;
			this._y2 = null;
		}
		
		public double random(){
            double M = 1/(this._sd*Math.sqrt(Math.PI*2));
            double x = this._rng11 - this._mean;
            double w = Math.exp(-x*x/(2*this._variance));
            return M*w;
		}
    }
	
	public class ExponentialDistribution extends Distribution {
		public ExponentialDistribution(double rng01, double lambda) {
			this._rng01 = rng01;
			this._min = 0;
			this._max = Double.POSITIVE_INFINITY;
			this._mean = 1 / lambda;
			this._variance = Math.pow(lambda, -2);
			this._type = DistributionType.Continuous;
		}

		public double random(){
			return -1 * Math.log(this._rng01) * this._mean;
		}
	}
	
	class LogNormalDistribution extends Distribution{
		private NormalDistribution _nf;

		LogNormalDistribution(double rng11, double mu, double sigma) {
			this._rng11 = rng11;
			this._min = 0;
			this._max = Double.POSITIVE_INFINITY;
			this._mean = Math.exp(mu + ((sigma * sigma) / 2));
			this._variance = (Math.exp(sigma * sigma) - 1) * Math.exp(2 * mu + sigma * sigma);
			this._type = DistributionType.Continuous;
			this._nf = new NormalDistribution(rng11, mu, sigma);
		}

		public double random(){
			return Math.exp(this._nf.random());
		}
    }

    public class PoissonDistribution extends Distribution {
    	private double _L;
		public PoissonDistribution(double rng01, double lambda) {
			this._rng01 = rng01;
			this._min = 0;
			this._max = Double.POSITIVE_INFINITY;
			this._mean = lambda;
			this._variance = lambda;
			this._type = DistributionType.Discrete;
			// Knuth's algorithm
			this._L = Math.exp(-lambda);
		}

		public double random(){
			double k= 0;
			double p= 1;
			while (true) {
				// FIXME This should be [0,1] not [0,1)
				p = p * this._rng01;
				if (p <= this._L) {
					break;
				}
				k++;
			}
			return p;
		}
    }

    public class BimodalDistribution extends Distribution{
    	private double _p;
    	
        public BimodalDistribution(double rng01, double lambda){
        	this._rng01 = rng01;
        	this._min = 0;
        	this._max = Double.POSITIVE_INFINITY;
        	this._mean = lambda;
        	this._variance = lambda;
        	this._type = DistributionType.Discrete;
            double abs = Math.abs(lambda);
            if(abs < 1 && abs != 0)
                this._p = abs;
            else
                if(abs == 0)
                    this._p = 0.6;
                else
                    this._p = 1/abs;
        }
        public double random() {
            double N = 3628800; //n!
            double x = Math.floor(this._rng01*9);
            double px = Math.pow(this._p, x);
            double qx = Math.pow(1 - this._p, 10 - x);
            double M = 1;
            for(int i = 1; i <= x; i++){
                M = M*i*(10 - i);
            }
        	return (N/M)*px*qx;
        }
    }
}

