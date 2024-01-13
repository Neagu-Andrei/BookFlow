package ro.mta.libraryproject.Main;


import ro.mta.libraryproject.Others.Utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

  /* @author Andi*
 * For testing
 *   
 */
public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
          Server.runServer();
    }
}
