package com.novadart.utils.image;

import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;

public class ImageUtils {

	public static Dimension getImageDimension(File imageFile){
		Image image = new ImageIcon(imageFile.getPath()).getImage();
		int imgHeight = image.getHeight(null), imgWidth = image.getWidth(null);
		return new Dimension(imgWidth, imgHeight);
	}
	
	public static Dimension resizeConvertImage(File imageFile, int width, int height, File resizedImageFile) throws IOException, InterruptedException, IM4JavaException{
		Dimension dimension = getImageDimension(imageFile);
		boolean resize = dimension.width > width || dimension.height > height; 
		if(!resize && FilenameUtils.getExtension(imageFile.getName()).equals(FilenameUtils.getExtension(resizedImageFile.getName()))){
			FileUtils.copyFile(imageFile, resizedImageFile);
			return dimension;
		}
		int newWidth = dimension.width, newHeight = dimension.height;
		IMOperation op = new IMOperation();
		op.addImage(imageFile.getPath());
		if(resize){
			double wRatio = ((double)dimension.width) / ((double)width);
			double hRatio = ((double)dimension.height) / ((double)height);
			double ratio = Math.max(hRatio, wRatio);
			newWidth = (int)Math.round(dimension.width / ratio);
			newHeight = (int)Math.round(dimension.height / ratio);
			op.resize(newWidth, newHeight);
		}
		op.addImage(resizedImageFile.getPath());
		new ConvertCmd().run(op);
		return new Dimension(newWidth, newHeight);
	}
	
}
