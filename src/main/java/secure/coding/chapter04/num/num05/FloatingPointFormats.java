package secure.coding.chapter04.num.num05;

public class FloatingPointFormats {

	strictfp public static boolean isDenormalized(float val) {
		if (val == 0) {
			return false;
		}
		return ((val > -Float.MIN_NORMAL) && (val < Float.MIN_NORMAL));
	}

	strictfp public static boolean isDenormalized(double val) {
		if (val == 0) {
			return false;
		}
		return ((val > -Double.MIN_NORMAL) && (val < Double.MIN_NORMAL));
	}
	
	static void print(float x, double y) {
		System.out.println("---------------------------------");
		System.out.println(isDenormalized(x) ? "x is Denormalized value as " + x : "x is Normalized value as " + x);
		System.out.println(isDenormalized(y) ? "y is Denormalized value as " + y : "y is Normalized value as " + y);
	}
	
	public static void main(String[] args) {
		float x = 0x1p-125f;
		double y = 0x1p-1020;
		
		print(x,y);				
		System.out.format("normalized float with %%e : %e\n", x);
		System.out.format("normalized float with %%a : %a\n", x);
		
		x = 0x1p-140f;
		print(x,y);
		System.out.format("denormalized float with %%e : %e\n", x);
		System.out.format("denormalized float with %%a : %a\n", x);
		
		System.out.format("normalized double with %%e : %e\n", y);
		System.out.format("normalized double with %%a : %a\n", y);
		
		y = 0x1p-1050;
		print(x,y);
		System.out.format("denormalized double with %%e: %e\n", y);
		System.out.format("denormalized double with %%a: %a\n", y);
	}
}
