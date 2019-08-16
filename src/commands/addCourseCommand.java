package commands;
import lib280.exception.*;
import entities.Course;
import containers.courseTree;
public class addCourseCommand extends commandStatus{
    /**
     * Create a course with the specified name, and add the course into the system.
     *
     * @param name: course name
     */
    public void addCourse (String name,int grade,int credit){
        try {
        Course c = new Course(name);
        c.setGrade(grade);
        c.setCredit(credit);


        courseTree.tree().insert(c);
        successful=true;
        }
        catch (InvalidArgument280Exception e){successful=false;
        errorMessage=e.getMessage();}
        catch (DuplicateItems280Exception e) {
            successful = false;
            errorMessage = "Cannot add this course since it is already in the system";
        }

    }
}
