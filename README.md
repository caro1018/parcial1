<h1 align="center">CVDS-lab-3</h1>

#### CLASES DE EQUIVALENCIA

# CREAR UN PROYECTO CON MAVEN
Ejecutamos el siguiente comando para crear un proyecto maven con ayuda de los arquetipos

```sh
$ mvn archetype:generate -DgroupId=edu.eci.cvds -DartifactId=ClasesEquivalencia -DarchetypeArtifactId=maven-archetype-quickstart -Dpackage=edu.eci.cvds.tdd
```

![image](https://github.com/caro1018/CVDS-lab-3/assets/77819591/b61ffd43-8b6f-4e6b-8ffb-4de4610f20a7)

# ACTUALIZAR Y CREAR DEPENDENCIAS EN EL PROYECTO

Ingresamos al repositorio central de maven y buscamos la versión mas reciente de JUnit 

![image](https://github.com/caro1018/CVDS-lab-3/assets/77819591/274ae595-0bb6-43ac-9ad7-cd38901133d1)

Editamos el archivo pom.xml y añadimos la dependencia correspondiente 

![image](https://github.com/caro1018/CVDS-lab-3/assets/77819591/db9d22b3-b1e5-462b-89c6-5499a221c5fc)

Ajustamos la versión de java

![image](https://github.com/caro1018/CVDS-lab-3/assets/77819591/756fda7b-4d71-44ef-b3cb-207e604623e2)

# COMPILAR Y EJECUTAR
Comandos necesarios para compilar y ejecutar las pruebas:

Comando 'package' toma el código compilado y empaquetado en un formato distribuible, como un JAR.
```sh
$ mvn package
```

Comando 'test' ejecuta pruebas utilizando un marco de pruebas unitario adecuado. Estas pruebas no deberían requerir que el código esté empaquetado o implementado.
```sh
$ mvn test
```
# EJERCICIO “REGISTRADURÍA”

Creamos las clases correspondientes tanto para las pruebas como para la aplicación. Añadimos validaciones para la edad, el Id, el genero y si está vivo o no esto para retornar un estado valido de la persona para votar.

![image](https://github.com/caro1018/CVDS-lab-3/assets/77819591/c597ebfb-0880-476b-9cc6-abdf919e7b8d)

Y compilamos/ejecutamos pruebas 

![image](https://github.com/caro1018/CVDS-lab-3/assets/77819591/d90ac0ba-7ab5-4d5c-bcaf-c5dc584594d9)


## FINALIZAR EL EJERCICIO

<p align="center">Implementación clase `RegistryTest.java` .</p>

```java
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
```
<p align="center">Implementación método `registerVoter` en la clase `Registry.java` .</p>


```java
package edu.eci.cvds.tdd.registry;
import java.util.HashMap;

public class Registry {
    private HashMap<Integer,Person> voters;

    public Registry()
    {
        voters = new HashMap<Integer,Person>();
    }

    public RegisterResult registerVoter(Person p)
    {
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
        if(p.getId() <= 0)
        {
            return RegisterResult.INVALID_ID;
        }
        else if (voters.containsKey(p.getId()))
        {
            return RegisterResult.DUPLICATED;
        }
        //Gender
        if(p.getGender() != Gender.FEMALE && p.getGender() != Gender.MALE && p.getGender() != Gender.UNIDENTIFIED)
        {
            return RegisterResult.UNDERAGE;
        }

        voters.put(p.getId(),p);

        return RegisterResult.VALID;
    }

    public RegisterResult validId(Person _p)
    {
        if(_p.getId() <= 0)
        {
            return RegisterResult.INVALID_ID;
        }
        return RegisterResult.VALID;
    }
    public RegisterResult duplicateId(Person _p1, Person _p2)
    {
        if(this.validId(_p1) == RegisterResult.VALID &&
           this.validId(_p2) == RegisterResult.VALID )
        {
            if (_p1.getId() == _p2.getId()) {
                return RegisterResult.DUPLICATED;
            }
        }
        return RegisterResult.VALID;
    }

    public RegisterResult isAlive(Person _p){
        if(_p.isAlive()){
            return RegisterResult.VALID;
        }
        return RegisterResult.DEAD;
    }

    public RegisterResult registerAge (Person _p)
    {
        RegisterResult registerResult = RegisterResult.VALID ;

        if( _p.getAge() >= 0 && _p.getAge() < 18)
        {
            registerResult = RegisterResult.UNDER_AGE;
        }
        else  if(_p.getAge() < 0)
        {
            registerResult = RegisterResult.INVALID_AGE;
        }
        else  if(_p.getAge() > 135)
        {
            registerResult = RegisterResult.INVALID_AGE;
        }
        return registerResult;
    }


}
```
## EJERCICIO "DESCUENTO DE TARIFAS"

### REALIZAR DISEÑO DE PRUEBAS
Documento de diseño de pruebas [EjercicioClasesEquivalencia.docx](https://github.com/caro1018/CVDS-lab-3/files/14353697/EjercicioClasesEquivalencia.docx)


Clase de pruebas `TarifasTest.java` en directorio `src/test/java/edu/eci/cvds/tdd/aerodescuentos`.

```java
package edu.eci.cvds.tdd.aerodescuentos;

import org.junit.Assert;
import org.junit.Test;

public class TarifasTest
{
    private CalculadorDescuentos calDesc = new CalculadorDescuentos();
    private double tarifa;

    @Test
    public void validateInvalidAge()
    {
        tarifa = calDesc.calculoTarifa(2000, 21, -1);

        Assert.assertTrue(tarifa != 999);
    }

    @Test
    public void validateDays()
    {
        this.validateParmsValids(2000, 21, 1, 1600);
        this.validateParmsValids(2000, 21, 17, 1600);
        this.validateParmsValids(2000, 21, 18, 1700);
        this.validateParmsValids(2000, 21, 66, 1540);
        this.validateParmsValids(2000, 1, 1, 1900);
        this.validateParmsValids(2000, 19, 17, 1900);
        this.validateParmsValids(2000, 1, 18, 2000);
        this.validateParmsValids(2000, 19, 64, 2000);
        this.validateParmsValids(2000, 1, 66, 1840);
        this.validateParmsValids(2000, 19, 136, 1840);

        this.validateParmsInValids(2000, 21, -1, 1600);
        this.validateParmsInValids(2000, 21, -17, 1600);
        this.validateParmsInValids(1700, -2, 18, 1700);
        this.validateParmsInValids(0, -21, 66, 1540);
        this.validateParmsInValids(2000, -1, 1, 1900);
        this.validateParmsInValids(0, 19, 17, 1900);
        this.validateParmsInValids(2000, 1, -18, 1900);
    }


    public void validateParmsValids(double _tarifa, int _days, int _age, double _Total)
    {
        tarifa = calDesc.calculoTarifa(_tarifa, _days, _age);
        Assert.assertTrue(tarifa == _Total);
    }

    public void validateParmsInValids(double _tarifa, int _days, int _age, double _Total)
    {
        if(_tarifa <= 0)
        {
            Assert.assertFalse(false);
        }
        else {
            tarifa = calDesc.calculoTarifa(_tarifa, _days, _age);
            Assert.assertFalse(tarifa != _Total);
        }
    }


}

```

Fases de compilación y pruebas.

![image](https://github.com/caro1018/CVDS-lab-3/assets/77819591/edbcf333-1be9-478b-879e-c0942901f48d)


