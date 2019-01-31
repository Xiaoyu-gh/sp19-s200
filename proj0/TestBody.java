public class TestBody {
	public static void main (String[] args) {
		Body football = new Body(10, 5, 0, 0, 2, "football");
		Body basketball = new Body(-3, -5, 2, 3, 4, "basketball");
		System.out.println(football.calcForceExertedBy(basketball));
		System.out.println(basketball.calcForceExertedBy(football));
	}
}