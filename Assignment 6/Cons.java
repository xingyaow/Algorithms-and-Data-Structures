/*
Author: Zachary Tschirhart
Assignment: 6;
Section: 53395
*/
/**
 * this class Cons implements a Lisp-like Cons cell
 * 
 * @author  Gordon S. Novak Jr.
 * @version 29 Nov 01; 25 Aug 08; 05 Sep 08; 08 Sep 08; 12 Sep 08; 24 Sep 08
 *          06 Oct 08; 07 Oct 08; 09 Oct 08; 27 Mar 09
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
    /* access functions for expression representation */
    public static Object op  (Cons x) { return first(x); }
    public static Object lhs (Cons x) { return first(rest(x)); }
    public static Object rhs (Cons x) { return first(rest(rest(x))); }
    public static boolean numberp (Object x)
       { return ( (x != null) &&
                  (x instanceof Integer || x instanceof Double) ); }
    public static boolean integerp (Object x)
       { return ( (x != null) && (x instanceof Integer ) ); }
    public static boolean floatp (Object x)
       { return ( (x != null) && (x instanceof Double ) ); }
    public static boolean stringp (Object x)
       { return ( (x != null) && (x instanceof String ) ); }

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

// member returns null if requested item not found
public static Cons member (Object item, Cons lst) {
  if ( lst == null )
     return null;
   else if ( item.equals(first(lst)) )
           return lst;
         else return member(item, rest(lst)); }

public static Cons union (Cons x, Cons y) {
  if ( x == null ) return y;
  if ( member(first(x), y) != null )
       return union(rest(x), y);
  else return cons(first(x), union(rest(x), y)); }

    // combine two lists: (append '(a b) '(c d e))  =  (a b c d e)
public static Cons append (Cons x, Cons y) {
  if (x == null)
     return y;
   else return cons(first(x),
                    append(rest(x), y)); }

    // look up key in an association list
    // (assoc 'two '((one 1) (two 2) (three 3)))  =  (two 2)
public static Cons assoc(Object key, Cons lst) {
  if ( lst == null )
     return null;
  else if ( key.equals(first((Cons) first(lst))) )
      return ((Cons) first(lst));
          else return assoc(key, rest(lst)); }

    public static int square(int x) { return x*x; }
    public static int pow (int x, int n) {        // x to the power n
        if ( n <= 0 ) return 1;
        if ( (n & 1) == 0 )
            return square( pow(x, n / 2) );
        else return x * pow(x, n - 1); }

    // you can use these lookup tables with assoc if you wish.
    public static Cons engwords = list(list("+", "sum"),
                                       list("-", "difference"),
                                       list("*", "product"),
                                       list("/", "quotient"),
                                       list("expt", "power"));

    public static Cons opprec = list(list("=", new Integer(1)),
                                     list("+", new Integer(5)),
                                     list("-", new Integer(5)),
                                     list("*", new Integer(6)),
                                     list("/", new Integer(6)) );

    // ****** your code starts here ******

   public static Integer maxbt (Object tree){
      Integer lhs;
      Integer rhs;
      
      if(consp(first((Cons)tree)))
	 lhs = maxbt(first((Cons)tree));
      else
	 return (Integer)first((Cons)tree);
      
      if(consp(rest((Cons)tree)))
	 rhs = maxbt(rest((Cons)tree));
      else
	 return (Integer)first(rest((Cons)tree));
      if(rhs.compareTo(lhs) > 0)
	 return rhs;
      else
	 return lhs;
   }   

   public static Cons vars (Object expr) {
      return varsb(expr, null);
   }
   public static Cons varsb (Object expr, Cons result){
      if(stringp(expr)) return cons(expr, result);
      if(numberp(expr)) return null;
      return union(varsb(lhs((Cons)expr), result), varsb(rhs((Cons)expr), result));
   }  

   public static boolean occurs(Object value, Object tree) {
      if(value.equals(first((Cons)tree))) return true;
      if(consp(first((Cons)tree))) return occurs(value, first((Cons)tree));
      if(consp(rest((Cons)tree))) return occurs(value, rest((Cons)tree));
      return false;
   }

   public static Integer eval (Object tree) {
      Integer lhs;
      Integer rhs;
      if(consp(lhs((Cons)tree))) lhs = eval(lhs((Cons)tree));
      else lhs = (Integer)lhs((Cons)tree);
      if(consp(rhs((Cons)tree))) rhs = eval(rhs((Cons)tree));
      else rhs = (Integer)rhs((Cons)tree);
            
      if(op((Cons)tree).equals("+")) return lhs + rhs;
      if(op((Cons)tree).equals("-")){
	 if (rhs((Cons)tree) == null) return -1*lhs;
	 else return lhs - rhs;
      }
      if(op((Cons)tree).equals("*")) return lhs * rhs;
      if(op((Cons)tree).equals("/")) return lhs / rhs;
      if(op((Cons)tree).equals("expt")) return pow(lhs, rhs);
      return null;
      
   }

   public static Integer eval (Object tree, Cons bindings) {
      Integer lhs;
      Integer rhs;
      if(!consp(tree)) tree = cons(" ", cons(tree, null));
      
      if(consp(lhs((Cons)tree))) lhs = eval(lhs((Cons)tree), bindings);
      else if(stringp(lhs((Cons)tree))) lhs = (Integer)first(rest(assoc(lhs((Cons)tree), bindings)));
      else lhs = (Integer)lhs((Cons)tree);
       
      if(consp(rhs((Cons)tree))) rhs = eval(rhs((Cons)tree), bindings);
      else if(stringp(rhs((Cons)tree))) rhs = (Integer)first(rest(assoc(rhs((Cons)tree), bindings)));
      else if(rhs((Cons)tree) == null && op((Cons)tree).equals(" ")) return lhs;
      else rhs = (Integer)rhs((Cons)tree);
            
      
      if(op((Cons)tree).equals("+")) return lhs + rhs;
      if(op((Cons)tree).equals("-")){
	 if (rhs((Cons)tree) == null) return -1*lhs;
	 else return lhs - rhs;
      }
      if(op((Cons)tree).equals("*")) return lhs * rhs;
      if(op((Cons)tree).equals("/")) return lhs / rhs;
      if(op((Cons)tree).equals("expt")) return pow(lhs, rhs);
      return null;
   }

   public static Cons english (Object tree) {
      return cons(englishb(tree), null);
   }
   
   public static String englishb (Object tree){
      String lhs;
      String rhs;
      
      if(consp(lhs((Cons)tree))) lhs = englishb(lhs((Cons)tree));
      else lhs = lhs((Cons)tree).toString();
       
      if(consp(rhs((Cons)tree))) rhs = englishb(rhs((Cons)tree));
      else if(rhs((Cons)tree) == null && op((Cons)tree).equals("-")){
	 lhs = "-" + lhs;
	 return "the " + first(rest(assoc(op((Cons)tree), engwords))) + " of " + lhs;
      }
      else rhs = rhs((Cons)tree).toString();
            
      
      return "the " + first(rest(assoc(op((Cons)tree), engwords))) + " of " + lhs + " and " + rhs;
   }

   public static String tojava (Object tree) {
      return (tojavab(tree, 0) + ";"); 
   }

   public static String tojavab (Object tree, int prec) {
      String lhs;
      String rhs;
      if(consp(lhs((Cons)tree))) lhs =tojavab(lhs((Cons)tree),(Integer)first(rest(assoc(op((Cons)tree), opprec))));
      else lhs = lhs((Cons)tree).toString();
       
      if(consp(rhs((Cons)tree))) rhs =tojavab(rhs((Cons)tree),(Integer)first(rest(assoc(op((Cons)tree), opprec))));
      else if(rhs((Cons)tree) == null && op((Cons)tree).equals("-"))
	 return "(-" + lhs + ")";
      else if(rhs((Cons)tree) == null)
	 return "Math." + op((Cons)tree) + "(" + lhs + ")";
      else rhs = rhs((Cons)tree).toString();
      
      if(prec > (Integer)first(rest(assoc(op((Cons)tree), opprec))))
	 return "(" + lhs + op((Cons)tree) + rhs + ")";
      else
	 return lhs + op((Cons)tree) + rhs;
   }

    // ****** your code ends here ******

    public static void main( String[] args ) {
        Cons bt1 = cons( cons( cons(new Integer(23), cons(new Integer(77),
                                                          null)),
                               cons(new Integer(-3), cons(new Integer(88),
                                                          null))),
                         cons( cons(new Integer(99), cons(new Integer(7),
                                                          null)),
                               cons(new Integer(15), cons(new Integer(-1),
                                                          null))));
        System.out.println("bt1 = " + bt1.toString());
        System.out.println("maxbt(bt1) = " + maxbt(bt1));

        Cons expr1 = list("=", "f", list("*", "m", "a"));
        System.out.println("expr1 = " + expr1.toString());
        System.out.println("vars(expr1) = " + vars(expr1).toString());

        Cons expr2 = list("=", "f", list("/", list("*", "m",
                                                   list("expt", "v",
                                                        new Integer(2))),
                                         "r"));
        System.out.println("expr2 = " + expr2.toString());
        System.out.println("vars(expr2) = " + vars(expr2).toString());
        System.out.println("occurs(m, expr2) = " + occurs("m", expr2));
        System.out.println("occurs(7, expr2) = " + occurs(new Integer(7), expr2));

        Cons expr3 = list("+", new Integer(3), list("*", new Integer(5),
                                                         new Integer(7)));
        System.out.println("expr3 = " + expr3.toString());
        System.out.println("eval(expr3) = " + eval(expr3));

        Cons expr4 = list("+", list("-", new Integer(3)),
			  list("expt", list("-", new Integer(7),
					    list("/", new Integer(4),
						 new Integer(2))),
			       new Integer(3)));
        System.out.println("expr4 = " + expr4.toString());
        System.out.println("eval(expr4) = " + eval(expr4));

        System.out.println("eval(b) = " + eval("b", list(list("b", 7))));

        Cons expr5 = list("+", new Integer(3), list("*", new Integer(5), "b"));
        System.out.println("expr5 = " + expr5.toString());
        System.out.println("eval(expr5) = " + eval(expr5, list(list("b", 7))));

        Cons expr6 = list("+", list("-", "c"),
                          list("expt", list("-", "b", list("/", "z", "w")),
                                            new Integer(3)));
        Cons alist = list(list("c", 3), list("b", 7), list("z", 4),
                          list("w", 2), list("fred", 5));
        System.out.println("expr6 = " + expr6.toString());
        System.out.println("alist = " + alist.toString());
        System.out.println("eval(expr6) = " + eval(expr6, alist));
        System.out.println("english(expr5) = " + english(expr5).toString());
        System.out.println("english(expr6) = " + english(expr6).toString());
        System.out.println("tojava(expr1) = " + tojava(expr1).toString());
        Cons expr7 = list("=", "x", list("*", list("+", "a", "b"), "c"));
        System.out.println("expr7 = " + expr7.toString());
        System.out.println("tojava(expr7) = " + tojava(expr7).toString());
        Cons expr8 = list("=", "x", list("*", "r", list("sin", "theta")));
        System.out.println("expr8 = " + expr8.toString());
        System.out.println("tojava(expr8) = " + tojava(expr8).toString());


       Cons set3 = list("d", "b", "c", "a");

      }

}
