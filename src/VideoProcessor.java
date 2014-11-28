import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;

public class VideoProcessor {
	
	public static void main(String[] args) {
	  //Create canvas frame for displaying video.
	     CanvasFrame canvas = new CanvasFrame("VideoCanvas"); 
	   
	  //Set Canvas frame to close on exit
	     canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);   
	      
	     //Declare FrameGrabber to import video from "video.mp4"
	     FrameGrabber grabber = new OpenCVFrameGrabber("capture.avi");  
	      
	      
	     try {      
	       
	      //Start grabber to capture video
	      grabber.start();      
	       
	      //Declare img as IplImage
	      IplImage img;
	       
	      while (true) {
	        
	       //inser grabed video fram to IplImage img
	       img = grabber.grab();
	        
	       //Set canvas size as per dimentions of video frame.
	       canvas.setCanvasSize(grabber.getImageWidth(), grabber.getImageHeight()); 
	        
	       if (img != null) {       
	        //Show video frame in canvas
	        canvas.showImage(img);               
	        }
	       }
	      }
	     catch (Exception e) {      
	     }
	    }
	}