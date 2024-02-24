package edu.eci.cvds.tdd.registry;

import org.junit.Assert;
import org.junit.Test;

public class RegistryTest {

    // TODO Complete with more test cases
    @Test
    public void validateRegistryResult() {
        Registry registry = new Registry();
        Person person1 = new Person();
        person1.setAge(18);
        person1.setId(1019153396);
        RegisterResult result = registry.registerVoter(person1);
        Assert.assertEquals(RegisterResult.VALID, result);
    }

    @Test
    public void validateIsLive(){
        Registry registry = new Registry();
        Person person1 = new Person();
        person1.setAlive(true);
        person1.setId(1019153396);
        RegisterResult result = registry.registerVoter(person1);
        Assert.assertEquals(RegisterResult.VALID, result);
    }

    @Test
    public void validateIsDead(){
        Registry registry = new Registry();
        Person person1 = new Person();
        person1.setAlive(false);
        RegisterResult result = registry.registerVoter(person1);
        Assert.assertEquals(RegisterResult.DEAD, result);
    }


    public void validateAge(Integer age, RegisterResult expectedValue){
        Registry registry = new Registry();
        Person person1 = new Person();
        person1.setAge(age);
        person1.setId(1019153396);
        RegisterResult result = registry.registerVoter(person1);
        Assert.assertEquals(expectedValue, result);
    }

    @Test
    public void validateAge(){

        validateAge(-1000, RegisterResult.INVALID_AGE);
        validateAge(-1, RegisterResult.INVALID_AGE);
        validateAge(0, RegisterResult.UNDER_AGE);
        validateAge(1, RegisterResult.UNDER_AGE);
        validateAge(17, RegisterResult.UNDER_AGE);
        validateAge(18, RegisterResult.VALID);
        validateAge(135, RegisterResult.VALID);
        validateAge(136, RegisterResult.INVALID_AGE);
        validateAge(10000, RegisterResult.INVALID_AGE);


    }

    @Test
    public void validateCorrectID()
    {
        Registry registry = new Registry();
        Person person = new Person();
        person.setId(1019153396);
        person.setGender(Gender.MALE);
        RegisterResult result = registry.registerVoter(person);
        Assert.assertEquals(RegisterResult.VALID, result);
    }

    @Test
    public void validateIncorrectID()
    {
        Registry registry = new Registry();
        Person person = new Person();
        person.setGender(Gender.MALE);
        RegisterResult result = registry.registerVoter(person);
        Assert.assertEquals(RegisterResult.INVALID_ID, result);
    }

    @Test
    public void validateUnidentifiedGender()
    {
        Registry registry = new Registry();
        Person person = new Person();
        person.setId(1019153396);
        person.setGender(Gender.FEMALE);
        RegisterResult result = registry.registerVoter(person);
        Assert.assertEquals(RegisterResult.VALID, result);
    }

    @Test
    public void validateDuplicate()
    {
        Registry registry = new Registry();
        Person person1 = new Person();
        person1.setId(1019153396);

        Person person2 = new Person();
        person2.setId(1019153396);

        RegisterResult result = registry.registerVoter(person1);
        RegisterResult result2 = registry.registerVoter(person2);

        Assert.assertEquals(RegisterResult.DUPLICATED, result2);
    }

}