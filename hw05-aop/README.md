## Автоматическое логирование.

Цель:
Понять как реализуется AOP, какие для этого есть технические средства.

Описание/Пошаговая инструкция выполнения домашнего задания:
Разработайте такой функционал:
метод класса можно пометить самодельной аннотацией @Log, например, так:
```java
class TestLogging implements TestLoggingInterface {
    @Log
    public void calculation(int param) {}
}
```
При вызове этого метода "автоматически" в консоль должны логироваться значения параметров.
Например, так:
```java
class Demo {
    public void action() {
        new TestLogging().calculation(6);
    }
}
```
В консоли должно быть:

executed method: calculation, param: 6

Обратите внимание: явного вызова логирования быть не должно.

Учтите, что аннотацию можно поставить, например, на такие методы:

public void calculation(int param1)
public void calculation(int param1, int param2)
public void calculation(int param1, int param2, String param3)

P.S.
Выбирайте реализацию с ASM, если действительно этого хотите и уверены в своих силах.
