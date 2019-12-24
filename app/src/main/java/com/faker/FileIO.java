package com.faker;

import android.os.Bundle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class FileIO {

    static Bundle read(){
        createIfNotExists();
        Bundle bundle = new Bundle();
        try {
            FileReader fr = new FileReader(getPath());
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while(line != null){
                String[] array = line.split(":");
                bundle.putString(array[0], array[1]);
                line = br.readLine();
            }
            br.close();
            fr.close();
        } catch (IOException e) {
        }
        return bundle;
    }

    private static void createIfNotExists(){
        File f = new File(getPath());
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
            }
        }
    }

    private static String getPath(){
        return "/data/system/faker";
    }
}