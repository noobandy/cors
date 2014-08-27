/**
 * 
 */
package in.anandm.yell;

import java.util.Date;

/**
 * @author anandm
 * 
 */
public class Yell {

	private Long id;
	private String yell;
	private Date yelledAt;

	/**
	 * 
	 */
	Yell() {
		super();

	}

	/**
	 * @param yell
	 * @param yelledAt
	 */
	public Yell(String yell, Date yelledAt) {
		super();
		this.yell = yell;
		this.yelledAt = yelledAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getYell() {
		return yell;
	}

	public Date getYelledAt() {
		return yelledAt;
	}

	@Override
	public String toString() {
		return "Yell [id=" + id + ", yell=" + yell + ", yelledAt=" + yelledAt
				+ "]";
	}
}
