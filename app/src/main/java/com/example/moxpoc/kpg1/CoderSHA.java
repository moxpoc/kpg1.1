package com.example.moxpoc.kpg1;


import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;


public class CoderSHA {

    String current;
    String next;
    String game;
    String nextName;
    String nextFam;
    private static String secret = "vvgltglfituumunxjqbwfqcoarzrslrgrvowijwaoueqhpoztkenbeonxszshcuruvlamz";

    CoderSHA(){}

    private CoderSHA(String player, String target, String game, String nextName, String nextFam){
        this.current = player;
        this.next = target;
        this.game = game;
        this.nextName = nextName;
        this.nextFam = nextFam;
    }

    private String getHashFromString(Player player){
        String hash = "failed!";
        try {
            Mac sha256_MAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_MAC.init(secretKeySpec);
            byte[] base = Base64.encodeBase64(sha256_MAC.doFinal(player.toString().getBytes()));
            hash = new String(base);

        } catch (Exception e){
            System.out.print("Error");
        }
        return hash;
    }

    private String hashEquals(String hash){
        String question = hash ;
        try {
            Mac sha256_MAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_MAC.init(secretKeySpec);
            byte[] base = Base64.decodeBase64(sha256_MAC.doFinal(hash.getBytes()));

            // = new String(base);
        } catch (Exception e){
            System.out.print("Error");
        }
        return hash;
    }

    public String toJson(Player player, Player target, String game){
        CoderSHA coderSHA = new CoderSHA(getHashFromString(player), getHashFromString(target), game, target.getFirstName(), target.getSecondName());
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<CoderSHA> jsonAdapter = moshi.adapter(CoderSHA.class);

        String json = jsonAdapter.toJson(coderSHA);
        return json;
    }

    public CoderSHA toObject(String json){
        Moshi moshi = new Moshi.Builder().build();
        CoderSHA coderSHA = new CoderSHA(current, next, game, nextName, nextFam);
        JsonAdapter<CoderSHA> jsonAdapter = moshi.adapter(CoderSHA.class);
        try {
           coderSHA = jsonAdapter.fromJson(json);
        } catch (Exception e ){
            System.out.print("Error");
        }

        return coderSHA;
    }
}
