import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_highgui;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.IplROI;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;

public class VisualTest {
	static boolean reproducir = true;
	static  FrameGrabber grabber;
	static IplImage img;
	static CanvasFrame canvas ;
	static IplImage logo;
	static FFmpegFrameRecorder recorder;
	static boolean terminado = false;
	

	public static void crearVideo() throws org.bytedeco.javacv.FrameRecorder.Exception{
		
		recorder = new FFmpegFrameRecorder("merge_de_videos.mp4",600, 600);
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4);
        recorder.setFormat("mp4");
        recorder.setFrameRate(12);
        recorder.setVideoBitrate(30000);
        recorder.start();
	}
	
	public static void anadirFrameAVideo(IplImage imagen){
		try {
			recorder.record(imagen);
		} catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
			
			try {
				recorder.stop();
			} catch (org.bytedeco.javacv.FrameRecorder.Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}
	
	
	public static void tenerImagen() throws org.bytedeco.javacv.FrameGrabber.Exception, InterruptedException{
		
		Thread t = new Thread(new Runnable() {
		    public void run() {
		    	while(reproducir){
				    try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
					//inser grabed video fram to IplImage img
				       try {
						img = grabber.grab();
					} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
						System.out.println("todo a terminado");
						terminado = true;
						reproducir = false;
						try {
							recorder.stop();
						} catch (org.bytedeco.javacv.FrameRecorder.Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				        
				       //Set canvas size as per dimentions of video frame.
				       //canvas.setCanvasSize(grabber.getImageWidth(), grabber.getImageHeight()); 
				        
				       
				       
				       //Obtengo posicion del mouse en la pantalla.
					   	PointerInfo a = MouseInfo.getPointerInfo();
						Point point = new Point(a.getLocation());
						SwingUtilities.convertPointFromScreen(point, canvas);
						int x=(int) point.getX();
						int y=(int) point.getY();
						System.out.println(x + "   " + y);
						System.out.println( canvas.size().width + "   " + canvas.size().height);
				       
				       
				       
				       
				       if (img != null) {       
				        //se hace el procesamiento con la imagen.
				    	 if(x > 0 && y > 0 && x <canvas.size().width - 30 && y < canvas.size().height - 30){
					  		 IplROI roi = new IplROI();
					  		 roi.xOffset(x -50);
					  		 roi.yOffset(y -100);
					  		 roi.width(logo.width());
					  		 roi.height(logo.height());
					  		img.roi(roi);
					  		opencv_core.cvCopy(logo, img);
					  		
					  		img.roi(null);
				    	 }
				    	   //Se muestra la imagesn
				    	   canvas.showImage(img);
				    	   System.out.println(img); 
				    	   anadirFrameAVideo(img);
				    	   
				      //  canvas.add(button);
				        }

				}
		    }
		});
		
		t.start();
   }

	
	public static void main(String[] args) throws org.bytedeco.javacv.FrameRecorder.Exception, ClassNotFoundException {
		Class.forName("org.bytedeco.javacpp.swresample");
	  //Create canvas frame for displaying video.
	     canvas = new CanvasFrame("VideoCanvas"); 
	  //Set Canvas frame to close on exit
	     canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);   
	     // canvas.setCanvasSize(900, 600);
	     //Declare FrameGrabber to import video from "video.mp4"
	      grabber = new OpenCVFrameGrabber("capture.avi"); 
	      crearVideo();
	      logo = opencv_highgui.cvLoadImage("meme.jpg");
	      //Boton
	      Button button_play = new Button("play/pausa"); 
	      Button button_stop = new Button("stop"); 
	      JPanel panel1 = new JPanel();
          panel1.setPreferredSize(new Dimension(50,50));
          panel1.setBackground(Color.RED);
          panel1.add(button_play);
          panel1.add(button_stop);
          canvas.add(panel1, BorderLayout.NORTH);
	      
	  //   button.setBounds(100, 100, 40, 40);
          button_play.setSize(20, 20);
          button_stop.setSize(20, 20);
	     
          button_play.addActionListener(new ActionListener() {
	    	  public void actionPerformed(ActionEvent evt) {
	    	    if(terminado)return;
	    		  System.out.println("primero");
	    	    reproducir = !reproducir;
	    	    try {
	    	    	if(reproducir)
					tenerImagen();
				} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	  }
	    	});
          
          button_stop.addActionListener(new ActionListener() {
	    	  public void actionPerformed(ActionEvent evt) {
	    		  if(terminado)return;
	    	    System.out.println("primero");
	    	    reproducir = false;
	    	    try {
	    	    	if(reproducir)
					tenerImagen();
				} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					grabber.start();
				} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	    	  }
	    	});
	    
	     //Acabo boton
	     
	     try {      
	       
	      //Start grabber to capture video
	      grabber.start();      
	       
	      //Declare img as IplImage
	      img = null;
	      
	      tenerImagen();
	      
	      
	      }
	     catch (Exception e) {      
	     }
	    }
	}