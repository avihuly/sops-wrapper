import java.io.*;

public class SopsWrapper {
    private String sopsBin;
    private String gpgFingerPrint;

    // Sops flags
    private static final String dec = " --decrypt";
    private static final String enc = " --encrypt --pgp";

    // Constructor
    public SopsWrapper(String sopsBin, String gpgFingerPrint) {
        this.sopsBin = sopsBin;
        this.gpgFingerPrint = gpgFingerPrint;
    }

    //////////////////////////////////////
    //// Encrypt and Decrypt Wrappers ////
    //////////////////////////////////////
    /**
     *
     * @param srcPath   path to file for encryption
     *
     * @return String   encrypted content as string
     * @throws Exception
     */
    public String encrypt(String srcPath) throws Exception {
        return run(String.join(" ", enc, gpgFingerPrint, srcPath));
    }

    /**
     *
     * @param srcPath       path to file for encryption
     * @param targetPath    path to write encrypted file
     *
     * @return String   encrypted content as string
     * @throws Exception
     */
    public String encryptToFile(String srcPath, String targetPath) throws Exception {
        String encryptedOutput = encrypt(srcPath);
        writeFile(encryptedOutput,targetPath);
        return encryptedOutput;
    }

    /**
     *
     * @param srcPath   path to file for decryption
     *
     * @return String   decrypted content as string
     * @throws Exception
     */
    public String decrypt(String srcPath) throws Exception {
        return run(String.join(" ", dec, srcPath));
    }
    /**
     *
     * @param srcPath       path to file for decryption
     * @param targetPath    path to write decrypted file
     *
     * @return String   decrypted content as string
     * @throws Exception
     */
    public String decryptToFile(String srcPath, String targetPath) throws Exception {
        String decryptedOutput = decrypt(srcPath);
        writeFile(decryptedOutput,targetPath);
        return decryptedOutput;
    }

    // run full commend and return string
    private String run(String command) throws Exception {
        String output = "";

            Process process = Runtime.getRuntime().exec(sopsBin + command);

            BufferedReader reader =     new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errReader =  new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String line;
            while ((line = errReader.readLine()) != null)   { System.out.println(line); }
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // !! Delete this or use logger for not printing to console secret data !!
                output += line + "\n";
            }

            int exitCode = process.waitFor();
            System.out.println("\nError code : " + exitCode); // !! Delete this or use logger for not printing sops exit code !!

        return output;
    }

    // Write file
    private void writeFile(String input, String path) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(path));
        writer.write(input);
        writer.close();
    }
}
