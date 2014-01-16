package com.novadart.utils.image;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;

public class ImageUtils {

	public static Dimension getImageDimension(File imageFile) throws FileNotFoundException, IOException, UnsupportedImageFormatException{
		ImageInputStream in = ImageIO.createImageInputStream(new FileInputStream(imageFile));
		try {
			final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
			if (readers.hasNext()) {
				ImageReader reader = (ImageReader) readers.next();
				try {
					reader.setInput(in);
					return new Dimension(reader.getWidth(0), reader.getHeight(0));
				} finally {
					reader.dispose();
				}
			}
		} finally {
			if (in != null) in.close();
		}
		throw new UnsupportedImageFormatException();
	}
	
	public static Dimension resizeConvertImage(File imageFile, int width, int height, File resizedImageFile, double quality) throws IOException, InterruptedException, IM4JavaException, UnsupportedImageFormatException{
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
		op.strip();
		op.quality(quality);
		op.alpha("Background");
		op.addImage(resizedImageFile.getPath());
		new ConvertCmd().run(op);
		return new Dimension(newWidth, newHeight);
	}
	
}
