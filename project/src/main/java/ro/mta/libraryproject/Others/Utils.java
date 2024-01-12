package ro.mta.libraryproject.Others;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.DrbgParameters;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;


/**
 *  Ioana   
 */
public class Utils {

    /**
     * @param size
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static byte[] getRandomBytes(int size)
            throws NoSuchAlgorithmException
    {
        byte[] b = new byte[size];
        SecureRandom random = SecureRandom.getInstance("DRBG",
                DrbgParameters.instantiation(256, DrbgParameters.Capability.PR_AND_RESEED, null));
        byte[] seed = random.generateSeed(64);
        random.nextBytes(b);
        return b;
    }

    /**
     * @param input
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String toHex (byte[] input)
            throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, input);
        String hex = bi.toString(16);

        int paddingLength = (input.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }

    /**
     * @param hex
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static byte[] fromHex(String hex)
            throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i < bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    /**
     * @param password
     * @param salt
     * @return
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public static String getHash(String password,byte[] salt)
            throws InvalidKeySpecException, NoSuchAlgorithmException
    {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 512);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return Utils.toHex(salt)+":"+Utils.toHex(hash);
    }

    /**
     * @param originalPassword
     * @param storedPassword
     * @return
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public static boolean verifyHash(String originalPassword, String storedPassword)
            throws InvalidKeySpecException, NoSuchAlgorithmException
    {
        String[] parts = storedPassword.split(":");
        byte[] salt = fromHex(parts[0]);
        byte[] passwordHash = fromHex(parts[1]);
        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(),
                salt, 65536, 512);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] verificationHash = skf.generateSecret(spec).getEncoded();

        int diff = passwordHash.length ^ verificationHash.length;
        int i;
        for(i=0;i<passwordHash.length;i++) {
            diff |= passwordHash[i] ^ verificationHash[i];
        }

        return diff == 0;
    }

}
