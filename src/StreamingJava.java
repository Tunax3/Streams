import jdk.jshell.execution.StreamingExecutionControl;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.lang.Byte.parseByte;
import static java.lang.Byte.valueOf;
import static java.lang.Double.NaN;
import static java.lang.Double.parseDouble;

public class StreamingJava {

    public static void main (String[]args) throws IOException, ParseException {
        NaturalGasBilling.serialize(NaturalGasBilling.orderByInvoiceDateDesc(fileLines("FIles/NaturalGasBilling.csv")));
    }
    // Aufgabe 2) a)
    public static <E> Stream<E> flatStreamOf(List<List<E>> list) {
        // TODO
        return list.stream()
                .flatMap(x -> x.stream());
    }

    // Aufgabe 2) b)
    public static <E> Stream<E> mergeStreamsOf(Stream<Stream<E>> stream) {
        // TODO
        return stream.
                reduce(Stream.empty(), (eStream, eStream2) -> Stream.concat(eStream, eStream2));
    }

    // Aufgabe 2) c)
    public static <E extends Comparable<? super E>> E minOf(List<List<E>> list) {
        // TODO
        var t = list.stream().parallel().
                flatMap(x -> x.stream()).
        min(Comparator.naturalOrder()).orElseThrow();
        return t;
    }

    // Aufgabe 2) d)
    public static <E> E lastWithOf(Stream<E> stream, Predicate<? super E> predicate) {
        // TODO
        Object [] a = stream.
                filter(predicate).toArray();
        return (E) a[a.length];
    }

    // Aufgabe 2) e)
    public static <E> Set<E> findOfCount(Stream<E> stream, int count) {
        // TODO
        return stream.collect(Collectors.groupingBy(e -> e,Collectors.counting()))
                .entrySet().stream().filter(entry  -> entry.getValue() == count)
                .map(entry -> entry.getKey()).collect(Collectors.toSet());
    }

    // Aufgabe 2) f)
    public static IntStream makeStreamOf(String[] strings) {
        // TODO
        return Arrays.stream(strings).flatMapToInt(x -> x.chars());
    }

//-------------------------------------------------------------------------------------------------

    // Aufgabe 3) a)
    public static Stream<String> fileLines(String path) throws IOException {
        // TODO
        FileReader reader = new FileReader(path);
        BufferedReader bufreader = new BufferedReader(reader);

        Stream<String> str = bufreader.lines().sequential().skip(1).onClose(() -> System.out.println("Stream geschlossen."));
        return str;
    }

    // Aufgabe 3) b)
    public static double averageCost(Stream<String> lines) {
        // TODO
        return lines.mapToDouble(s -> parseDouble
                        (s.split(",")[12]))
                .average()
                .getAsDouble();
    }

    // Aufgabe 3) c)
    public static long countCleanEnergyLevy(Stream<String> stream) {
        // TODO
        return stream.map(s -> s.split(",")[10])
                .filter(val -> val.equals("0") || val.equals(""))
                .count();
    }

    // Aufgabe 3) d)
    // TODO:
    //  1. Create record "NaturalGasBilling".
    //  2. Implement static method: "Stream<NaturalGasBilling> orderByInvoiceDateDesc(Stream<String> stream)".

    public Date Invoice;
    public Date From;
    public Date To;
    public double Billing;
    public double BilledGJ;
    public double BasicCharge;
    public double DeliveryCharges;
    public double StorageAndTransport;
    public double CommodityCharges;
    public double Tax;
    public String CleanEnergyLevy;
    public double CarbonTax;
    public double Amount;

    //static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public record NaturalGasBilling(Date Invoice,Date From,Date To,int Billing ,double BilledGJ,double BasicCharge,
                                     double DeliveryCharges,double StorageAndTransport,double CommodityCharges,
                                     double Tax,String CleanEnergyLevy,
                                     double CarbonTax,double Amount) {
        static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        public NaturalGasBilling(String[] strings) throws ParseException {

            this(
                    formatter.parse(strings[0]),
                    formatter.parse(strings[1]),
                    formatter.parse(strings[2]),
                    Integer.valueOf(strings[3]),
                    parseDouble(strings[4]),
                    parseDouble(strings[5]),
                    parseDouble(strings[6]),
                    parseDouble(strings[7]),
                    parseDouble(strings[8]),
                    parseDouble(strings[9]),
                    (strings[10]),
                    parseDouble(strings[11]),
                    parseDouble(strings[12])
            );
        }

        public static void main(String[] args) throws IOException, ParseException {
        }

        public static Stream<NaturalGasBilling> orderByInvoiceDateDesc(Stream<String> stream) {
            return stream.map(it -> it.split(","))
                    .map(it -> {
                        System.out.println(Arrays.toString(it));
                        try {
                            var ne = new NaturalGasBilling(it);
                            System.out.println(ne);
                            return ne ;
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }).sorted(Comparator.comparing(NaturalGasBilling::Invoice).reversed());
        }

        // Aufgabe 3) e)
        // TODO: Implement object method: "Stream<Byte> toBytes()" for record "NaturalGasBilling".
        public Stream<Byte> toBytes() {

            Stream<String> convert = Stream.of(formatter.format(Invoice), ",", formatter.format(From), ",", formatter.format(To), ",",
                    String.valueOf(Billing), ",", String.valueOf(BilledGJ), ",", String.valueOf(BasicCharge), ",",
                    String.valueOf(DeliveryCharges), ",", String.valueOf(StorageAndTransport), ",",
                    String.valueOf(CommodityCharges), ",", String.valueOf(Tax), ",", CleanEnergyLevy, ",",
                    String.valueOf(CarbonTax), ",", String.valueOf(Amount), "\n");
            StringBuilder res = new StringBuilder();

            convert.forEachOrdered(val -> res.append(val));
            return res.toString().chars().mapToObj(it -> (byte) it);
            //return Stream.of(res).map(val -> Byte.parseByte(String.valueOf(val)));
        }

        // Aufgabe 3) f)
        // TODO: Implement static method: "Stream<Byte> serialize(Stream<NaturalGasBilling> stream)".

        public static Stream<Byte> serialize(Stream<NaturalGasBilling> stream) throws IOException, ParseException {
            Stream<Byte> res = stream.flatMap(val -> val.toBytes());

            File sample = new File("FIles/sample.csv");
            PrintWriter writing = new PrintWriter(sample);

            writing.write("Invoice Date,From Date,To Date,Billing Days," +
                    "Billed GJ,Basic charge,Delivery charges,Storage and transport,Commodity charges," +
                    "Tax,Clean energy levy,Carbon tax,Amount \n");

            res.forEachOrdered(val -> writing.write(val));

            writing.close();

            return res;
        }

        // Aufgabe 3) g)
        // TODO: Implement static method: "Stream<NaturalGasBilling> deserialize(Stream<Byte> stream)".
        // TODO: Execute the call: "deserialize(serialize(orderByInvoiceDateDesc(fileLines(Datei aus f))))"
        // TODO: in a main Method and print the output to the console.

        public static Stream<NaturalGasBilling> deserialize(Stream<Byte> stream) {
            return stream.map(val -> String.valueOf(val))
                    .map(str -> str.split(","))
                    .map(nat -> {
                        try {
                            return new NaturalGasBilling(nat);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return null;
                    });
        }

        // Aufgabe 3) h)
        public static Stream<File> findFilesWith(String dir, String startsWith, String endsWith, int maxFiles) throws IOException {
            // TODO
            var root = Paths.get(dir);
            List<File> files = Files.walk(root)
                    .filter(path -> path.toFile().isFile())
                    .filter(path -> path.toFile().getName().startsWith(startsWith))
                    .filter(path -> path.toFile().getName().endsWith(endsWith))
                    .sorted(Comparator.comparingLong(value -> {
                        try {
                            return Files.size(value);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }))
                    .limit(maxFiles)
                    .map(Path::toFile)
                    .toList();


            return StreamSupport.stream(files.spliterator(), true);
        }
    }
}
