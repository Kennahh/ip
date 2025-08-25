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
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("[" + getStatusIcon() + "] " + description);
    }

    public void markAsUndone(){
        this.isDone = false;
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("[" + getStatusIcon() + "] " + description);
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
}
