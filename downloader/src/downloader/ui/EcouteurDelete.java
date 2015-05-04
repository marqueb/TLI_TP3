package downloader.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class EcouteurDelete implements ActionListener {
	Main main;
	Worker worker;
	JPanel telecharge,liste;
	
	public EcouteurDelete(Main main, Worker worker,JPanel telecharge,JPanel liste) {
		super();
		this.main = main;
		this.worker = worker;

		this.liste=liste;
		this.telecharge=telecharge;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(!worker.isCancelled())
			worker.cancel(true);
		telecharge.setVisible(false);
		main.validate();
	}

}
