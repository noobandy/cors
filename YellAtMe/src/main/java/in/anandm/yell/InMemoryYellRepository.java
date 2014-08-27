/**
 * 
 */
package in.anandm.yell;

import java.util.ArrayList;
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
			return yells.subList(start, start + query.getPageSize() - 1);
		}
		return yells;
	}

}
