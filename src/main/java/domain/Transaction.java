
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Transaction extends Commentable {

	// Constructors ----------------------------------------------------------
	public Transaction() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private String	title;
	private String	description;
	private Date	movingMoment;
	private Place	originPlace;
	private Place	destinationPlace;
	private boolean	banned;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@Future
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMovingMoment() {
		return this.movingMoment;
	}

	public void setMovingMoment(final Date movingMoment) {
		this.movingMoment = movingMoment;
	}

	@NotNull
	@Valid
	@AttributeOverrides({
		@AttributeOverride(name = "address", column = @Column(name = "originAddress")), @AttributeOverride(name = "latitude", column = @Column(name = "originLatitude")), @AttributeOverride(name = "longitude", column = @Column(name = "originLongitude")),
	})
	public Place getOriginPlace() {
		return this.originPlace;
	}

	public void setOriginPlace(final Place originPlace) {
		this.originPlace = originPlace;
	}

	@NotNull
	@Valid
	@AttributeOverrides({
		@AttributeOverride(name = "address", column = @Column(name = "destinationAddress")), @AttributeOverride(name = "latitude", column = @Column(name = "destinationLatitude")),
		@AttributeOverride(name = "longitude", column = @Column(name = "destinationLongitude")),
	})
	public Place getDestinationPlace() {
		return this.destinationPlace;
	}

	public void setDestinationPlace(final Place destinationPlace) {
		this.destinationPlace = destinationPlace;
	}

	@NotNull
	public boolean isBanned() {
		return this.banned;
	}

	public void setBanned(final boolean banned) {
		this.banned = banned;
	}


	// Relationships ----------------------------------------------------------
	private Customer			customer;
	private Collection<Apply>	applies;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "transaction")
	public Collection<Apply> getApplies() {
		return applies;
	}

	public void setApplies(Collection<Apply> applies) {
		this.applies = applies;
	}

}
