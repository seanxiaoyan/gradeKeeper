package startup;
import userInterfaces.InputOutputInterface;
import userInterfaces.ConsoleIO;
import userInterfaces.DialogIO;
import commands.addCourseCommand;
import commands.currentStateCommand;
import commands.outputCourseCommand;
import containers.courseTree;
public class gradeKeeper {
    /**
     * The interface to be used to read input from the user and output results to the user.
     */
    private InputOutputInterface ioInterface;
    /**
     * Initialize the system by creating the dictionaries, ward, and interface for I/O.
     */
    public void initialize() {
        ioInterface = new DialogIO();
        String option = ioInterface.readString("Should dialog boxes be used for I/O? (Y/N) ");
        if (option != null)
            if (option.charAt(0) == 'N' || option.charAt(0) == 'n')
                ioInterface = new ConsoleIO();

    }
    /**
     * Output the prompt that lists the possible tasks, and read the selection chosen by the user.
     *
     * @return the int corresponding to the task selected
     */
    public int readOpId() {
        String[] taskChoices =
                new String[] {"quit", "add a new course", "display current system state","outputCourse"};

        return ioInterface.readChoice(taskChoices);
    }
    /**
     * Run the hospital system: initialize, and then accept and carry out operations. Output the
     * patient and doctor dictionaries, and the ward when finishing.
     */
    public void run() {
        initialize();
        int opId = readOpId();
        while (opId != 0) {
            switch (opId) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    displaySystemState();
                    break;
                case 3:
                    outputCourse();
                    break;
                default:
                    ioInterface.outputString("Invalid int value; try again\n");
            }

            opId = readOpId();
        }

        displaySystemState();
        System.exit(0);
    }

    public void displaySystemState() {
        currentStateCommand state = new currentStateCommand();
        state.traversal(courseTree.tree());
        ioInterface.outputString( state.getCurState() + "\n");
    }
    public void addCourse() {
        String name = ioInterface.readString("Enter the name of the course: ");
        int grade = ioInterface.readInt("Enter the grade of this course: ");
        int credit = ioInterface.readInt("Enter the credit units of this course: ");
        addCourseCommand addCourse = new addCourseCommand();
        addCourse.addCourse(name, grade,credit);
        if (!addCourse.wasSuccessful())
            ioInterface.outputString(addCourse.getErrorMessage() + "\n");
    }
    public void outputCourse() {

        outputCourseCommand outputCourse = new outputCourseCommand();//create new outputCourseCommand object

        //decide target path. default is current path of GradeKeeper
        String answer = ioInterface.readString("Would you like output to default path? Y/N: ");

        if (answer.equals("Y")||answer.equals("y")){   //check the answer
            outputCourse.outputCourse("C:\\Users\\51676\\GradeKeeper\\output.txt");
        }
        else if (answer.equals("N")||answer.equals("n")){
            String path = ioInterface.readString("Enter the path: ");
            outputCourse.outputCourse(path);
        }


        if (!outputCourse.wasSuccessful())
            ioInterface.outputString(outputCourse.getErrorMessage() + "\n");
    }

    /**
     * Return a String that contains all the patients and doctors in the system.
     *
     * @return a String that contains all the patients and doctors in the system.
     */
    public String toString() {
        currentStateCommand state = new currentStateCommand();
        state.traversal(courseTree.tree());
        return "The system is as follows: " + state.getCurState() + "\n";
    }

    /**
     * Run the hospital system.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        gradeKeeper sys = new gradeKeeper();
        sys.run();
    }
}
