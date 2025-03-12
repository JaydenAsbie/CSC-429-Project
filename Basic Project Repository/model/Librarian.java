// specify the package
package model;

// system imports

import event.Event;
import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;
import impresario.IModel;
import impresario.IView;
import impresario.ModelRegistry;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userinterface.*;
import model.Patron;
import model.PatronCollection;

import java.util.Hashtable;
import java.util.Properties;

/** The class containing the Teller  for the ATM application */
//==============================================================
public class Librarian implements IView, IModel
// This class implements all these interfaces (and does NOT extend 'EntityBase')
// because it does NOT play the role of accessing the back-end database tables.
// It only plays a front-end role. 'EntityBase' objects play both roles.
{
	// For Impresario
	private Properties dependencies;
	private ModelRegistry myRegistry;

	private AccountHolder myAccountHolder;
	private PatronCollectionView pColl;

	// GUI Components
	private Hashtable<String, Scene> myViews;
	private Stage	  	myStage;

	private String loginErrorMessage = "";
	private String transactionErrorMessage = "";

	// constructor for this class
	//----------------------------------------------------------
	public Librarian()
	{
		myStage = MainStageContainer.getInstance();
		myViews = new Hashtable<String, Scene>();

		// STEP 3.1: Create the Registry object - if you inherit from
		// EntityBase, this is done for you. Otherwise, you do it yourself
		myRegistry = new ModelRegistry("Librarian");
		if(myRegistry == null)
		{
			new Event(Event.getLeafLevelClassName(this), "Librarian",
				"Could not instantiate Registry", Event.ERROR);
		}

		// STEP 3.2: Be sure to set the dependencies correctly
		//setDependencies();

		// Set up the initial view
		createAndShowLibrarianView();
	}

	//-----------------------------------------------------------------------------------
	private void setDependencies()
	{
		dependencies = new Properties();
		dependencies.setProperty("Login", "LoginError");
		dependencies.setProperty("Deposit", "TransactionError");
		dependencies.setProperty("Withdraw", "TransactionError");
		dependencies.setProperty("Transfer", "TransactionError");
		dependencies.setProperty("BalanceInquiry", "TransactionError");
		dependencies.setProperty("ImposeServiceCharge", "TransactionError");

		myRegistry.setDependencies(dependencies);
	}

	/**
	 * Method called from client to get the value of a particular field
	 * held by the objects encapsulated by this object.
	 *
	 * @param	key	Name of database column (field) for which the client wants the value
	 *
	 * @return	Value associated with the field
	 */
	//----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("LoginError") == true)
		{
			return loginErrorMessage;
		}
		else
		if (key.equals("TransactionError") == true)
		{
			return transactionErrorMessage;
		}
		else
		if (key.equals("Name") == true)
		{
			if (myAccountHolder != null)
			{
				return myAccountHolder.getState("Name");
			}
			else
				return "Undefined";
		}
		else
			return "";
	}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		// STEP 4: Write the sCR method component for the key you
		// just set up dependencies for
		// DEBUG System.out.println("Teller.sCR: key = " + key);
		if(key.equals("PatronView") == true){
			createAndShowPatronView();
		}
        else if(key.equals("LibrarianView") == true)
        {
            createAndShowLibrarianView();
        }
        else if(key.equals("InsertPatron") == true)
        {
            Patron insertPatron = new Patron((Properties)value);
            insertPatron.save();
        }
        else if(key.equals("PatronSearchView") == true)
        {
            createAndShowPatronSearchView();
        }
        else if(key.equals("PatronSearch") == true)
        {
			//if(pColl != null) pColl.clearTable();
			PatronCollection newPatronCollection = new PatronCollection();
            newPatronCollection.findPatronsAtZipCode((String)value);
			System.out.println("Test 1: " + value);
            createAndShowPatronCollectionView(newPatronCollection);
        }
		else if (key.equals("Exit") == true)
		{
//			myAccountHolder = null;
//			myViews.remove("TransactionChoiceView");
//
//			createAndShowLibrarianView();
			System.exit(0);
		}

		myRegistry.updateSubscribers(key, this);
	}

	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		// DEBUG System.out.println("Teller.updateState: key: " + key);

		stateChangeRequest(key, value);
	}

	//----------------------------------------------------------
	private void createAndShowPatronView()
	{
		Scene currentScene = (Scene)myViews.get("PatronView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("PatronView", this); // USE VIEW FACTORY
			currentScene = new Scene(newView);
			myViews.put("PatronView", currentScene);
		}


		// make the view visible by installing it into the frame
		swapToView(currentScene);

	}

	//------------------------------------------------------------
	private void createAndShowLibrarianView()
	{
		Scene currentScene = (Scene)myViews.get("LibrarianView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("LibrarianView", this); // USE VIEW FACTORY
			currentScene = new Scene(newView);
			myViews.put("LibrarianView", currentScene);
		}
				
		swapToView(currentScene);
		
	}

    //------------------------------------------------------------
    private void createAndShowPatronSearchView()
    {
        Scene currentScene = (Scene)myViews.get("PatronSearchView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("PatronSearchView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("PatronSearchView", currentScene);
        }

        swapToView(currentScene);

    }

    //------------------------------------------------------------
    private void createAndShowPatronCollectionView(PatronCollection newPatronCollection)
    {
		Scene currentScene = (Scene)myViews.get("PatronCollectionView");

		System.out.println("Test 4");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("PatronCollectionView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("PatronCollectionView", currentScene);
			pColl = (PatronCollectionView) newView;
			System.out.println("Test 3: " + (newPatronCollection != null));
        }
		pColl.updateTable(newPatronCollection);


        swapToView(currentScene);
    }


	/** Register objects to receive state updates. */
	//----------------------------------------------------------
	public void subscribe(String key, IView subscriber)
	{
		// DEBUG: System.out.println("Cager[" + myTableName + "].subscribe");
		// forward to our registry
		myRegistry.subscribe(key, subscriber);
	}

	/** Unregister previously registered objects. */
	//----------------------------------------------------------
	public void unSubscribe(String key, IView subscriber)
	{
		// DEBUG: System.out.println("Cager.unSubscribe");
		// forward to our registry
		myRegistry.unSubscribe(key, subscriber);
	}

	//-----------------------------------------------------------------------------
	public void swapToView(Scene newScene)
	{

		
		if (newScene == null)
		{
			System.out.println("Teller.swapToView(): Missing view for display");
			new Event(Event.getLeafLevelClassName(this), "swapToView",
				"Missing view for display ", Event.ERROR);
			return;
		}

		myStage.setScene(newScene);
		myStage.sizeToScene();
		
			
		//Place in center
		WindowPosition.placeCenter(myStage);

	}

}

