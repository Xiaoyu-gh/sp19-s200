public class Trail1 {

	public static String universe = "images/starfield.jpg";

	public static void main(String[] args) {
		StdDraw.setScale(-100, 100);
		StdDraw.clear();
		StdDraw.picture(0, 0, universe);
		StdDraw.show();
	}
}