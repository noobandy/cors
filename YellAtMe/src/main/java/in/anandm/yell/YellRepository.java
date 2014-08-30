/**
 * 
 */
package in.anandm.yell;

import java.util.List;

/**
 * @author anandm
 * 
 */
public interface YellRepository {

	void saveYell(Yell yell);

	long count(YellQuery query);

	List<Yell> list(YellQuery query);

	QueryResult<Yell> query(YellQuery query);
}
