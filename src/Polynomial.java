import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.text.DecimalFormat;


/** <p>Polynomial represents a polynomial from algebra with integer 
    coefficients.   e.g. 4x<sup>3</sup> + 3x<sup>2</sup> - 5x + 2</p>

    <p>Polynomial extends ArrayList<Integer>, and contains the coefficients
    of the polynomial, such that for Polynomial p, p.get(i) returns
    the coefficient of the x<sup>i<sup> term.    p.get(0) returns
    the constant term.</p>

    <p>For a Polynomial object p that represents a polynomial of degree
    d, (i.e. where x<sup>d</sup> is the highest order term with a
    non-zero coefficient), invoking p.get(k) where k&gt;d should
    result in an IndexOutOfBoundsException; that is, the ArrayList
    should be of size exactly d+1.</p>

    @author P. Conrad
    @author TODO: Kevin Son, Wilson Mui
    @version UCSB, CS56, W17
*/

public class Polynomial extends ArrayList<Integer> {

    /** Assign Polynomial.debug to true or false to turn debugging
     * output on or off.
     */

    public static boolean debug = false;

    /**
       no-arg constructor returns a polynomial of degree 1, with value 0
       
     */
    
    public Polynomial() {
        // invoke superclass constructor, i.e. the
        // constructor of ArrayList<Integer> with
        super(1); // we want capacity at least 1
        // parameter 1 (capacity, not size)
        this.add(0,0); // uses autoboxing (index, value)
        
    }

    /**
       get the degree of the polynomial.  Always >= 1
       @return degree of the polynomial
     */

    public int getDegree() { return this.size() - 1; }

    /**
       Construct polynomial from int array of coefficients.
       The coefficients are listed in order from highest to lowest degree.
       The degree is the size of the array - 1.


       Example: 7x^3 - 2x^2 + 3 would be represented as {7,-2,0,3}

       Example: x^4 - 4 would be represented as {1,0,0,0,-4}

       NOTE that the order of coefficients is not necessarily the way
       they will be stored in the array.   That is, the order of coefficients
       in the array passed in is from highest degree down to lowest
       degree, so for a cubic:<ul>
           <li> <code>coeffs[0]</code> is the x<sup>3</sup> coefficient </li>
           <li> <code>coeffs[1]</code> is the x<sup>2</sup> coefficient </li>
           <li> <code>coeffs[2]</code> is the x coefficient </li>
           <li> <code>coeffs[3]</code> is the constant term </li>
	</ul>

	It is done this way so that when initializing a polynomial
	from an array literal, the order of coefficients mirrors the 
	way polynomials are typically written, from highest order
	term to lowest order term:

	Example: to represent 4x<sup>3</sup> - 7x<sup>2</sup> + 5,<br />
	use: <code>Polynomial p = new Polynomial (new int [] {4,-7,0,5});</code><br />
	NOT: <code>Polynomial p = new Polynomial (new int [] {5,0,-7,4});</code>


       @param coeffsHighToLow array of coefficients in order from largest degree down to smallest.  If array has length zero, return a polynomial of degree zero with constant value zero.
     */
    
    public Polynomial(int [] coeffsHighToLow) {
	
        if (coeffsHighToLow.length==0) {
            coeffsHighToLow = new int [] {0};
        }

        int [] coeffsLowToHigh = Polynomial.lowToHigh(coeffsHighToLow);

        for (int i=0;i<coeffsLowToHigh.length;i++) {
            this.add(i,coeffsLowToHigh[i]);
        }
    }


    /**
       Return string respresentation of Polynomial.
       
       Leading coefficient has negative sign in front, with no space.
       Other signs have a space on either side.    Coefficients that are ones
       should be omitted (except in the x^0 term).    
       Terms with zero coefficients, except in the 
       special case where the polynomial is of degree zero, and the constant
       term is in fact zero--in that case, "0" should be returned.


       Examples:<br /> 
       <code>0<br />
       1<br />
       -4<br />
       2x - 3<br />
       x^2 - 5x + 6<br />
       x^2 - x - 1<br />
       x^2 - x <br />
       -x^7 - 2x^5 + 3x^3 - 4x<br /></code>

       @return string representation of Polynomial
     */

    public String toString() {
    
        int length = this.size();
        int exponent = 0;
        int coeff = 0;
        String result = "";
        
        //Poly stored from low to high. Must print high to low.
        /*
         special cases:
         If string is empty, do not precede with '+'.
         There should be spacing in front of and behind '+' or '-'.
         Don't include '+' on leading coeff.
         Don't include ^1. (Test case earlier to catch it?)
         Check for positive or negative.
         Don;t include coeff 1.
         
         */
        // this has coeff from low to high
        //hopefully this removes preceding and trailing zeroes
        int[] tArray = new int[this.size()];
        for ( int j=0; j < this.size(); j++){
            tArray[j] = this.get(j);
        }
        
        int[] t2Array = highToLow(tArray);
        int[] t3Array = lowToHigh(t2Array);
        int[] t4Array = highToLow(t3Array);
        
        for (int i = 0; i < t4Array.length; i++){
            exponent = t4Array.length-1-i;
            coeff = t4Array[i];
            //deal with start of polynomial
            if(t4Array.length==1 && coeff == 0){
                result += "0";
                break;
            }
            if (exponent == 1 && coeff != 0 && result.isEmpty()){
                if(coeff==1){
                    result += "x";
                }
                else if(coeff==-1){
                    result += "-x";
                }
                else{
                    result += coeff + "x";
                }
            }
            else if(coeff != 0 && result.isEmpty()){
                if(exponent == 0){
                    result += coeff;
                }
                else if(coeff == 1){
                    result += "x^" + exponent;
                }
                else if(coeff == -1){
                    result += "-x^" + exponent;
                }
                else{
                    result += coeff + "x^" + exponent;
                }
            }
            //case where exponent == 0
            else if(exponent == 0 && coeff != 0){
                if (coeff > 0){
                    result += " + ";
                }
                if (coeff < 0){
                    result += " - ";
                }
                if (Math.abs(coeff)==1){
                    result += "1";
                }
                else{
                    result += Math.abs(coeff);
                }
            }
            else if(coeff != 0){
                if (coeff > 0){
                    result += " + ";
                }
                if (coeff < 0){
                    result += " - ";
                }
                
                if (exponent == 1){
                    if(Math.abs(coeff) == 1){
                        result += "x";
                    }
                    else{
                        result += Math.abs(coeff) + "x";
                    }
                }
                else{
                    if(Math.abs(coeff) == 1){
                        result += "x^" + exponent;
                    }
                    else{
                        result += Math.abs(coeff) + "x^" + exponent;
                    }
                }
                
            }
            
        }
        
        
        return result;
    }


    /**
       Construct polynomial from string representation
       that matches the output format of the Polynomial toString method.

       That is, you should be able to do:

       <code>Polynomial p = new Polynomial("0");<br />
       Polynomial p = new Polynomial("1");<br />
       Polynomial p = new Polynomial("-4");<br />
       Polynomial p = new Polynomial("2x - 3");<br />
       Polynomial p = new Polynomial("x^2 - 5x + 6");<br />
       Polynomial p = new Polynomial("x^2 - x - 1");<br />
       Polynomial p = new Polynomial("x^2 - x");<br />
       Polynomial p = new Polynomial("-x^7 - 2x^5 + 3x^3 - 4x");<br /></code>
       
       And for any Polymomial object p, the following test should pass:<br />
       <code>assertEquals(new Polynomial(p.toString()), p);</code><br />

       @param s string representation of Polynomial
     */
    
    public Polynomial(String s) {

	// invoke superclass constructor, i.e. the
	// constructor of ArrayList<Integer> with 

	super(1); // we want capacity at least 1 

	if (debug) {System.out.println("In Polynomial(String s), s=" + s);}

	// For information on regular expressions in Java,
	// see http://docs.oracle.com/javase/tutorial/essential/regex

	// First check for special case of only digits,
	// with possibly a - in front
	// i.e. a degree 0 polynomial that is just an integer constant

	Pattern integerConstantPattern = 
            Pattern.compile("^-?\\d+$");
	Matcher integerConstantMatcher = integerConstantPattern.matcher(s);
	
	// if that pattern matches, then the whole string is just
	// an integer constant.  So we can safely call Integer.parseInt(s)
	// to convert to int, and add in this parameter.

	if (integerConstantMatcher.matches()) {
	    this.add(0,Integer.parseInt(s)); 
	    return; // we are done!
	}

	// now, try for polynomials of degree 1

	Pattern degreeOnePattern = 
            Pattern.compile("^(-?)(\\d*)x( ([+-]) (\\d+))?$");
	// Explanation: 
	// start/end         ^                            $
	// sign for x term    (-?)                            group(1)
	// coeff for x term       (\\d*)                      group(2)
	// x in x term                  x
	// optional constant part        (               )?   group(3)
	// sign for constant                ([+-])            group(4)
	// coeff for constant                      (\\d+)     group(5)

	Matcher degreeOneMatcher = degreeOnePattern.matcher(s);

	if (degreeOneMatcher.matches()) {
	    
	    int xCoeff = 1;
	    int constantTerm = 0;

	    String xCoeffSign = degreeOneMatcher.group(1);
	    String xCoeffString = degreeOneMatcher.group(2);
	    String constantTermSign = degreeOneMatcher.group(4);
	    String constantTermString = degreeOneMatcher.group(5);

	    if (xCoeffString != null && !xCoeffString.equals("")) {
		xCoeff = Integer.parseInt(xCoeffString);
	    }

	    if (xCoeffSign != null && xCoeffSign.equals("-")) {
		xCoeff *= -1;
	    }

	    if (constantTermString != null && !constantTermString.equals("")) {
		constantTerm = Integer.parseInt(constantTermString);
	    }

	    if (constantTermSign != null && constantTermSign.equals("-")) {
		constantTerm *= -1;
	    }

	    this.add(0,constantTerm); 
	    this.add(1,xCoeff); 
	    return;
	}

	// then try for higher degree

	String twoOrMoreRe = 
	    "^" // start of the string 
	    + "([-]?)(\\d*)x\\^(\\d+)" // first x^d term, groups 1,2,3
	    + "(( [+-] \\d*x\\^\\d+)*)" // zero or more x^k terms group 4 (and 5)
	    + "( [+-] \\d*x)?" // optional x term (group 6)
	    + "( [+-] \\d+)?" // optional constant term (group 7)
	    + "$"; // the end of the string

	Pattern degreeTwoOrMorePattern  = Pattern.compile(twoOrMoreRe);
	Matcher degreeTwoOrMoreMatcher = degreeTwoOrMorePattern.matcher(s);

	// if we have a match...
	if (degreeTwoOrMoreMatcher.matches()) {
	    
	    int firstCoeff = 1;
	    String startSign      = degreeTwoOrMoreMatcher.group(1);
	    String coeffString    = degreeTwoOrMoreMatcher.group(2);
	    String degreeString   = degreeTwoOrMoreMatcher.group(3);
	    String middleXtoTheTerms = degreeTwoOrMoreMatcher.group(4);
	    String optionalXTermPart = degreeTwoOrMoreMatcher.group(6);
	    String optionalConstantTermPart = degreeTwoOrMoreMatcher.group(7);

	    if (coeffString != null && !coeffString.equals("")) {
		firstCoeff = Integer.parseInt(coeffString);
	    }

	    if (startSign != null && startSign.equals("-")) {
		firstCoeff *= -1;
	    }
	    
	    int degree = Integer.parseInt(degreeString);

	    this.ensureCapacity(degree+1); // method of ArrayList<Integer>
	    for(int i=0; i<=degree; i++) // initialize all to zero
		this.add(0,0);

	    this.set(degree,firstCoeff);

	    if (middleXtoTheTerms!=null && !middleXtoTheTerms.equals("")) {
		    
		Pattern addlXtoThePowerTermPattern  = 
		    Pattern.compile(" ([+-]) (\\d+)(x\\^)(\\d+)");
		Matcher addlXtoThePowerTermMatcher 
		    = addlXtoThePowerTermPattern.matcher(middleXtoTheTerms);

		while (addlXtoThePowerTermMatcher.find()) {
		    
		    int coeff = 1;
		    String sign           = addlXtoThePowerTermMatcher.group(1);
		    String nextCoeffString    = addlXtoThePowerTermMatcher.group(2);
		    String nextDegreeString   = addlXtoThePowerTermMatcher.group(4);
		    
		    if (nextCoeffString != null && !nextCoeffString.equals("")) {
			coeff = Integer.parseInt(nextCoeffString);
		    }

		    if (sign != null && sign.equals("-")) {
			coeff *= -1;
		    }
		    
		    this.set(Integer.parseInt(nextDegreeString),coeff);
		    
		}
	    } // if middleXToTheTerms has something
	    
	    // Now all that is left is, possibly, an x term and a constant
	    // term.    We need to select them out if they are there.
	    
	    if (optionalXTermPart != null && !optionalXTermPart.equals("")) {

		if (debug) {System.out.println("optionalXTermPart=" +
					       optionalXTermPart);}

		Pattern optXTermPattern = 
		    Pattern.compile("^ ([+-]) (\\d*)x$");
		Matcher optXTermMatcher = optXTermPattern.matcher(optionalXTermPart);
		optXTermMatcher.find();
	    
		int xCoeff = 1;
		int constantTerm = 0;
		String xCoeffSign = optXTermMatcher.group(1);
		String xCoeffString = optXTermMatcher.group(2);
		
		if (xCoeffString != null && !xCoeffString.equals("")) {
		    xCoeff = Integer.parseInt(xCoeffString);
		}
		
		if (xCoeffSign != null && xCoeffSign.equals("-")) {
		    xCoeff *= -1;
		}
		
		this.set(1,xCoeff); 
	    } // optionalXTerm part

	    if (optionalConstantTermPart != null 
		&& !optionalConstantTermPart.equals("")) {

		Pattern constantTermPattern = 
		    Pattern.compile("^ ([+-]) (\\d+)$");
		Matcher constantTermMatcher 
		    = constantTermPattern.matcher(optionalConstantTermPart);
		
		constantTermMatcher.find();

		int constant = 0;
		String sign = constantTermMatcher.group(1);
		String constantString = constantTermMatcher.group(2);

		if (constantString != null && !constantString.equals("")) {
		    constant = Integer.parseInt(constantString);
		}

		if (sign!=null && sign.equals("-")) {
		    constant *= -1;
		}
		
		this.set(0,constant); 
	    } // a constant term is present

	    return;
	} // degreeTwoOrMore
	
	if (debug) {System.out.println("at bottom");}

	// in the end, if we don't find what we need,
	// through an exception

	throw new IllegalArgumentException("Bad Polynomial String: [" + s + "]");

    }


    /** 
	determine whether two polynomials are equal 
	(same degree, same coefficients)
	
	@param o arbitrary object to test for equality
	@return true if objects are equal, otherwise false
    */

    public boolean equals(Object o) {

	// This is boiler plate code for a equals method in Java
	// Always do this first

	if (o == null)
	    return false;
	if (!(o instanceof Polynomial))
	    return false;

	Polynomial p = (Polynomial) o;

	// @@@ TODO: Check the size of each ArrayList.  
	// If they don't match, return false

	if(p.size() != this.size()) {
	    return false;
	}
	
	// @@@ TODO: If the sizes match, check whether the
	// values match.  If not, return False.  Otherwise, return true.

	else {
	    for(int i = 0; i < p.size(); i++) {
		if(p.get(i) != this.get(i)) {
		    return false;
		}
	    }
	    return true;
	}
	
	//return false; // @@@ STUB
	
    }

    /** Given an int [] of coefficients from lowest to highest
	degree (where the index in the array matches the power of the
	x term), find the degree of the polynomial (ignoring trailing terms with a coefficient of zero)
	If all terms are zero, return 0.

	This is a utility method that may be useful in converting
	between the low to high and high to low representations of
	coefficients, both in user programs that use the Polynomial
	class, as well as in internal routines used to implement
	Polynomial methods.
	
	@param coeffsLowToHigh coefficients of a polynomial in order from lowest degree to highest degree.  May have trailing zeros.
	@return the degree of the polynomial as an int, ignoring trailing terms with a coefficient of zero

    */

    public static int degreeCoeffsLowToHigh(int [] coeffsLowToHigh) {
	//return -42; // @@@ STUB!
	int i = coeffsLowToHigh.length - 1; //start at the end of the array to find highest
	while(i >= 0) {
	    if(coeffsLowToHigh[i] != 0) {
		return i;
	    }
	    i--;
	}
	return 0;
    }


    /** Given an int [] of coefficients from highest to lowest
	degree (the formal used for input to the Polynomial constructor),
	find the degree of the polynomial (ignoring leading terms with a coefficient of zero)
	If all terms are zero, return 0.

	This is a utility method that may be useful in converting
	between the low to high and high to low representations of
	coefficients, both in user programs that use the Polynomial
	class, as well as in internal routines used to implement
	Polynomial methods.
	
	@param coeffsHighToLow coefficients of a polynomial in order from highest degree first to lowest degree last.  May have leading zeros.
	@return the degree of the polynomial as an int, ignoring leading terms with a coefficient of zero

    */

   public static int degreeCoeffsHighToLow(int [] coeffsHighToLow) {

	//return -42; // @@@ STUB!
	int i = 0;
	while (i < coeffsHighToLow.length) {
	    if(coeffsHighToLow[i] != 0) { //finds the first nonzero coefficient in array
            return coeffsHighToLow.length-i-1;
	    }
	    i++;
	}
	return 0;
    }


    /** Convert a list of coefficients in order from 
	highest degree to lowest degree (the order used
	for input to the Polynomial constructor) to one where
	the order is lowest degree to highest degree (where index matches
	power of the x term).
	
	@param coeffsHighToLow coefficients of a polynomial in order from highest degree to lowest degree.   May have leading zeros.
	@return An int [] with coefficients in order from lowest degree to highest degree.   No trailing zeros.
    */

    public static int [] lowToHigh(int [] coeffsHighToLow) {	
	//int [] stub = new int [] {-42, -42, -42};
	//return stub;
	//reversing the array makes the coefficients go from high to low to low to high
    //idea for removing preceding zeroes from stackoverflow
        int gap = 0;
        int[] newArray;
        
        for ( int k : coeffsHighToLow){
            if (k!=0){
                break;
            }
            gap++;
        }
        
        //if coeffsHighToLow is all zeros, return array {0}
        if( gap == coeffsHighToLow.length ){
            newArray = new int[] {0};
        }
        else{
            
            //create new array without leading 0's
            newArray = new int[coeffsHighToLow.length - gap];
            for(int m = 0; m < newArray.length; m++){
                newArray[m] = coeffsHighToLow[m + gap];
            }
            
            
            int length = newArray.length;
            for(int i = 0; i < length/2; i++) {
                int store = newArray[i];
                newArray[i] = newArray[length-i-1];
                newArray[length-i-1] = store;
            }
            
        }
        return newArray;
    }


    /** Convert a list of coefficients in order from 
	lowest degree to highest degree (where index matches
	power of the x term) to one in order from 
	highest degree to lowest degree (the order used
	for input to the Polynomial constructor).
	
	@param coeffsLowToHigh coefficients of a polynomial in order from lowest degree to highest degree.  May have trailing zeros.
	@return An int [] with coefficients in order from highest degree to lowest degree.   No leading zeros.
    */

    public static int [] highToLow(int [] coeffsLowToHigh) {
	//int [] stub = new int [] {-42, -42, -42};
	//return stub;
	//reversing the array makes low to high to high to low
        int[] newArray;
        int gap = 0;
        for (int k = (coeffsLowToHigh.length-1); k >= 0; k--){
            if (coeffsLowToHigh[k] != 0){
                break;
            }
            gap++;
        }
        
        if (gap == coeffsLowToHigh.length){
            newArray = new int[] {0};
        }
        else{
            
            newArray = new int[ coeffsLowToHigh.length - gap];
            
            for( int m = 0; m < newArray.length; m++){
                newArray[m] = coeffsLowToHigh[m];
            }
            
            int length = newArray.length;
            for(int i = 0; i < length/2; i++) {
                int store = newArray[i];
                newArray[i] = newArray[length-i-1];
                newArray[length-i-1] = store;
            }
        }
        return newArray;
    }
    
    /** return a new Polynomial which has as its value the 
	this polynomial plus the one passed in as a parameter.

	@param p the Polynomial to add to this one
	@return sum of this Polynomial and p

    */

    public Polynomial plus (Polynomial p) {	
	//Polynomial stub = new Polynomial (new int [] {-42});
	//return stub;

    
    /*
	//comparing the lengths of both polynomials
	int length1 = this.getDegree() + 1;
	int length2 = p.getDegree() + 1;

        int length = 0;
	//choosing the greatest length to account the highest degrees
	if(length1 < length2) {
	    length = length2; //making the same length for both polynomials
	    /*int[] poly1 = new int[length]; 
	    for(int i = 0; i < length1; i++) {
		poly1[i] = this.get(i);
	    } //adding trailing 0's to match the other polynomial's degree
	    for(int j = length1; j < length; j++) {
		poly1[j] = 0;
		} */
	/*
    }
    

	else {
	    length = length1;
	}

	int[] poly = new int[length];
	if(length == length1) {
	    for(int i = 0; i < length2; i++) {
		poly[i] = p.get(i);
	    }
	    for(int j = length2; j < length; j++) {
		poly[j] = 0;
	    }
	}
	else {
	    for(int k = 0; k < length2; k++) {
		poly[k] = this.get(k);
	    }
	    for(int l = length1; l < length; l++) {
		poly[l] = 0;
	    }
	}
	
	/*
	int[] poly1 = new int[this.size()];
	int[] poly2 = new int[p.size()];

	int[] poly3 = highToLow(poly1);
	int[] poly4 = lowToHigh(poly3);
	int[] final_poly1 = highToLow(poly4);

	int[] poly5 = highToLow(poly2);
	int[] poly6 = lowToHigh(poly5);
	int[] final_poly2 = highToLow(poly6);
	*/
	//new polynomial for all the sums with the appropriate length
	/*
    Polynomial sum = new Polynomial (new int [length]);
	for(int m = 0; m < length; m++) {
        sum.add(m, (this.get(m)+p.get(m)));
        
    }

	return sum;
	*/
    
        int length1 = this.size();
        int length2 = p.size();
        int length = 0;
        
        int[] temp = new int[0];
        int[] sum = new int[0];
        Polynomial Psum;
        int gap = 0;
        
        //make length = largest poly
        //make temp into a Poly similar to shorter poly, but with trailing 0's
        if(length2>length1){
            
            length = length2;
            gap = length2 - length1;
            
            temp = new int[length];
            sum = new int[length];
            
            for( int i = 0; i < length1; i++ ){
                temp[i] = this.get(i);
            }
            for (int k = 0; k < gap; k++){
                temp[length1+k] = 0;
            }
            
            for ( int j = 0; j<length; j++){
                sum[j] = p.get(j) + temp[j];
            }
            
        }
        else if(length2<length1){
            length = length1;
            gap = length1-length2;
            
            temp = new int[length];
            sum = new int[length];
            
            for( int i = 0; i < length2; i++ ){
                temp[i] = p.get(i);
            }
            for (int k = 0; k < gap; k++){
                temp[length2+k] = 0;
            }
            
            for ( int j = 0; j<length; j++){
                sum[j] = this.get(j) + temp[j];
            }
        }
        else if(length1==length2){
            length = length1;
            
            sum = new int[length];
            
            for ( int j = 0; j<length; j++){
                sum[j] = this.get(j) + p.get(j);
            }
        }
        
        int[] tSum = highToLow(sum);
        
        Psum = new Polynomial(tSum);
        
        return Psum;
    
	    
    }

    /** return a new Polynomial which has as its value the 
	this polynomial times the one passed in as a parameter.

	@param p the Polynomial to multiply this one by
	@return product of this Polynomial and p

    */

    public Polynomial times (Polynomial p) {
	
	//Polynomial stub = new Polynomial (new int [] {-42});
	//return stub; // @@@ TODO: FIXME!
/*
	int length = this.getDegree() + p.getDegree() + 1;
	
	Polynomial product = new Polynomial (new int [length]);
	for (int i = 0; i < this.getDegree() + 1; i++) {
	    for (int j = 0; j < p.getDegree() + 1; j++) {
            //product[i + j] += this.get(i) * p.get(j);
            product.add(i+j, this.get(i)*p.get(j));
	    }
	}

	return product;
*/
        int tsize = this.size();
        int psize = p.size();
        int[] prod = new int[tsize+psize-1];
        for (int i=0; i<prod.length; i++){
            prod[i] = 0;
        }
        for (int k=0; k<tsize; k++){
            for(int j=0; j<psize; j++){
                prod[k+j] += this.get(k) * p.get(j);
            }
        }
        
        int[] tprod = highToLow(prod);
        return new Polynomial(tprod);
        
        
    }

    /** return a new Polynomial which has as its value the 
	this polynomial minus the one passed in as a parameter.

	@param p the Polynomial to subtract from this one
	@return the result of this Polynomial minus p

    */


    public Polynomial minus (Polynomial p) {

	//Polynomial stub = new Polynomial (new int [] {-42});
	//return stub; // @@@ TODO: FIXME!
        /*
	Polynomial negative = new Polynomial (new int [] {-1});
	Polynomial neg = new Polynomial();
	Polynomial diff = new Polynomial();
	neg = p.times(negative);
	diff = this.plus(p);
	return diff;
         */
        
        Polynomial negative = new Polynomial(new int[] {-1});
        Polynomial negP = p.times(negative);
        return this.plus(negP);
	
    }

    /** Print Usage message for Polynomial main 
     */

    public static void usage() {
	System.err.println("Usage: java Polynomial 'string' ");
	System.err.println(" java Polynomial 'string' ");
    }

    /** 
	Main for testing constructing Polynomials from strings,
	and for testing plus, minus and times.

	At Unix command line, use single quotes to make the entire
	Polynomial be a single argument, and use 
	\* when operating is * (to avoid
	having * expanded as a wildcard.)  For example:
	<code>java -cp build Polynomial 'x^2 + 2x + 3' \* 'x - 4'</code>
     */

    public static void main (String [] args) {

	if (args.length < 1) {
	    Polynomial.usage();
	    System.exit(1); // error code 1
	}

	Polynomial p = new Polynomial(args[0]);

	if (args.length == 1) {
	    System.out.println("p=" + p);
	    System.exit(0); // successful completion
	}

	if (args.length != 3) {
	    System.out.println("There should be either 1 cmd line argument or 3 arguments, but there were: " + args.length + " arguments.");
	    Polynomial.usage();
	    System.exit(1);
	}
	
	Polynomial p2 = new Polynomial(args[2]);
	if (args[1].equals("+")) {
	    Polynomial result = p.plus(p2);
	    System.out.println("(" + p.toString() + ") + (" +
			       p2.toString() + ") = " + result.toString());
	}  else if (args[1].equals("-")) {
	    Polynomial result = p.minus(p2);
	    System.out.println("(" + p.toString() + ") - (" +
			       p2.toString() + ") = " + result.toString());
	} else if (args[1].equals("*")) {
	    Polynomial result = p.times(p2);
	    System.out.println("(" + p.toString() + ") * (" +
			       p2.toString() + ") = " + result.toString());
	} else {
	    System.out.println("Error: illegal operand:" + args[1]);
	    Polynomial.usage();
	    System.exit(2); // error code 2
	}
    } // end main


} // end class Polynomial
