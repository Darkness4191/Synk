package de.dragon.synk.copy;

import de.dragon.synk.info.Progressbar;

import java.io.*;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Copy {

    private File from, to;
    private CopyConfiguration config;
    private PrintStream printStream;
    private Progressbar progressbar;

    private ArrayList<File> toCopy = new ArrayList<>(50);

    public Copy(File from, File to, CopyConfiguration config) {
        this.from = from;
        this.to = to;
        this.config = config;
    }

    public void start(PrintStream print) {
        this.printStream = print;

        start();
    }

    public void start() {
        if(printStream == null) {
            printStream = System.out;
        }

        toCopy.clear();
        printStream.println("Discovering files...");
        printStream.println("Found " + discoverFiles(from) + " files.");
        printStream.println("Copying files...");

        progressbar = new Progressbar(printStream, true);

        int failed = 0;
        int succeeded = 0;

        for(int i = 0; i < toCopy.size(); i++) {
            if(toCopy.get(i).exists()) {
                printVerbose("Replacing " + toCopy.get(i).getAbsolutePath());
            } else {
                printVerbose("Copying " + toCopy.get(i).getAbsolutePath());
            }

            try {
                Files.copy(Paths.get(toCopy.get(i).getAbsolutePath()),
                           new FileOutputStream(toCopy.get(i).getAbsolutePath().replace(from.getAbsolutePath(), to.getAbsolutePath())));
            } catch (IOException e) {
                printVerbose(toCopy.get(i).getAbsolutePath() + " failed: " + e.getMessage());
                failed++;
            }


        }
    }

    private int discoverFiles(File file) {
        int i = 0;
        if(file.isDirectory()) {
            for(File f : file.listFiles()) {
                toCopy.add(f);
                i++;
            }
            toCopy.add(file);
        } else {
            toCopy.add(file);
        }
        return i + 1;
    }

    private void printVerbose(String s) {
        if(config.isVerbose()) {
            printStream.println(s);
        }
    }

    public File getFrom() {
        return from;
    }

    public File getTo() {
        return to;
    }

    public CopyConfiguration getConfig() {
        return config;
    }
}
