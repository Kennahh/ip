package Chauncey.task;

public class Todo extends Task{
    private static final char LABEL = 'T';

    public Todo(String description) {
        super(description);
    }

    @Override
    public void outputTaskDetails() {
        System.out.println("[" + LABEL + "][" + super.getStatusIcon() + "] " + super.getDescription());
    }

    @Override
    public String getTaskDetails() {
        return "[" + LABEL + "][" + super.getStatusIcon() + "] " + super.getDescription();
    }
}
