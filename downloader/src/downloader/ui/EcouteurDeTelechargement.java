package downloader.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

import downloader.fc.Downloader;

public class EcouteurDeTelechargement implements PropertyChangeListener{
	Downloader download;
	JProgressBar progress;
	JFrame frame;
	public EcouteurDeTelechargement (Downloader download,JProgressBar progress, JFrame frame){
		this.download=download;
		this.progress=progress;
		this.frame=frame;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
			progress.setValue(download.getProgress());
			progress.setString(progress.getValue()+" %");
			progress.update(progress.getGraphics());
	}

}
