/**
 * 
 */
package in.anandm.yell;

/**
 * @author anandm
 * 
 */
public class YellQuery {

	private Integer page;
	private Integer pageSize;

	/**
	 * @param page
	 * @param pageSize
	 */
	public YellQuery(Integer page, Integer pageSize) {
		super();
		this.page = page;
		this.pageSize = pageSize;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
