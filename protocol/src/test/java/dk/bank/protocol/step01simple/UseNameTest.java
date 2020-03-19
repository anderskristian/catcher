package dk.bank.protocol.step01simple;

import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Nonnull;

public class UseNameTest {


    /**
     * This test demonstrate that
     */

    @Test
    public void getName() {

        UseName un = new UseName();
        final String name = un.api.getName();

        Assert.assertNotNull("Check that name is not null, might be protocol does not tell that", name);
        System.out.println(String.format("Name is %s", name));

    }


    /**
     * Run application
     * <p>
     * NOTE that test for name==null is not necessary when using {@link Nonnull}
     */
    @Test
    public void getNameWithAnnotation() {

        UseName2 un = new UseName2();
        final String name = un.api.getName();
        if (name == null) {
            System.out.println("got no name");
        } else {
            System.out.println(name);
        }
        Assert.assertNotNull("Check that name is not null, protocol tells never null", name);
    }

}