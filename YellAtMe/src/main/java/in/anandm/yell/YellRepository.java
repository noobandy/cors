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

	int count(YellQuery query);

	List<Yell> list(YellQuery query);

}
