package best.project.multithreading.restaurant;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestaurantTest {
	private static final Logger LOGGER = Logger.getLogger(RestaurantTest.class.getName());
	private List<Visitor> visitors;
	private int visitorOrderNumber = 0;
	private int numberOfVisitors = 5;
	private int visitorNumber = 1;
	private int numberOfTables = 2;
	private int moneyBudgetPerVisitor = 100;
	CountDownLatch latch = new CountDownLatch(numberOfVisitors);
	Table table = new Table(numberOfTables);
	@Test
	void restaurantTest() throws InterruptedException {
		generateVisitors();
		Stream.generate(()-> new Thread(getVisitor())).limit(numberOfVisitors).forEach(Thread::start);
		latch.await();
		LOGGER.log(Level.INFO, String.format("Table total cash income %d", getTablesCashIncome()));
		assertEquals(moneyBudgetPerVisitor * numberOfVisitors, getTablesCashIncome());
		assertEquals(0, getVisitorsBudgetBalance());
	}

	private void generateVisitors() {
		visitors = Stream.generate(()-> new Visitor(table, getVisitorNumber(), latch, moneyBudgetPerVisitor)).limit(numberOfVisitors).collect(Collectors.toList());
	}

	private Visitor getVisitor(){
		Visitor visitor = visitors.get(visitorOrderNumber);
		visitorOrderNumber++;
		return visitor;
	}

	private int getVisitorNumber(){
		return visitorNumber++;
	}

	private int getTablesCashIncome(){
		return table.getCashBox();
	}

	private int getVisitorsBudgetBalance(){
		return visitors.stream().mapToInt(Visitor::getMoneyBudget).sum();
	}
}
