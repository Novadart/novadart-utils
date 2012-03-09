package com.novadart.utils.image;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;

public interface Image extends Serializable {
	
	String getName();
	
	void setName(String name);
	
	Integer getWidth();
	
	void setWidth(Integer width);
	
	Integer getHeight();
	
	void setHeight(Integer height);
	
	ImageFormat getFormat();
	
	void setFormat(ImageFormat format);
	
	InputStream getImageDataStream() throws FileNotFoundException;
	
	void storeImageData(InputStream in);

}
