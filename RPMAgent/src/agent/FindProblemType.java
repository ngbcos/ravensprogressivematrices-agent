package agent;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * A series of static helper functions to determine a relationship between rows or columns of images. 
 * Based around image pixel count, relative position, and overlap.  
 *
 */
public class FindProblemType {
    
	/**
	 * tests the provided images for equality
	 * @param questionImages a row or column subset of images to be tested.
	 * @return true if the two are equal (within a bound of error and false otherwise
	 */
	public static boolean testForEquality(ArrayList<ImageInfo> questionImages) {	
		double errorTolerance = 100;
		//testing a 2x2 matrix
		if (questionImages.size() == 2) {
			if (Math.abs(questionImages.get(0).getNumBlackPixels() - questionImages.get(1).getNumBlackPixels()) < errorTolerance) {
				return true;
			}
		}
		
		//testing a 3x3 matrix
		else if (questionImages.size() == 3) {
			if (Math.abs(questionImages.get(0).getNumBlackPixels() - questionImages.get(1).getNumBlackPixels()) < errorTolerance && 
					Math.abs(questionImages.get(1).getNumBlackPixels() - questionImages.get(2).getNumBlackPixels()) < errorTolerance) {
				return true;
			}
		}
		return false;
	}
	
	
    public static boolean testForEquality3x3(ArrayList<ImageInfo> questionImages){
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
    
    
    public static boolean testIsOneWayVerticalGain(ArrayList<ImageInfo> questionImages){
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
    
    
    public static boolean testIsOneWayVerticalLoss(ArrayList<ImageInfo> questionImages){
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
    
    
    public static boolean testIsOneWayHorizontalGain(ArrayList<ImageInfo> questionImages){
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
    
    
    public static boolean testIsOneWayHorizontalLoss(ArrayList<ImageInfo> questionImages){
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
    
    
    public static boolean testIsTwoWayGain(ArrayList<ImageInfo> questionImages){
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
    
    
    public static boolean testIsTwoWayLoss(ArrayList<ImageInfo> questionImages){
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
    
    
    public static boolean testIsRadial(ArrayList<ImageInfo> questionImages){
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
    
    
    public static boolean testIsVGainHLoss(ArrayList<ImageInfo> questionImages){
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
    
    
    public static boolean testIsVLossHGain(ArrayList<ImageInfo> questionImages){
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
    
    
    public static boolean testIsRightRotation(ArrayList<ImageInfo> questionImages){
    	boolean isTrue = false;
    	double errorTolerance = 200;
    	
    	if (Math.abs(questionImages.get(0).getNumBlackPixels() - questionImages.get(4).getNumBlackPixels()) < errorTolerance
    			&& Math.abs(questionImages.get(1).getNumBlackPixels() - questionImages.get(5).getNumBlackPixels()) < errorTolerance && Math.abs(questionImages.get(5).getNumBlackPixels() - questionImages.get(6).getNumBlackPixels()) < errorTolerance
    			&& Math.abs(questionImages.get(2).getNumBlackPixels() - questionImages.get(3).getNumBlackPixels()) < errorTolerance &&Math.abs(questionImages.get(3).getNumBlackPixels() - questionImages.get(7).getNumBlackPixels()) < errorTolerance){
    		
    		isTrue = true;
    	}
    	
    	return isTrue;
    }
    
    
    public static boolean testIsLeftRotation(ArrayList<ImageInfo> questionImages){
    	boolean isTrue = false;
    	double errorTolerance = 100;

    	if (Math.abs(questionImages.get(1).getNumBlackPixels() - questionImages.get(3).getNumBlackPixels()) < errorTolerance
    			&& Math.abs(questionImages.get(0).getNumBlackPixels() - questionImages.get(5).getNumBlackPixels()) < errorTolerance && Math.abs(questionImages.get(5).getNumBlackPixels() - questionImages.get(7).getNumBlackPixels()) < errorTolerance
    			&& Math.abs(questionImages.get(2).getNumBlackPixels() - questionImages.get(4).getNumBlackPixels()) < errorTolerance &&Math.abs(questionImages.get(4).getNumBlackPixels() - questionImages.get(6).getNumBlackPixels()) < errorTolerance){
    		
    		isTrue = true;
    	}

    	return isTrue;
    }
    
    
    public static boolean testIsCenterAdditive(ArrayList<ImageInfo> questionImages){
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
    
    
    public static boolean testIsEndAdditive(ArrayList<ImageInfo> questionImages){
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
    
    
    public static boolean testIsSubtractive(ArrayList<ImageInfo> questionImages){
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
    
    
    //does not work in all cases, needs more testing
    public static boolean testIsXOR(ArrayList<ImageInfo> questionImages){
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
    
    
    public static boolean testIsAnd(ArrayList<ImageInfo> questionImages){
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
    
    
    public static boolean testIsHorizontalSlideThrough(ArrayList<ImageInfo> questionImages){
    	boolean isTrue = false;
    	double errorTolerance = 200;
    	
    	if ((Math.abs(questionImages.get(0).getNumBlackPixels() - questionImages.get(2).getNumBlackPixels()) < errorTolerance && questionImages.get(0).getNumBlackPixels() >= questionImages.get(1).getNumBlackPixels())
    			&& (Math.abs(questionImages.get(3).getNumBlackPixels() - questionImages.get(5).getNumBlackPixels()) < errorTolerance && questionImages.get(3).getNumBlackPixels() >= questionImages.get(4).getNumBlackPixels())){
    		isTrue = true;
    	}
    	return isTrue;
    }
    
    
    public static boolean testIsVerticalSlideThrough(ArrayList<ImageInfo> questionImages){
    	boolean isTrue = false;
    	double errorTolerance = 200;
    	
    	if ((Math.abs(questionImages.get(0).getNumBlackPixels() - questionImages.get(6).getNumBlackPixels()) < errorTolerance && questionImages.get(0).getNumBlackPixels() >= questionImages.get(3).getNumBlackPixels())
    			&& (Math.abs(questionImages.get(1).getNumBlackPixels() - questionImages.get(7).getNumBlackPixels()) < errorTolerance && questionImages.get(1).getNumBlackPixels() >= questionImages.get(4).getNumBlackPixels())){
    		isTrue = true;
    	}

    	return isTrue;
    }
    
    
    public static boolean testIsHorizontalSame(ArrayList<ImageInfo> questionImages){
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
    
    
    public static boolean testIsVerticalSame(ArrayList<ImageInfo> questionImages){
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
    
    
    public static boolean testIsPartialHorizontalMatch(ArrayList<ImageInfo> questionImages){
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
    
    
    public static boolean testIsPartialVerticalMatch(ArrayList<ImageInfo> questionImages){
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
    
    
    public static boolean testIsPartialRightRotation(ArrayList<ImageInfo> questionImages){
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
    
    
    public static boolean testIsPartialLeftRotation(ArrayList<ImageInfo> questionImages){
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
    
    
    //layer two images on top of one another and determine how many of their pixels overlap, how many pixels are unique to each image, and how many are in the new image total. 
    public static ArrayList<Double> overlayImages(ImageInfo imageinfo1, ImageInfo imageinfo2){
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
    public static ArrayList<Double> XORImages(ImageInfo imageinfo1, ImageInfo imageinfo2){
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
    
}
