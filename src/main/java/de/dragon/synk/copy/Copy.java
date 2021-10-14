package de.dragon.synk.copy;

import de.dragon.synk.info.Progressbar;

import java.io.*;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Copy {

    private Path from, to;
    private CopyConfiguration config;
    private PrintStream printStream;
    private Progressbar progressbar;

    private int failed = 0, succeeded = 0;

    private ArrayList<Path> toCopy = new ArrayList<>(50);

    public Copy(Path from, Path to, CopyConfiguration config) {
        this.from = from;
        this.to = to;
        this.config = config;
    }

    public void start(PrintStream print) {
        this.printStream = print;

        start();
    }

    public void start() {
        succeeded = 0;
        failed = 0;

        if (printStream == null) {
            printStream = System.out;
        }

        toCopy.clear();
        printStream.println("Discovering files...");
        printStream.println("Found " + discoverFiles(from) + " files.");
        printStream.println("Copying files...");

        if (config.isShowProgress()) {
            progressbar = new Progressbar(printStream, true);
        }

        for (int i = 0; i < toCopy.size(); i++) {
            if (Files.exists(toCopy.get(i))) {
                printVerbose("Replacing " + toCopy.get(i));
            } else {
                printVerbose("Copying " + toCopy.get(i));
            }

            try {
                Files.copy(toCopy.get(i), new FileOutputStream(toCopy.get(i).toString().replace(from.toString(), to.toString())));
                succeeded++;
            } catch (IOException e) {
                printVerbose(toCopy.get(i) + " failed: " + e.getMessage());
                failed++;
            }

            if (config.isShowProgress()) {
                progressbar.update((i + 1) * 1D / toCopy.size());
            }
        }

        if (config.isShowProgress()) {
            progressbar.finish();
        }

        printStream.println(String.format("Operation complete: %i successful, %i failed", succeeded, failed));
    }

    private int discoverFiles(Path path) {
        int i = 0;
        try {
            if (Files.isDirectory(path)) {
                i += Files.list(path).mapToInt(this::discoverFiles).sum();
                toCopy.add(path);
            } else {
                toCopy.add(path);
            }
        } catch (IOException e) {
            printVerbose(path + " failed: " + e.getMessage());
            failed++;
        } finally {
            return i + 1;
        }
    }

    private void printVerbose(String s) {
        if (config.isVerbose()) {
            printStream.println(s);
        }
    }

    public Path getFrom() {
        return from;
    }

    public Path getTo() {
        return to;
    }

    public CopyConfiguration getConfig() {
        return config;
    }
}
