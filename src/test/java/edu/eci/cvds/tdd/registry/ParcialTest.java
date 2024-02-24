package edu.eci.cvds.tdd.registry;

import org.junit.Assert;
import org.junit.Test;

public class ParcialTest {
    @Test
    public void trueValidated() throws MyException {
        Parcial parcial = new Parcial();
        Assert.assertTrue(parcial.validated(15));
    }

    @Test
    public void falseValidated() throws MyException {
        Parcial parcial = new Parcial();
        Assert.assertFalse(parcial.validated(19));
    }

    @Test(expected=MyException.class)
    public void errorValidate() throws MyException {
        Parcial parcial = new Parcial();
        parcial.validated(-10);
    }

}
