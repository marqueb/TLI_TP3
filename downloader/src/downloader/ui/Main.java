package downloader.ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import downloader.fc.Downloader;

public class Main extends JFrame  {
	JProgressBar progress;
	Downloader downloader;
	JLabel url_text;
	JPanel zone_telechargement, liste, ajout, bouton_menu, bouton, telecharge;
	ReentrantLock lock;
	Condition cond;
	int num;
	Main(String argv[]) {	
		//attribut de la fenetre
		setLayout(new BorderLayout());
		setSize(new Dimension(750,450));
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		zone_telechargement = new JPanel();
		ajout = new JPanel();


		liste = new JPanel();

		liste.setLayout(new StackLayout());

		//definition de la zone de telechargement
		zone_telechargement.add(liste,BorderLayout.CENTER);
		zone_telechargement.setBorder(BorderFactory.createLineBorder(Color.black));
		zone_telechargement.setPreferredSize(new Dimension(750,450));
		liste.setBackground(Color.white);
		//Ajout de la zone de telechargement a la fenetre
		add(zone_telechargement,BorderLayout.CENTER);
		add(ajout,BorderLayout.SOUTH);
		setTitle("Downloader");
		pack();

		num=0;
		//Worker worker=new Worker(this, null);
		for(String url: argv) {
			num++;
			Worker worker=new Worker(this, url);
			lock = new ReentrantLock();
			cond = lock.newCondition();
			lock.lock();


			try {
				url_text= new JLabel(url);
				downloader = new Downloader(url);
				//traitement de la progress bar
				progress = new JProgressBar();		
				progress.setValue(downloader.getProgress());
				progress.setStringPainted(true);		
				progress.setString("0 %");
				progress.update(progress.getGraphics());
				//Ajout de la progress bar ainsi que de l url a la liste
				telecharge = new JPanel();
				telecharge.setLayout(new BorderLayout());
				JPanel contenu = new JPanel();
				contenu.setLayout(new GridLayout(2,1));
				contenu.add(url_text);
				contenu.add(progress);
				telecharge.add(contenu, BorderLayout.CENTER);

				//Ajout des bouton play/pause delete
				JToggleButton play = new JToggleButton(" Play  ");
				play.setSelected(true);
				play.addActionListener(new EcouteurPlay(this,worker,cond,lock));

				JButton delete=  new JButton(" x ");
				delete.addActionListener(new EcouteurDelete(this,worker,telecharge,liste));
				bouton = new JPanel();
				bouton.setLayout(new GridLayout(1,2));
				bouton.add(play);
				bouton.add(delete);				
				telecharge.add(bouton, BorderLayout.EAST);	
				System.out.println("ICIIIC "+telecharge);
				liste.add(telecharge);		
				//actualiser la fenetre
				validate();

			}
			catch(RuntimeException e) {
				System.err.format("skipping %s %s\n", url, e);
				continue;
			}

			System.out.format("Downloading %s: \n", downloader);
			System.out.println(" taille octects" + downloader.getContent_length());
			downloader.addPropertyChangeListener(new EcouteurDeTelechargement(downloader, progress,this));
			worker.execute();
			lock.unlock();
		}


		ajout.setLayout(new BorderLayout());
		JTextArea entrer_url = new JTextArea();
		ajout.add(entrer_url,BorderLayout.CENTER);
		JButton add = new JButton("add");
		add.addActionListener(new EcouteurBoutonAdd(this,entrer_url));
		ajout.add(add,BorderLayout.EAST);
	}

	public void execute (String url) throws InterruptedException{

		String filename=null;
		try {
			filename = downloader.download();
			System.out.format("into %s\n", filename);
		}
		catch(InterruptedException e) {
			System.err.println("failed!");
		}

	}	


	public static void main(final String[] argv) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() { 
				new Main(argv); 
			}
		});
	}
}

