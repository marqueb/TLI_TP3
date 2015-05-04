package downloader.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import downloader.fc.Downloader;

public class EcouteurBoutonAdd implements ActionListener {
	Main main;
	JTextArea url;
	public EcouteurBoutonAdd(Main main,JTextArea url) {
		super();
		this.url=url;
		this.main = main;
	}

	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Worker worker=new Worker(main, url.getText());

		try {
			main.url_text = new JLabel(url.getText());
			main.downloader = new Downloader(url.getText());
			main.progress = new JProgressBar();		
			main.progress.setValue(main.downloader.getProgress());
			main.progress.setStringPainted(true);		
			main.progress.setString("0 %");
			main.progress.update(main.progress.getGraphics());			
			main.liste.add(main.progress);
			main.validate();
			System.out.format("Downloading %s: \n", main.downloader);
			System.out.println(" taille octects" + main.downloader.getContent_length());
			main.downloader.addPropertyChangeListener(new EcouteurDeTelechargement(main.downloader, main.progress,main));
			worker.execute();
			
		}
		catch(RuntimeException e) {
			System.err.format("skipping %s\n", e);
		}
		

	}

}
