package fr.dta.jdbc;

public class Client {
	private Integer id;
	private String lastname, firstname;
	private Gender gender;
	private int favorite;

	public Client(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.gender = null;
		this.favorite = 0;
	}

	public Client(String firstname, String lastname, Gender gender) {
		this(firstname, lastname);
		this.gender = gender;
		this.favorite = 0;
	}

	public Client(String firstname, String lastname, Gender gender, int favorite) {
		this(firstname, lastname, gender);
		this.favorite = favorite;
	}

	public int getFavorite() {
		return favorite;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public void setFavorite(int favorite) {
		this.favorite = favorite;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getfirstname() {
		return firstname;
	}

	public void setfirstname(String firstname) {
		this.firstname = firstname;
	}

	public String toString() {
		if (favorite == 0) {
			return "Client [lastname=" + lastname + ", firstname=" + firstname + ", gender=" + gender
					+ ", pas de favoris]";
		} else {
			return "Client [lastname=" + lastname + ", firstname=" + firstname + ", gender=" + gender + ", favorite="
					+ favorite + "]";
		}
	}

	public String getlastname() {
		return lastname;
	}

	public void setlastname(String lastname) {
		this.lastname = lastname;
	}

	

}
