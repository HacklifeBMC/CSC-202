//Author: Christian BASUBI 
//CSC 202 
//Homework #6
//Copy right: November, 2016
//Last Updated: November 6, 2016

package Homework;

/*Write a program in java to record the status of a tuning circuit for a frequency ranges of fmin to fmax using random acess file.
 The dynamic element values must be in a file along with fmin and fmax.
 Input :
f = 16.7 mHZ
15pF rangge 375 pF
Cm  	Cx               	
L=((2Pi/f)^2)/C 
C = sqrt(Cmin*Cmax);    	
fmin = (2Pi)/sqrt(LCm) ;
fmax=(2Pi)/sqrt(LCx); 
Wo = 2PiFinitial;  	
 How to do it : 
a. Read All Variables         	
b. Compute the value of L for a given frequency.
c. Compute the value of C. 
d. Record the values of Fmin and Fmax for capacitor ranging from 15 Pf  to 365 Pf with increment of 30 Pf.
***Increment L by 2%

*The user must input
  */
import java.nio.file.*;
import java.io.IOException; 

public class Homework_6 {

	public static void main(String[] args) throws IOException{
		
		// This is the main method
		//let create the object
	TuningCircuit cr = new TuningCircuit(); 
		
		cr.generate(); 
		
		cr.save(); 
	//Print data that exists
	System.out.println("\nGenerated");
	cr.print(); 
	
	//Print data after the modification
	 System.out.println("\nModified database:");
     cr.print();

     //close input stream that was opened by the constructor
     cr.close();

     System.exit(0);
	}

}
