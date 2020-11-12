package ci583.scheduling.output;

public abstract class StatsFormatter {

    private SchedulerStats stats;

    public StatsFormatter(SchedulerStats stats) {
        this.stats = stats;
    }

    public abstract void formatRuns();

    public abstract void formatAverage();

    public SchedulerStats getStats() {
        return stats;
    }
}
