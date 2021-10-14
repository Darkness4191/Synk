package de.dragon.synk;

import de.dragon.synk.copy.CopyConfiguration;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        if(args.length > 0) {
            CopyConfiguration copyConfiguration = new CopyConfiguration();
            Path from = null, to = null;

            for(String s : args) {
                switch (s) {
                    case "-v" -> {copyConfiguration.setVerbose(true);}
                    case "-p" -> {copyConfiguration.setShowProgress(true);}
                    default -> {
                        if(from == null) {
                            from = Paths.get(s);
                        } else if(to == null){
                            to = Paths.get(s);
                        } else {
                            throw new IllegalArgumentException("Please provide two paths!");
                        }
                    }
                }
            }
        } else {
            System.out.println("HELP PLACEHOLDER");
        }
    }
}
