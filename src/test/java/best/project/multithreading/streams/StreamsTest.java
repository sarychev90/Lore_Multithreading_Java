package best.project.multithreading.streams;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class StreamsTest {

    @Test
    void testFlatMap(){
        List<NumbersHolder> numbersHolders = Stream.generate(() ->
                new NumbersHolder(
                        IntStream.generate(()-> new Random().nextInt(100))
                        .limit(4)
                        .boxed()
                        .collect(Collectors.toList()))
        )
                .limit(2)
                .collect(Collectors.toList());

        List<Integer> numbers = numbersHolders.stream().flatMap(nh -> nh.getNumbers().stream()).collect(Collectors.toList());
        numbers.forEach(System.out::println);
        assertEquals(8, numbers.size());
    }

}
