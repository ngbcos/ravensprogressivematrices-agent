package agent;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * A list of RavensProblems within one set.
 * 
 */

public class ProblemSet {
    private String name;
    private ArrayList<RavensProblem> problems;
    
    /**
     * Initializes a new empty ProblemSet with the given name 
     * 
     * @param name The name of the problem set.
     */
    public ProblemSet(String name) {
        this.name=name;
        problems=new ArrayList<>();
        loadProblemSet();
    }
    

    public String getName() {
        return name;
    }
    

    public ArrayList<RavensProblem> getProblems() {
        return problems;
    }
    
    /**
     * Loads the problem set from the folder whose name matches that of the problem set.
     */
    private void loadProblemSet() {
        Scanner r = null;
        try {
            r = new Scanner(new File("Problems" + File.separator + getName() + File.separator + "ProblemList.txt"));
        } catch(Exception ex) {
            System.out.println(ex);
        }
        while(r.hasNext()) {
            String line = r.nextLine();
            loadProblem(line);
        }
    }
    
    /**
     * Loads the problem from the folder whose name is given in 'problemName'     * 
     */
    private void loadProblem(String problemName) {
        Scanner r = null;
        try {
            r = new Scanner(new File("Problems" + File.separator + getName() + File.separator + problemName + File.separator + "ProblemData.txt"));
        } catch(Exception ex) {
            System.out.println(ex);
        }
        
        String problemType = r.nextLine();
        int correctAnswer = Integer.parseInt(r.nextLine());
        
        RavensProblem newProblem = new RavensProblem(problemName, problemType, correctAnswer);
        newProblem.getFigures().put("A", new RavensFigure("A", problemName, getName()));
        newProblem.getFigures().put("B", new RavensFigure("B", problemName, getName()));
        newProblem.getFigures().put("C", new RavensFigure("C", problemName, getName()));
        newProblem.getFigures().put("1", new RavensFigure("1", problemName, getName()));
        newProblem.getFigures().put("2", new RavensFigure("2", problemName, getName()));
        newProblem.getFigures().put("3", new RavensFigure("3", problemName, getName()));
        newProblem.getFigures().put("4", new RavensFigure("4", problemName, getName()));
        newProblem.getFigures().put("5", new RavensFigure("5", problemName, getName()));
        newProblem.getFigures().put("6", new RavensFigure("6", problemName, getName()));

        //3x3 problems have more images to load than 2x2
        if(problemType.equals("3x3")) {
            newProblem.getFigures().put("D", new RavensFigure("D", problemName, getName()));
            newProblem.getFigures().put("E", new RavensFigure("E", problemName, getName()));
            newProblem.getFigures().put("F", new RavensFigure("F", problemName, getName()));
            newProblem.getFigures().put("G", new RavensFigure("G", problemName, getName()));
            newProblem.getFigures().put("H", new RavensFigure("H", problemName, getName()));
            newProblem.getFigures().put("7", new RavensFigure("7", problemName, getName()));
            newProblem.getFigures().put("8", new RavensFigure("8", problemName, getName()));
        }
        problems.add(newProblem);
    }
    
    /**
     * Returns the total number of problems answered in this set
     */
    public int getTotal(String result) {
        int count = 0;
        for(RavensProblem problem : problems) {
            if(problem.getCorrect().equals(result)) {
                count++;
            }
        }
        return count;
    }
}
