public class NBody {
	
	/* This method read and return the radius of universe
	*/
	public static double readRadius(String fileName) {
		In in = new In(fileName);

		int numberPlanet = in.readInt();
		double universeRadius = in.readDouble();

		return universeRadius;

	}

	/* This method read and return information of all the bodies.
	*/
	public static Body[] readBodies(String fileName) {
		In in = new In(fileName);

		int numberPlanet = in.readInt();
		double universeRadius = in.readDouble();

		Body[] b = new Body[numberPlanet];
		for(int i = 0; i < numberPlanet; i += 1) {
			b[i] = new Body(0,0,0,0,0,"0");
			b[i].xxPos = in.readDouble();
			b[i].yyPos = in.readDouble();
			b[i].xxVel = in.readDouble();
			b[i].yyVel = in.readDouble();
			b[i].mass = in.readDouble();
			b[i].imgFileName = in.readString();
		}

		return b;
	}


	public static String universe = "images/starfield.jpg";

	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double universeRadius = readRadius(filename);
		Body[] planets = readBodies(filename);

		StdDraw.enableDoubleBuffering();

		StdDraw.setScale(-universeRadius, universeRadius);
		StdDraw.clear();

		/* Draw the background/Universe
		*/
		StdDraw.picture(0, 0, universe);

		
		/* Draw the bodies
		*/
		for (int i = 1; i < planets.length; i += 1) {
			Body.draw(planets[i]);
		}
		
		StdDraw.show();

		/* initialize xForces, yForces and time
		*/

		double[] xForces = new double[planets.length];
		double[] yForces = new double[planets.length];

		for (double tt = 0; tt < T; tt += dt) {

			/* Calculate xForces and yForces for each body
			*/
			for (int i = 1; i < planets.length; i += 1) {
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);
			}

			/* Draw the background/Universe
			*/
			StdDraw.picture(0, 0, universe);

			/* Update the parameters for each body and draw their current location
			*/
			for (int i = 1; i < planets.length; i += 1) {
				planets[i] = planets[i].update(dt, xForces[i], yForces[i]);
				Body.draw(planets[i]);
			}
			StdDraw.show();
			StdDraw.pause(10);
		}

		StdOut.printf("%d\n", bodies.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < bodies.length; i++) {
    		StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                  bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);   
		}		
	}
}