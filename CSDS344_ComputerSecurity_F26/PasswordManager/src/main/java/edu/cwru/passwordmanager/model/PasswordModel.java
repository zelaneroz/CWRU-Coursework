package edu.cwru.passwordmanager.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class PasswordModel {
    private ObservableList<Password> passwords = FXCollections.observableArrayList();

    // !!! DO NOT CHANGE - VERY IMPORTANT FOR GRADING !!!
    static private File passwordFile = new File("passwords.txt");

    static private String separator = "\t";

    static private String passwordFilePassword = "";
    static private byte [] passwordFileKey;
    static private byte [] passwordFileSalt;

    // TODO: You can set this to whatever you like to verify that the password the user entered is correct
    private static String verifyString = "cookies";

    private void loadPasswords() {
        // TODO: Replace with loading passwords from file, you will want to add them to the passwords list defined above
        // TODO: Tips: Use buffered reader, make sure you split on separator, make sure you decrypt password
        if (!passwordFile.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(passwordFile), java.nio.charset.StandardCharsets.UTF_8))) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] passwordParts = line.split(separator, 2);
                if (passwordParts.length != 2) {
                    throw new IOException("Invalid password file entry");
                }

                passwords.add(new Password(passwordParts[0], decryptPassword(passwordParts[1], passwordFileKey)));
            }
        } catch (IOException | java.security.GeneralSecurityException | IllegalArgumentException e) {
            throw new IllegalStateException("Unable to load passwords", e);
        }
    }

    public PasswordModel() {
        loadPasswords();
    }

    static public boolean passwordFileExists() {
        return passwordFile.exists();
    }

    static public void initializePasswordFile(String password) throws IOException {
        passwordFile.createNewFile();

        // TODO: Use password to create token and save in file with salt (TIP: Save these just like you would save password)
        passwordFilePassword = password;

        try {
            passwordFileSalt = generateSalt();
            passwordFileKey = generateKey(passwordFilePassword, passwordFileSalt);

            try (BufferedWriter writer = createPasswordFileWriter()) {
                writer.write(Base64.getEncoder().encodeToString(passwordFileSalt));
                writer.write(separator);
                writer.write(encryptPassword(verifyString, passwordFileKey));
                writer.newLine();
            }
        } catch (java.security.GeneralSecurityException e) {
            throw new IOException("Unable to initialize password file", e);
        }
    }

    static public boolean verifyPassword(String password) {
        passwordFilePassword = password; // DO NOT CHANGE

        // TODO: Check first line and use salt to verify that you can decrypt the token using the password from the user
        // TODO: TIP !!! If you get an exception trying to decrypt, that also means they have the wrong passcode, return false!
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(passwordFile), java.nio.charset.StandardCharsets.UTF_8))) {
            String firstLine = reader.readLine();
            if (firstLine != null) {
                String[] verificationParts = firstLine.split(separator, 2);
                if (verificationParts.length == 2) {
                    byte[] storedSalt = Base64.getDecoder().decode(verificationParts[0]);
                    byte[] generatedKey = generateKey(passwordFilePassword, storedSalt);
                    String decryptedToken = decryptPassword(verificationParts[1], generatedKey);

                    if (verifyString.equals(decryptedToken)) {
                        passwordFileSalt = storedSalt;
                        passwordFileKey = generatedKey;
                        return true;
                    }
                }
            }
        } catch (IOException | java.security.GeneralSecurityException | IllegalArgumentException e) {
            return false;
        }

        return false;
    }

    public ObservableList<Password> getPasswords() {
        return passwords;
    }

    public void deletePassword(int index) {
        passwords.remove(index);

        // TODO: Remove it from file
        savePasswords();
    }

    public void updatePassword(Password password, int index) {
        passwords.set(index, password);

        // TODO: Update the file with the new password information
        savePasswords();
    }

    public void addPassword(Password password) {
        passwords.add(password);

        // TODO: Add the new password to the file
        savePasswords();
    }

    // TODO: Tip: Break down each piece into individual methods, for example: generateSalt(), encryptPassword, generateKey(), saveFile, etc ...
    // TODO: Use these functions above, and it will make it easier! Once you know encryption, decryption, etc works, you just need to tie them in
    private static byte[] generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    private static byte[] generateKey(String password, byte[] salt) throws java.security.GeneralSecurityException {
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65_536, 256);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        return keyFactory.generateSecret(keySpec).getEncoded();
    }

    private static String encryptPassword(String password, byte[] key) throws java.security.GeneralSecurityException {
        byte[] nonce = new byte[12];
        new SecureRandom().nextBytes(nonce);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"),
                new javax.crypto.spec.GCMParameterSpec(128, nonce));
        byte[] encryptedPassword = cipher.doFinal(password.getBytes(java.nio.charset.StandardCharsets.UTF_8));

        byte[] encryptedPasswordWithNonce = new byte[nonce.length + encryptedPassword.length];
        System.arraycopy(nonce, 0, encryptedPasswordWithNonce, 0, nonce.length);
        System.arraycopy(encryptedPassword, 0, encryptedPasswordWithNonce, nonce.length, encryptedPassword.length);
        return Base64.getEncoder().encodeToString(encryptedPasswordWithNonce);
    }

    private static String decryptPassword(String encryptedPassword, byte[] key) throws java.security.GeneralSecurityException {
        byte[] decodedPassword = Base64.getDecoder().decode(encryptedPassword);
        if (decodedPassword.length < 28) {
            throw new java.security.GeneralSecurityException("Invalid encrypted password");
        }

        byte[] nonce = new byte[12];
        byte[] passwordAndAuthenticationTag = new byte[decodedPassword.length - nonce.length];
        System.arraycopy(decodedPassword, 0, nonce, 0, nonce.length);
        System.arraycopy(decodedPassword, nonce.length, passwordAndAuthenticationTag, 0,
                passwordAndAuthenticationTag.length);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"),
                new javax.crypto.spec.GCMParameterSpec(128, nonce));
        return new String(cipher.doFinal(passwordAndAuthenticationTag), java.nio.charset.StandardCharsets.UTF_8);
    }

    private static BufferedWriter createPasswordFileWriter() throws IOException {
        return new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(passwordFile), java.nio.charset.StandardCharsets.UTF_8));
    }

    private void savePasswords() {
        try (BufferedWriter writer = createPasswordFileWriter()) {
            writer.write(Base64.getEncoder().encodeToString(passwordFileSalt));
            writer.write(separator);
            writer.write(encryptPassword(verifyString, passwordFileKey));
            writer.newLine();

            for (Password password : passwords) {
                writer.write(password.getLabel());
                writer.write(separator);
                writer.write(encryptPassword(password.getPassword(), passwordFileKey));
                writer.newLine();
            }
        } catch (IOException | java.security.GeneralSecurityException e) {
            throw new IllegalStateException("Unable to save passwords", e);
        }
    }
}
