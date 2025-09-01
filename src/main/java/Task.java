public class Task {
    private String description;
    private boolean isDone;

    // Constructor
    public Task(String description){
        this.description = description;
        this.isDone = false;
    }

    // getter and setter of isDone
    public void markAsDone(){
        this.isDone = true;
    }

    public void markAsUndone(){
        this.isDone = false;
    }

    public String getStatusIcon(){
        return (isDone ? "X":" ");
    }

    // getter and setter of description
    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void outputTaskDetails() {
        System.out.println();
    }

    public String getTaskDetails() {
        return description;
    }
}
