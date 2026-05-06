import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Scanner;

public class SecretKeyAuthenticator {

    private static final int iterations = 600000;
    private static final int hashLengthBytes = 32;
    private static final int saltLengthBytes = 16 ;
    private static final String pwdHashFilePath = "pwd.bin";

    private static byte[] hashPassphrase(char[] pwd, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(pwd, salt, iterations, hashLengthBytes * 8);
        byte[] hash;

        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            hash = skf.generateSecret(spec).getEncoded();

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("hashing error");
        } finally {
            spec.clearPassword();
        }

        return hash;
    }

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();

        byte[] salt = new byte[saltLengthBytes];
        random.nextBytes(salt);

        return salt;
    }

    // used to initialize / change a pwd.hash file with a passphrase
    static void main() {
        char[] pwd = getPassphrase();

        byte[] salt = SecretKeyAuthenticator.generateSalt();

        byte[] hash = SecretKeyAuthenticator.hashPassphrase(pwd, salt);

        try (FileOutputStream writer = new FileOutputStream(pwdHashFilePath)) {
            writer.write(salt);
            writer.write(hash);

            System.out.println("Password updated!");
        } catch (Exception e) {
            System.out.println("Could not write hash to file");
        }
    }

    public static char[] getPassphrase(){
        char[] pwd;
        Console console = System.console();

        if (console != null) {
            pwd = console.readPassword("Enter passphrase: ");
        } else {
            System.out.println("No console detected (IDE mode).");
            System.out.print("Enter passphrase: ");
            Scanner scanner = new Scanner(System.in);
            pwd = scanner.nextLine().toCharArray();
        }

        return pwd;
    }

    // returns true if password is correct, otherwise false
    public static boolean authenticatePassphrase(char[] pwd) {
        byte[] fileSalt = new byte[saltLengthBytes];
        byte[] fileHash = new byte[hashLengthBytes];

        try (FileInputStream reader = new FileInputStream(pwdHashFilePath)){
            reader.read(fileSalt);
            reader.read(fileHash);
        }
        catch (Exception e){
            return false;
        }

        byte[] hash = hashPassphrase(pwd, fileSalt);

        return Arrays.equals(hash, fileHash);
    }


}
