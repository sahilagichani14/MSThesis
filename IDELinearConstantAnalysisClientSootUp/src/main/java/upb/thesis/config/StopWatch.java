package upb.thesis.config;

public class StopWatch {
    private final String name;
    private long elapsedTime = 0L;
    private long startTime;
    private boolean inCounting = false;

    public static StopWatch newAndStart(String name) {
        StopWatch stopwatch = new StopWatch(name);
        stopwatch.start();
        return stopwatch;
    }

    private StopWatch(String name) {
        this.name = name;
    }

    private void start() {
        if (!this.inCounting) {
            this.inCounting = true;
            this.startTime = System.currentTimeMillis();
        }

    }

    public void stop() {
        if (this.inCounting) {
            this.elapsedTime += System.currentTimeMillis() - this.startTime;
            this.inCounting = false;
        }

    }

    public float elapsed() {
        return (float)this.elapsedTime / 1000.0F;
    }

    public void reset() {
        this.elapsedTime = 0L;
        this.inCounting = false;
    }

    public void restart() {
        this.reset();
        this.start();
    }

    public String toString() {
        return String.format("%s elapsed time: %.2fs", this.name, this.elapsed());
    }
}
