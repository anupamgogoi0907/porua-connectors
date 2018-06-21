import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.porua.db.component.PoruaDatabaseConnector;

/**
 * Created by ac-agogoi on 3/9/18.
 */
public class TestDbConnector {
	@Test
	public void testConnector() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("db-test.xml");
		PoruaDatabaseConnector connector = context.getBean(PoruaDatabaseConnector.class);
		assertNotNull(connector);
		context.close();
	}
}
