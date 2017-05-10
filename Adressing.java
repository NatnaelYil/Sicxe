import java.util.*;
import java.io.*;
import java.awt.*;
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
      System.out.println("\nTable Location"+"\t"+"Label\t\tAddress");
   
      for(int j=0; j<arraySize; j++)      // until all the elements have been visited
      {
         if(hashArray[j][0] != null)      // check to see if there is nothing their
            System.out.println("At Index-"+j+"\t\t " +hashArray[j][0]+"\t\t"+hashArray[j][1]);
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
                   
      while(hashArray[hashVal][0] != null && hashArray[hashVal][0]!= null)                                                                   // until empty cell
      {               
         if ( hashArray[hashVal][0].equals(item))                          // if the element already exit,dont insert it into the array
         {System.out.println("Error '"+item+"' already exist at location -> "+ hashVal );
            return ;
         }
                  
         hashVal=hashVal+(j*j);                                         // go to next cell
         hashVal %= arraySize;                                       // wraparound if necessary
         j++;
         
      }
           
   
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

class Adressing
{  

   static boolean flag=false, adrflag=false;


   public static void main(String[] args)
   { 
   
      int size=0;                                                                //variableto know the number of lines in the file
      String Locctr;
      String word;
      String[][] OPTAB = new String[136][4];                                     //fixed array to hold the labels and there corresponging bit
          
   
   
   
      if (args.length <1 )                                                       // if file not present- display an error
      {    System.out.println("Unable to read files \n ------ Please read the readme filee on how to load the correct files into the program---------- ");
      }
       
      File file = new File(args[0]);
      File file2=new File (args[0]);
      
      try (Scanner filescan = new Scanner(new File(args[0]))) 
      { 
         while (filescan.hasNext())                                  //unitl file is scanned to the end
         { 
            filescan.nextLine();                                     //scan and determine the number of lines present
            size++;
         }
      
      } 
      catch (IOException | InputMismatchException ex) {
         ex.printStackTrace();
      }      
      
      int j=0;
      try (Scanner filescan2 = new Scanner(new File(args[1]))) 
      { 
         while (filescan2.hasNext())                                  //unitl file is scanned to the end
         { word=filescan2.nextLine();
            
           
            String [] words= word.split("\\s+"); 
                                                                  //scan and insert into optable array
            if (words.length>2)   
            {  
               OPTAB[j][0]=words[0];
               OPTAB[j][1]=words[1];
               OPTAB[j][2]=words[2];
               OPTAB[j][3]=words[3];
            }
            else
            { OPTAB[j][0]=words[0];
               OPTAB[j][1]=words[1];
            }
          
            j++;
         }
      } 
      catch (IOException | InputMismatchException ex) {
         ex.printStackTrace();
      }   
      
      
      
      size =getprime(2*size);                                             //get the size of the array to built from the number of possible elements from file                                  
                                                                      // get a prime number for the symbol(Hash) table array  
                                     
      SymbolTable SymTab= new SymbolTable(size);                          // CREATE THE SYMBOLE TABLE
   
      String s;                                                           // VARIABLES USED TO ACCEPT WORDS FROM A LINE 
      String ss;
      int i=0;
      
      
      try (Scanner scan = new Scanner(new FileReader(file)))
      {
         s=scan.nextLine();
         
         char[] cArray = s.toCharArray();
         ss= builder(s,10,16);
       
       
                                                                        //if it is comment move to the next line until it you find "start"                                       
         if (ss.equals("START"))
         {
            Locctr= builder(s,19,28);
            if (Locctr == "")
               Locctr="100";    
         } 
         else 
            Locctr="0";
             
         
         String Adr= builder(s,0,7);  
         if ( Adr!= "")
         {  SymTab.insertquad(Adr,Locctr );   
         }
      
         System.out.print(Locctr+"\t");
         System.out.println(s);
         s=scan.nextLine();
         
         
         
         while (true) 
         {  
            if ((builder(s,10,16).equals("END")))   
               break;
               
         
            if (s.charAt(i) != '.')
            {        adrflag= true;
            
               ss=builder(s,0,7);
              
               if (ss != "")
               {   SymTab.insertquad(ss,Locctr );  } 
                    
               ss=(builder(s,9,16));
             
               String sss=opSearch(OPTAB,builder(s,9,16));
               
               if(flag) 
               {
                  Locctr=DectoHex(Integer.parseInt(sss)+ Integer.parseInt(Locctr,16));
                  flag=false;
               }
                
               else if (ss.equals("WORD") || (ss.equals("RESB")))
                  Locctr=DectoHex( Integer.parseInt(Locctr,16)+3);   
                 
                     
               else if(ss.equals("RESW")) 
               {  
                  ss=(builder(s,19,28));
                  Locctr=DectoHex( ((Integer.parseInt(ss))*3) + Integer.parseInt(Locctr,16));  
                        
               }
               else if(ss.equals("BASE"))
               {
               } 
               /*else if(ss.equals("BYTE")) 
                     Locctr= Locctr+3;//////////////////////////////////////////////////
                  */
               else 
                  System.out.println("INVALID OPERATION CODE");
            }             
            
            ss="";
            s=scan.nextLine(); 
            if (adrflag || (s.charAt(i) != '.' && adrflag== false))
               System.out.print(Locctr+"\t");
         
            System.out.println(s);
            adrflag=false;
         } 
         
         SymTab.displayArray();                                    
                                        
                        
      } 
      catch (IOException | InputMismatchException ex) 
      {
         ex.printStackTrace();
      }         
      
                       
   }//main
 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
   
   
   
   
   //-----------------------------------------------------------------------------------------------------------------
   //This method searchs the opTable for the proper laber and return the instruction size to be calculated
   //
   public static String opSearch(String [][] optab, String label)          
   { String s="";
   
      for (int i=0; i<optab.length;i++)
      {  
         if ((optab[i][0]).equals(label))
         {  flag=true;
            String searchstr=(optab[i][2]); 
            return searchstr;
         }  
      }      
      
      return s;
   }
   //---------------------------------------------------------------------------------------------------------------
   //This function builds a string word from a line given the corresponding starting and ending column number
   //
   public static String builder(String line, int start, int end)
   {
      String s="";
      for (int i=start;i<end && i<line.length();i++)
      {  
         if (i== start && (line.charAt(i)== ' ' || line.charAt(i)== '\t'))
            continue;
         else if (i== start)
            s= String.valueOf(line.charAt(i));
         else if (line.charAt(i)== ' ' || line.charAt(i)== '\t')
            continue;
         else 
            s=s+line.charAt(i);
      
      }
      return s;
   }


//---------------------------------------------------------------------------------------------------------------------
//This function takes a deciaml as a parameter and converts a decimal to hexadecimal
//
   public static String DectoHex(int dec)                      ///conver decimal to hexdecimal
   { 
   
      return (Integer.toHexString(dec));
   
   }
//-------------------------------------------------------------------------
//get the next prime number for size of array
//
   public static int getprime (int min)                       
   { 
      for (int j=min+1;true;j++)
         if (isprime(j))
            return j;
   }
//--------------------------------------------------------------- 
//checks whether the number is prime or not
//
   public static boolean isprime(int n)                      
   {
      for (int j=2;(j*j<=n);j++)
         if (n%j==0)
            return false;
      return true;
   }

}
