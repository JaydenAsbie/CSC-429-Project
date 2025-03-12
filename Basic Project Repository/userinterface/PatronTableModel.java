package userinterface;

import javafx.beans.property.SimpleStringProperty;

import java.util.Vector;

//==============================================================================
public class PatronTableModel
{
	private final SimpleStringProperty patronId;
	private final SimpleStringProperty name;
	private final SimpleStringProperty address;
	private final SimpleStringProperty city;
    private final SimpleStringProperty stateCode;
    private final SimpleStringProperty zip;
    private final SimpleStringProperty email;
    private final SimpleStringProperty dateOfBirth;
    private final SimpleStringProperty status;

	//----------------------------------------------------------------------------
	public PatronTableModel(Vector<String> accountData)
	{
		patronId =  new SimpleStringProperty(accountData.elementAt(0));
		name =  new SimpleStringProperty(accountData.elementAt(1));
		address =  new SimpleStringProperty(accountData.elementAt(2));
		city =  new SimpleStringProperty(accountData.elementAt(3));
        stateCode = new SimpleStringProperty(accountData.elementAt(4));
        zip = new SimpleStringProperty(accountData.elementAt(5));
        email = new SimpleStringProperty(accountData.elementAt(6));
        dateOfBirth = new SimpleStringProperty(accountData.elementAt(7));
        status = new SimpleStringProperty(accountData.elementAt(8));
	}

	//----------------------------------------------------------------------------
	public String getPatronId() {
        return patronId.get();
    }

	//----------------------------------------------------------------------------
    public void setPatronId(String number) {
        patronId.set(number);
    }

    //----------------------------------------------------------------------------
    public String getName() { return name.get(); }

    //----------------------------------------------------------------------------
    public void setName(String sName) {
        name.set(sName);
    }

    //----------------------------------------------------------------------------
    public String getAddress() {
        return address.get();
    }

    //----------------------------------------------------------------------------
    public void setAddress(String sAddress) {
        address.set(sAddress);
    }
    
    //----------------------------------------------------------------------------
    public String getCity() {
        return city.get();
    }

    //----------------------------------------------------------------------------
    public void setCity(String sCity)
    {
    	city.set(sCity);
    }

    //----------------------------------------------------------------------------
    public String getStateCode() { return stateCode.get(); }

    //----------------------------------------------------------------------------
    public void setStateCode(String sStateCode) { stateCode.set(sStateCode); }

    //----------------------------------------------------------------------------
    public String getZip() { return zip.get(); }

    //----------------------------------------------------------------------------
    public void setZip(String sZip) { zip.set(sZip); }

    //----------------------------------------------------------------------------
    public String getEmail() { return email.get(); }

    //----------------------------------------------------------------------------
    public void setEmail(String sEmail) { email.set(sEmail); }

    //----------------------------------------------------------------------------
    public String getDateOfBirth() { return dateOfBirth.get(); }

    //----------------------------------------------------------------------------
    public void setDateOfBirth(String sDateOfBirth) { dateOfBirth.set(sDateOfBirth); }

    //----------------------------------------------------------------------------
    public String getStatus() { return status.get(); }

    //----------------------------------------------------------------------------
    public void setStatus(String sStatus) { status.set(sStatus); }
}
