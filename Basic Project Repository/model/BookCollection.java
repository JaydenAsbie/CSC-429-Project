// specify the package
package model;

// system imports
import java.util.Properties;
import java.util.Vector;
import javafx.scene.Scene;

// project imports
import exception.InvalidPrimaryKeyException;
import event.Event;
import database.*;

import impresario.IView;

import userinterface.View;
import userinterface.ViewFactory;


/** The class containing the AccountCollection for the ATM application */
//==============================================================
public class BookCollection extends EntityBase implements IView
{
    private static final String myTableName = "Book";

    private Vector<Book> bookList;
    // GUI Components

    // constructor for this class
    //----------------------------------------------------------
    public BookCollection() throws
            Exception
    {
        super(myTableName);
        bookList = new Vector();
    }

    //----------------------------------------------------------
    public void findBooksOlderThanDate(String year) {
        String query = "SELECT * FROM " + myTableName + " WHERE year < '" + year + "'";
        //populateBookList(query);
    }

    //----------------------------------------------------------
    public void findBooksNewerThanDate(String year) {
        String query = "SELECT * FROM " + myTableName + " WHERE year > '" + year + "'";
        //populateBookList(query);
    }

    //----------------------------------------------------------
    public void findBooksWithTitleLike(String title) {
        String query = "SELECT * FROM " + myTableName + " WHERE title LIKE '%" + title + "%'";
        //populateBookList(query);
    }

    //----------------------------------------------------------
    public void findBooksWithAuthorLike(String author) {
        String query = "SELECT * FROM " + myTableName + " WHERE author LIKE '%" + author + "%'";
        //populateBookList(query);
    }

    //----------------------------------------------------------------------------------
    private void addAccount(Book a)
    {
        //accounts.add(a);
        int index = findIndexToAdd(a);
        bookList.insertElementAt(a,index); // To build up a collection sorted on some key
    }

    //----------------------------------------------------------------------------------
    private int findIndexToAdd(Book a)
    {
        //users.add(u);
        int low=0;
        int high = bookList.size()-1;
        int middle;

        while (low <=high)
        {
            middle = (low+high)/2;

            Book midSession = bookList.elementAt(middle);

            int result = Book.compare(a,midSession);

            if (result ==0)
            {
                return middle;
            }
            else if (result<0)
            {
                high=middle-1;
            }
            else
            {
                low=middle+1;
            }


        }
        return low;
    }


    /**
     *
     */
    //----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("Book"))
            return bookList;
        else
        if (key.equals("bookList"))
            return this;
        return null;
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {

        myRegistry.updateSubscribers(key, this);
    }

    //----------------------------------------------------------
    public Book retrieve(String bookId)
    {
        Book retValue = null;
        for (int cnt = 0; cnt < bookList.size(); cnt++)
        {
            Book nextAcct = bookList.elementAt(cnt);
            String nextAccNum = (String)nextAcct.getState("AccountNumber");
            if (nextAccNum.equals(bookId) == true)
            {
                retValue = nextAcct;
                return retValue; // we should say 'break;' here
            }
        }

        return retValue;
    }

    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value)
    {
        stateChangeRequest(key, value);
    }

    //------------------------------------------------------
    protected void createAndShowView()
    {

        Scene localScene = myViews.get("AccountCollectionView");

        if (localScene == null)
        {
            // create our new view
            View newView = ViewFactory.createView("AccountCollectionView", this);
            localScene = new Scene(newView);
            myViews.put("AccountCollectionView", localScene);
        }
        // make the view visible by installing it into the frame
        swapToView(localScene);

    }

    //-----------------------------------------------------------------------------------
    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }
}

