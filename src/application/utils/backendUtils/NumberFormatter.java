package application.utils.backendUtils;

public class NumberFormatter {

	public static String format(double value) 
	{
        if (value < 1000) 
        {
            return String.format("%.1f", value);
        } 
        else if (value < 1000000) 
        {
            return String.format("%.2fK", value / 1000);
        } 
        else if (value < 1000000000) 
        {
            return String.format("%.2fM", value / 1000000);
        } 
        else 
        {
            return String.format("%.2fB", value / 1000000000);
        }
    }
	
}
