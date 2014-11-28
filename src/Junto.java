import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.bytedeco.javacpp.opencv_core.CvRect;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.IplROI;
import org.bytedeco.javacpp.opencv_highgui;
import org.bytedeco.javacv.*;
import org.bytedeco.javacpp.*;
class ImageImplement extends JPanel { 
	private Image img; 
	public ImageImplement(Image img) { 
		this.img = img; 
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null)); 
		setPreferredSize(size); setMinimumSize(size); 
		setMaximumSize(size); setSize(size); 
		setLayout(null); 
		} 
	public void paintComponent(Graphics g) { 
		g.drawImage(img, 0, 0, null); 
		
		} 
	} 

public class Junto extends JFrame { 
	public static void main(String args[]) {
		//new Junto().start();
		
		 IplImage image = opencv_highgui.cvLoadImage("Lighthouse.jpg");
		 //IplImage mask = opencv_highgui.cvLoadImage("Lighthouse.jpg");
		 IplImage logo = opencv_highgui.cvLoadImage("meme.jpg");
		 
		 
		 
		 IplROI roi = new IplROI();
		 roi.xOffset(385);
		 roi.yOffset(270);
		 roi.width(logo.width());
		 roi.height(logo.height());
		 
		image.roi(roi);
		 
		 opencv_core.cvCopy(logo, image);
		//x1   y1   width height
		//opencv_core.cvCopy(logo, imagen);
		
		final CanvasFrame canvas = new CanvasFrame("Demo");
        canvas.showImage(image);
        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		 
	
	
	
	} 
	public void start() {
			ImageImplement panel = new ImageImplement(new ImageIcon("Lighthouse.jpg").getImage());
			ImageImplement panel2 = new ImageImplement(new ImageIcon("Penguins.jpg").getImage());
			panel2.setSize(200, 200);
			panel.setSize(50, 50);
			add(panel); 
			add(panel2);
			setVisible(true);
			setSize(400,400); 
			setDefaultCloseOperation(EXIT_ON_CLOSE);
	} 
}
