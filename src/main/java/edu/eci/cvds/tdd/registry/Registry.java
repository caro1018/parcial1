package edu.eci.cvds.tdd.registry;

import java.util.HashMap;

public class Registry {
    private HashMap<Integer, Person> voters;

    public Registry() {
        voters = new HashMap<Integer, Person>();
    }

    public RegisterResult registerVoter(Person p) {
        // TODO Validate person and return real result.
        //Alive
        if (!p.isAlive()) {
            return RegisterResult.DEAD;
        }
        //Age

        if (p.getAge() < 0 || p.getAge() > 135) {
            return RegisterResult.INVALID_AGE;
        }
        else if (p.getAge() >= 0 && p.getAge() < 18) {
            return RegisterResult.UNDER_AGE;
        }

        //id
        if (p.getId() <= 0) {
            return RegisterResult.INVALID_ID;
        } else if (voters.containsKey(p.getId())) {
            return RegisterResult.DUPLICATED;
        }
        //Gender
        if (p.getGender() != Gender.FEMALE && p.getGender() != Gender.MALE && p.getGender() != Gender.UNIDENTIFIED) {
            return RegisterResult.UNDERAGE;
        }

        voters.put(p.getId(), p);

        return RegisterResult.VALID;
    }

    public RegisterResult validId(Person _p) {
        if (_p.getId() <= 0) {
            return RegisterResult.INVALID_ID;
        }
        return RegisterResult.VALID;
    }

    public RegisterResult duplicateId(Person _p1, Person _p2) {
        if (this.validId(_p1) == RegisterResult.VALID &&
                this.validId(_p2) == RegisterResult.VALID) {
            if (_p1.getId() == _p2.getId()) {
                return RegisterResult.DUPLICATED;
            }
        }
        return RegisterResult.VALID;
    }

    public RegisterResult isAlive(Person _p) {
        if (_p.isAlive()) {
            return RegisterResult.VALID;
        }
        return RegisterResult.DEAD;
    }

    public RegisterResult registerAge(Person _p) {
        RegisterResult registerResult = RegisterResult.VALID;

        if (_p.getAge() >= 0 && _p.getAge() < 18) {
            registerResult = RegisterResult.UNDER_AGE;
        } else if (_p.getAge() < 0) {
            registerResult = RegisterResult.INVALID_AGE;
        } else if (_p.getAge() > 135) {
            registerResult = RegisterResult.INVALID_AGE;
        }
        return registerResult;
    }


}