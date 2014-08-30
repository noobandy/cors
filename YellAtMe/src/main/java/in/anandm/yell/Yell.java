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

	private String id;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setYell(String yell) {
		this.yell = yell;
	}

	public void setYelledAt(Date yelledAt) {
		this.yelledAt = yelledAt;
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
