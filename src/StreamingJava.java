import java.io.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamingJava {
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
        return lines.mapToDouble(s -> Double.parseDouble
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
    public record NaturalGasBilling (Date Invoice,Date From,Date To,double Billing ,double BilledGJ,double BasicCharge,
                                     double DeliveryCharges,double StorageAndTransport,double CommodityCharges,
                                     double Tax,double CleanEnergyLevy,
                                     double CarbonTax,double Amount) {

   /**     public NaturalGasBilling(String[] strings) {
            this {
                format.parse(strings[0]);

            }
        } **/
    }


    public static Stream<NaturalGasBilling> orderByInvoiceDateDesc(Stream<String> stream){
        Stream <NaturalGasBilling> str = Stream.empty();
        //var r = stream.map(it -> it.split(",")).map(it -> new NaturalGasBilling(it));

        return str;
    }

    // Aufgabe 3) e)
    // TODO: Implement object method: "Stream<Byte> toBytes()" for record "NaturalGasBilling".

    // Aufgabe 3) f)
    // TODO: Implement static method: "Stream<Byte> serialize(Stream<NaturalGasBilling> stream)".

    // Aufgabe 3) g)
    // TODO: Implement static method: "Stream<NaturalGasBilling> deserialize(Stream<Byte> stream)".
    // TODO: Execute the call: "deserialize(serialize(orderByInvoiceDateDesc(fileLines(Datei aus f))))"
    // TODO: in a main Method and print the output to the console.

    // Aufgabe 3) h)
    public static Stream<File> findFilesWith(String dir, String startsWith, String endsWith, int maxFiles) {
        // TODO

        return null;
    }
}
