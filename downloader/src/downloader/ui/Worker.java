package downloader.ui;
import javax.swing.SwingWorker;

public class Worker extends SwingWorker<Void,Void> {
	Main main;
	String url;
    public Worker(Main main,String url) {
      this.main=main;
      this.url=url;
    }

	@Override
	protected Void doInBackground() throws Exception {
		main.execute(url);
		return null;
	}
   

}
