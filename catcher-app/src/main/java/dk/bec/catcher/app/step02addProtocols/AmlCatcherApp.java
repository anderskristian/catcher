package dk.bec.catcher.app.step02addProtocols;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Demonstration of how to catch criminal transactions
 * We search for suspicious words in the posting text.
 * <p>
 * This demo got less problems that demo01
 * <p>
 * Now we got interfaces
 */
public class AmlCatcherApp {


    /**
     * Business protocol
     */
    public interface Catcher {
        List<Posting> checkFraudAtDay(LocalDate aDay);
    }

    /**
     * Adapter protocol
     */
    public interface Repository {
        List<Posting> selectPostings(LocalDate aDay);
    }

    final Catcher catcher;
    final Repository repository;

    /**
     * Create the application
     */
    public AmlCatcherApp() {
        repository = new RepositoryImpl();
        catcher = new CatcherImpl(repository);
    }

    /**
     * Service checkFraudAtDay
     */
    public List<Posting> checkFraudAtDay(LocalDate aDay){
       return catcher.checkFraudAtDay(aDay);
    }

    /**
     * Implementation of the business code does not know repository's internal design.
     */
    static class CatcherImpl implements Catcher {

        final Repository repository;

        public CatcherImpl(Repository repository) {
            this.repository = repository;
        }

        /**
         * Business level solution that checks for bad words and returns postings with bad words.
         * @return list of postings we have caught - hallelujah
         */
        public List<Posting> checkFraudAtDay(LocalDate aDay) {
            final List<Posting> rows=new ArrayList<>();
            final List<Posting> daysPostings = repository.selectPostings(aDay);
            for(Posting p: daysPostings){
                if(p.postingText.contains("pimp")){
                    rows.add(p);
                }
            }
            for(Posting p: daysPostings){
                if(p.postingText.toLowerCase().contains("orange")){
                    rows.add(p);
                }
            }
            return rows;
        }

//        /**
//         * Business level solution that checks for bad words and returns postings with bad words.
//         *
//         * Design with streaming
//         *
//         * @return list of postings we have caught - hallelujah
//         */
//        public List<Posting> checkFraudAtDay(LocalDate aDay) {
//            final List<Posting> daysPostings = repository.selectPostings(aDay);
//            final List<Posting> candidates;
//
//            candidates=daysPostings.stream()
//                    .filter(row->(row.postingText.contains("pimp") || row.postingText.toLowerCase().contains("orange")))
//                    .collect(Collectors.toList());
//
//            return candidates;
//        }
    }

    public static class RepositoryImpl implements Repository {
        public List<Posting> selectPostings(LocalDate aDay) {
            Postings database = new Postings();
            final List<Posting> rows = new ArrayList<>();
            for (Posting p : database.rows) {
                if (p.date.isEqual(aDay)) {
                    rows.add(p);
                }
            }
            return rows;
        }


        public static class Postings {

            final List<Posting> rows;

            public Postings() {
                String[] csv = new String[]{
                        "2020-04-01;Birthday present to my wife;-300.00"
                        , "2020-04-01;BS Topdenmark Lifeinsurance;-6179.00"
                        , "2020-04-01;Orange payment for building Julias terrase;8000.00"
                        , "2020-04-01;Paying the pimp for yesterday;-2000.00"
                        , "2020-04-02;Paying the pimp for yesterday;-2000.00"
                };
                rows = readCsv(csv);
            }

            private List<Posting> readCsv(String[] csv) {
                final List<Posting> rows;
                rows = new ArrayList<>();
                for (String post : csv) {
                    String[] fields = post.split(";");
                    LocalDate date = LocalDate.parse(fields[0]);
                    String text = fields[1];
                    Double amount = Double.parseDouble(fields[2]);
                    Posting p = new Posting(date, text, amount);
                    rows.add(p);
                }
                return rows;
            }
        }

    }

    public static class Posting {
        final LocalDate date;
        final String postingText;
        final Double amount;

        public Posting(LocalDate date, String postingText, Double amount) {
            this.date = date;
            this.postingText = postingText;
            this.amount = amount;
        }
    }

}
