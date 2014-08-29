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

	private int count;
	private List<T> items;

	/**
	 * @param count
	 * @param items
	 */
	public QueryResult(int count, List<T> items) {
		super();
		this.count = count;
		this.items = items;
	}

	public int getCount() {
		return count;
	}

	public List<T> getItems() {
		return items;
	}

}
