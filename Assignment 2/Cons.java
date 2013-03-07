/*
 * Author: Zachary Tschirhart
 * Class: CS 315 (53395)
 * 
 * 
 * Answers to Questions:
 * 
 * 2. Can you think of an invariant (property that is always true) of peanoplus? What is the Big O of peanoplus?
 *       
 *       This function will always be called y times. The Big O is O(n) (only for the y number).
 *       
 * 3. What is the Big O of peanotimes? 
 *       
 *       The Big O for peanotimes is O(n) (only for the y number).
 *       
 * 8. Use the function (choose n k) that you wrote earlier to calculate (choose 4 k) for k from 0 through 4; 
 *    what is the relationship between these values and the binomial coefficients? 
 *    
 *       The (choose n k) seems to calculate pascal's triangle with the n being the row that it's calculating, 
 *       and the k being what column you would like to display.
 */
/**
 * this class Cons implements a Lisp-like Cons cell
 * 
 * @author  Gordon S. Novak Jr.
 * @version 29 Nov 01; 25 Aug 08; 05 Sep 08; 08 Sep 08; 02 Sep 09; 27 Jan 10
 *          05 Feb 10; 16 Jul 10; 02 Sep 10
 */

public class Cons
{
    // instance variables
    private Object car;
    private Cons cdr;
    private Cons(Object first, Cons rest)
       { car = first;
         cdr = rest; }

    // make a new Cons and put the arguments into it
    // add one new thing to the front of an existing list
    // cons("a", list("b", "c"))  =  (a b c)
    public static Cons cons(Object first, Cons rest)
      { return new Cons(first, rest); }

    // test whether argument is a Cons
    public static boolean consp (Object x)
       { return ( (x != null) && (x instanceof Cons) ); }

    // first thing in a list:    first(list("a", "b", "c")) = "a"
    // safe, returns null if lst is null
    public static Object first(Cons lst) {
        return ( (lst == null) ? null : lst.car  ); }

    // rest of a list after the first thing:
    //    rest(list("a", "b", "c")) = (b c)
    // safe, returns null if lst is null
    public static Cons rest(Cons lst) {
      return ( (lst == null) ? null : lst.cdr  ); }

    // second thing in a list:    second(list("a", "b", "c")) = "b"
    public static Object second (Cons x) { return first(rest(x)); }

    // third thing in a list:    third(list("a", "b", "c")) = "c"
    public static Object third (Cons x) { return first(rest(rest(x))); }

    // destructively replace the first
    public static void setfirst (Cons x, Object i) { x.car = i; }

    // destructively replace the rest
    public static void setrest  (Cons x, Cons y) { x.cdr = y; }

    // make a list of things:   list("a", "b", "c") = (a b c)
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

    /* Sum of squares of integers from 1..n */
    public static int sumsq(int n) {
       return n == 1 ? 1 : n*n + sumsq(n-1);
    }

    /* Addition using Peano arithmetic */
    public static int peanoplus(int x, int y) {
       return y == 0 ? x : peanoplus(x+1,y-1);
    }

    /* Multiplication using Peano arithmetic */
    public static int peanotimes(int x, int y) {
       return y <= 1 ? (y == 0 || x == 0 ? 0 : x) : peanoplus(peanotimes(x,y-1),x);
    }

    /* n choose k: distinct subsets of k items chosen from n items */
    public static int choose(int n, int k) { 
       return (int)chooseb(n, k, 1, 1);
    }
    /* Aux tail recursive choose function */
    public static long chooseb(int n, int k, long numerator, long denomonator){
       return k == 0 ? numerator/denomonator : chooseb(n-1, k-1, (long)n * numerator, (long)k * denomonator);
    }

/* Add up a list of Integer */
/* iterative version, using while */
public static int sumlist (Cons lst) {
  int sum = 0;
   while ( lst != null ) {
      sum += (Integer) first(lst);   // cast since first() can be Object
    lst = rest(lst); }
  return sum; }

/* second iterative version, using for */
public static int sumlistb (Cons lst) {
  int sum = 0;
  for ( ; lst != null; lst = rest(lst) )
    sum += (Integer) first(lst);   // cast since first() can be Object
  return sum; }

/* recursive version */
public static int sumlistr (Cons lst) {
   return lst == null ? 0 : (Integer) first(lst) + sumlistr(rest(lst));
}

/* tail recursive version */
public static int sumlisttr (Cons lst) {
   return sumlisttrb(lst, 0);
}

public static int sumlisttrb (Cons lst, int sum){
   return lst == null ? sum : sumlisttrb(rest(lst), sum + (Integer) first(lst));
}

/* Sum of squared differences of elements of two lists */
/* iterative version */
public static int sumsqdiff (Cons lst, Cons lstb) {
   int sum = 0;
   for(; lst != null; lst = rest(lst), lstb = rest(lstb)){
      sum += square((Integer)first(lst) - (Integer)first(lstb));
   }
   return sum;
}

/* recursive version */
public static int sumsqdiffr (Cons lst, Cons lstb) {
   return lst == null ? 0 : square((Integer)first(lst) - (Integer)first(lstb)) + sumsqdiffr(rest(lst), rest(lstb)); 
}

/* tail recursive version */
public static int sumsqdifftr (Cons lst, Cons lstb) {
   return sumsqdifftrb(lst, lstb, 0);
}

public static int sumsqdifftrb (Cons lst, Cons lstb, int sum){
   return lst == null ? sum : sumsqdifftrb(rest(lst), rest(lstb), sum + square((Integer)first(lst) - (Integer)first(lstb)));
}

/* Find the maximum value in a list of Integer */
/* iterative version */
public static int maxlist (Cons lst) {
   int max = (Integer)first(lst);
   for(; lst != null; lst = rest(lst)){
      max = ((Integer)first(lst) > max ? (Integer)first(lst) : max);
   }
   return max;
}

/* recursive version */
public static int maxlistr (Cons lst) {
   int max = 0;
   return rest(lst) == null ? (Integer)first(lst) : ((max = maxlistr(rest(lst))) > (Integer) first(lst) ? max : (Integer) first(lst));
}

/* tail recursive version */
public static int maxlisttr (Cons lst) {
   return maxlisttrb(lst, (Integer) first(lst));
}

public static int maxlisttrb (Cons lst, int max){
   return lst == null ? max : maxlisttrb(rest(lst), max > (Integer)first(lst) ? max : (Integer)first(lst));
}

    /* Make a list of Binomial coefficients */
    /* binomial(2) = (1 2 1) */
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


    // ****** your code ends here ******


    public static void main( String[] args )
      { 
        System.out.println("sumsq(5) = " + sumsq(5));

        System.out.println("peanoplus(3, 5) = " + peanoplus(3, 5));
        System.out.println("peanotimes(3, 5) = " + peanotimes(3, 5));
        System.out.println("peanotimes(30, 30) = " + peanotimes(30, 30));
        System.out.println("peanotimes(0,0) = " + peanotimes(0,0));
        System.out.println("peanotimes(5,0) = " + peanotimes(5,0));
        System.out.println("peanotimes(0,5) = " + peanotimes(0,5));
        
        System.out.println("choose 5 3 = " + choose(5, 3));
        System.out.println("choose 100 3 = " + choose(100, 3));
        System.out.println("choose 20 10 = " + choose(20, 10));
        System.out.println("choose 100 5 = " + choose(100, 5));
        for (int i = 0; i <= 4; i++)
          System.out.println("choose 4 " + i + " = " + choose(4, i));

        Cons mylist = list(Integer.valueOf(3), Integer.valueOf(4),
                           Integer.valueOf(8), Integer.valueOf(2));
        Cons mylistb = list(Integer.valueOf(2), Integer.valueOf(1),
                           Integer.valueOf(6), Integer.valueOf(5));

        System.out.println("mylist = " + mylist);

        System.out.println("sumlist = " + sumlist(mylist));
        System.out.println("sumlistb = " + sumlistb(mylist));
        System.out.println("sumlistr = " + sumlistr(mylist));
        System.out.println("sumlisttr = " + sumlisttr(mylist));

        System.out.println("mylistb = " + mylistb);

        System.out.println("sumsqdiff = " + sumsqdiff(mylist, mylistb));
        System.out.println("sumsqdiffr = " + sumsqdiffr(mylist, mylistb));

        System.out.println("sumsqdifftr = " + sumsqdifftr(mylist, mylistb));

        System.out.println("maxlist " + mylist + " = " + maxlist(mylist));
        System.out.println("maxlistr = " + maxlistr(mylist));
        System.out.println("maxlisttr = " + maxlisttr(mylist));

        System.out.println("binomial(0) = " + binomial(0));
        System.out.println("binomial(1) = " + binomial(1));
        System.out.println("binomial(2) = " + binomial(2));
        System.out.println("binomial(3) = " + binomial(3));
        System.out.println("binomial(4) = " + binomial(4));
        System.out.println("binomial(20) = " + binomial(20));
      }

}
