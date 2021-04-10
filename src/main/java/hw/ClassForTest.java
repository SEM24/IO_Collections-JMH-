package hw;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Fork(value = 1, jvmArgs = {"-Xms256m", "-Xmx256m"})
@State(Scope.Benchmark)
@Warmup(iterations = 2, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Timeout(time = 1, timeUnit = TimeUnit.MINUTES)

public class ClassForTest {

    String textFile = "F:\\Hw2Enterprise\\src\\main\\resources\\Test.txt";
    Path path = Paths.get(textFile);
    int longestLine;

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void methodOne(Blackhole bk) throws IOException {

        List<String> list = new ArrayList<>();
        Scanner scanner = new Scanner(path);

        while (scanner.hasNextLine()) {
            String cache = scanner.nextLine();
            list.add(cache);
            if (longestLine < cache.length())
                longestLine = cache.length();
        }
        scanner.close();
        bk.consume(longestLine);

    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void methodTwo(Blackhole bk) throws IOException {
        longestLine = Files.lines(Paths.get(textFile)).map(String::length).max(Integer::compareTo).orElse(0);
        bk.consume(longestLine);
    }
//    ClassForTest.methodOne  avgt    5  0,079 ± 0,041  ms/op
//    ClassForTest.methodTwo  avgt    5  0,060 ± 0,014  ms/op
}
