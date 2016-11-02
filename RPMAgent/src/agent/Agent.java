package agent;

import java.util.ArrayList;
import java.io.File;
import javax.imageio.ImageIO;


/**
 * The Agent for solving Raven's Progressive Matrices.
 */
public class Agent {

    public Agent() {   
    }
    
    
    /**
     * The driver method for solving problems. Passed in one by one from the main method.
     * @param problem the RavensProblem the agent should solve
     * @return the Agent's answer to this problem
     */
    public int Solve(RavensProblem problem) {
		int answerChoice = -2;
		ArrayList<ImageInfo> questionImages = new ArrayList<ImageInfo>();
    	ArrayList<ImageInfo> answerImages = new ArrayList<ImageInfo>();
    	
    	//2x2 and 3x3 problems are read in differently since they have a different number of associated images making up the whole problem.
    	if (problem.getProblemType().equals("2x2")){
    		try{
    			questionImages.add(new ImageInfo("A", ImageIO.read(new File(problem.getFigures().get("A").getVisual()))));
    			questionImages.add(new ImageInfo("B", ImageIO.read(new File(problem.getFigures().get("B").getVisual()))));
    			questionImages.add(new ImageInfo("C", ImageIO.read(new File(problem.getFigures().get("C").getVisual()))));
    			answerImages.add(new ImageInfo("1", ImageIO.read(new File(problem.getFigures().get("1").getVisual()))));
    			answerImages.add(new ImageInfo("2", ImageIO.read(new File(problem.getFigures().get("2").getVisual()))));
    			answerImages.add(new ImageInfo("3", ImageIO.read(new File(problem.getFigures().get("3").getVisual()))));
    			answerImages.add(new ImageInfo("4", ImageIO.read(new File(problem.getFigures().get("4").getVisual()))));
    			answerImages.add(new ImageInfo("5", ImageIO.read(new File(problem.getFigures().get("5").getVisual()))));
    			answerImages.add(new ImageInfo("6", ImageIO.read(new File(problem.getFigures().get("6").getVisual()))));
    		}
    		catch(Exception e){
    			System.out.println("Something has gone wrong with reading in images! ");
    			e.printStackTrace();
    			return -1;
    		}
    	}
    	
    	else if (problem.getProblemType().equals("3x3")){
    		try{
    			questionImages.add(new ImageInfo("A", ImageIO.read(new File(problem.getFigures().get("A").getVisual()))));
    			questionImages.add(new ImageInfo("B", ImageIO.read(new File(problem.getFigures().get("B").getVisual()))));
    			questionImages.add(new ImageInfo("C", ImageIO.read(new File(problem.getFigures().get("C").getVisual()))));
    			questionImages.add(new ImageInfo("D", ImageIO.read(new File(problem.getFigures().get("D").getVisual()))));
    			questionImages.add(new ImageInfo("E", ImageIO.read(new File(problem.getFigures().get("E").getVisual()))));
    			questionImages.add(new ImageInfo("F", ImageIO.read(new File(problem.getFigures().get("F").getVisual()))));
    			questionImages.add(new ImageInfo("G", ImageIO.read(new File(problem.getFigures().get("G").getVisual()))));
    			questionImages.add(new ImageInfo("H", ImageIO.read(new File(problem.getFigures().get("H").getVisual()))));
    			answerImages.add(new ImageInfo("1", ImageIO.read(new File(problem.getFigures().get("1").getVisual()))));
    			answerImages.add(new ImageInfo("2", ImageIO.read(new File(problem.getFigures().get("2").getVisual()))));
    			answerImages.add(new ImageInfo("3", ImageIO.read(new File(problem.getFigures().get("3").getVisual()))));
    			answerImages.add(new ImageInfo("4", ImageIO.read(new File(problem.getFigures().get("4").getVisual()))));
    			answerImages.add(new ImageInfo("5", ImageIO.read(new File(problem.getFigures().get("5").getVisual()))));
    			answerImages.add(new ImageInfo("6", ImageIO.read(new File(problem.getFigures().get("6").getVisual()))));  
    			answerImages.add(new ImageInfo("7", ImageIO.read(new File(problem.getFigures().get("7").getVisual())))); 
    			answerImages.add(new ImageInfo("8", ImageIO.read(new File(problem.getFigures().get("8").getVisual()))));
    		
    			//begin testing relationships between images in the problem to determine what solving method should be used
    			if (FindProblemType.testForEquality3x3(questionImages) == true){
    				answerChoice = SolveProblem.solveisEqual(questionImages, answerImages);
    			}
    			else if (FindProblemType.testIsOneWayVerticalGain(questionImages) == true){
    				answerChoice = SolveProblem.solveIsOneWayVerticalGainOrLoss(questionImages, answerImages);
    			}
    			else if (FindProblemType.testIsOneWayVerticalLoss(questionImages) == true){
    				answerChoice = SolveProblem.solveIsOneWayVerticalGainOrLoss(questionImages, answerImages);
    			}
    			else if (FindProblemType.testIsOneWayHorizontalGain(questionImages) == true){
    				answerChoice = SolveProblem.solveIsOneWayHorizontalGainOrLoss(questionImages, answerImages);
    			}
    			else if (FindProblemType.testIsOneWayHorizontalLoss(questionImages) == true){
    				answerChoice = SolveProblem.solveIsOneWayHorizontalGainOrLoss(questionImages, answerImages);
    			}
    			else if (FindProblemType.testIsTwoWayGain(questionImages) == true){
    				answerChoice = SolveProblem.solveIsTwoWayGain(questionImages, answerImages);
    			}
    			else if (FindProblemType.testIsTwoWayLoss(questionImages) == true){
    				answerChoice = SolveProblem.solveIsTwoWayLoss(questionImages, answerImages);
    			}
    			else if (FindProblemType.testIsRadial(questionImages) == true){
    				answerChoice = SolveProblem.solveIsRadial(questionImages, answerImages);
    			}
    			else if (FindProblemType.testIsVGainHLoss(questionImages) == true){
    				answerChoice = SolveProblem.solveIsGainLoss(questionImages, answerImages);
    			}
    			else if (FindProblemType.testIsVLossHGain(questionImages) == true){
    				answerChoice = SolveProblem.solveIsGainLoss(questionImages, answerImages);
    			}
    			else if (FindProblemType.testIsRightRotation(questionImages)== true){
    				answerChoice = SolveProblem.solveIsRightRotation(questionImages, answerImages);
    			}
    			else if (FindProblemType.testIsLeftRotation(questionImages) == true){
    				answerChoice = SolveProblem.solveIsLeftRotation(questionImages, answerImages);
    			}
    			else if (FindProblemType.testIsAnd(questionImages) == true){
    				answerChoice = SolveProblem.solveisAnd(questionImages, answerImages);
    			}
    			else if (FindProblemType.testIsCenterAdditive(questionImages) == true){
    				answerChoice = SolveProblem.solveIsCenterAdditive(questionImages, answerImages);
    			}
    			else if (FindProblemType.testIsEndAdditive(questionImages) == true){
    				answerChoice = SolveProblem.solveIsEndAdditive(questionImages, answerImages);
    			}
    			else if (FindProblemType.testIsSubtractive(questionImages) == true){
    				answerChoice = SolveProblem.solveIsSubtractive(questionImages, answerImages);
    			}
    			else if (FindProblemType.testIsHorizontalSame(questionImages) == true){
    				answerChoice = SolveProblem.solveIsHorizontalSame(questionImages, answerImages);
    			}
    			else if (FindProblemType.testIsVerticalSame(questionImages) == true){
    				answerChoice = SolveProblem.solveIsVerticalSame(questionImages, answerImages);
    			}
    			else if (FindProblemType.testIsPartialHorizontalMatch(questionImages) == true){
    				answerChoice = SolveProblem.solveIsPartialHorizontalMatch(questionImages, answerImages);
    			}
    			else if (FindProblemType.testIsPartialVerticalMatch(questionImages) == true){
    				answerChoice = SolveProblem.solveIsPartialVerticalMatch(questionImages, answerImages);
    			}
    			else if(FindProblemType.testIsPartialRightRotation(questionImages) == true){
    				answerChoice = SolveProblem.solveIsPartialRightRotation(questionImages, answerImages);
    			}
    			else if (FindProblemType.testIsPartialLeftRotation(questionImages) == true){
    				answerChoice = SolveProblem.solveIsPartialLeftRotation(questionImages, answerImages);
    			}
    			else if (FindProblemType.testIsXOR(questionImages) == true){
    				answerChoice = SolveProblem.solveIsXOR(questionImages, answerImages);
    			}
    			else {
    				answerChoice = -1;
    			}
    		}
    		catch(Exception e){
    			System.out.println("Something has gone wrong with reading in images! ");
    			e.printStackTrace();
    			return -1;
    		}
    	}
    	answerChoice +=1;
        return answerChoice;
    }

}

