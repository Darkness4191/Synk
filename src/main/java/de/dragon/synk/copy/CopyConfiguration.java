package de.dragon.synk.copy;

public class CopyConfiguration {

    private boolean copyTime = false;
    private boolean showProgress = false;
    private boolean verbose = false;

    public boolean isCopyTime() {
        return copyTime;
    }

    public boolean isShowProgress() {
        return showProgress;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setCopyTime(boolean copyTime) {
        this.copyTime = copyTime;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
}
