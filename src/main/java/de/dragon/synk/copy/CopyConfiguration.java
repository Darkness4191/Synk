package de.dragon.synk.copy;

public class CopyConfiguration {

    private boolean showProgress = false;
    private boolean verbose = false;

    public boolean isShowProgress() {
        return showProgress;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
}
