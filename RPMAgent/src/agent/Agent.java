package agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;


/**
 * The Agent for solving Raven's Progressive Matrices.
 */
public class Agent {
    /**
     * default constructor 
     */
    public Agent() {
        
    }
    /**
     * @param problem the RavensProblem the agent should solve
     * @return the Agent's answer to this problem
     */
    public int Solve(RavensProblem problem) {
		int answerChoice = -1;
		ArrayList<ImageInfo> questionImages = new ArrayList<ImageInfo>();
    	ArrayList<ImageInfo> answerImages = new ArrayList<ImageInfo>();
    	
    	System.out.println("looking at problem " + problem.getName());
    	
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
    		
    			
    			if (testForEquality3x3(questionImages) == true){
    				answerChoice = solveisEqual(questionImages, answerImages);
    			}
    			else if (testIsOneWayVerticalGain(questionImages) == true){
    				answerChoice = solveIsOneWayVerticalGainOrLoss(questionImages, answerImages);
    			}
    			else if (testIsOneWayVerticalLoss(questionImages) == true){
    				answerChoice = solveIsOneWayVerticalGainOrLoss(questionImages, answerImages);
    			}
    			else if (testIsOneWayHorizontalGain(questionImages) == true){
    				answerChoice = solveIsOneWayHorizontalGainOrLoss(questionImages, answerImages);
    			}
    			else if (testIsOneWayHorizontalLoss(questionImages) == true){
    				answerChoice = solveIsOneWayHorizontalGainOrLoss(questionImages, answerImages);
    			}
    			else if (testIsTwoWayGain(questionImages) == true){
    				answerChoice = solveIsTwoWayGain(questionImages, answerImages);
    			}
    			else if (testIsTwoWayLoss(questionImages) == true){
    				answerChoice = solveIsTwoWayLoss(questionImages, answerImages);
    			}
    			else if (testIsRadial(questionImages) == true){
    				answerChoice = solveIsRadial(questionImages, answerImages);
    			}
    			else if (testIsVGainHLoss(questionImages) == true){
    				answerChoice = solveIsGainLoss(questionImages, answerImages);
    			}
    			else if (testIsVLossHGain(questionImages) == true){
    				answerChoice = solveIsGainLoss(questionImages, answerImages);
    			}
    			else if (testIsRightRotation(questionImages)== true){
    				answerChoice = solveIsRightRotation(questionImages, answerImages);
    			}
    			else if (testIsLeftRotation(questionImages) == true){
    				answerChoice = solveIsLeftRotation(questionImages, answerImages);
    			}
    			else if (testIsAnd(questionImages) == true){
    				answerChoice = solveisAnd(questionImages, answerImages);
    			}
    			else if (testIsCenterAdditive(questionImages) == true){
    				answerChoice = solveIsCenterAdditive(questionImages, answerImages);
    			}
    			else if (testIsEndAdditive(questionImages) == true){
    				answerChoice = solveIsEndAdditive(questionImages, answerImages);
    			}
    			else if (testIsSubtractive(questionImages) == true){
    				answerChoice = solveIsSubtractive(questionImages, answerImages);
    			}
    			else if (testIsHorizontalSame(questionImages) == true){
    				answerChoice = solveIsHorizontalSame(questionImages, answerImages);
    			}
    			else if (testIsVerticalSame(questionImages) == true){
    				answerChoice = solveIsVerticalSame(questionImages, answerImages);
    			}
    			else if (testIsPartialHorizontalMatch(questionImages) == true){
    				answerChoice = solveIsPartialHorizontalMatch(questionImages, answerImages);
    			}
    			else if (testIsPartialVerticalMatch(questionImages) == true){
    				answerChoice = solveIsPartialVerticalMatch(questionImages, answerImages);
    			}
    			else if(testIsPartialRightRotation(questionImages) == true){
    				answerChoice = solveIsPartialRightRotation(questionImages, answerImages);
    			}
    			else if (testIsPartialLeftRotation(questionImages) == true){
    				answerChoice = solveIsPartialLeftRotation(questionImages, answerImages);
    			}
    			else if (testIsXOR(questionImages) == true){
    				answerChoice = solveIsXOR(questionImages, answerImages);
    			}
    			else{
    			}

    		}
    		catch(Exception e){
    			System.out.println("Something has gone wrong with reading in images! ");
    			e.printStackTrace();
    			return -1;
    		}
    	}
    	else{
    		//this should never be reached
    		return -1;
    	}
    	answerChoice +=1;
        return answerChoice;
    }
    
    //layer two images on top of one another and determine how many of their pixels overlap, how many pixels are unique to each image, and how many are in the new image total. 
    public ArrayList<Double> overlayImages(ImageInfo imageinfo1, ImageInfo imageinfo2){
    	ArrayList<Double> comparisonInfo = new ArrayList<Double>(4);
    	BufferedImage image1 = imageinfo1.getImage();
    	BufferedImage image2 = imageinfo2.getImage();
    	if (!(image1.getHeight() == image2.getHeight() && image1.getWidth() == image2.getWidth())){
    		//make sure the two images are comparable (they should be - mostly just a sanity check)
    		return comparisonInfo;
    	}
    	double uniquePixels1 = 0;
    	double uniquePixels2 = 0;
    	double pixelsInCommon = 0;
    	double totalNewImagePixels = 0;

		for (int i = 0; i < image1.getHeight(); i++){
			for (int j = 0; j < image1.getWidth(); j++){
				if (image1.getRGB(i,j) == -16777216 && image2.getRGB(i, j) == -16777216){
					pixelsInCommon +=1;
				}
				else if (image1.getRGB(i,j) == -16777216){
					uniquePixels1 += 1;
				}
				else if (image2.getRGB(i,j) == -16777216){
					uniquePixels2 += 1;
				}
			}
		}
		
		comparisonInfo.add(uniquePixels1);
		comparisonInfo.add(uniquePixels2);
		comparisonInfo.add(pixelsInCommon);
		totalNewImagePixels = uniquePixels1 + uniquePixels2 + pixelsInCommon;
		comparisonInfo.add(totalNewImagePixels);
		
		//comment out if you don't need print statements to debug
		//System.out.println("\nimage1 has " + imageinfo1.getNumBlackPixels() + " total black pixels and image 2 has " + imageinfo2.getNumBlackPixels() + " total black pixels");
		//System.out.println("Unique pixels in 1: " + uniquePixels1 + " Unique pixels in 2:" + uniquePixels2);
		//System.out.println("pixelsInCommon: " + pixelsInCommon);
		
		return comparisonInfo;
    }
       
    //find the result of performing the XOR operation on two images. The pixels left will be the pixels that appear in one, but not both. 
    public ArrayList<Double> XORImages(ImageInfo imageinfo1, ImageInfo imageinfo2){
    	ArrayList<Double> comparisonInfo = new ArrayList<Double>(4);
    	BufferedImage image1 = imageinfo1.getImage();
    	BufferedImage image2 = imageinfo2.getImage();
    	if (!(image1.getHeight() == image2.getHeight() && image1.getWidth() == image2.getWidth())){
    		//make sure the two images are comparable (they should be - mostly just a sanity check)
    		return comparisonInfo;
    	}
    	double uniquePixels1 = 0;
    	double uniquePixels2 = 0;
    	double pixelsInCommon = 0;
    	double totalNewImagePixels = 0;

		for (int i = 0; i < image1.getHeight(); i++){
			for (int j = 0; j < image1.getWidth(); j++){
				if (image1.getRGB(i,j) == -16777216 && image2.getRGB(i, j) == -16777216){
					pixelsInCommon +=1;
				}
				else if (image1.getRGB(i,j) == -16777216){
					uniquePixels1 += 1;
				}
				else if (image2.getRGB(i,j) == -16777216){
					uniquePixels2 += 1;
				}
			}
		}
		
		comparisonInfo.add(uniquePixels1);
		comparisonInfo.add(uniquePixels2);
		comparisonInfo.add(pixelsInCommon);
		totalNewImagePixels = uniquePixels1 + uniquePixels2 + pixelsInCommon;
		comparisonInfo.add(totalNewImagePixels);		
		return comparisonInfo;
    }
    
    //used to test if a 2x2 relationship is equal 
    public boolean testForEquality2x2(ArrayList<ImageInfo> questionImages){
    	boolean isEqual = false;
    	double errorTolerance = 100;
    	System.out.println("differences: ");
    	System.out.println("    horiontal: " + (questionImages.get(0).getNumBlackPixels() - questionImages.get(1).getNumBlackPixels()));
    	System.out.println("    vertical: " + (questionImages.get(0).getNumBlackPixels() - questionImages.get(2).getNumBlackPixels()));
    	if (Math.abs(questionImages.get(0).getNumBlackPixels() - questionImages.get(1).getNumBlackPixels()) < errorTolerance 
    			&& Math.abs(questionImages.get(0).getNumBlackPixels() - questionImages.get(2).getNumBlackPixels()) < errorTolerance){
    		isEqual = true;
    	}
    	
    	if (isEqual == true ){
    		ArrayList<Double> overlaidImages = overlayImages(questionImages.get(0), questionImages.get(1));
			if (overlaidImages.get(0) > errorTolerance || overlaidImages.get(1) > errorTolerance){
				isEqual = false;
			}
			overlaidImages = overlayImages(questionImages.get(0), questionImages.get(2));
			if (overlaidImages.get(0) > errorTolerance || overlaidImages.get(1) > errorTolerance){
				isEqual = false;				
			}
    	}
    	
    	return isEqual;
    }
    
    //used to test if a 3x3 relationship is equal
    public boolean testForEquality3x3(ArrayList<ImageInfo> questionImages){
    	boolean isEqual = false;
    	double errorTolerance = 100;
    	
    	//check to make sure there are an equal number of pixels in all shapes
    	if (Math.abs(questionImages.get(0).getNumBlackPixels() - questionImages.get(1).getNumBlackPixels()) < errorTolerance && Math.abs(questionImages.get(1).getNumBlackPixels() - questionImages.get(2).getNumBlackPixels()) < errorTolerance
    			&& Math.abs(questionImages.get(3).getNumBlackPixels() - questionImages.get(4).getNumBlackPixels()) < errorTolerance && Math.abs(questionImages.get(4).getNumBlackPixels() - questionImages.get(5).getNumBlackPixels()) < errorTolerance
    			&& Math.abs(questionImages.get(0).getNumBlackPixels() - questionImages.get(3).getNumBlackPixels()) < errorTolerance && Math.abs(questionImages.get(3).getNumBlackPixels() - questionImages.get(6).getNumBlackPixels()) < errorTolerance
    			&& Math.abs(questionImages.get(1).getNumBlackPixels() - questionImages.get(4).getNumBlackPixels()) < errorTolerance && Math.abs(questionImages.get(4).getNumBlackPixels() - questionImages.get(7).getNumBlackPixels()) < errorTolerance){
    		isEqual = true;
    	}
    	
    	//check to make sure this is a true equality problem, and not a rotation or reflection, which would also allow the above to be true
    	if (isEqual == true){
    		
    		//horizontal relationship row 1
    		ArrayList<Double> overlaidImages = overlayImages(questionImages.get(0), questionImages.get(1));
    		if (overlaidImages.get(0) > errorTolerance || overlaidImages.get(1) > errorTolerance){
				isEqual = false;
			}
    		overlaidImages = overlayImages(questionImages.get(1), questionImages.get(2));
    		if (overlaidImages.get(0) > errorTolerance || overlaidImages.get(1) > errorTolerance){
				isEqual = false;
			}
    		
    		//horizontal relationship row 2
    		overlaidImages = overlayImages(questionImages.get(3), questionImages.get(4));
    		if (overlaidImages.get(0) > errorTolerance || overlaidImages.get(1) > errorTolerance){
				isEqual = false;
			}
    		overlaidImages = overlayImages(questionImages.get(4), questionImages.get(5));
    		if (overlaidImages.get(0) > errorTolerance || overlaidImages.get(1) > errorTolerance){
				isEqual = false;
			}
    		
    		//vertical relationship row 1
    		overlaidImages = overlayImages(questionImages.get(0), questionImages.get(3));
    		if (overlaidImages.get(0) > errorTolerance || overlaidImages.get(1) > errorTolerance){
				isEqual = false;
			}
    		overlaidImages = overlayImages(questionImages.get(3), questionImages.get(6));
    		if (overlaidImages.get(0) > errorTolerance || overlaidImages.get(1) > errorTolerance){
				isEqual = false;
			}
    		
    		//vertical relationship row 2
    		overlaidImages = overlayImages(questionImages.get(1), questionImages.get(4));
    		if (overlaidImages.get(0) > errorTolerance || overlaidImages.get(1) > errorTolerance){
				isEqual = false;
			}
    		overlaidImages = overlayImages(questionImages.get(4), questionImages.get(7));
    		if (overlaidImages.get(0) > errorTolerance || overlaidImages.get(1) > errorTolerance){
				isEqual = false;
			}
    	}
    	return isEqual;
    }
    
    //use this to solve an RPM that has been classified as equal
    public int solveisEqual(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	ArrayList<Integer> possibleAnswers = new ArrayList<Integer>();
    	double errorTolerance = 10;
    	
    	for (int i = 0; i < answerImages.size(); i++){
    		double pixInCommon = (overlayImages(questionImages.get(0), answerImages.get(i))).get(2);
    		if (Math.abs(pixInCommon - questionImages.get(0).getNumBlackPixels()) < errorTolerance){
    			possibleAnswers.add(i);
    		}
    	}
    	if (possibleAnswers.size() == 1){
    		return possibleAnswers.get(0);
    	}
    	else{
    		return -2;
    	}
    }
    
    public boolean testIsOneWayVerticalGain(ArrayList<ImageInfo> questionImages){
    	boolean isTrue = false;
    	double errorTolerance = 100;
    	
    	//test that all horizontal relationships are equal
    	if(Math.abs(questionImages.get(0).getNumBlackPixels() - questionImages.get(1).getNumBlackPixels()) < errorTolerance && Math.abs(questionImages.get(1).getNumBlackPixels() - questionImages.get(2).getNumBlackPixels()) < errorTolerance
    			&& Math.abs(questionImages.get(3).getNumBlackPixels() - questionImages.get(4).getNumBlackPixels()) < errorTolerance && Math.abs(questionImages.get(4).getNumBlackPixels() - questionImages.get(5).getNumBlackPixels()) < errorTolerance) {
    		//test that all vertical relationships grow
    		if( questionImages.get(0).getNumBlackPixels() < questionImages.get(3).getNumBlackPixels() && questionImages.get(3).getNumBlackPixels() < questionImages.get(6).getNumBlackPixels() 
    				&& questionImages.get(1).getNumBlackPixels() < questionImages.get(4).getNumBlackPixels() && questionImages.get(4).getNumBlackPixels() < questionImages.get(7).getNumBlackPixels()){
    			isTrue = true;
    		}
    	}
    	
    	return isTrue;
    }
    
    public int solveIsOneWayVerticalGainOrLoss(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	ArrayList<Integer> answerChoices = new ArrayList<Integer>();
    	double errorTolerance = 50;
    	
    	for (int i = 0; i < answerImages.size(); i++){
    		if (Math.abs(answerImages.get(i).getNumBlackPixels() - questionImages.get(6).getNumBlackPixels()) < errorTolerance 
    				&& Math.abs(answerImages.get(i).getNumBlackPixels() - questionImages.get(7).getNumBlackPixels()) < errorTolerance ){
    			answerChoices.add(i);
    		}
    	}
    	
    	if (answerChoices.size() == 1){
    		return answerChoices.get(0);
    	}
    	else{
    		return -2;
    	}
    }
    
    public boolean testIsOneWayVerticalLoss(ArrayList<ImageInfo> questionImages){
    	boolean isTrue = false;
    	double errorTolerance = 100;
    	
    	//test that all horizontal relationships are equal
    	if(Math.abs(questionImages.get(0).getNumBlackPixels() - questionImages.get(1).getNumBlackPixels()) < errorTolerance && Math.abs(questionImages.get(1).getNumBlackPixels() - questionImages.get(2).getNumBlackPixels()) < errorTolerance
    			&& Math.abs(questionImages.get(3).getNumBlackPixels() - questionImages.get(4).getNumBlackPixels()) < errorTolerance && Math.abs(questionImages.get(4).getNumBlackPixels() - questionImages.get(5).getNumBlackPixels()) < errorTolerance) {
    		//test that all vertical relationships shrink
    		if( questionImages.get(0).getNumBlackPixels() > questionImages.get(3).getNumBlackPixels() && questionImages.get(3).getNumBlackPixels() > questionImages.get(6).getNumBlackPixels() 
    				&& questionImages.get(1).getNumBlackPixels() > questionImages.get(4).getNumBlackPixels() && questionImages.get(4).getNumBlackPixels() > questionImages.get(7).getNumBlackPixels()){
    			isTrue = true;
    		}
    	}
    	
    	return isTrue;
    }
    
    public boolean testIsOneWayHorizontalGain(ArrayList<ImageInfo> questionImages){
    	boolean isTrue = false;
    	double errorTolerance = 100;
    	
    	//test that all vertical relationships are equal
    	if(Math.abs(questionImages.get(0).getNumBlackPixels() - questionImages.get(3).getNumBlackPixels()) < errorTolerance && Math.abs(questionImages.get(3).getNumBlackPixels() - questionImages.get(6).getNumBlackPixels()) < errorTolerance
    			&& Math.abs(questionImages.get(1).getNumBlackPixels() - questionImages.get(4).getNumBlackPixels()) < errorTolerance && Math.abs(questionImages.get(4).getNumBlackPixels() - questionImages.get(7).getNumBlackPixels()) < errorTolerance) {
    		//test that all horizontal relationships grow
    		if( questionImages.get(0).getNumBlackPixels() < questionImages.get(1).getNumBlackPixels() && questionImages.get(1).getNumBlackPixels() < questionImages.get(2).getNumBlackPixels() 
    				&& questionImages.get(3).getNumBlackPixels() < questionImages.get(4).getNumBlackPixels() && questionImages.get(4).getNumBlackPixels() < questionImages.get(5).getNumBlackPixels()){
    			isTrue = true;
    		}
    	}
    	
    	return isTrue;
    }
    
    public boolean testIsOneWayHorizontalLoss(ArrayList<ImageInfo> questionImages){
    	boolean isTrue = false;
    	double errorTolerance = 100;
    	
    	//test that all vertical relationships are equal
    	if(Math.abs(questionImages.get(0).getNumBlackPixels() - questionImages.get(3).getNumBlackPixels()) < errorTolerance && Math.abs(questionImages.get(3).getNumBlackPixels() - questionImages.get(6).getNumBlackPixels()) < errorTolerance
    			&& Math.abs(questionImages.get(1).getNumBlackPixels() - questionImages.get(4).getNumBlackPixels()) < errorTolerance && Math.abs(questionImages.get(4).getNumBlackPixels() - questionImages.get(7).getNumBlackPixels()) < errorTolerance) {
    		//test that all horizontal relationships shrink
    		if( questionImages.get(0).getNumBlackPixels() > questionImages.get(1).getNumBlackPixels() && questionImages.get(1).getNumBlackPixels() > questionImages.get(2).getNumBlackPixels() 
    				&& questionImages.get(3).getNumBlackPixels() > questionImages.get(4).getNumBlackPixels() && questionImages.get(4).getNumBlackPixels() > questionImages.get(5).getNumBlackPixels()){
    			isTrue = true;
    		}
    	}
    	
    	return isTrue;
    }
    
    public int solveIsOneWayHorizontalGainOrLoss(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	ArrayList<Integer> answerChoices = new ArrayList<Integer>();
    	double errorTolerance = 50;
    	
    	for (int i = 0; i < answerImages.size(); i++){
    		if (Math.abs(answerImages.get(i).getNumBlackPixels() - questionImages.get(2).getNumBlackPixels()) < errorTolerance 
    				&& Math.abs(answerImages.get(i).getNumBlackPixels() - questionImages.get(5).getNumBlackPixels()) < errorTolerance ){
    			answerChoices.add(i);
    		}
    	}
    	
    	if (answerChoices.size() == 1){
    		return answerChoices.get(0);
    	}
    	else{
    		return -2;
    	}
    }
    
    public boolean testIsTwoWayGain(ArrayList<ImageInfo> questionImages){
    	boolean isTrue = false;
    	double errorTolerance = 100;
    	
    	//test that all vertical relationships grow
    	if( questionImages.get(0).getNumBlackPixels() < questionImages.get(1).getNumBlackPixels() && questionImages.get(1).getNumBlackPixels() < questionImages.get(2).getNumBlackPixels() 
				&& questionImages.get(3).getNumBlackPixels() < questionImages.get(4).getNumBlackPixels() && questionImages.get(4).getNumBlackPixels() < questionImages.get(5).getNumBlackPixels()){
    		
    		//test that all horizontal relationships grow
    		if( questionImages.get(0).getNumBlackPixels() < questionImages.get(3).getNumBlackPixels() && questionImages.get(3).getNumBlackPixels() < questionImages.get(6).getNumBlackPixels() 
    				&& questionImages.get(1).getNumBlackPixels() < questionImages.get(4).getNumBlackPixels() && questionImages.get(4).getNumBlackPixels() < questionImages.get(7).getNumBlackPixels()){
    			isTrue = true;
    		}
		}
    	
    	return isTrue;
    }
    
    public int solveIsTwoWayGain(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
       	ArrayList<Integer> answerChoices = new ArrayList<Integer>();

    	double dGrowthPercent = (questionImages.get(4).getRatioBlackPixels() - questionImages.get(1).getRatioBlackPixels()) * 2;
    	
    	for (int i = 0; i < answerImages.size(); i++){
			
    		if (answerImages.get(i).getNumBlackPixels() > questionImages.get(7).getNumBlackPixels() 
    				&& answerImages.get(i).getNumBlackPixels() > questionImages.get(5).getNumBlackPixels()){
    			answerChoices.add(i);
    		}
    	}
    	
    	if (answerChoices.size() > 1){
    		int bestChoice = -1;
    		double lowestGrowthPercent = 1.0;
    		
	    	for (int j = 0; j < answerChoices.size(); j++){
				double ansDGrowthPercent = answerImages.get(answerChoices.get(j)).getRatioBlackPixels() - questionImages.get(4).getRatioBlackPixels();
	    		
	    		if (Math.abs(ansDGrowthPercent- dGrowthPercent) < lowestGrowthPercent){
	    			bestChoice = answerChoices.get(j);
	    			lowestGrowthPercent = Math.abs(ansDGrowthPercent- dGrowthPercent);
	    		}
	    	}
	    	answerChoices.clear();
	    	answerChoices.add(bestChoice);
    	
    	}
    	
    	if (answerChoices.size() == 1){
    		return answerChoices.get(0);
    	}
    	else{
    		return -2;
    	}
    }
    
    public boolean testIsTwoWayLoss(ArrayList<ImageInfo> questionImages){
    	boolean isTrue = false;
    	double errorTolerance = 100;
    	
    	//test that all vertical relationships shrink
    	if( questionImages.get(0).getNumBlackPixels() > questionImages.get(1).getNumBlackPixels() && questionImages.get(1).getNumBlackPixels() > questionImages.get(2).getNumBlackPixels() 
				&& questionImages.get(3).getNumBlackPixels() > questionImages.get(4).getNumBlackPixels() && questionImages.get(4).getNumBlackPixels() > questionImages.get(5).getNumBlackPixels()){
    		
    		//test that all horizontal relationships grow
    		if( questionImages.get(0).getNumBlackPixels() > questionImages.get(3).getNumBlackPixels() && questionImages.get(3).getNumBlackPixels() > questionImages.get(6).getNumBlackPixels() 
    				&& questionImages.get(1).getNumBlackPixels() > questionImages.get(4).getNumBlackPixels() && questionImages.get(4).getNumBlackPixels() > questionImages.get(7).getNumBlackPixels()){
    			isTrue = true;
    		}
		}
    	
    	return isTrue;
    }
    
    public int solveIsTwoWayLoss(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
       	ArrayList<Integer> answerChoices = new ArrayList<Integer>();

    	double dGrowthPercent = (questionImages.get(4).getRatioBlackPixels() - questionImages.get(1).getRatioBlackPixels()) * 2;
    	
    	for (int i = 0; i < answerImages.size(); i++){
			
    		if (answerImages.get(i).getNumBlackPixels() < questionImages.get(7).getNumBlackPixels() 
    				&& answerImages.get(i).getNumBlackPixels() < questionImages.get(5).getNumBlackPixels()){
    			answerChoices.add(i);
    		}
    	}
    	
    	if (answerChoices.size() > 1){
    		int bestChoice = -1;
    		double lowestGrowthPercent = 1.0;
    		
	    	for (int j = 0; j < answerChoices.size(); j++){
				double ansDGrowthPercent = answerImages.get(answerChoices.get(j)).getRatioBlackPixels() - questionImages.get(4).getRatioBlackPixels();
	    		
	    		if (Math.abs(ansDGrowthPercent- dGrowthPercent) < lowestGrowthPercent){
	    			bestChoice = answerChoices.get(j);
	    			lowestGrowthPercent = Math.abs(ansDGrowthPercent- dGrowthPercent);
	    		}
	    	}
	    	answerChoices.clear();
	    	answerChoices.add(bestChoice);
    	
    	}
    	
    	if (answerChoices.size() == 1){
    		return answerChoices.get(0);
    	}
    	else{
    		return -2;
    	}
    }
    
    public boolean testIsRadial(ArrayList<ImageInfo> questionImages){
    	boolean isTrue = false;
    	double errorTolerance = 200;
    	
    	//test first vertical and first horizontal column for equal relationship
    	if (Math.abs(questionImages.get(0).getNumBlackPixels() - questionImages.get(1).getNumBlackPixels()) < errorTolerance && Math.abs(questionImages.get(1).getNumBlackPixels() - questionImages.get(2).getNumBlackPixels()) < errorTolerance
    			&& Math.abs(questionImages.get(0).getNumBlackPixels() - questionImages.get(3).getNumBlackPixels()) < errorTolerance && Math.abs(questionImages.get(3).getNumBlackPixels() - questionImages.get(6).getNumBlackPixels()) < errorTolerance){
    		
    		//check second vertical and horizontal columns to make sure the middle image is smaller than the two outer
    		if(questionImages.get(1).getNumBlackPixels() > questionImages.get(4).getNumBlackPixels() && questionImages.get(7).getNumBlackPixels() > questionImages.get(4).getNumBlackPixels()
    			&& questionImages.get(3).getNumBlackPixels() > questionImages.get(4).getNumBlackPixels() && questionImages.get(5).getNumBlackPixels() > questionImages.get(4).getNumBlackPixels()){
    			isTrue = true;
    		}
    	}
    	return isTrue;
    }
    
    public int solveIsRadial(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	ArrayList<Integer> answerChoices = new ArrayList<Integer>();
    	double errorTolerance = 50;
    	
    	for (int i = 0; i < answerImages.size(); i++){
    		if (Math.abs(answerImages.get(i).getNumBlackPixels() - questionImages.get(3).getNumBlackPixels()) < errorTolerance ){
    			answerChoices.add(i);
    		}
    	}
    	if (answerChoices.size() > 1){
    		// Flip the image horizontally (code taken from http://examples.javacodegeeks.com/desktop-java/awt/image/flipping-a-buffered-image)
    		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
    		tx.translate(-(questionImages.get(6).getImage()).getWidth(null), 0);
    		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    		BufferedImage reflectedImage = op.filter((questionImages.get(6).getImage()), null);
    		ImageInfo reflectedImageInfo = new ImageInfo("reflection", reflectedImage);
    		
    		int bestOption = -2;
    		double bestCommonScore = 0;
    		for (int j = 0; j < answerChoices.size(); j++){
    			double tempScore = overlayImages(reflectedImageInfo, answerImages.get(answerChoices.get(j))).get(2);
    			if ( tempScore > bestCommonScore){
    				bestCommonScore = tempScore;
    				bestOption = answerChoices.get(j);
    			}
    		}
    		
    		answerChoices.clear();
    		answerChoices.add(bestOption);
    	}
    	
    	if (answerChoices.size() == 1){
    		return answerChoices.get(0);
    	}
    	else{
    		return -2;
    	}
    }
    
    public boolean testIsVGainHLoss(ArrayList<ImageInfo> questionImages){
    	boolean isTrue = false;
    	
    	//test to see if the vertical relationship grows
    	if( questionImages.get(0).getNumBlackPixels() < questionImages.get(3).getNumBlackPixels() && questionImages.get(3).getNumBlackPixels() < questionImages.get(6).getNumBlackPixels() 
				&& questionImages.get(1).getNumBlackPixels() < questionImages.get(4).getNumBlackPixels() && questionImages.get(4).getNumBlackPixels() < questionImages.get(7).getNumBlackPixels()){
    		
    		//test to see if the horizontal relationship shrinks
    		if( questionImages.get(0).getNumBlackPixels() > questionImages.get(1).getNumBlackPixels() && questionImages.get(1).getNumBlackPixels() > questionImages.get(2).getNumBlackPixels() 
    				&& questionImages.get(3).getNumBlackPixels() > questionImages.get(4).getNumBlackPixels() && questionImages.get(4).getNumBlackPixels() > questionImages.get(5).getNumBlackPixels()){
        		
    			isTrue = true;
    			
        	}
    		
    	}    	
    	return isTrue;
    }
    
    public int solveIsGainLoss(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	ArrayList<Integer> answerChoices = new ArrayList<Integer>();
    	double errorTolerance = 100;
    	
    	for (int i = 0; i < answerImages.size(); i++){
    		if (Math.abs(answerImages.get(i).getNumBlackPixels() - questionImages.get(0).getNumBlackPixels()) < errorTolerance){
    			answerChoices.add(i);
    		}
    	}
    	
    	if (answerChoices.size() > 1){
    		double pixInCommon1 = (overlayImages(questionImages.get(2), questionImages.get(5)).get(2));
    		for (int j = 0; j < answerChoices.size(); j++){
    			double pixInCommon2 = (overlayImages(questionImages.get(5), answerImages.get(answerChoices.get(j)))).get(2);
    			if (Math.abs(pixInCommon1 - pixInCommon2) > 50){
    				answerChoices.remove(j);
    			}
    		}
    	}
    	
    	if (answerChoices.size() == 1){
    		return answerChoices.get(0);
    	}
    	else{
    		return -2;
    	}
    }
    
    public boolean testIsVLossHGain(ArrayList<ImageInfo> questionImages){
    	boolean isTrue = false;
    	
    	//test to see if the vertical relationship shrinks
    	if( questionImages.get(0).getNumBlackPixels() > questionImages.get(3).getNumBlackPixels() && questionImages.get(3).getNumBlackPixels() > questionImages.get(6).getNumBlackPixels() 
				&& questionImages.get(1).getNumBlackPixels() > questionImages.get(4).getNumBlackPixels() && questionImages.get(4).getNumBlackPixels() > questionImages.get(7).getNumBlackPixels()){
    		
    		//test to see if the horizontal relationship grows
    		if( questionImages.get(0).getNumBlackPixels() < questionImages.get(1).getNumBlackPixels() && questionImages.get(1).getNumBlackPixels() < questionImages.get(2).getNumBlackPixels() 
    				&& questionImages.get(3).getNumBlackPixels() < questionImages.get(4).getNumBlackPixels() && questionImages.get(4).getNumBlackPixels() < questionImages.get(5).getNumBlackPixels()){
        		
    			isTrue = true;
    			
        	}
    		
    	}    	
    	return isTrue;
    }
    
    public boolean testIsRightRotation(ArrayList<ImageInfo> questionImages){
    	boolean isTrue = false;
    	double errorTolerance = 200;
    	
    	if (Math.abs(questionImages.get(0).getNumBlackPixels() - questionImages.get(4).getNumBlackPixels()) < errorTolerance
    			&& Math.abs(questionImages.get(1).getNumBlackPixels() - questionImages.get(5).getNumBlackPixels()) < errorTolerance && Math.abs(questionImages.get(5).getNumBlackPixels() - questionImages.get(6).getNumBlackPixels()) < errorTolerance
    			&& Math.abs(questionImages.get(2).getNumBlackPixels() - questionImages.get(3).getNumBlackPixels()) < errorTolerance &&Math.abs(questionImages.get(3).getNumBlackPixels() - questionImages.get(7).getNumBlackPixels()) < errorTolerance){
    		
    		isTrue = true;
    	}
    	
    	return isTrue;
    }
    
    public int solveIsRightRotation(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	double lowestDifference = 500;
    	int bestAnswer = -2;
    	
    	for (int i = 0; i < answerImages.size(); i++){
    		ArrayList<Double> overlaidImages = overlayImages(questionImages.get(4), answerImages.get(i));
    		double totalNewPix = overlaidImages.get(0) + overlaidImages.get(1);
    		if (totalNewPix < lowestDifference){
    			lowestDifference = totalNewPix;
    			bestAnswer = i;
    		}
    	}
    	
    	return bestAnswer;
    }
    
    public boolean testIsLeftRotation(ArrayList<ImageInfo> questionImages){
    	boolean isTrue = false;
    	double errorTolerance = 100;

    	if (Math.abs(questionImages.get(1).getNumBlackPixels() - questionImages.get(3).getNumBlackPixels()) < errorTolerance
    			&& Math.abs(questionImages.get(0).getNumBlackPixels() - questionImages.get(5).getNumBlackPixels()) < errorTolerance && Math.abs(questionImages.get(5).getNumBlackPixels() - questionImages.get(7).getNumBlackPixels()) < errorTolerance
    			&& Math.abs(questionImages.get(2).getNumBlackPixels() - questionImages.get(4).getNumBlackPixels()) < errorTolerance &&Math.abs(questionImages.get(4).getNumBlackPixels() - questionImages.get(6).getNumBlackPixels()) < errorTolerance){
    		
    		isTrue = true;
    	}

    	return isTrue;
    }
    
    public int solveIsLeftRotation(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	double lowestDifference = 500;
    	int bestAnswer = -2;
    	
    	for (int i = 0; i < answerImages.size(); i++){
    		ArrayList<Double> overlaidImages = overlayImages(questionImages.get(3), answerImages.get(i));
    		double totalNewPix = overlaidImages.get(0) + overlaidImages.get(1);
    		if (totalNewPix < lowestDifference){
    			lowestDifference = totalNewPix;
    			bestAnswer = i;
    		}
    	}
    	
    	return bestAnswer;
    }
    
    public boolean testIsCenterAdditive(ArrayList<ImageInfo> questionImages){
    	boolean isTrue = true;
    	double errorTolerance = 100;
    	
    	//test each row and column to make sure the two outer frames when combined add up to the center frame
    	//row1
    	double combinedPixels = overlayImages(questionImages.get(0), questionImages.get(2)).get(3);
    	if (!(Math.abs(questionImages.get(1).getNumBlackPixels() - combinedPixels) < errorTolerance)){
    		isTrue = false;
    	}
    	//row2
    	combinedPixels = overlayImages(questionImages.get(3), questionImages.get(5)).get(3);
    	if (!(Math.abs(questionImages.get(4).getNumBlackPixels() - combinedPixels) < errorTolerance)){
    		isTrue = false;
    	}
    	
    	//column1
    	combinedPixels = overlayImages(questionImages.get(0), questionImages.get(6)).get(3);
    	if (!(Math.abs(questionImages.get(3).getNumBlackPixels() - combinedPixels) < errorTolerance)){
    		isTrue = false;
    	}
    	//column2
    	combinedPixels = overlayImages(questionImages.get(1), questionImages.get(7)).get(3);
    	if (!(Math.abs(questionImages.get(4).getNumBlackPixels() - combinedPixels) < errorTolerance)){
    		isTrue = false;
    	}

    	return isTrue;
    }
    
    public int solveIsCenterAdditive(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	int bestAnswer = -2;
    	double bestScore = 1000;
    	
    	for (int i = 0; i < answerImages.size(); i++){
    		double combinedPixels = overlayImages(questionImages.get(6), answerImages.get(i)).get(3);
    		if (Math.abs(questionImages.get(7).getNumBlackPixels() - combinedPixels) < bestScore){
    			bestAnswer = i;
    			bestScore = Math.abs(answerImages.get(i).getNumBlackPixels() - combinedPixels);
    		}

    	}
    	
    	return bestAnswer;
    }
    
    public boolean testIsEndAdditive(ArrayList<ImageInfo> questionImages){
    	boolean isTrue = true;
    	double errorTolerance = 100;
    	
    	//test each row and column to make sure the first two frames add up to the third
    	//row1
    	double combinedPixels = overlayImages(questionImages.get(0), questionImages.get(1)).get(3);
    	if (!(Math.abs(questionImages.get(2).getNumBlackPixels() - combinedPixels) < errorTolerance)){
    		isTrue = false;
    	}
    	//row2
    	combinedPixels = overlayImages(questionImages.get(3), questionImages.get(4)).get(3);
    	if (!(Math.abs(questionImages.get(5).getNumBlackPixels() - combinedPixels) < errorTolerance)){
    		isTrue = false;
    	}
    	
    	//column1
    	combinedPixels = overlayImages(questionImages.get(0), questionImages.get(3)).get(3);
    	if (!(Math.abs(questionImages.get(6).getNumBlackPixels() - combinedPixels) < errorTolerance)){
    		isTrue = false;
    	}
    	//column2
    	combinedPixels = overlayImages(questionImages.get(1), questionImages.get(4)).get(3);
    	if (!(Math.abs(questionImages.get(7).getNumBlackPixels() - combinedPixels) < errorTolerance)){
    		isTrue = false;
    	}

    	return isTrue;
    }
    
    public int solveIsEndAdditive(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	int bestAnswer = -2;
    	double bestScore = 1000;
    	
    	for (int i = 0; i < answerImages.size(); i++){
    		double combinedPixels = overlayImages(questionImages.get(6), questionImages.get(7)).get(3);
    		if (Math.abs(answerImages.get(i).getNumBlackPixels() - combinedPixels) < bestScore){
    			bestAnswer = i;
    			bestScore = Math.abs(answerImages.get(i).getNumBlackPixels() - combinedPixels);
    		}

    	}
    	
    	return bestAnswer;
    }
    
    public boolean testIsSubtractive(ArrayList<ImageInfo> questionImages){
    	boolean isTrue = true;
    	double errorTolerance = 200;
    	
    	//test each row and column to make sure the first frame minus the second frame equals the third frame
    	//row1
    	double differenceOfFirstTwo = Math.abs(questionImages.get(0).getNumBlackPixels() - questionImages.get(1).getNumBlackPixels());
    	if (Math.abs(questionImages.get(2).getNumBlackPixels() - differenceOfFirstTwo) > errorTolerance){
    		isTrue = false;
    	}
    	//row2
    	differenceOfFirstTwo = Math.abs(questionImages.get(3).getNumBlackPixels() - questionImages.get(4).getNumBlackPixels());
    	if (Math.abs(questionImages.get(5).getNumBlackPixels() - differenceOfFirstTwo) > errorTolerance){
    		isTrue = false;
    	}
    	
    	//col1
    	differenceOfFirstTwo = Math.abs(questionImages.get(0).getNumBlackPixels() - questionImages.get(3).getNumBlackPixels());
    	if (Math.abs(questionImages.get(6).getNumBlackPixels() - differenceOfFirstTwo) > errorTolerance){
    		isTrue = false;
    	}
    	//col2
    	differenceOfFirstTwo = Math.abs(questionImages.get(1).getNumBlackPixels() - questionImages.get(4).getNumBlackPixels());
    	if (Math.abs(questionImages.get(7).getNumBlackPixels() - differenceOfFirstTwo) > errorTolerance){
    		isTrue = false;
    	}

    	return isTrue;
    }
    
    public int solveIsSubtractive(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	int bestAnswer = -2;
    	double bestScore = 1000;
    	
    	for (int i = 0; i < answerImages.size(); i++){
    		double differenceOfFirstTwo = Math.abs(questionImages.get(6).getNumBlackPixels() - questionImages.get(7).getNumBlackPixels());
        	if (Math.abs(answerImages.get(i).getNumBlackPixels() - differenceOfFirstTwo) <= bestScore){
        		bestAnswer = i;
        		bestScore = Math.abs(answerImages.get(i).getNumBlackPixels() - differenceOfFirstTwo);
        	}
    	}
    	
    	return bestAnswer;
    }
    
    //does not work - needs improvement
    public boolean testIsXOR(ArrayList<ImageInfo> questionImages){
    	boolean isTrue = true;
    	double errorTolerance = 500;
    	
    	//for each row and column, overlay the first and second pics and eliminate the pixels that appear in both. this weight should equal that of the third picture.
    	//row1
    	ArrayList<Double> firstAndSecondOverlay = overlayImages(questionImages.get(0), questionImages.get(1));
    	double XORPixels = firstAndSecondOverlay.get(3) - firstAndSecondOverlay.get(2);
    	if (Math.abs(questionImages.get(2).getNumBlackPixels() - XORPixels) > errorTolerance){
    		isTrue = false;
    	}
    	//row2
    	firstAndSecondOverlay = overlayImages(questionImages.get(3), questionImages.get(4));
    	XORPixels = firstAndSecondOverlay.get(3) - firstAndSecondOverlay.get(2);
    	if (Math.abs(questionImages.get(5).getNumBlackPixels() - XORPixels) > errorTolerance){
    		isTrue = false;
    	}
    	//col1
    	firstAndSecondOverlay = overlayImages(questionImages.get(0), questionImages.get(3));
    	XORPixels = firstAndSecondOverlay.get(3) - firstAndSecondOverlay.get(2);
    	//System.out.println("the number of pixels in 0 is " + questionImages.get(0).getNumBlackPixels() + " the number of pixels in 3 is " + questionImages.get(3).getNumBlackPixels() + " the number of pixels in 6 is " + questionImages.get(6).getNumBlackPixels());
    	//System.out.println("the weight of 0 and 3 combined is " + firstAndSecondOverlay.get(3) + " and the number of pixels they have in common is " + firstAndSecondOverlay.get(2));
    	//System.out.println("the XOR image has " + XORPixels + " pixels in it");
    	if (Math.abs(questionImages.get(6).getNumBlackPixels() - XORPixels) > errorTolerance){
    		//System.out.println("Does not match on first column");
    		isTrue = false;
    	}
    	//col2
    	firstAndSecondOverlay = overlayImages(questionImages.get(1), questionImages.get(4));
    	XORPixels = firstAndSecondOverlay.get(3) - firstAndSecondOverlay.get(2);
    	if (Math.abs(questionImages.get(7).getNumBlackPixels() - XORPixels) > errorTolerance){
    		//System.out.println("Does not match on second column");
    		isTrue = false;
    	}
    	
    	//System.out.println("is this XOR? " + isTrue);
    	return isTrue;
    }
    
    public int solveIsXOR(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	int bestAnswer = -2;
    	double bestScore = 1000;
    	
    	for (int i = 0; i < answerImages.size(); i++){
        	ArrayList<Double> firstAndSecondOverlay = overlayImages(questionImages.get(6), questionImages.get(7));
        	double XORPixels = firstAndSecondOverlay.get(3) - firstAndSecondOverlay.get(2);
        	if (Math.abs(answerImages.get(i).getNumBlackPixels() - XORPixels) <  bestScore){
        		bestAnswer = i;
        		bestScore = Math.abs(answerImages.get(i).getNumBlackPixels() - XORPixels);
        	}
    	}
    	
    	return bestAnswer;
    }
    
    public boolean testIsAnd(ArrayList<ImageInfo> questionImages){
    	boolean isTrue = true;
    	double errorTolerance = 200;
    	
    	//overlay 1 and 2 and find the pixels they have in common. this should equal roughly the number of pixels in 3
    	//row1
    	double pixInCommon = (overlayImages(questionImages.get(0), questionImages.get(1))).get(2);
    	if (Math.abs(questionImages.get(2).getNumBlackPixels() - pixInCommon) > errorTolerance){
    		isTrue = false;
    	}
    	//row2
    	pixInCommon = (overlayImages(questionImages.get(3), questionImages.get(4))).get(2);
    	if (Math.abs(questionImages.get(5).getNumBlackPixels() - pixInCommon) > errorTolerance){
    		isTrue = false;
    	}
    	//col1
    	pixInCommon = (overlayImages(questionImages.get(0), questionImages.get(3))).get(2);
    	if (Math.abs(questionImages.get(6).getNumBlackPixels() - pixInCommon) > errorTolerance){
    		isTrue = false;
    	}
    	//col2
    	pixInCommon = (overlayImages(questionImages.get(1), questionImages.get(4))).get(2);
    	if (Math.abs(questionImages.get(7).getNumBlackPixels() - pixInCommon) > errorTolerance){
    		isTrue = false;
    	}

    	return isTrue;
    }
    
    public int solveisAnd(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	int bestAnswer = -2;
    	double bestSoFar = 500; 
    	for (int i = 0; i < answerImages.size(); i++){
	    	double pixInCommon = (overlayImages(questionImages.get(0), answerImages.get(i))).get(2);
	    	if (Math.abs(answerImages.get(i).getNumBlackPixels() - pixInCommon) < bestSoFar){
	    		bestSoFar = Math.abs(answerImages.get(i).getNumBlackPixels() - pixInCommon);
	    		bestAnswer = i;
	    	}
    	}
    	return bestAnswer;
    }
    
    public boolean testIsHorizontalSlideThrough(ArrayList<ImageInfo> questionImages){
    	boolean isTrue = false;
    	double errorTolerance = 200;
    	
    	if ((Math.abs(questionImages.get(0).getNumBlackPixels() - questionImages.get(2).getNumBlackPixels()) < errorTolerance && questionImages.get(0).getNumBlackPixels() >= questionImages.get(1).getNumBlackPixels())
    			&& (Math.abs(questionImages.get(3).getNumBlackPixels() - questionImages.get(5).getNumBlackPixels()) < errorTolerance && questionImages.get(3).getNumBlackPixels() >= questionImages.get(4).getNumBlackPixels())){
    		isTrue = true;
    	}
    	return isTrue;
    }
    
    public boolean testIsVerticalSlideThrough(ArrayList<ImageInfo> questionImages){
    	boolean isTrue = false;
    	double errorTolerance = 200;
    	
    	if ((Math.abs(questionImages.get(0).getNumBlackPixels() - questionImages.get(6).getNumBlackPixels()) < errorTolerance && questionImages.get(0).getNumBlackPixels() >= questionImages.get(3).getNumBlackPixels())
    			&& (Math.abs(questionImages.get(1).getNumBlackPixels() - questionImages.get(7).getNumBlackPixels()) < errorTolerance && questionImages.get(1).getNumBlackPixels() >= questionImages.get(4).getNumBlackPixels())){
    		isTrue = true;
    	}

    	return isTrue;
    }
    
    public boolean testIsHorizontalSame(ArrayList<ImageInfo> questionImages){
    	boolean isTrue = false;
    	double errorTolerance = 100;
    	
    	if (Math.abs(questionImages.get(0).getNumBlackPixels() - questionImages.get(1).getNumBlackPixels()) < errorTolerance && Math.abs(questionImages.get(1).getNumBlackPixels() - questionImages.get(2).getNumBlackPixels()) < errorTolerance
    			&& Math.abs(questionImages.get(3).getNumBlackPixels() - questionImages.get(4).getNumBlackPixels()) < errorTolerance && Math.abs(questionImages.get(4).getNumBlackPixels() - questionImages.get(5).getNumBlackPixels()) < errorTolerance
    			&& Math.abs(questionImages.get(6).getNumBlackPixels() - questionImages.get(7).getNumBlackPixels()) < errorTolerance){
    		isTrue = true;
		}
    	if ( Math.abs(questionImages.get(0).getNumBlackPixels() - questionImages.get(3).getNumBlackPixels()) < errorTolerance && Math.abs(questionImages.get(3).getNumBlackPixels() - questionImages.get(6).getNumBlackPixels()) < errorTolerance
    			&& Math.abs(questionImages.get(1).getNumBlackPixels() - questionImages.get(4).getNumBlackPixels()) < errorTolerance && Math.abs(questionImages.get(4).getNumBlackPixels() - questionImages.get(7).getNumBlackPixels()) < errorTolerance){
    		isTrue = false;
    	}
    	

    	return isTrue;
    }
    
    public int solveIsHorizontalSame(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	double lowestDifference = 500;
    	int bestAnswer = -2;
    	
    	for (int i = 0; i < answerImages.size(); i++){
    		ArrayList<Double> overlaidImages = overlayImages(questionImages.get(7), answerImages.get(i));
    		double totalNewPix = overlaidImages.get(0) + overlaidImages.get(1);
    		if (totalNewPix < lowestDifference){
    			lowestDifference = totalNewPix;
    			bestAnswer = i;
    		}
    	}
    	
    	return bestAnswer;
    }
    
    public boolean testIsVerticalSame(ArrayList<ImageInfo> questionImages){
    	boolean isTrue = false;
    	double errorTolerance = 100;
    	
    	if ( Math.abs(questionImages.get(0).getNumBlackPixels() - questionImages.get(3).getNumBlackPixels()) < errorTolerance && Math.abs(questionImages.get(3).getNumBlackPixels() - questionImages.get(6).getNumBlackPixels()) < errorTolerance
    			&& Math.abs(questionImages.get(1).getNumBlackPixels() - questionImages.get(4).getNumBlackPixels()) < errorTolerance && Math.abs(questionImages.get(4).getNumBlackPixels() - questionImages.get(7).getNumBlackPixels()) < errorTolerance
    			&& Math.abs(questionImages.get(2).getNumBlackPixels() - questionImages.get(5).getNumBlackPixels()) < errorTolerance){
    		isTrue = true;
    	}
    	if (Math.abs(questionImages.get(0).getNumBlackPixels() - questionImages.get(1).getNumBlackPixels()) < errorTolerance && Math.abs(questionImages.get(1).getNumBlackPixels() - questionImages.get(2).getNumBlackPixels()) < errorTolerance
    			&& Math.abs(questionImages.get(3).getNumBlackPixels() - questionImages.get(4).getNumBlackPixels()) < errorTolerance && Math.abs(questionImages.get(4).getNumBlackPixels() - questionImages.get(5).getNumBlackPixels()) < errorTolerance){
    		isTrue = false;
		}
    	
    	return isTrue;
    }
    
    public int solveIsVerticalSame(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	double lowestDifference = 500;
    	int bestAnswer = -2;
    	
    	for (int i = 0; i < answerImages.size(); i++){
    		ArrayList<Double> overlaidImages = overlayImages(questionImages.get(5), answerImages.get(i));
    		double totalNewPix = overlaidImages.get(0) + overlaidImages.get(1);
    		if (totalNewPix < lowestDifference){
    			lowestDifference = totalNewPix;
    			bestAnswer = i;
    		}
    	}
    	
    	return bestAnswer;
    }
    
    public boolean testIsPartialHorizontalMatch(ArrayList<ImageInfo> questionImages){
    	boolean isTrue = true;
    	double errorTolerance = 200;
    	
    	double matchNumOne = (overlayImages(questionImages.get(0), questionImages.get(1))).get(2);
    	double matchNumTwo = (overlayImages(questionImages.get(1), questionImages.get(2))).get(2);
    	if (Math.abs(matchNumOne - matchNumTwo) > errorTolerance){
    		isTrue = false;
    	}
    	//row2
    	matchNumOne = (overlayImages(questionImages.get(3), questionImages.get(4))).get(2);
    	matchNumTwo = (overlayImages(questionImages.get(4), questionImages.get(5))).get(2);
    	if (Math.abs(matchNumOne - matchNumTwo) > errorTolerance){
    		isTrue = false;
    	}
    	
    	return isTrue;
    }
    
    public int solveIsPartialHorizontalMatch(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	double matchFirstTwo = (overlayImages(questionImages.get(6), questionImages.get(7))).get(2);
    	double lowestDifference = 300;
    	int bestAnswer = -2;
    	
    	for (int i = 0; i < answerImages.size(); i++){
    		double matchSecondTwo = (overlayImages(questionImages.get(7), answerImages.get(i))).get(2);
    		if (Math.abs(matchFirstTwo - matchSecondTwo) < lowestDifference){
    			lowestDifference = Math.abs(matchFirstTwo - matchSecondTwo);
    			bestAnswer = i;
    		}
    	}
    	
    	return bestAnswer;
    }
    
    public boolean testIsPartialVerticalMatch(ArrayList<ImageInfo> questionImages){
    	boolean isTrue = true;
    	double errorTolerance = 200;
    	//col1
    	double matchNumOne = (overlayImages(questionImages.get(0), questionImages.get(3))).get(2);
    	double matchNumTwo = (overlayImages(questionImages.get(3), questionImages.get(6))).get(2);
    	if (Math.abs(matchNumOne - matchNumTwo) > errorTolerance){
    		isTrue = false;
    	}
    	//col2
    	matchNumOne = (overlayImages(questionImages.get(1), questionImages.get(4))).get(2);
    	matchNumTwo = (overlayImages(questionImages.get(4), questionImages.get(7))).get(2);
    	if (Math.abs(matchNumOne - matchNumTwo) > errorTolerance){
    		isTrue = false;
    	}
    	return isTrue;
    }
    
    public int solveIsPartialVerticalMatch(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	double matchFirstTwo = (overlayImages(questionImages.get(2), questionImages.get(5))).get(2);
    	double lowestDifference = 300;
    	int bestAnswer = -2;
    	
    	for (int i = 0; i < answerImages.size(); i++){
    		double matchSecondTwo = (overlayImages(questionImages.get(5), answerImages.get(i))).get(2);
    		if (Math.abs(matchFirstTwo - matchSecondTwo) < lowestDifference){
    			lowestDifference = Math.abs(matchFirstTwo - matchSecondTwo);
    			bestAnswer = i;
    		}
    	}
    	
    	return bestAnswer;
    }
        
    public boolean testIsPartialRightRotation(ArrayList<ImageInfo> questionImages){
    	boolean isTrue = true;
    	double errorTolerance = 500;
    	
    	double matchNumOne = (overlayImages(questionImages.get(2), questionImages.get(3))).get(2);
    	double matchNumTwo = (overlayImages(questionImages.get(3), questionImages.get(7))).get(2);
    	if (Math.abs(matchNumOne - matchNumTwo) > errorTolerance){
    		isTrue = false;
    	}   	
    	matchNumOne = (overlayImages(questionImages.get(1), questionImages.get(5))).get(2);
    	matchNumTwo = (overlayImages(questionImages.get(5), questionImages.get(6))).get(2);
    	if (Math.abs(matchNumOne - matchNumTwo) > errorTolerance){
    		isTrue = false;
    	}
    	
    	return isTrue;
    }
    
    public int solveIsPartialRightRotation(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	double matchFirstTwo = (overlayImages(questionImages.get(0), questionImages.get(4))).get(2);
    	double lowestDifference = 1000;
    	int bestAnswer = -2;
    	
    	for (int i = 0; i < answerImages.size(); i++){
    		ArrayList<Double> firstSet = overlayImages(questionImages.get(0), answerImages.get(i));
    		ArrayList<Double> secondSet = overlayImages(questionImages.get(4), answerImages.get(i));
    		double matchSecondTwo = secondSet.get(2);
    		if (Math.abs(matchFirstTwo - matchSecondTwo) < lowestDifference
    				&&  firstSet.get(0) > 350 && firstSet.get(1) > 350
    				&& secondSet.get(0) > 350 && secondSet.get(1) > 350){
    			//System.out.println("these are so similar, they're the same!");
    			lowestDifference = Math.abs(matchFirstTwo - matchSecondTwo);
    			bestAnswer = i;
    		}
    	}
    	
    	return bestAnswer;
    }
    
    public boolean testIsPartialLeftRotation(ArrayList<ImageInfo> questionImages){
    	boolean isTrue = true;
    	double errorTolerance = 500;
    	
    	double matchNumOne = (overlayImages(questionImages.get(0), questionImages.get(5))).get(2);
    	double matchNumTwo = (overlayImages(questionImages.get(5), questionImages.get(7))).get(2);
    	if (Math.abs(matchNumOne - matchNumTwo) > errorTolerance){
    		isTrue = false;
    	}
    	
    	matchNumOne = (overlayImages(questionImages.get(2), questionImages.get(4))).get(2);
    	matchNumTwo = (overlayImages(questionImages.get(4), questionImages.get(6))).get(2);
    	if (Math.abs(matchNumOne - matchNumTwo) > errorTolerance){
    		isTrue = false;
    	}
    	
    	
    	return isTrue;
    }
    
    public int solveIsPartialLeftRotation(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	double matchFirstTwo = (overlayImages(questionImages.get(1), questionImages.get(3))).get(2);
    	double lowestDifference = 300;
    	int bestAnswer = -2;
    	
    	for (int i = 0; i < answerImages.size(); i++){
    		ArrayList<Double> firstSet = overlayImages(questionImages.get(1), answerImages.get(i));
    		ArrayList<Double> secondSet = overlayImages(questionImages.get(3), answerImages.get(i));
    		double matchSecondTwo = secondSet.get(2);
    		if (Math.abs(matchFirstTwo - matchSecondTwo) < lowestDifference
    				&&  firstSet.get(0) > 350 && firstSet.get(1) > 350
    				&& secondSet.get(0) > 350 && secondSet.get(1) > 350){
    			lowestDifference = Math.abs(matchFirstTwo - matchSecondTwo);
    			bestAnswer = i;
    		}
    	}
    	
    	return bestAnswer;
    }

}

