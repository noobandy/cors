/**
 * 
 */
package in.anandm.yell;

import java.util.List;

/**
 * @author anandm
 * 
 */
public class QueryResult<T> {

	private long count;
	private List<T> items;

	/**
	 * @param count
	 * @param items
	 */
	public QueryResult(long count, List<T> items) {
		super();
		this.count = count;
		this.items = items;
	}

	public long getCount() {
		return count;
	}

	public List<T> getItems() {
		return items;
	}

}
