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
     * Run application
     */
    public static void main(String[] args) {

        UseName un = new UseName();
        final String name = un.api.getName();
        if(name==null){
            System.out.println("got no name");
        } else {
            System.out.println(String.format("Name is %s",name));
        }
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
