package in.anandm.yell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/api")
public class YellController {

	private static final Logger logger = LoggerFactory
			.getLogger(YellController.class);

	@Autowired
	private YellRepository yellRepository;

	@RequestMapping(value = "/yells", method = RequestMethod.GET)
	public @ResponseBody
	ResponseEntity<QueryResult<Yell>> getYells(
			@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "pageSize", required = true) int pageSize) {

		logger.info("loading yells : page {}, pageSize {}", page, pageSize);

		return new ResponseEntity<QueryResult<Yell>>(
				yellRepository.query(new YellQuery(page, pageSize)),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/yells", method = RequestMethod.POST)
	public @ResponseBody
	ResponseEntity<Yell> addYell(@RequestBody Yell yell) {
		logger.info("adding yell : {}", yell);
		yellRepository.saveYell(yell);
		return new ResponseEntity<Yell>(yell, HttpStatus.CREATED);
	}
}
