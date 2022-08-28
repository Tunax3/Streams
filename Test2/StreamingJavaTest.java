import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

class StreamingJavaTest {

    @Test
    void flatStreamOf() {
    }

    @Test
    void mergeStreamsOf() {
    }

    @Test
    void minOf() {
    }

    @Test
    void lastWithOf() {
    }

    @Test
    void findOfCount() {
    }

    @Test
    void makeStreamOf() {
    }

    @Test
    void fileLines() {
    }

    @Test
    void averageCost() {
    }

    @Test
    void countCleanEnergyLevy() {
    }

    @Test
    void orderByInvoiceDateDesc() throws ParseException {
        String[] str = new String[]{"2012-04-19","2012-03-19","2012-04-19","31","3.2","12.06","11.29","4.37","10.76","2.12","","3.97","44.57",
        "2012-05-17","2012-04-19","2012-05-17","28","3.4","10.89","11.99","4.64","10.12","2.09","","4.22","43.95",
        "2012-06-18","2012-05-17","2012-06-18","32","0.9","12.45","3.09","1.23","2.68","1.03","","1.12","21.60"};

        String[] str1 = new String[]{"2012-04-19","2012-03-19","2012-04-19","31","3.2","12.06","11.29","4.37","10.76","2.12","","3.97","44.57"};
        String[] str2 = new String[]{"2012-05-17","2012-04-19","2012-05-17","28","3.4","10.89","11.99","4.64","10.12","2.09","","4.22","43.95"};
        String[] str3 = new String[]{"2012-06-18","2012-05-17","2012-06-18","32","0.9","12.45","3.09","1.23","2.68","1.03","","1.12","21.60"};

        StreamingJava.NaturalGasBilling nat1 = new StreamingJava.NaturalGasBilling(str1);
        StreamingJava.NaturalGasBilling nat2 = new StreamingJava.NaturalGasBilling(str2);
        StreamingJava.NaturalGasBilling nat3 = new StreamingJava.NaturalGasBilling(str3);

        StreamingJava.NaturalGasBilling[] natArr = new StreamingJava.NaturalGasBilling[]{nat1,nat2,nat3};
    }

    @Test
    void findFilesWith() {
    }
}