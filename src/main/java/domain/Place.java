
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
@Access(AccessType.PROPERTY)
public class Place {

	// Constructors ----------------------------------------------------------
	public Place() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	address;
	private Double	latitude;
	private Double	longitude;


	@NotBlank
	public String getAddress() {
		return this.address;
	}
	public void setAddress(final String address) {
		this.address = address;
	}

	public Double getLatitude() {
		return this.latitude;
	}
	public void setLatitude(final Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return this.longitude;
	}
	public void setLongitude(final Double longitude) {
		this.longitude = longitude;
	}

	// toString ---------------------------------------------------------------

	@Override
	public String toString() {
		return "Place [address=" + this.address + ", latitude=" + this.latitude + ", longitude=" + this.longitude + "]";
	}

}
