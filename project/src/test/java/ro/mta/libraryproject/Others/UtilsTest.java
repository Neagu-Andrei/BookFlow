package ro.mta.libraryproject.Others;

import junit.framework.TestCase;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;

import ro.mta.libraryproject.Others.Utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *   ,  tin
 */

public class UtilsTest extends TestCase {

    public void testGetRandomBytes() throws NoSuchAlgorithmException {

        boolean different = true;

        ArrayList<byte[]> randomBytes = new ArrayList<>();
        int randomLength = 50;
        for (int i = 0; i < 100 && different; i++) {
            randomBytes.add(Utils.getRandomBytes(randomLength));
            for (int j = 0; j < i; j++) {
                boolean equal = true;
                for (int k = 0; k < randomLength; k++)
                    if (randomBytes.get(i)[k] != randomBytes.get(j)[k])
                        equal = false;
                if (equal == true)
                    different = false;
            }
        }

        assertEquals(different, true);
    }

    public void testToHex() {
        byte[] bytesHelloWorld = {72, 101, 108, 108, 111, 119, 111, 114, 108, 100, 33};

        String stringHelloWorld = null;

        try {
            stringHelloWorld = Utils.toHex(bytesHelloWorld);
            System.out.println(stringHelloWorld);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        assertEquals("48656c6c6f776f726c6421", stringHelloWorld);
    }

    public void testFromHex() {
        byte[] bytesHelloWorld = null;

        String stringHelloWorld = "48656c6c6f776f726c6421";

        try {
            bytesHelloWorld = Utils.fromHex(stringHelloWorld);
            System.out.println(bytesHelloWorld.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String expectedBytesHelloWorld = "[B@7cef4e59";
        assertEquals(expectedBytesHelloWorld, bytesHelloWorld.toString());
    }
    public void testGetHash() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = "parola";
        byte[] salt = Utils.getRandomBytes(20);

        String hashedPassword = Utils.getHash(password, salt);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 512);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();

        assertEquals(hashedPassword, Utils.toHex(salt) + ":" + Utils.toHex(hash));
    }
    public void testVerifyHash() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = "parola";
        byte[] salt = Utils.getRandomBytes(20);
        String hash = Utils.getHash(password, salt);

        assertEquals(Utils.verifyHash(password, hash),
                true);

    }
}