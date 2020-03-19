package dk.bank.protocol.step01simple;

import javax.annotation.Nonnull;

public class UseName2 {

    interface Protocol {
        @Nonnull
        String getName();
    }

    final Protocol api;

    /**
     * Assemble the application
     */
    public UseName2() {
        api = new Implementation();
    }



    /**
     *  
     */
    static class Implementation implements Protocol {
        @Override
        @Nonnull
        public String getName() {
            return "Eva";
        }
    }
}
