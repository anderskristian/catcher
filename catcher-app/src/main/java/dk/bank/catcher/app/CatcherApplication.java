package dk.bank.catcher.app;

import io.vlingo.actors.World;

/**
 * Answers how an application can be started
 */
public class CatcherApplication {

    private final World world;

    private static CatcherApplication app;

    public static void main(String[] args) {
        System.out.println("==============================");
        System.out.println("Starting catcher           ...");
        System.out.println("==============================");
        app=new CatcherApplication();
    }

    private CatcherApplication() {
        this.world = World.startWithDefaults("neo-catcher");

        //TODO ADD APP

        //noinspection AnonymousHasLambdaAlternative
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                app.world.terminate();
                System.out.println("\n");
                System.out.println("==============================");
                System.out.println("World terminated ...");
                System.out.println("==============================");
            }
        });
    }

}
