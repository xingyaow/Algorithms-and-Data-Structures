/**
 * this class Cons implements a Lisp-like Cons cell
 * 
 * @author  Gordon S. Novak Jr.
 * @version 29 Nov 01; 25 Aug 08; 05 Sep 08; 08 Sep 08; 12 Sep 08; 24 Sep 08
 *          02 Oct 09; 12 Feb 10
 */

interface Functor { Object fn(Object x); }

interface Predicate { boolean pred(Object x); }

public class Cons
{
    // instance variables
    private Object car;
    private Cons cdr;
    private Cons(Object first, Cons rest)
       { car = first;
         cdr = rest; }
    public static Cons cons(Object first, Cons rest)
      { return new Cons(first, rest); }
    public static boolean consp (Object x)
       { return ( (x != null) && (x instanceof Cons) ); }
// safe car, returns null if lst is null
    public static Object first(Cons lst) {
        return ( (lst == null) ? null : lst.car  ); }
// safe cdr, returns null if lst is null
    public static Cons rest(Cons lst) {
      return ( (lst == null) ? null : lst.cdr  ); }
    public static Object second (Cons x) { return first(rest(x)); }
    public static Object third (Cons x) { return first(rest(rest(x))); }
    public static void setfirst (Cons x, Object i) { x.car = i; }
    public static void setrest  (Cons x, Cons y) { x.cdr = y; }
   public static Cons list(Object ... elements) {
       Cons list = null;
       for (int i = elements.length-1; i >= 0; i--) {
           list = cons(elements[i], list);
       }
       return list;
   }

/* convert a list to a string for printing */
    public String toString() {
       return ( "(" + toStringb(this) ); }
    public static String toString(Cons lst) {
       return ( "(" + toStringb(lst) ); }
    private static String toStringb(Cons lst) {
       return ( (lst == null) ?  ")"
                : ( first(lst) == null ? "()" : first(lst).toString() )
                  + ((rest(lst) == null) ? ")" 
                     : " " + toStringb(rest(lst)) ) ); }

    public static int square(int x) { return x*x; }

    /* iterative destructive merge using compareTo < */
public static Cons dmerj (Cons x, Cons y) {
  if ( x == null ) return y;
   else if ( y == null ) return x;
   else { Cons front = x;
          if ( ((Comparable) first(x)).compareTo(first(y)) < 0)
             x = rest(x);
            else { front = y;
                   y = rest(y); };
          Cons end = front;
          while ( x != null )
            { if ( y == null ||
                   ((Comparable) first(x)).compareTo(first(y)) < 0)
                 { setrest(end, x);
                   x = rest(x); }
               else { setrest(end, y);
                      y = rest(y); };
              end = rest(end); }
          setrest(end, y);
          return front; } }

public static Cons midpoint (Cons lst) {
  Cons current = lst;
  Cons prev = current;
  while ( lst != null && rest(lst) != null) {
    lst = rest(rest(lst));
    prev = current;
    current = rest(current); };
  return prev; }

    // Destructive merge sort of a linked list, Ascending order.
    // Assumes that each list element implements the Comparable interface.
    // This function will rearrange the order (but not location)
    // of list elements.  Therefore, you must save the result of
    // this function as the pointer to the new head of the list, e.g.
    //    mylist = llmergesort(mylist);
public static Cons llmergesort (Cons lst) {
  if ( lst == null || rest(lst) == null)
     return lst;
   else { Cons mid = midpoint(lst);
          Cons half = rest(mid);
          setrest(mid, null);
          return dmerj( llmergesort(lst),
                        llmergesort(half)); } }


    // ****** your code starts here ******
    // add other functions as you wish.

   public static Cons union (Cons x, Cons y) {
      x = llmergesort(x);
      y = llmergesort(y);
      x = mergeunion(x,y);
      return dmerj(x,y);
   }
  
   //Below code is very similar to slides that were handed out
   public static Cons mergeunion (Cons x, Cons y){
      if ( x == null || y == null ) return null;
      else if ( first(x).equals(first(y)) )
	 return mergeunion(rest(x),rest(y));
      else if ( ((Comparable) first(x)).compareTo(first(y)) < 0)
	 return cons(first(x), mergeunion(rest(x), y));
      else return cons(first(y), mergeunion(x, rest(y)));
   }

   public static Cons setDifference (Cons x, Cons y) {
      x = llmergesort(x);
      y = llmergesort(y);
      return mergediff(x,y);
   }

   // following is a helper function for setDifference
   //Below code is very similar to slides that were handed out
   public static Cons mergediff (Cons x, Cons y) {
      if ( x == null || y == null ) return null;
      else if ( first(x).equals(first(y)) ) 
	 return mergeunion(rest(x),rest(y));
      else if ( ((Comparable) first(x)).compareTo(first(y)) < 0)
	 return cons(first(x), mergeunion(rest(x), y));
      else return cons(first(y), mergeunion(x, rest(y)));
   }

   

   public static Cons bank(Cons accounts, Cons updates) {
      //accounts = llmergesort(accounts);
      updates = llmergesort(updates);
      Cons updatedAccounts = processNewAccounts(accounts,updates);
      updatedAccounts = llmergesort(updatedAccounts);
      return updatedAccounts ;
   }
   
   public static Cons processNewAccounts(Cons accounts, Cons updates){
      
      while(updates != null){
	 Cons tempAccounts = accounts;
	 while(tempAccounts != null){
	    if(((Account)first(tempAccounts)).name().equals(((Account)first(updates)).name())){
	       int newAmount = ((Account)first(tempAccounts)).amount() + ((Account)first(updates)).amount();
	       if(newAmount < 0){
		  System.out.println("Overdraft: " + ((Account)first(tempAccounts)).name() + " " + newAmount);
		  newAmount -= 10;
	       }
	       ((Account)first(tempAccounts)).updateAmount(newAmount);
	       break;
	    }
	    else if (rest(tempAccounts) == null && ((Account)first(updates)).amount() > 0){
	       accounts = cons(first(updates), accounts);
	    }
	    tempAccounts = rest(tempAccounts);
	 }
	 updates = rest(updates);
      }
      return accounts;
	 							       
   }   

   public static String [] mergearr(String [] x, String [] y) {
      String[] newStr = new String[x.length + y.length];
      int i = 0;
      int j = 0;
      int k = 0;
      while(k < newStr.length){
	 if(i >= x.length) i = x.length-1;
	 if(j >= y.length) j = y.length-1;
	    
	 if(x[i].compareTo(y[j]) < 0){
	    newStr[k] = x[i];
	    i++;
	 }
	 else if (x[i].compareTo(y[j]) > 0){
	    newStr[k] = y[j];
	    j++;
	 }
	 else{
	    newStr[k] = y[j];
	    j++;
	    i++;
	 }
	 k++;
      }
      return newStr;
   }
   
   public static boolean markup(Cons text) {
      Cons openingMarks = openingStack(text,null);
      Cons closingMarks = closingStack(text,null);
      Cons firstSetdifference = setDifference(openingMarks,closingMarks);
      Cons secondSetdifference = setDifference(closingMarks, openingMarks);
      if(firstSetdifference == null && secondSetdifference == null) return true;
      else return false;
	 
   }

   public static Cons openingStack(Cons text, Cons stack){
      if(text == null) return stack;
      if(((String)first(text)).contains("<") && ((String)first(text)).contains(">") 
	 && !((String)first(text)).contains("/")){
	 return openingStack(rest(text),cons(((String)first(text)).substring(1,((String)first(text)).length()-1), stack));
      }   
      else return openingStack(rest(text), stack);
   }
   public static Cons closingStack(Cons text, Cons stack){
      if(text == null) return stack;
      if(((String)first(text)).contains("<") &&
	 ((String)first(text)).contains(">") && ((String)first(text)).contains("/")){
	 return closingStack(rest(text),cons(((String)first(text)).substring(2,((String)first(text)).length()-1), stack));
      }
      else return closingStack(rest(text), stack);
   }

	 

    // ****** your code ends here ******

    public static void main( String[] args )
      { 
        Cons set1 = list("d", "b", "c", "a");
        Cons set2 = list("f", "d", "b", "g", "h");
        System.out.println("set1 = " + set1.toString());
        System.out.println("set2 = " + set2.toString());
        System.out.println("union = " + union(set1, set2).toString());

        Cons set3 = list("d", "b", "c", "a");
        Cons set4 = list("f", "d", "b", "g", "h");
        System.out.println("set3 = " + set3.toString());
        System.out.println("set4 = " + set4.toString());
        System.out.println("difference = " +
                           setDifference(set3, set4).toString());

        Cons accounts = list(
               new Account("Arbiter", new Integer(498)),
               new Account("Flintstone", new Integer(102)),
               new Account("Foonly", new Integer(123)),
               new Account("Kenobi", new Integer(373)),
               new Account("Rubble", new Integer(514)),
               new Account("Tirebiter", new Integer(752)),
               new Account("Vader", new Integer(1024)) );

        Cons updates = list(
               new Account("Foonly", new Integer(100)),
               new Account("Flintstone", new Integer(-10)),
               new Account("Arbiter", new Integer(-600)),
               new Account("Garble", new Integer(-100)),
               new Account("Rabble", new Integer(100)),
               new Account("Flintstone", new Integer(-20)),
               new Account("Foonly", new Integer(10)),
               new Account("Tirebiter", new Integer(-200)),
               new Account("Flintstone", new Integer(10)),
               new Account("Flintstone", new Integer(-120))  );
        System.out.println("accounts = " + accounts.toString());
        System.out.println("updates = " + updates.toString());
        Cons newaccounts = bank(accounts, updates);
        System.out.println("result = " + newaccounts.toString());

        String[] arra = {"a", "big", "dog", "hippo"};
        String[] arrb = {"canary", "cat", "fox", "turtle"};
        String[] resarr = mergearr(arra, arrb);
        for ( int i = 0; i < resarr.length; i++ )
            System.out.println(resarr[i]);

        Cons xmla = list( "<TT>", "foo", "</TT>");
        Cons xmlb = list( "<TABLE>", "<TR>", "<TD>", "foo", "</TD>",
                          "<TD>", "bar", "</TD>", "</TR>",
                          "<TR>", "<TD>", "fum", "</TD>", "<TD>",
                          "baz", "</TD>", "</TR>", "</TABLE>" );
        Cons xmlc = list( "<TABLE>", "<TR>", "<TD>", "foo", "</TD>",
                          "<TD>", "bar", "</TD>", "</TR>",
                          "<TR>", "<TD>", "fum", "</TD>", "<TD>",
                          "baz", "</TD>", "</WHAT>", "</TABLE>" );
        Cons xmld = list( "<TABLE>", "<TR>", "<TD>", "foo", "</TD>",
                          "<TD>", "bar", "</TD>", "", "</TR>",
                          "</TABLE>", "</NOW>" );
        Cons xmle = list( "<THIS>", "<CANT>", "<BE>", "foo", "<RIGHT>" );
        Cons xmlf = list( "<CATALOG>",
                          "<CD>",
                          "<TITLE>", "Empire", "Burlesque", "</TITLE>",
                          "<ARTIST>", "Bob", "Dylan", "</ARTIST>",
                          "<COUNTRY>", "USA", "</COUNTRY>",
                          "<COMPANY>", "Columbia", "</COMPANY>",
                          "<PRICE>", "10.90", "</PRICE>",
                          "<YEAR>", "1985", "</YEAR>",
                          "</CD>",
                          "<CD>",
                          "<TITLE>", "Hide", "your", "heart", "</TITLE>",
                          "<ARTIST>", "Bonnie", "Tyler", "</ARTIST>",
                          "<COUNTRY>", "UK", "</COUNTRY>",
                          "<COMPANY>", "CBS", "Records", "</COMPANY>",
                          "<PRICE>", "9.90", "</PRICE>",
                          "<YEAR>", "1988", "</YEAR>",
                          "</CD>", "</CATALOG>");
        System.out.println("xmla = " + xmla.toString());
        System.out.println("result = " + markup(xmla));
        System.out.println("xmlb = " + xmlb.toString());
        System.out.println("result = " + markup(xmlb));
        System.out.println("xmlc = " + xmlc.toString());
        System.out.println("result = " + markup(xmlc));
        System.out.println("xmld = " + xmld.toString());
        System.out.println("result = " + markup(xmld));
        System.out.println("xmle = " + xmle.toString());
        System.out.println("result = " + markup(xmle));
        System.out.println("xmlf = " + xmlf.toString());
        System.out.println("result = " + markup(xmlf));

      }

}
