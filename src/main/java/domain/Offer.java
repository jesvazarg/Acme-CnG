
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Offer extends Transaction {

	// Constructors ----------------------------------------------------------
	public Offer() {
		super();
	}

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------

}
