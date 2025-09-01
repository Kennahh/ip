public class Event extends Task{
    private String startTime;
    private String endTime;
    private static final char LABEL = 'E';

    public Event(String description, String startTime, String endTime) {
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void outputTaskDetails() {
        System.out.println("[" + LABEL + "][" + super.getStatusIcon() + "] " + super.getDescription() + " (" + startTime + " " + endTime + ")");
    }

    public String getTaskDetails() {
        return "[" + LABEL + "][" + super.getStatusIcon() + "] " + super.getDescription() + " (" + startTime + " " + endTime + ")";
    }
}
