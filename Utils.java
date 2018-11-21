public class Utils {

    public static String bytesToString(byte[] bytes) {
        String result = "";
        for(byte b : bytes) {
            result += String.format("%02x", b);
        }
        return result;
    }
}