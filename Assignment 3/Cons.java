/**
 * this class Cons implements a Lisp-like Cons cell
 * 
 * @author  Gordon S. Novak Jr.
 * @version 29 Nov 01; 25 Aug 08; 05 Sep 08; 08 Sep 08; 12 Sep 08; 16 Feb 09
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

   // ****** your code starts here ******
   public static double square(double x) { return x*x; }
   public static double pow(double x, double y){
      if(x == 0) return 0;
      if(y == 0) return 1;
      for(int i = 1; i < y; i++){
	 x*=x;
      }
      return x;
   }
   public static double factorial(double x){
      double i = x;
      while(i > 1){
	 i--;
	 x *= i;
      }
      return x;
   }
   public static int sum(Cons lst){
      return sumb(lst, 0);
   }

   public static int sumb(Cons lst, int total){
      return lst == null ? total : sumb(rest(lst), total + (Integer) first(lst));
   }
   /* mean = (sum x[i]) / n   */
   public static double mean (Cons lst) {
      return meanb(lst, 0, 0);
   }
   public static double meanb (Cons lst, int sum, int n){
      return lst == null ? ((double)sum)/((double)n) : meanb(rest(lst), sum + (Integer) first(lst), ++n);
   } 
    

     /* square of the mean = mean(lst)^2   */

     /* mean square = (sum x[i]^2) / n   */
   public static double meansq (Cons lst) {
      return meansqb(lst, 0, 0);
   }
   public static double meansqb (Cons lst, int sumsq, int n){
      return lst == null ? ((double)sumsq)/((double)n) : meansqb(rest(lst), sumsq + square((Integer) first(lst)), ++n);
   }

   public static double variance (Cons lst) {
      return meansq(lst) - square(mean(lst));
   }

   public static double stddev (Cons lst) {
      return Math.sqrt(variance(lst)); 
   }

   public static double sine (double x) {
      int highestPower = 21;
      int startingSign = 1;
      return sineb(x, highestPower, 1, startingSign, 0);
   }
   
   public static double sineb (double x, int maxPower, double currentPower, int sign, double total){
      if(currentPower > maxPower){
	 return total;
      }
      else{
	 if(sign == 1) sign = 0;
	 else sign = 1;
	 return sineb(x, maxPower, currentPower + 2, sign, total + ((pow(-1,sign)*pow(x, currentPower))/factorial(currentPower)));
      } 
   }

   public static Cons nthcdr (int n, Cons lst) {
      if(lst == null) return null;
      if(n == 0){
	 return rest(lst);
      }
      else{
	 return nthcdr (--n, rest(lst));
      }
   }

   public static Object elt (Cons lst, int n) {
      if(lst == null) return null;
      if(n == 0){
	 return first(lst);
      }
      else{
	 return elt(rest(lst), --n);
      }
   }

   public static double interpolate (Cons lst, double x) {
      int i = (int)x;
      double delta = x - i;
      int y = (Integer)elt(lst, i);
      int z = (Integer)elt(lst, i+1);
      return y + delta*(z-y);
   }
   public static Cons binomial(int n) {
      Cons lst = list(Integer.valueOf(1));
      return binomialb(lst, n);
   }
    
   public static Cons binomialb(Cons lst, int n){
      Cons lstb = list(Integer.valueOf(1));
      return n == 0 ? lst : binomialb(cons(Integer.valueOf(1),binomialc(lst, lstb)) , n-1);
   }
    
   public static Cons binomialc(Cons lst, Cons lstb){
      return rest(lst) == null ? lstb : binomialc(rest(lst), cons((Integer)first(lst) + (Integer)first(rest(lst)), lstb));
   }
   

   public static int sumtr (Cons lst) {
      return sumtrb(lst, 0);
   }   
   public static int sumtrb (Cons lst, int total){
      if(lst == null) return total;
      else if(!consp(first(lst))) return sumtrb(rest(lst), total + (Integer)first(lst));
      else return sumtrb(rest(lst), 0) + sumtrb((Cons)first(lst), total);
   }

//     /* use auxiliary functions as you wish. */
   public static Cons reverse (Cons lst){
      return reverseb (lst, null);
   }
   public static Cons reverseb (Cons lst, Cons lstb){
      return lst == null ? lstb : reverseb(rest(lst), cons(first(lst), lstb));
   }
   
   public static Cons subseq (Cons lst, int start, int end) {
      return reverse(subseqb(lst, start, end, 0, null));
   }
   public static Cons subseqb (Cons lst, int start, int end, int cursor, Cons nlst){
      if(lst == null) return nlst;
      if(cursor == end){
	 return cons(first(lst), nlst);
      }
      if(cursor >= start){
	 nlst = cons(first(lst), nlst);
	 return subseqb(rest(lst), start, end, ++cursor, nlst);
      }
      else{
	 return subseqb(rest(lst), start, end, ++cursor, nlst);
      }
   }
   public static Cons posfilter (Cons lst){
      return reverse(posfilterb(lst, null));
      }
   public static Cons posfilterb (Cons lst, Cons lstb) {
      if(lst == null) return lstb;
      if((Integer)first(lst) >= 0){
	 lstb = cons(first(lst), lstb);
	 return posfilterb(rest(lst), lstb);
      }
      else return posfilterb(rest(lst), lstb);
   }

   public static Cons subset (Predicate p, Cons lst) {
      return reverse(subsetb(p,lst,null));
   }
   public static Cons subsetb (Predicate p, Cons lst, Cons lstb){
      if(lst == null) return lstb;
      if(p.pred(first(lst))){
	 lstb = cons(first(lst), lstb);
	 return subsetb(p, rest(lst), lstb);
      }
      else return subsetb(p, rest(lst), lstb);
   }

   public static Cons mapcar (Functor f, Cons lst) {
      return reverse(mapcarb(f,lst,null));
   }
   public static Cons mapcarb (Functor f, Cons lst, Cons lstb){
      if(lst == null) return lstb;
      else{
	 return mapcarb(f,rest(lst), cons(f.fn((Integer)first(lst)), lstb));
      }
   }

   public static Object some (Predicate p, Cons lst) {
      if(lst == null) return null;
      if(p.pred((Integer)first(lst))) return first(lst);
      else return some(p,rest(lst));
   }

   public static boolean every (Predicate p, Cons lst) {
      if(lst == null) return true;
      if(!p.pred((Integer)first(lst))) return false;
      else return every(p,rest(lst));
   }

    // ****** your code ends here ******

    public static void main( String[] args )
      { 
        Cons mylist =
            list(new Integer(95), new Integer(72), new Integer(86),
                 new Integer(70), new Integer(97), new Integer(72),
                 new Integer(52), new Integer(88), new Integer(77),
                 new Integer(94), new Integer(91), new Integer(79),
                 new Integer(61), new Integer(77), new Integer(99),
                 new Integer(70), new Integer(91) );
        System.out.println("mylist = " + mylist.toString());
        System.out.println("sum = " + sum(mylist));
        System.out.println("mean = " + mean(mylist));
        System.out.println("meansq = " + meansq(mylist));
        System.out.println("variance = " + variance(mylist));
        System.out.println("stddev = " + stddev(mylist));
	System.out.println("factorial of 10 = " + factorial(10));
        System.out.println("sine(0.5) = " + sine(0.5));  // 0.47942553860420301
        System.out.print("nthcdr 5 = ");
        System.out.println(nthcdr(5, mylist));
        System.out.print("nthcdr 18 = ");
        System.out.println(nthcdr(18, mylist));
        System.out.println("elt 5 = " + elt(mylist,5));

        Cons mylistb = list(new Integer(0), new Integer(30), new Integer(56),
                            new Integer(78), new Integer(96));
        System.out.println("mylistb = " + mylistb.toString());
        System.out.println("interpolate(3.4) = " + interpolate(mylistb, 3.4));
        Cons binom = binomial(12);
        System.out.println("binom = " + binom.toString());
        System.out.println("interpolate(3.4) = " + interpolate(binom, 3.4));

        Cons mylistc = list(new Integer(1),
                            list(new Integer(2), new Integer(3)),
                            list(list(new Integer(4), new Integer(5)),
                                 new Integer(6)));
        System.out.println("mylistc = " + mylistc.toString());
        System.out.println("sumtr = " + sumtr(mylistc));

        Cons mylistd = list(new Integer(0), new Integer(1), new Integer(2),
                             new Integer(3), new Integer(4), new Integer(5),
                             new Integer(6) );
        System.out.println("mylistd = " + mylistd.toString());
        System.out.println("subseq(2 5) = " + subseq(mylistd, 2, 5));

        Cons myliste = list(new Integer(3), new Integer(17), new Integer(-2),
                            new Integer(0), new Integer(-3), new Integer(4),
                            new Integer(-5), new Integer(12) );
        System.out.println("myliste = " + myliste.toString());
        System.out.println("posfilter = " + posfilter(myliste));

        final Predicate myp = new Predicate()
            { public boolean pred (Object x)
                { return ( (Integer) x > 3); }};

        System.out.println("subset = " + subset(myp, myliste).toString());

        final Functor myf = new Functor()
             { public Integer fn (Object x)
                 { return new Integer( (Integer) x + 2); }};

        System.out.println("mapcar = " + mapcar(myf, mylistd).toString());

        System.out.println("some = " + some(myp, myliste).toString());

        System.out.println("every = " + every(myp, myliste));

      }

}
