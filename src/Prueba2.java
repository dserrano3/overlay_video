import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber.Exception;


public class Prueba2 {

	/**
	 * @param args
	 * @throws Exception 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */

	public static void main(String[] args) throws Exception, IOException, ClassNotFoundException {
		Class.forName("org.bytedeco.javacpp.swresample");
		FFmpegFrameGrabber g = new FFmpegFrameGrabber("capture.avi");
		g.start();

		for (int i = 0 ; i < 50 ; i++) {
		    ImageIO.write(g.grab().getBufferedImage(), "png", new File("frames/video-frame-" + System.currentTimeMillis() + ".png"));
		}

		g.stop();
	}

}
