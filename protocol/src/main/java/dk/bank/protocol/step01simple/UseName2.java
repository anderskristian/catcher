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
     * Run application
     *
     * NOTE that test for name==null is not necessary when using {@link Nonnull}
     */
    public static void main(String[] args) {

        UseName2 un = new UseName2();
        final String name = un.api.getName();
        if(name==null){
            System.out.println("got no name");
        } else {
            System.out.println(name);
        }
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
