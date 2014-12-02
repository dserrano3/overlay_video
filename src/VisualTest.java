import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.CvSize;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.IplROI;
import org.bytedeco.javacpp.opencv_highgui;
import org.bytedeco.javacpp.opencv_highgui.*;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.bytedeco.javacpp.*;
import org.bytedeco.javacv.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;




public class VisualTest {
	
	static boolean reproducir = true;
	static  FrameGrabber grabber;
	static IplImage img;
	static CanvasFrame canvas ;
	static IplImage logo;
	static FFmpegFrameRecorder recorder;
	static boolean terminado = false;
	static ArrayList<IplImage> imagenes = new ArrayList<IplImage>();
	static int x;
	static int y;
	
	public static void crearVideo(String lugar) throws org.bytedeco.javacv.FrameRecorder.Exception{
		
		//recorder = new FFmpegFrameRecorder("merge_de_videos.mp4",grabber.getImageWidth(), grabber.getImageWidth());
		recorder = new FFmpegFrameRecorder(lugar,grabber.getImageWidth(), grabber.getImageWidth());
		recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4);
        recorder.setFormat("mp4");
        recorder.setFrameRate(grabber.getFrameRate());
        recorder.setVideoBitrate(10000000);
        recorder.start();
	}
	
	public static void anadirFrameAVideo(IplImage imagen){
		try {
			recorder.record(imagen);
		} catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
			
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
					}
				        
				       //Set canvas size as per dimentions of video frame.
				       canvas.setCanvasSize(grabber.getImageWidth(), grabber.getImageHeight()); 
				        
				       
				       
				       //Obtengo posicion del mouse en la pantalla.
					   	PointerInfo a = MouseInfo.getPointerInfo();
						Point point = new Point(a.getLocation());
						SwingUtilities.convertPointFromScreen(point, canvas);
						
						 x=(int) point.getX();
						 y=(int) point.getY();
						
						System.out.println( canvas.getCanvasSize() + " " );
				       
				       
				       System.out.println(x + "   " + y);
						double monitorWindowScale = canvas.getCanvasScale();
				       if (img != null) {       
				        //se hace el procesamiento con la imagen.
				    	 if(x > 60 && y > 110 && x <canvas.size().width - 70 && y < canvas.size().height - 60){
					  		 IplROI roi = new IplROI();
					  		 roi.xOffset((int)(x));
					  		 
					  		 roi.yOffset((int)(y-100));
					  		 roi.width(logo.width());
					  		 roi.height(logo.height());
					  		img.roi(roi);
					  		opencv_core.cvCopy(logo, img);
					  		
					  		img.roi(null);
				    	 }
				    	   //Se muestra la imagesn
				    	   canvas.showImage(img);
				    	  // System.out.println(img); 
				    	  //Esta linea es si se quiere ir guardando en tiempo real, pero toca inicializarlo abajo.
				    	   // anadirFrameAVideo(img);
				    	   imagenes.add(img.clone());
				      //  canvas.add(button);
				        }

				}
		    }
		});
		
		t.start();
   }
	
	public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
	
	public static void cojerCoordenadas(){
		System.out.println("empece");
		canvas.addMouseListener(new MouseAdapter() {
	            @Override public void mouseClicked(MouseEvent e) {
	            	System.out.println("evento");
	            	x = e.getX();
	            	y = e.getY();
	            }
	        });
	            
	}
	
	
	
	
	public static void main(String[] args) throws org.bytedeco.javacv.FrameRecorder.Exception, ClassNotFoundException {
		Class.forName("org.bytedeco.javacpp.swresample");
	  //Create canvas frame for displaying video.
	     canvas = new CanvasFrame("VideoCanvas"); 
	  //Set Canvas frame to close on exit
	     canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);   
	     // canvas.setCanvasSize(700, 400);
	      //canvas.
	     //Declare FrameGrabber to import video from "video.mp4"
	     FileChooser fc = new FileChooser();
	     infoBox("Por favor escoja un video en la siguiente ventanna.", "Informacion importantisima");
	     String s_video = fc.open();
	     infoBox("Por favor escoja una imagen en la siguiente ventanna.", "Informacion importantisima");
	     String s_imagen = fc.open();
	     // grabber = new OpenCVFrameGrabber("capture.avi"); 
	     infoBox("Por favor espere mientras se carga el video.", "Informacion importantisima");
	     grabber = new OpenCVFrameGrabber(s_video); 
	     
	     // crearVideo();
	     // logo = opencv_highgui.cvLoadImage("meme.jpg");
	     IplImage logo2;
	     logo2 = opencv_highgui.cvLoadImage(s_imagen);
	     
	     logo = IplImage.create(60, 60, logo2.depth(), logo2.nChannels());
	     
	     cvResize(logo2, logo);
	     
	     
	     
	     
	      //Boton
	      Button button_play = new Button("play/pausa"); 
	      Button button_stop = new Button("stop"); 
	      Button button_guardar = new Button("guardar");
	      JPanel panel1 = new JPanel();
          panel1.setPreferredSize(new Dimension(50,50));
          panel1.setBackground(Color.RED);
          panel1.add(button_play);
          panel1.add(button_stop);
          panel1.add(button_guardar);
          canvas.add(panel1, BorderLayout.NORTH);
          canvas.setCanvasSize(640, 480);
	  //   button.setBounds(100, 100, 40, 40);
          button_play.setSize(20, 20);
          button_stop.setSize(20, 20);
          button_guardar.setSize(20, 20);
          
          
          button_play.addActionListener(new ActionListener() {
	    	  public void actionPerformed(ActionEvent evt) {
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
          
          
          button_guardar.addActionListener(new ActionListener() {
	    	  public void actionPerformed(ActionEvent evt) {
	    		  try {
	    			  infoBox("Escoja donde guardarlo y un nombre sin extension(sera guardado en mp4)" , "Informacion importantisima");
	    			  FileChooser fc = new FileChooser();
	    			  String s_guardado = fc.save();
	    			  crearVideo(s_guardado+".mp4");
	    			  infoBox("Su video ha sido guardado en: " + s_guardado, "Informacion importantisima");
				} catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		  
	    		for(int i = 0; i < imagenes.size()-1;i++){
	    			anadirFrameAVideo(imagenes.get(i));
	    			//canvas.showImage(imagenes.get(i));
	    			//if(imagenes.get(i) == imagenes.get(i+1))System.out.println("tienen la misma direccion");
	    		}
	    		try {
					recorder.stop();
				} catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		
	    		  
	    	  }
	    	});
          
          
          button_stop.addActionListener(new ActionListener() {
	    	  public void actionPerformed(ActionEvent evt) {
	    		 
	    		
	    	    reproducir = false;
	    	    System.out.println("entre a stop");
	    	    imagenes.clear();
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