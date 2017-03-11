
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

	private Collection<Comment>	postedOnComments;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "commentable")
	public Collection<Comment> getPostedOnComments() {
		return this.postedOnComments;
	}

	public void setPostedOnComments(final Collection<Comment> postedOnComments) {
		this.postedOnComments = postedOnComments;
	}
}
