package agent;

import java.util.HashMap;

/**
 * A single Raven's Progressive Matrix problem, represented by a type (2x2 or
 * or 3x3), a String name, and a HashMap of figures. Information is also given
 * regarding whether visual or verbal representations are provided for the
 * problem.
 * 
 */
public class RavensProblem {
    private String name;
    private String problemType; //2x2 or 3x3
    
    private int correctAnswer;
    private int givenAnswer;
    private boolean answerReceived;
    private HashMap<String, RavensFigure> figures;
    private HashMap<String, Boolean> relationships;
    
    /**
     * Initializes a new Raven's Progressive Matrix problem given a name, a
     * type, and a correct answer to the problem. Also initializes a blank
     * HashMap representing the figures in the problem.
     * 
     * @param name the name of the problem, typically a number
     * @param problemType the type of problem: 2x2 or 3x3
     * @param correctAnswer the correct answer to the problem, a number 1-6 (2x2) or 1-8 (3x3)
     */
    public RavensProblem(String name, String problemType, int correctAnswer) {
        this.name = name;
        this.problemType = problemType;
        this.correctAnswer = correctAnswer;
        
        figures = new HashMap<>();
        givenAnswer = -1;
        answerReceived = false;
        InitializeRelationships();
    }
    
    /*
     * helper method to initialize all possible row and column relationships shapes can take
     */
    private void InitializeRelationships() {
    	relationships.put("horizontal_equal", false);
    	relationships.put("vertical_equal", false);
    	relationships.put("horizontal_rotation", false);
    	relationships.put("vertical_rotation", false);
    	relationships.put("horizontal_growth", false);
    	relationships.put("vertical_growth", false);
    	relationships.put("horizontal_loss", false);
    	relationships.put("vertical_loss", false);
    }
    
    public void SetRelationshipAsTrue(String key) {
    	if (relationships.containsKey(key)) {
    		relationships.put(key, true);
    	}
    }
    
    
    /**
     * Returns the correct answer to the problem.
     * 
     * In order to receive the correct answer to the problem, your Agent must
     * supply a guess (givenAnswer). Once it has supplied its guess, it will NOT
     * be able to change its answer to the problem; the answer passed as
     * givenAnswer will be stored as the answer to this problem.
     * 
     * This method is provided to enable your Agent to participate in meta-
     * reasoning by reflecting on its past incorrect answers. Using this method
     * is completely optional.
     * 
     * @param givenAnswer your Agent's answer to the problem
     * @return the correct answer to the problem
     */
    public int checkAnswer(int givenAnswer) {
        setAnswerReceived(givenAnswer);
        return correctAnswer;
    }
    
    
    /**
     * Sets your Agent's answer to this problem. This method can only be used
     * once; the first answer that is received will be stored. This method is
     * called by either checkAnswer or by the main() method.
     * 
     * @param givenAnswer your Agent's answer to the problem
     */
    public void setAnswerReceived(int givenAnswer) {
        if(!answerReceived) {
            answerReceived=true;
            this.givenAnswer=givenAnswer;
        }
    }
    
    
    /**
     * Returns your Agent's answer to this problem.
     * @return your Agent's answer
     */
    public int getGivenAnswer() {
        return givenAnswer;
    }
    
    
    /**
     * Returns whether your Agent's answer is the correct answer. Your agent
     * does not need to use this method; it is used to identify whether each
     * problem is correct in main().
     * 
     * @return "Correct" if the agent's answer is correct, "Skipped" if the
     * agent did not answer the problem, and "Incorrect" if the agent answered
     * the problem incorrectly.
     */
    public String getCorrect() {
        if(givenAnswer == correctAnswer) {
            return "Correct";
        } else if(givenAnswer < 0) {
            return "Skipped";
        } else {
            return "Incorrect";
        }
    }

    
    /**
     * Returns the type of problem: 2x2 or 3x3. 2x2 problems have 6 answer
     * choices; 3x3 problems have 8 answer choices.
     * 
     * @return a String of the problem type: "2x2" or "3x3".
     */
    public String getProblemType() {
        return problemType;
    }
    
    
    /**
     * Returns the HashMap representing the RavensFigures of the problem. The
     * key for each figure is the name of the figure. For example:
     * 
     * getFigures().get("A") would return the first frame in a problem.
     * getFigures().get("3") would return the third answer choice in a problem.
     * getFigures().get("G") would return the third row, first column of a 3x3
     * problem.
     * 
     * The value for each key is the RavensFigure corresponding to that figure
     * of the problem.
     * 
     * @return a HashMap of the RavensFigures in this problem
     */
    public HashMap<String, RavensFigure> getFigures() {
        return figures;
    }


    
    public String getName() {
        return name;
    }
    
}
