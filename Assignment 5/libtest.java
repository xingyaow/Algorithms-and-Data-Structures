// libtest.java      GSN    03 Oct 08
// 
/*
Author: Zachary Tschirhart
Assignment: 5;
Section: 53395
*/

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

interface Functor { Object fn(Object x); }

interface Predicate { boolean pred(Object x); }

public class libtest {

    // ****** your code starts here ******


public static Integer sumlist(LinkedList<Integer> lst) {
   int total = 0;
   for(Integer item : lst){
      total += item;
   }
   return total;
}

public static Integer sumarrlist(ArrayList<Integer> lst) {
   int total = 0;
   for(Integer item : lst){
      total += item;
   }
   return total;
}

public static LinkedList<Object> subset (Predicate p,
                                          LinkedList<Object> lst) {
   LinkedList<Object> lstb = new LinkedList<Object>();
   for(Object item : lst){
      if(p.pred(item)){
	 lstb.add(item);
      }
   }
   return lstb;
}

public static LinkedList<Object> dsubset (Predicate p,
                                           LinkedList<Object> lst) {
   for(Iterator<Object> it = lst.iterator(); it.hasNext();){
      if(p.pred(it.next())){
	 it.remove();
      }
   }
   return lst;
}

public static Object some (Predicate p, LinkedList<Object> lst) {
   LinkedList<Object> lstb = new LinkedList<Object>();
   for(Object item : lst){
      if(p.pred(item)){
	 return item;
      }
   }
   return null;
}

public static LinkedList<Object> mapcar (Functor f, LinkedList<Object> lst) {
   LinkedList<Object> lstb = new LinkedList<Object>();
   for(Object item : lst){
      lstb.add(f.fn(item));
   }
   return lstb;
}

public static LinkedList<Object> merge (LinkedList<Object> lsta,
                                        LinkedList<Object> lstb) {
   LinkedList<Object> lstc = new LinkedList<Object>();
   ListIterator<Object> ita = lsta.listIterator();
   ListIterator<Object> itb = lstb.listIterator();
   while(ita.hasNext() || itb.hasNext()){
      if(ita.hasNext() && !itb.hasNext())
	 lstc.add(ita.next());
      else if(!ita.hasNext() && itb.hasNext())
	 lstc.add(itb.next());
      else{
	 Object itema = ita.next();
	 Object itemb = itb.next();
	 if(((Comparable)itema).compareTo((Comparable)itemb) > 0){
	    lstc.add(itemb);
	    ita.previous();
	 }
	 else if(((Comparable)itema).compareTo((Comparable)itemb) < 0){
	    lstc.add(itema);
	    itb.previous();
	 }
	 else{
	    lstc.add(itema);
	    itb.previous();
	 }
      }
   }
      return lstc;
}


public static LinkedList<Object> sort (LinkedList<Object> lst) {
   int size = lst.size();
   if(size <= 1) return lst;
   else{
      LinkedList<Object> lsta = new LinkedList<Object>();
      LinkedList<Object> lstb = new LinkedList<Object>();
      splitlist(lst,lsta,lstb);
      return merge(sort(lsta),sort(lstb));
   }
}


public static void splitlist( LinkedList<Object> lst, 
			      LinkedList<Object> lsta, LinkedList<Object> lstb){
   int half = lst.size()/2;
   int index = 0;
   for(Object item: lst){
      if(index < half) lsta.add(item);
      else lstb.add(item);
      index++;
   }
}

public static LinkedList<Object> intersection (LinkedList<Object> lsta,
                                               LinkedList<Object> lstb) {
   lsta = sort(lsta);
   lstb = sort(lstb);
   
   LinkedList<Object> lstc = new LinkedList<Object>();
   ListIterator<Object> ita = lsta.listIterator();
   ListIterator<Object> itb = lstb.listIterator();
   while(ita.hasNext() || itb.hasNext()){
      if(ita.hasNext() && !itb.hasNext())
	 ita.next();
      else if(!ita.hasNext() && itb.hasNext())
	 itb.next();
      else{
	 Object itema = ita.next();
	 Object itemb = itb.next();
	 if(((Comparable)itema).compareTo((Comparable)itemb) > 0){
	    ita.previous();
	 }
	 else if(((Comparable)itema).compareTo((Comparable)itemb) < 0){
	    itb.previous();
	 }
	 else{
	    lstc.add(itema);
	 }
      }
   }
      return lstc;

}

public static LinkedList<Object> reverse (LinkedList<Object> lst) {
   LinkedList<Object> lstb = new LinkedList<Object>();
   for(Object item : lst){
      lstb.addFirst(item);
   }
   return lstb;
}

    // ****** your code ends here ******

    public static void main(String args[]) {
        LinkedList<Integer> lst = new LinkedList<Integer>();
        lst.add(new Integer(3));
        lst.add(new Integer(17));
        lst.add(new Integer(2));
        lst.add(new Integer(5));
        System.out.println("lst = " + lst.toString());
        System.out.println("sum = " + sumlist(lst));

        ArrayList<Integer> lstb = new ArrayList<Integer>();
        lstb.add(new Integer(3));
        lstb.add(new Integer(17));
        lstb.add(new Integer(2));
        lstb.add(new Integer(5));
        System.out.println("lstb = " + lstb.toString());
        System.out.println("sum = " + sumarrlist(lstb));

        final Predicate myp = new Predicate()
            { public boolean pred (Object x)
                { return ( (Integer) x > 3); }};

        LinkedList<Object> lstc = new LinkedList<Object>();
        lstc.add(new Integer(3));
        lstc.add(new Integer(17));
        lstc.add(new Integer(2));
        lstc.add(new Integer(5));
        System.out.println("lstc = " + lstc.toString());
        System.out.println("subset = " + subset(myp, lstc).toString());

        System.out.println("lstc     = " + lstc.toString());
        System.out.println("dsubset  = " + dsubset(myp, lstc).toString());
        System.out.println("now lstc = " + lstc.toString());

        LinkedList<Object> lstd = new LinkedList<Object>();
        lstd.add(new Integer(3));
        lstd.add(new Integer(17));
        lstd.add(new Integer(2));
        lstd.add(new Integer(5));
        System.out.println("lstd = " + lstd.toString());
        System.out.println("some = " + some(myp, lstd).toString());

        final Functor myf = new Functor()
            { public Integer fn (Object x)
                { return new Integer( (Integer) x + 2); }};

        System.out.println("mapcar = " + mapcar(myf, lstd).toString());
	System.out.println("lstd = " + lstd.toString());
        LinkedList<Object> lste = new LinkedList<Object>();
        lste.add(new Integer(1));
        lste.add(new Integer(3));
        lste.add(new Integer(5));
        lste.add(new Integer(6));
        lste.add(new Integer(9));
        System.out.println("lste = " + lste.toString());
        LinkedList<Object> lstf = new LinkedList<Object>();
        lstf.add(new Integer(2));
        lstf.add(new Integer(3));
        lstf.add(new Integer(6));
        lstf.add(new Integer(7));
        System.out.println("lstf = " + lstf.toString());
        System.out.println("merge = " + merge(lste, lstf).toString());

        LinkedList<Object> lstg = new LinkedList<Object>();
        lstg.add(new Integer(39));
        lstg.add(new Integer(84));
        lstg.add(new Integer(5));
        lstg.add(new Integer(59));
        lstg.add(new Integer(86));
        lstg.add(new Integer(17));
        System.out.println("lstg = " + lstg.toString());

        System.out.println("intersection(lstd, lstg) = " + intersection(lstd, lstg).toString());
        System.out.println("reverse lste = " + reverse(lste).toString());

        System.out.println("sort(lstg) = " + sort(lstg).toString());

   }
}
