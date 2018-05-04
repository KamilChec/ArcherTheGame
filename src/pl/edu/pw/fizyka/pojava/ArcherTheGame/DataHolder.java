package pl.edu.pw.fizyka.pojava.ArcherTheGame;

public class DataHolder {
	
	double a, g, ro, diameter, mass, coeff;
	
	DataHolder()
	{		
		diameter = 0.1;
		mass = 0.1;
		coeff = 1;
		g = 9.81;
		ro = 1.2;
		a = coeff*diameter*ro*mass;
	}
	

}
