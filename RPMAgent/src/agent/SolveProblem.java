package agent;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SolveProblem {
	
    //use this to solve an RPM that has been classified as equal
    public static int solveisEqual(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	ArrayList<Integer> possibleAnswers = new ArrayList<Integer>();
    	double errorTolerance = 10;
    	
    	for (int i = 0; i < answerImages.size(); i++){
    		double pixInCommon = (FindProblemType.overlayImages(questionImages.get(0), answerImages.get(i))).get(2);
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
    
    
    public static int solveIsOneWayVerticalGainOrLoss(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
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
    
    
    public static int solveIsOneWayHorizontalGainOrLoss(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
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
    
    
    public static int solveIsTwoWayGain(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
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
    
    
    public static int solveIsTwoWayLoss(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
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
    
    
    public static int solveIsRadial(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
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
    			double tempScore = FindProblemType.overlayImages(reflectedImageInfo, answerImages.get(answerChoices.get(j))).get(2);
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
    
    
    public static int solveIsGainLoss(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	ArrayList<Integer> answerChoices = new ArrayList<Integer>();
    	double errorTolerance = 100;
    	
    	for (int i = 0; i < answerImages.size(); i++){
    		if (Math.abs(answerImages.get(i).getNumBlackPixels() - questionImages.get(0).getNumBlackPixels()) < errorTolerance){
    			answerChoices.add(i);
    		}
    	}
    	
    	if (answerChoices.size() > 1){
    		double pixInCommon1 = (FindProblemType.overlayImages(questionImages.get(2), questionImages.get(5)).get(2));
    		for (int j = 0; j < answerChoices.size(); j++){
    			double pixInCommon2 = (FindProblemType.overlayImages(questionImages.get(5), answerImages.get(answerChoices.get(j)))).get(2);
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
    
    
    public static int solveIsRightRotation(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	double lowestDifference = 500;
    	int bestAnswer = -2;
    	
    	for (int i = 0; i < answerImages.size(); i++){
    		ArrayList<Double> overlaidImages = FindProblemType.overlayImages(questionImages.get(4), answerImages.get(i));
    		double totalNewPix = overlaidImages.get(0) + overlaidImages.get(1);
    		if (totalNewPix < lowestDifference){
    			lowestDifference = totalNewPix;
    			bestAnswer = i;
    		}
    	}
    	
    	return bestAnswer;
    }
    
    
    public static int solveIsLeftRotation(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	double lowestDifference = 500;
    	int bestAnswer = -2;
    	
    	for (int i = 0; i < answerImages.size(); i++){
    		ArrayList<Double> overlaidImages = FindProblemType.overlayImages(questionImages.get(3), answerImages.get(i));
    		double totalNewPix = overlaidImages.get(0) + overlaidImages.get(1);
    		if (totalNewPix < lowestDifference){
    			lowestDifference = totalNewPix;
    			bestAnswer = i;
    		}
    	}
    	
    	return bestAnswer;
    }
    
    
    public static int solveIsCenterAdditive(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	int bestAnswer = -2;
    	double bestScore = 1000;
    	
    	for (int i = 0; i < answerImages.size(); i++){
    		double combinedPixels = FindProblemType.overlayImages(questionImages.get(6), answerImages.get(i)).get(3);
    		if (Math.abs(questionImages.get(7).getNumBlackPixels() - combinedPixels) < bestScore){
    			bestAnswer = i;
    			bestScore = Math.abs(answerImages.get(i).getNumBlackPixels() - combinedPixels);
    		}

    	}
    	
    	return bestAnswer;
    }
    
    
    public static int solveIsEndAdditive(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	int bestAnswer = -2;
    	double bestScore = 1000;
    	
    	for (int i = 0; i < answerImages.size(); i++){
    		double combinedPixels = FindProblemType.overlayImages(questionImages.get(6), questionImages.get(7)).get(3);
    		if (Math.abs(answerImages.get(i).getNumBlackPixels() - combinedPixels) < bestScore){
    			bestAnswer = i;
    			bestScore = Math.abs(answerImages.get(i).getNumBlackPixels() - combinedPixels);
    		}

    	}
    	
    	return bestAnswer;
    }
    
    
    public static int solveIsSubtractive(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
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
    
    
    public static int solveIsXOR(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	int bestAnswer = -2;
    	double bestScore = 1000;
    	
    	for (int i = 0; i < answerImages.size(); i++){
        	ArrayList<Double> firstAndSecondOverlay = FindProblemType.overlayImages(questionImages.get(6), questionImages.get(7));
        	double XORPixels = firstAndSecondOverlay.get(3) - firstAndSecondOverlay.get(2);
        	if (Math.abs(answerImages.get(i).getNumBlackPixels() - XORPixels) <  bestScore){
        		bestAnswer = i;
        		bestScore = Math.abs(answerImages.get(i).getNumBlackPixels() - XORPixels);
        	}
    	}
    	
    	return bestAnswer;
    }
    
    
    public static int solveisAnd(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	int bestAnswer = -2;
    	double bestSoFar = 500; 
    	for (int i = 0; i < answerImages.size(); i++){
	    	double pixInCommon = (FindProblemType.overlayImages(questionImages.get(0), answerImages.get(i))).get(2);
	    	if (Math.abs(answerImages.get(i).getNumBlackPixels() - pixInCommon) < bestSoFar){
	    		bestSoFar = Math.abs(answerImages.get(i).getNumBlackPixels() - pixInCommon);
	    		bestAnswer = i;
	    	}
    	}
    	return bestAnswer;
    }
    
    
    public static int solveIsHorizontalSame(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	double lowestDifference = 500;
    	int bestAnswer = -2;
    	
    	for (int i = 0; i < answerImages.size(); i++){
    		ArrayList<Double> overlaidImages = FindProblemType.overlayImages(questionImages.get(7), answerImages.get(i));
    		double totalNewPix = overlaidImages.get(0) + overlaidImages.get(1);
    		if (totalNewPix < lowestDifference){
    			lowestDifference = totalNewPix;
    			bestAnswer = i;
    		}
    	}
    	
    	return bestAnswer;
    }
    
    
    public static int solveIsVerticalSame(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	double lowestDifference = 500;
    	int bestAnswer = -2;
    	
    	for (int i = 0; i < answerImages.size(); i++){
    		ArrayList<Double> overlaidImages = FindProblemType.overlayImages(questionImages.get(5), answerImages.get(i));
    		double totalNewPix = overlaidImages.get(0) + overlaidImages.get(1);
    		if (totalNewPix < lowestDifference){
    			lowestDifference = totalNewPix;
    			bestAnswer = i;
    		}
    	}
    	
    	return bestAnswer;
    }
    
    
    public static int solveIsPartialHorizontalMatch(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	double matchFirstTwo = (FindProblemType.overlayImages(questionImages.get(6), questionImages.get(7))).get(2);
    	double lowestDifference = 300;
    	int bestAnswer = -2;
    	
    	for (int i = 0; i < answerImages.size(); i++){
    		double matchSecondTwo = (FindProblemType.overlayImages(questionImages.get(7), answerImages.get(i))).get(2);
    		if (Math.abs(matchFirstTwo - matchSecondTwo) < lowestDifference){
    			lowestDifference = Math.abs(matchFirstTwo - matchSecondTwo);
    			bestAnswer = i;
    		}
    	}
    	
    	return bestAnswer;
    }
    
    
    public static int solveIsPartialVerticalMatch(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	double matchFirstTwo = (FindProblemType.overlayImages(questionImages.get(2), questionImages.get(5))).get(2);
    	double lowestDifference = 300;
    	int bestAnswer = -2;
    	
    	for (int i = 0; i < answerImages.size(); i++){
    		double matchSecondTwo = (FindProblemType.overlayImages(questionImages.get(5), answerImages.get(i))).get(2);
    		if (Math.abs(matchFirstTwo - matchSecondTwo) < lowestDifference){
    			lowestDifference = Math.abs(matchFirstTwo - matchSecondTwo);
    			bestAnswer = i;
    		}
    	}
    	
    	return bestAnswer;
    }
    
    
    public static int solveIsPartialRightRotation(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	double matchFirstTwo = (FindProblemType.overlayImages(questionImages.get(0), questionImages.get(4))).get(2);
    	double lowestDifference = 1000;
    	int bestAnswer = -2;
    	
    	for (int i = 0; i < answerImages.size(); i++){
    		ArrayList<Double> firstSet = FindProblemType.overlayImages(questionImages.get(0), answerImages.get(i));
    		ArrayList<Double> secondSet = FindProblemType.overlayImages(questionImages.get(4), answerImages.get(i));
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
    
    
    public static int solveIsPartialLeftRotation(ArrayList<ImageInfo> questionImages, ArrayList<ImageInfo> answerImages){
    	double matchFirstTwo = (FindProblemType.overlayImages(questionImages.get(1), questionImages.get(3))).get(2);
    	double lowestDifference = 300;
    	int bestAnswer = -2;
    	
    	for (int i = 0; i < answerImages.size(); i++){
    		ArrayList<Double> firstSet = FindProblemType.overlayImages(questionImages.get(1), answerImages.get(i));
    		ArrayList<Double> secondSet = FindProblemType.overlayImages(questionImages.get(3), answerImages.get(i));
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
