/**
 * 
 */
package in.anandm.yell;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @author anandm
 * 
 */
@Repository
public class MongoYellRepository implements YellRepository {

	private static final String YELL_COLLECTION_NAME = "yells";

	private MongoTemplate mongoTemplate;

	@Autowired
	/**
	 * @param mongoTemplate
	 */
	public MongoYellRepository(MongoTemplate mongoTemplate) {
		super();
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public void saveYell(Yell yell) {
		mongoTemplate.save(yell, YELL_COLLECTION_NAME);
	}

	@Override
	public long count(YellQuery query) {

		return mongoTemplate.count(new Query(), YELL_COLLECTION_NAME);

	}

	@Override
	public List<Yell> list(YellQuery query) {
		Query mongoQuery = new Query();

		if (query.getPage() != null && query.getPageSize() != null) {
			mongoQuery.skip((query.getPage() - 1) * query.getPageSize());
			mongoQuery.limit(query.getPageSize());
		}

		mongoQuery.with(new Sort(Sort.Direction.ASC, "yelledAt"));

		return mongoTemplate.find(mongoQuery, Yell.class, YELL_COLLECTION_NAME);
	}

	@Override
	public QueryResult<Yell> query(YellQuery query) {

		return new QueryResult<Yell>(count(query), list(query));
	}

}
