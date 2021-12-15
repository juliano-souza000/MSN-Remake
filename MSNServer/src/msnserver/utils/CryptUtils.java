package msnserver.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class CryptUtils
{
    public static String hash256(String input)
    {
	try
	{
	    MessageDigest digest = MessageDigest.getInstance("SHA-256");
	    return bytesToHex(digest.digest(input.getBytes(StandardCharsets.UTF_8)));
	} catch(Exception e) { }
	return null;
    }
    
    private static String bytesToHex(byte[] hash)
    {
	StringBuffer hexString = new StringBuffer();
	for (int i = 0; i < hash.length; i++) 
	{
	    String hex = Integer.toHexString(0xff & hash[i]);
	    if(hex.length() == 1) 
		hexString.append('0');
	    hexString.append(hex);
	}
	return hexString.toString();
    }
}