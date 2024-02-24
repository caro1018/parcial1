package edu.eci.cvds.tdd.registry;

import org.junit.Assert;
import org.junit.Test;

public class Parcial {


    public Boolean validated(int edge) throws MyException {

        if (edge >= 0 && edge < 18) {
            return true;
        } else if (edge < 0) {
            throw new MyException("Exception message");
        } else {

            return false;
        }

    }


}