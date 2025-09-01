public class Todo extends Task{
    private static final char LABEL = 'T';

    public Todo(String description) {
        super(description);
    }

    public void outputTaskDetails() {
        System.out.println("[" + LABEL + "][" + super.getStatusIcon() + "] " + super.getDescription());
    }

    public String getTaskDetails() {
        return "[" + LABEL + "][" + super.getStatusIcon() + "] " + super.getDescription();
    }
}
