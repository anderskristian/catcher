package dk.bank.protocol.step01simple;

public class UseName {

    interface Protocol {
        String getName();
    }

    final Protocol api;

    /**
     * Assemble the application
     */
    public UseName() {
        api = new Implementation();
    }

    /**
     *
     */
    static class Implementation implements Protocol {
        @Override
        public String getName() {
            return "Eva";
//            return null;
        }
    }
}
