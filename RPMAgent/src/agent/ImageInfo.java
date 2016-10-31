package agent;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * A class representing one image in a RPM. 
 *
 */
public class ImageInfo {
	private String name = "";
	private BufferedImage image;
	private double numBlackPixels = 0;
	private double numTotalPixels = 0;
	private double ratioBlackPixels = 0;

	
	/**
	 * @param name the name of the image corresponding to its position in the grid
	 * @param image the image data itself
	 */
	public ImageInfo(String name, BufferedImage image){
		this.name = name;
		this.image = image;
		
		numTotalPixels = image.getWidth() * image.getHeight();
		numBlackPixels = countPixels(image);
		ratioBlackPixels = numBlackPixels/numTotalPixels;
	}
	
	/**
	 * 
	 * @param image
	 * @return the number of black pixels in the image
	 */
	public double countPixels(BufferedImage image){
		double blackPixels = 0;
		for (int i = 0; i < image.getHeight(); i++){
			for (int j = 0; j < image.getWidth(); j++){
				if (image.getRGB(i, j) == -16777216){
					blackPixels+=1;
				}
			}
		}
		return blackPixels;
	}
	
	
	public String getName(){
		return name;
	}
	
	
	public BufferedImage getImage(){
		return image;
	}
	
	
	public double getNumPixels(){
		return numTotalPixels;
	}
	
	
	public double getNumBlackPixels(){
		return numBlackPixels;
	}
	
	
	public double getRatioBlackPixels(){
		return ratioBlackPixels;
	}
	
	/**
	 * Debugging helper function to print all relevant info about an image. 
	 */
	public void printImage(){
		System.out.println("Name of image is " + name);
		System.out.println("num of total pixels is " + numTotalPixels);
		System.out.println("num of black pixels is " + numBlackPixels);
		System.out.println("ratio of black pixels to total pixels is " + ratioBlackPixels);
	}
}
