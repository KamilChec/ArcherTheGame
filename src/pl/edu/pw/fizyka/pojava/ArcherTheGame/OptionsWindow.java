package pl.edu.pw.fizyka.pojava.ArcherTheGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class OptionsWindow extends JFrame {
	
	JSlider sl1, sl2, sl3, sl4, sl5;
	JLabel l1, l2, l3, l4, l5;
	JButton but;
	double temp1, temp2, temp3, temp4, temp5, temp;
	
	
	public void CloseOp(){
		super.dispose();
	}
	
	OptionsWindow(DataHolder h)
	{
		this.setSize(400,400);
		this.setLayout(new GridLayout(6,2));
		
		temp=10;
		
		sl1 = new JSlider(0, 10, 1);		
		sl2 = new JSlider(0, 10, 1);		
		sl3 = new JSlider(0, 20, 10);		
		sl4 = new JSlider(0, 150, 98);		
		sl5 = new JSlider(0, 30, 12);
		
		l1 = new JLabel("Srednica: " + Double.toString(0.1));
		l2 = new JLabel("Masa: " + Double.toString(0.1));
		l3 = new JLabel("Wspolczynnik: " + Double.toString(1));
		l4 = new JLabel("Grawitacja: " + Double.toString(9.8));
		l5 = new JLabel("Gestosc: " + Double.toString(1.2));
		
		this.add(l1);
		this.add(sl1);
		this.add(l2);
		this.add(sl2);
		this.add(l3);
		this.add(sl3);
		this.add(l4);
		this.add(sl4);
		this.add(l5);
		this.add(sl5);	
		
		sl1.addChangeListener(new ChangeListener()
		{	
			@Override
			public void stateChanged(ChangeEvent arg0) {
				temp1=sl1.getValue()/temp;
				l1.setText("Srednica: " + Double.toString(temp1));
			}			
		});
		sl2.addChangeListener(new ChangeListener()
		{	
			@Override
			public void stateChanged(ChangeEvent arg0) {
				temp2=sl2.getValue()/temp;
				l2.setText("Masa: " + Double.toString(temp2));
			}			
		});
		sl3.addChangeListener(new ChangeListener()
		{	
			@Override
			public void stateChanged(ChangeEvent arg0) {
				temp3=sl3.getValue()/temp;
				l3.setText("Wspolczynnik: " + Double.toString(temp3));
			}			
		});
		sl4.addChangeListener(new ChangeListener()
		{	
			@Override
			public void stateChanged(ChangeEvent arg0) {
				temp4=sl4.getValue()/temp;
				l4.setText("Grawitacja: " + Double.toString(temp4));
			}			
		});
		sl5.addChangeListener(new ChangeListener()
		{	
			@Override
			public void stateChanged(ChangeEvent arg0) {
				temp5=sl5.getValue()/temp;
				System.out.println(Double.toString(temp5));
				l5.setText("Gestosc: " + Double.toString(temp5));
			}			
		});
		
		
		but = new JButton("Akceptuj");
		this.add(but);
		but.addActionListener(new ActionListener()
		{			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				h.diameter=sl1.getValue()/temp;
				h.mass=sl2.getValue()/temp;
				h.coeff=sl3.getValue()/temp;
				h.g=sl4.getValue()/temp;
				h.ro=sl5.getValue()/temp;
				CloseOp();
				
			}			
		});
	}

}
