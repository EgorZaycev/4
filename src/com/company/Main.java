package com.company;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;


public class Main {

    static class P {
        int id, salary;
        String name, gender, birthday;
        A subunit;

        public P (int a, String b, String c, String d, int e, A f) {
            id = a;
            name = b;
            birthday = d;
            gender = c;
            salary = e;
            subunit = f;
        }
    }

    static class A {
        String s;
        int i;

        public A(String a, int b) {
            s = a;
            i = b;
        }
    }
    public static void main(String[] args) throws IOException {
        String csvFilePath = "foreign_names.csv";
        ArrayList<P> a = new ArrayList<>();
        HashSet<String> hs = new HashSet<>();
        Map<String, A> m = new HashMap<>();
        try (InputStream in = Main.class.getClassLoader().getResourceAsStream(csvFilePath)) {
            CSVReaderBuilder builder;
            builder = new CSVReaderBuilder(new FileReader(csvFilePath));
            CSVReader r = builder.withSkipLines(1).build();
            if (r == null) {
                throw new FileNotFoundException(csvFilePath);
            }
            String[] s;
            int i = 0;
            while ((s = r.readNext()) != null) {
                s = s[0].split(";");
                if (s.length > 2) {
                    if (!hs.contains(s[4])) {
                        hs.add(s[4]);
                        m.put(s[4], new A(s[4], i++));
                    }
                    a.add(new P(Integer.parseInt(s[0]), s[1],
                            s[2], s[3], Integer.parseInt(s[5]),  m.get(s[4])));
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();

        } finally {
            for (P p: a) {
                System.out.println(p.id + " " + p.name + " " + p.gender
                        + " " + p.birthday + " " + p.subunit.s + " "  + p.subunit.i + " " + p.salary);
            }
        }
    }

}