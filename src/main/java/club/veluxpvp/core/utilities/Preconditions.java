package club.veluxpvp.core.utilities;

public class Preconditions {

	public static boolean checkIfValidDuration(String duration) {
		String d = duration.toLowerCase();
		
		if(!d.contains("s") && !d.contains("m") && !d.contains("h") && !d.contains("d") && !d.contains("w") && !d.contains("y") && !d.equals("permanent")) return false;
		
		return true;
	}
}
