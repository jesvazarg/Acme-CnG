
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {

	// Constructors ----------------------------------------------------------

	public Customer() {
		super();
	}


	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	private Collection<Apply>		applies;
	private Collection<Transaction>	transactions;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "customer")
	public Collection<Apply> getApplies() {
		return this.applies;
	}

	public void setApplies(final Collection<Apply> applies) {
		this.applies = applies;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "customer")
	public Collection<Transaction> getTransactions() {
		return this.transactions;
	}

	public void setTransactions(final Collection<Transaction> transactions) {
		this.transactions = transactions;
	}

}
