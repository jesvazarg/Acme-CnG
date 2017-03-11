
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Transaction extends Comentable {

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
	public Place getOriginPlace() {
		return this.originPlace;
	}

	public void setOriginPlace(final Place originPlace) {
		this.originPlace = originPlace;
	}

	@NotNull
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
	private Customer	customer;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

}
