package com.example.moxpoc.kpg1.Helpers;

import com.example.moxpoc.kpg1.Player;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

public class JsonTargetHelper {
    private String current;
    private String next;
    private String game;
    private static String secret = "vvgltglfituumunxjqbwfqcoarzrslrgrvowijwaoueqhpoztkenbeonxszshcuruvlamz";
    private static String salt = "bbcdc866f870cd4b";


    public JsonTargetHelper (Player player, Player target, String game){
        this.current = getHashFromString( player.toString());
        this.next = getHashFromString(target.toString());
        this.game = game;
    }

    public JsonTargetHelper(){
    }

    public JsonTargetHelper(String current, String next, String game){
        this.current = getHashFromString(current);
        this.next = getHashFromString(next);
        this.game = game;
    }

    public String getSnameCurrent(){
        String sName[] = getStringFromHash(current).split(" ");
        return sName[1];
    }

    public String getFnameCurrent(){
        String fName[] = getStringFromHash(current).split(" ");
        return fName[0];
    }

    public String getSnameNext(){
        String sName[] = getStringFromHash(next).split(" ");
        return sName[1];
    }

    public String geFnameNext(){
        String fName[] = getStringFromHash(next).split(" ");
        return fName[0];
    }

    public String getGame() {
        return game;
    }

    public String getCurrent() {
        return current;
    }

    public String getCurrentText(){
        return getStringFromHash(current);
    }

    public String getNext() {
        return next;
    }

    public String getNextText(){
        return getStringFromHash(next);
    }

    private String getHashFromString(String inputData){
        TextEncryptor encryptor = Encryptors.text(secret, salt);
        return encryptor.encrypt(inputData);
    }

    private String getStringFromHash(String hash){
        TextEncryptor encryptor = Encryptors.text(secret, salt);
        return encryptor.decrypt(hash);
    }


}
