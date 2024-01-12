package ro.mta.libraryproject.Library;

import org.junit.Test;
import ro.mta.libraryproject.Main.ManagerDB;


public class returnBookTest {

    @Test
    public void testReturnRequest() {

        ManagerDB managerDB=new ManagerDB();

       boolean result=managerDB.returnRequest(1,1);
       if(result==true)
       {
           String result2= managerDB.unapprovedRequests("books");
           if(result2!=null)

           {

               System.out.println(result2);
               System.out.println("Test succeded");
           }

       }


    }
}