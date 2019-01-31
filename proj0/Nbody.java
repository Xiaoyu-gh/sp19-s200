public class NBody {
	
	public static double readRadius(String fileName) {
		In in = new In(fileName);

		int numberPlanet = in.readInt();
		double universeRadius = in.readDouble();

		return universeRadius;

	}

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
		double t = parseDouble(args[0]);
		double dt = parseDouble(args[1]);
		String filename = args[2];
		double universeRadius = readRadius(filename);
		Body[] planets = readBodies(filename);

		StdDraw.setScale(-universeRadius, universeRadius);
		StdDraw.clear();

		/* Draw the background/Universe
		*/
		StdDraw.picture(0, 0, universe);
		StdDraw.show();

		/* Draw the bodies
		*/

		for (int i = 1; i < b.length; i += 1) {
			draw(planets[i]);
		}
	}
}