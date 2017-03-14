
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
public class Commentable extends DomainEntity {

	// Constructors ----------------------------------------------------------

	public Commentable() {
		super();
	}


	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	private Collection<Comment>	postedToComments;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "postedTo")
	public Collection<Comment> getPostedToComments() {
		return this.postedToComments;
	}

	public void setPostedToComments(final Collection<Comment> postedToComments) {
		this.postedToComments = postedToComments;
	}
}
