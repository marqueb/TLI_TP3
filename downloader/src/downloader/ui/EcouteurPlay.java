package downloader.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JToggleButton;

public class EcouteurPlay implements ActionListener {
	Main main;
	Worker worker;
	ReentrantLock lock;
	Condition cond;

	public EcouteurPlay(Main main, Worker worker, Condition cond, ReentrantLock lock) {
		// TODO Auto-generated constructor stub
		super();
		this.main = main;
		this.worker = worker;
		this.cond=cond;
		this.lock=lock;
	}



	@Override
	public void actionPerformed(ActionEvent arg0) {
		JToggleButton bouton=(JToggleButton)arg0.getSource();
		if(bouton.isSelected()){
			try {
				cond.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		else{
			bouton.setText(" Play  ");
			cond.notify();
		}

	}

}
