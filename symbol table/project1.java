// 
import java.io.*;
import java.util.Scanner;
import java.util.Arrays;
////////////////////////////////////////////////////////////////
// -------------------------------------------------------------
class SymbolTable
{
   private String[][] hashArray;    // array holds hash table
   private int arraySize;           // hold the number of data from text
    // ------------------------------------------------------------- 
   public SymbolTable(int size)       // constructor
   {
      arraySize = size;
      hashArray = new String[arraySize][2];
   }
// -------------------------------------------------------------
   public void displayArray()       //  function that displays each elements in the array
   {
      System.out.println("\nThe elements in the array ...");
   
      for(int j=0; j<arraySize; j++)      // until all the elements have been visited
      {
         if(hashArray[j][0] != null)      // check to see if there is nothing their
            System.out.println("At Index-"+j+"\t " +hashArray[j][0]+" "+hashArray[j][1]);
      }
   }
    // -------------------------------------------------------------
   public int hashFunc(String key)            // hash function
   
   { 
      int i=0,result,count=1;      
      result=(int)key.charAt(0);
      while (key.length()>count)
      { 
         result = (result * 26 + (int)key.charAt(count++))%arraySize;
         i++;
      }
   
      return result;           
   }
// -------------------------------------------------------------
  
   public void insertquad(String item, String item2)                    // insert a the element using hashing 
                                                                         // (assumes table not full)
   {
      int j=0, hashVal = hashFunc(item);                                // hash the key
                   
                                                                         // until empty cell
      while(hashArray[hashVal][0] != null && hashArray[hashVal][0]!= null)
      {               
         if ( hashArray[hashVal][0].equals(item))                          // if the element already exit,dont insert it into the array
         {System.out.println("Error '"+item+"' already exist at location -> "+ hashVal );
            return ;
         }
         System.out.println("Collisions in position ---"+hashVal+" with  "+hashArray[hashVal][0] );  // display collisions
      
         hashVal=hashVal+(j*j);                                         // go to next cell
         hashVal %= arraySize;                                       // wraparound if necessary
         j++;
         
      }
      System.out.println("Hashing in position "+hashVal+" - "+item );      
   
      hashArray[hashVal][0] = item ;                                        // insert item
      hashArray[hashVal][1]=item2;
   }   
    //-------------------------------------------------------------------------------------
   
   public  void search ( String key)                              // function that searchs for elements in the array
   { 
      int i=0,hashVal = hashFunc(key);
      while (i<arraySize)                                          // until all the elements are visited
      {  
         if (hashArray[hashVal][0]== null)                          // check to see if the cell is empty,if so iterate one loop                      
            i++;
         else if  ( hashArray[hashVal][0].equals(key))            // if found display and exit
         
         {System.out.println("'"+key+"' Found at location "+hashVal+" with value "+hashArray[hashVal][1]);
            return;
         }
         hashVal=hashVal+(i*i);                                     // go to next cell 
         hashVal %= arraySize;                                      // wraparound if necessary
         
      
              
         i++;
      }
      System.out.println("ERROR '"+ key+"' not found.");           //Not found 
   }

}

  
  ////////////////////////////////////////////////////////////////
class project1
{
   public static void main(String[] args) throws IOException
   { 
      int size=0;
      String name ;
      PrintStream printStream = new PrintStream(new FileOutputStream("out.txt"));  // save on the out.txt file
      System.setOut(printStream);
      
      Scanner filescan = new Scanner(new File(args[0]));          // scanner for name of file form command line
   
      while (filescan.hasNext())                                  //unitl file is scanned to the end
      { 
         filescan.nextLine();                                     //scan and determine the number of lines present
         size++;
      }
      size =getprime(2*size);                                     //get the size of the array to built from the number of possible elements from file
     
     
      Scanner scan = new Scanner(new File(args[0]));              // scanner to read each elemt
              
      SymbolTable tableA= new SymbolTable(size);                      // create a table 
   
   
      while (scan.hasNextLine())                                  //until the end
      {      name=scan.nextLine();                                //scan a line and assign it to a string
         String [] words= name.split(" ");                        //split the string by empty space and assign it to a string array
         if (words.length>1)                                      // if there are two words                              
            tableA.insertquad(words[0], words[1]);                // insert it
         else if (words.length==1)
            tableA.search(words[0]);                              //else search for the string in the array
                               
      }
    // System.out.println("The elemnets in the array ...");
      tableA. displayArray();                                     //display the table at the end
   
      scan.close();                                               //close reader
          
            
               
               
   }  // end main()
//--------------------------------------------------------------
      
     
   public static int getprime (int min)                       // get the next prime number for size of array
   { 
      for (int j=min+1;true;j++)
         if (isprime(j))
            return j;
   }
//---------------------------------------------------------------  
   public static boolean isprime(int n)                       // checks whether the number is prime or not
   {
      for (int j=2;(j*j<=n);j++)
         if (n%j==0)
            return false;
      return true;
   }

  
  
}  // end class 
////////////////////////////////////////////////////////////////
