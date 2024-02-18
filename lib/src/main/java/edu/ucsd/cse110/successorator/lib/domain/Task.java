package edu.ucsd.cse110.successorator.lib.domain;


/*
 * This class is an object that represents a single task
 *
 * Private variables:
 * String taskText - contains the user input for what the task is
 * boolean isDone - whether or not the task has been marked as done
 *
 * Constructor:
 * 1. initializes the String taskText to constructor parameter
 * 2. intializes isDone to false by default;
 */
public class Task {
    private String taskText;
    private boolean isDone;

<<<<<<< Updated upstream

    public Task(String taskText){
        this.taskText = taskText;
        this.isDone = false;
=======
    private final @Nullable Integer id;
    private final String text;
    private final boolean isDone;
    private int sortOrder;

    private static int maxOrder = 0;
    private static int minOrder = 0;


    public Task(@Nullable Integer id, String text, boolean isDone, Integer sortOrder) {
        this.id = id;
        this.text = text;
        this.isDone = isDone;
        this.sortOrder = sortOrder;
        if (maxOrder < sortOrder) {
            maxOrder = sortOrder;
        }
        if (minOrder >sortOrder){
            minOrder = sortOrder;
        }
>>>>>>> Stashed changes
    }

    /* Below are getter methods for the taskText
     * both instance and static
     *
     * @return taskText
     */
    public String getTaskText() {
        return taskText;
    }
    public static String getTaskText(Task theTask) {
        return theTask.taskText;
    }
    /* Below are getter methods for whether this task is done or not
     * @return isDone
     */
    public boolean getDoneStatus() {
        return isDone;
    }
<<<<<<< Updated upstream
    public static boolean getDoneStatus(Task theTask) {
        return theTask.isDone;
=======

    public Task withDone(boolean isDone) {
        if(isDone){
            maxOrder = maxOrder +1;
            this.sortOrder = maxOrder;
        }else{
            minOrder = minOrder -1;
            this.sortOrder = minOrder;
        }
        return new Task(id, text, isDone, sortOrder);
>>>>>>> Stashed changes
    }

    /* Below are setter methods to "mark task as done"
     */
    public void markAsDone(){
        this.isDone = true;
    }
    public static void markAsDone(Task theTask){
        theTask.isDone = true;
    }

    /* Below are setter methods to "mark task as not done"
     */
    public void markAsToDo(){
        this.isDone = false;
    }
    public static void markAsToDo(Task theTask){
        theTask.isDone = false;
    }
}
