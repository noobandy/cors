/**
 * 
 */
package in.anandm.yell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

/**
 * @author anandm
 * 
 */
@Repository
public class InMemoryYellRepository implements YellRepository {

	private Hashtable<Long, Yell> yellTable = new Hashtable<Long, Yell>();

	private AtomicLong idGenerator = new AtomicLong(0L);

	@Override
	public void saveYell(Yell yell) {
		if (yell.getId() == null) {
			yell.setId(idGenerator.incrementAndGet());
		}

		yellTable.put(yell.getId(), yell);
	}

	@Override
	public int count(YellQuery query) {

		return yellTable.size();
	}

	@Override
	public List<Yell> list(YellQuery query) {
		List<Yell> yells = new ArrayList<Yell>();
		yells.addAll(yellTable.values());

		if (query.getPage() != null && query.getPageSize() != null) {
			int start = (query.getPage() - 1) * query.getPageSize();
			int end = start + query.getPageSize() - 1;

			if (start > yells.size()) {
				return Collections.EMPTY_LIST;
			}

			if (yells.size() < end) {
				end = yells.size();
			}

			return yells.subList(start, end);
		}
		return yells;
	}

	@Override
	public QueryResult<Yell> query(YellQuery query) {
		int count = count(query);
		List<Yell> items = list(query);
		return new QueryResult<Yell>(count, items);
	}

}
