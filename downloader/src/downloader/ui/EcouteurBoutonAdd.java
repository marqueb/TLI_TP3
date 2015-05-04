package downloader.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
			main.url_text= new JLabel(url.getText());
			main.downloader = new Downloader(url.getText());
			//traitement de la progress bar
			main.progress = new JProgressBar();		
			main.progress.setValue(main.downloader.getProgress());
			main.progress.setStringPainted(true);		
			main.progress.setString("0 %");
			main.progress.update(main.progress.getGraphics());
			//Ajout de la progress bar ainsi que de l url a la liste
			JPanel telecharge = new JPanel();
			telecharge.setLayout(new BorderLayout());
			JPanel contenu = new JPanel();
			contenu.setLayout(new GridLayout(2,1));
			contenu.add(main.url_text);
			contenu.add(main.progress);
			telecharge.add(contenu, BorderLayout.CENTER);
			
			//Ajout des bouton play/pause delete
			JButton play = new JButton(" Stop ");
			JButton delete=  new JButton(" x ");
			main.bouton = new JPanel();
			main.bouton.setLayout(new GridLayout(1,2));
			main.bouton.add(play);
			main.bouton.add(delete);		
			delete.addActionListener(new EcouteurDelete(main,worker,telecharge,main.liste));	
			telecharge.add(main.bouton, BorderLayout.EAST);
			main.liste.add(telecharge);
			//actualiser la fenetre
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
