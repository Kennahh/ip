package Chauncey.task;

public class Deadline extends Task{
    private String deadline;
    private static final char LABEL = 'D';

    public Deadline(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    public String getDeadline() {
        return deadline;
    }

    @Override
    public void outputTaskDetails() {
        System.out.println("[" + LABEL + "][" + super.getStatusIcon() + "] " + super.getDescription() + " (" + deadline + ")");
    }

    @Override
    public String getTaskDetails() {
        return "[" + LABEL + "][" + super.getStatusIcon() + "] " + super.getDescription() + " (" + deadline + ")";
    }
}
