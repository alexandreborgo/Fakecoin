public class Utils {

    /* Transform an array of bytes into a String in hexadecimal */
    public static String bytesToString(byte[] bytes) {
        String result = "";
        for(byte b : bytes) {
            result += String.format("%02x", b);
        }
        return result;
    }

    /* Return true if the given hash is a Fakecoin hash (two last octets at 0) or false if it is not */
    public static boolean isFakecoinHash(String hash) {        
        if( hash.charAt(hash.length()-1) == '0' && 
            hash.charAt(hash.length()-2) == '0' &&
            hash.charAt(hash.length()-3) == '0' &&
            hash.charAt(hash.length()-4) == '0') {
            return true;
        }
        return false;
    }
}