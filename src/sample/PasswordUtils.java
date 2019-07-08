package sample;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
//secure RNG engine
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
//makes key using key spec
import javax.crypto.spec.PBEKeySpec;
//convert password to key

public class PasswordUtils {
    private final Random Engine = new SecureRandom();
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final int ITERATIONS = 10000;
    private final int KEY_LENGTH = 256;

    //Salt is Random string to be passed in hash function input which concatenates with password to give it uniqueness
    public String getSalt(int length){
        StringBuilder s = new StringBuilder();
        for(int i=0;i<length;i++){
            s.append(ALPHABET.charAt(Engine.nextInt(ALPHABET.length())));
        }
        return new String(s);

    }

    public byte[] hash(char[] password,byte[] salt) throws InvalidKeySpecException{
        PBEKeySpec spec = new PBEKeySpec(password,salt,ITERATIONS,KEY_LENGTH);
        Arrays.fill(password,'\u0000');
        try{
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        }
        catch(NoSuchAlgorithmException e){
            throw new AssertionError("Password Hashing Error: "+e.getMessage(),e);

        }
        finally {
            spec.clearPassword();
        }
    }

    public String generateSecurePassword(String password,String salt) throws InvalidKeySpecException{
        byte[] hashedpassword = hash(password.toCharArray(),salt.getBytes());
        return Base64.getEncoder().encodeToString(hashedpassword);
    }
    public boolean verifyUserPassword(String providedPassword,String securedPassword,String salt) throws InvalidKeySpecException{
        String newSecurePassword = generateSecurePassword(providedPassword,salt);
        return newSecurePassword.equalsIgnoreCase(securedPassword);

    }
}
