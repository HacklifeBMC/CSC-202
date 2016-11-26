package Homework;
//This is an class of an interactive program,All the input data will be from the user
/* frequency(MHz):16.7
   increment(pF): 30
   Min. Cap.(pF): 15
   Max. Cap.(pF): 365
 */
import java.io.*;
import java.util.*; 

public class TuningCircuit {

	private List<Entry> entries; 	// List of entries in the file
	private String Filename;		//Filename associated with the file 
	private BufferedReader input; 	//We will be using the BufferReader Class to input data
	private double Cmin; 			// this is the Freq. min
	private double Cmax; 			//This is the freq.max
	
	
		public TuningCircuit () { //Default Constructor: will set all the file name 
	entries = new ArrayList <Entry>();
	Filename = ""; 
	Cmax = 0;
	Cmin = 0; 

	input = new BufferedReader (new InputStreamReader(System.in)); 
}

		public void TurninCircuit( String filename) { // data from the file
			 	entries  = new ArrayList<Entry>();
			    Filename = filename;
			    Cmax     = 0; 
			    Cmin     = 0;
			    input    = new BufferedReader(new InputStreamReader(System.in));
		
	}
		
	public void generate() throws IOException { 
		// This method will generate a table, using data input by the user
		
		//To input the frequency from the user
	    System.out.print("Enter a Frequency (in MHz):  "); //Multiplied to get to base
	    double f = Double.valueOf(input.readLine()).doubleValue()*1000000;
	    
	    //To input the increment 
	    System.out.print("Enter an increment(in pF): ");
	    double inc = Double.valueOf(input.readLine()).doubleValue();
	    
	    //To input the minimal capacitance 
	    System.out.print("Enter a minimal capacitance value( in pF): ");
	    double cn = Double.valueOf(input.readLine()).doubleValue();
	    
	    //To input the maximum capacitance
	    System.out.print("Enter a maximum capacitance value(in pF): ");
	    double cx = Double.valueOf(input.readLine()).doubleValue();
	    
	    //Calculate an initial "L" value [using the formula given in class] (and "C" to get "L")
	    double c = Math.sqrt(cn/1000000000000.*cx/1000000000000.);
	    double l = Math.sqrt(Math.pow((2*Math.PI/f), 2)/c);

	    //set the minimum and maximum capacitance
	    setCmin(cn);
	    setCmax(cx);

	    for(double i=cn; i<=cx; i+=inc) {//iterate Cmin form Cmin to Cmax
	       add(l, i, cx) ; //add such entry to database
	}
	}
	    public void add(double l, double cmin, double cmax) {
	        Entry tmp = new Entry();//create tmp empty entry
	        tmp.setL(l);            //  fill the empty entry
	        tmp.calcF(cmin, cmax);  //    - calculate Fmin/Fmax
	        tmp.calcC();            //    - calculate C
	        entries.add(tmp);       //  add filled entry to database
	      }

	      public void modify() throws IOException {
	        int index = -1;

	        //INPUT:
	        //  ask user which entry they want to modify
	        
	        do {
	           System.out.printf("\nEntry to modify[1-%d]: ", this.size());
	           index = Integer.valueOf(this.input.readLine()).intValue() - 1;
	        }
	        
	       while(index > this.size() - 1 || index < 0);

	        String srtMod; //To check the validity of the input

	        //Input:
	        //  get the value of Cmin of entry user wants to modify from the user
	        System.out.printf("New Cmin in pF(empty - no modification): ", index+1);
	        srtMod = this.input.readLine();
	        
	        if(srtMod.matches("\\d+(\\.\\d*)?"))
	          setCmin(Double.valueOf(srtMod).doubleValue());

	        //  get the value of Cmax of entry user wants to modify from the user
	        System.out.printf("New Cmax in pF(empty - no modification): ", index+1);
	        srtMod = this.input.readLine();
	        
	        if(srtMod.matches("\\d+(\\.\\d*)?"))
	          setCmax(Double.valueOf(srtMod).doubleValue());

	        entries.get(index).calcF(getCmin(), getCmax()); //re-calculate Fmin/max of selected entry
	                                                             //  
	        entries.get(index).calcC();

	        this.save(index);//save only the modified entry
	      }

	      public void modifyCmin(int index, double cn) {
	        setCmin(cn);
	        entries.get(index).calcF(getCmin(), getCmax()); //re-calc Fmin/max of
	                                                             //  selected entry
	        entries.get(index).calcC();
	      }

	      public int size() {
	        return entries.size();//return number of entries
	      }

	      public void setFile() throws IOException {
	        System.out.print("Enter the location to save the database: ");
	        Filename = input.readLine();
	      }

	      public String getFilename() {
	        return Filename;
	      }

	      public void setCmin(double Cmin) {
	        Cmin = Cmin/1000000000000.; //conversion to/from base units
	      }

	      public double getCmin() {
	        return Cmin*1000000000000.; //conversion to/from base units
	      }

	      public void setCmax(double cmax) {
	        this.Cmax = cmax/1000000000000.; //conversion to/from base units
	      }

	      public double getCmax() {
	        return this.Cmax*1000000000000.; //conversion to/from base units
	      }

	      public void save(long index) throws IOException {
	        if( Filename.length() == 0)//Set file name if not set
	          setFile();

	        //open to write
	        RandomAccessFile raf = new RandomAccessFile( Filename, "rw");
	        Entry tmp = entries.get((int)index);//Retrieve entry

	        //Write min/max values for capacitance(just in case)
	        raf.writeDouble( Cmin);
	        raf.writeDouble( Cmax);

	        raf.seek(32*index+16);//seek to postion of entry to write

	        raf.writeDouble(tmp.getL());//Write info on entry
	        raf.writeDouble(tmp.getC());
	        raf.writeDouble(tmp.getFmin());
	        raf.writeDouble(tmp.getFmax());

	        raf.close();//close stream
	      }

	      public void save() throws IOException {
	        if( Filename.length() == 0)//Set file name if not set
	          setFile();

	        //open to write
	        RandomAccessFile raf = new RandomAccessFile( Filename, "rw");

	        //Write min/max values for capacitance
	        raf.writeDouble(Cmin);
	        raf.writeDouble( Cmax);

	        for(Entry entry : entries) {    //For each entry in database
	          raf.writeDouble(entry.getL());//  Write info on entry
	          raf.writeDouble(entry.getC());
	          raf.writeDouble(entry.getFmin());
	          raf.writeDouble(entry.getFmax());
	        }
	        raf.close();//close stream
	      }

	      public void load() throws IOException {
	        if( Filename.length() == 0)//Set file name if not set
	          setFile();

	        //open as read only
	        RandomAccessFile raf = new RandomAccessFile(Filename, "r");
	        Entry tmp;

	        //Read min/max values for frequency
	        Cmin = raf.readDouble();
	        Cmax = raf.readDouble();

	        while(raf.length() > raf.getFilePointer()) {//While there is stuff to read
	          tmp = new Entry();                        //  create an empty entry
	          tmp.setL(raf.readDouble());               //  read each value from file
	          tmp.setC(raf.readDouble());
	          tmp.setFmin(raf.readDouble());
	          tmp.setFmax(raf.readDouble());
	          this.entries.add(tmp);                    //add read entry to database
	        }
	        raf.close();//close stream
	      }

	      public void close() throws IOException {
	        this.input.close();
	      }
	      
	      
	      //This methods prints the data in a table format to standard out
	      public void print() {
	        System.out.println("      L      |       C      |    Fmin    |    Fmax");
	        System.out.println("-----------------------------------------------------");
	        for(Entry entry : entries) {
	        	//Print all values whilst formating them
	          System.out.printf("%10.7f H |%10.6f pF |%7.2f MHz |%7.2f MHz\n",
	              entry.getL(),
	              entry.getC(),
	              entry.getFmin(),
	              entry.getFmax());
	        }
	      }
	    }