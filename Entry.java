package Homework;

public class Entry {
	private double l;     //Variables that seem
	  private double c;     //  have something to
	  private double fmin;  //  do with tuning or
	  private double fmax;  //  maybe circuts

	  //Default constructor - create 'empty' entry for database
	  public Entry() {
	    l    = 0; //Not sure what these values are, but I do have formulas.
	    c    = 0;
	    fmin = 0;
	    fmax = 0;
	  }

	  //Accessor/modifier methods
	  public double calcC() {
	    return this.c = Math.sqrt(fmin*fmax); //Use formula to calculate C
	  }

	  public void calcF(double cn, double cx) {
	    //cx to fn and cn to fx
	    // because capacitor max is *inversely* proportional to frequency min
	    //     and capacitor min is *inversely* proportional to frequency max
	    this.setFmin(2*Math.PI/Math.sqrt(this.l*cx)); //Use formula to calculate Fs
	    this.setFmax(2*Math.PI/Math.sqrt(this.l*cn));
	  }

	  public void setFmin(double fmin) {
	    this.fmin = fmin/1000000000000.; //conversion to/from base units
	  }

	  public double getFmin() {
	    return this.fmin*1000000000000.; //conversion to/from base units
	  }

	  public void setFmax(double fmax) {
	    this.fmax = fmax/1000000000000.; //conversion to/from base units
	  }

	  public double getFmax() {
	    return this.fmax*1000000000000.; //conversion to/from base units
	  }

	  public void setL(double l) {
	    this.l = l;
	  }

	  public double getL() {
	    return this.l;
	  }

	  public void setC(double c) {
	    this.c = c/1000000000000.; //conversion to/from base units
	  }

	  public double getC() {
	    return this.c*1000000000000.; //conversion to/from base units
	  }
	

}
