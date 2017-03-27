package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Comment extends DomainEntity {

	
	//Constructor---------------------------------------------
	
	public Comment(){
		super();
	}

	//Attributes-----------------------------------------------
	private String title;
	private Date postedMoment;
	private String text;
	private Integer starsNumber;
	private Boolean banned;
	
	@NotBlank
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getPostedMoment() {
		return postedMoment;
	}
	public void setPostedMoment(Date postedMoment) {
		this.postedMoment = postedMoment;
	}
	
	@NotBlank
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@NotNull
	@Range(min=0, max=5)
	public Integer getStarsNumber() {
		return starsNumber;
	}
	public void setStarsNumber(Integer starsNumber) {
		this.starsNumber = starsNumber;
	}
	
	@NotNull
	public Boolean getBanned() {
		return banned;
	}
	public void setBanned(Boolean banned) {
		this.banned = banned;
	}
	
	//Relationships---------------------------------------------------
	
	
	private Commentable postedTo;
	private Actor postedBy;

	@Valid
	@NotNull
	@ManyToOne(optional=false)
	@NotFound(action=NotFoundAction.IGNORE)
	public Commentable getPostedTo() {
		return postedTo;
	}
	public void setPostedTo(Commentable postedTo) {
		this.postedTo = postedTo;
	}
	
	
	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Actor getPostedBy() {
		return postedBy;
	}
	public void setPostedBy(Actor postedBy) {
		this.postedBy = postedBy;
	}
}
