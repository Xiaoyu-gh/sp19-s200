public class Body {
	
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName; 

	public Body(double xP, double yP, double xV, 
					double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Body(Body b) {
		this.xxPos = b.xxPos;
		this.yyPos = b.yyPos;
		this.xxVel = b.xxVel;
		this.yyVel = b.yyVel;
		this.mass = b.mass;
		this.imgFileName = b.imgFileName;
	}

	public double calcDistance(Body b) {
		double disSq = (this.xxPos - b.xxPos)*(this.xxPos - b.xxPos)+(this.yyPos - b.yyPos)*(this.yyPos - b.yyPos);
		return Math.sqrt(disSq);

	}

	public double calcForceExertedBy(Body b) {
		double f = 6.67e-11 * this.mass * b.mass / (this.calcDistance(b)*this.calcDistance(b)) ;
		return f;
	}

	public double calcForceExertedByX(Body b) {
		double fx = this.calcForceExertedBy(b) * (b.xxPos - this.xxPos) / this.calcDistance(b);
		return fx;
	}

	public double calcForceExertedByY(Body b) {
		double fy = this.calcForceExertedBy(b) * (b.yyPos - this.yyPos) / this.calcDistance(b);
		return fy;
	}

	public double calcNetForceExertedByX(Body[] b) {
		double fnetx = 0;
		for (int i = 0; i < b.length; i += 1) {
			if (!this.equals(b[i])) {
				fnetx += this.calcForceExertedByX(b[i]);
			}
		}
		return fnetx;
	}

	public double calcNetForceExertedByY(Body[] b) {
		double fnety = 0;
		for (int i = 0; i < b.length; i += 1) {
			if (!this.equals(b[i])) {
				fnety += this.calcForceExertedByY(b[i]);
			}
		}
		return fnety;
	}

	public Body update(double dt, double fX, double fY) {
		double accX = fX / this.mass;
		double accY = fY / this.mass;
		this.xxVel += accX * dt;
		this.yyVel += accY * dt;
		this.xxPos += dt * this.xxVel;
		this.yyPos += dt * this.yyVel;
		return this;
	}
} 





