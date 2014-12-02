import javax.swing.JFileChooser;
import javax.swing.JFrame;


public class FileChooser extends JFrame {

	   public FileChooser() {        

	  }
	   
	  public String open(){
		   JFileChooser chooser = new JFileChooser();
	        int option = chooser.showOpenDialog(FileChooser.this);
	        if (option == JFileChooser.APPROVE_OPTION) {
	          return chooser.getSelectedFile().getAbsolutePath();
	        }else {
	          return null;
	        }  
	   }
	  public String save(){
		  JFileChooser chooser = new JFileChooser();
	        int option = chooser.showSaveDialog(FileChooser.this);
	        if (option == JFileChooser.APPROVE_OPTION) {
	        	return chooser.getSelectedFile().getAbsolutePath();
	        }
	        else {
	        	return null;
	        }
	  }
	}