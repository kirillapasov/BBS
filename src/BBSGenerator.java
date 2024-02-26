import java.math.BigInteger;
import java.util.Date;


/**
 *
 * Класс, представляет собой генератор случайной битовой последовательности длинны m,
 * который реализует алгоритм BBS. Для реализации API, используется generateBits(int m), где в параметрах указывается кол-во бит
 * последовательности. Метод возвращает String. Для вызова метода, нужно создать экземпляр данного класса
 * @author KirillApasov
 * @version 1.0
 */

public class BBSGenerator {
    private BigInteger p;
    private BigInteger q;
    private BigInteger N;
    private BigInteger s;
    private BigInteger u;

    public BBSGenerator() {
        initialize();
    }


    /**
     * Метод, вызывающийся из конструктора класса, который производит инициализацию
     * полей класса, и вызова определённых методов. p,q - простые, такие, что:
     * (p)mod4 = 3; (q)mod4 = 3; и p != q
     * N = pq; число s взаимно простое с числом N;
     * Вычисляем u0 = s^2 mod N
     */
    private void initialize() {
        p = generatePrime(5);
        q = generatePrime(5);
        while (p.equals(q)) {
            q = generatePrime(5);
        }

        N = p.multiply(q);

        s = generateCoprime(N);


        u = s.pow(2).mod(N);
    }

    /**
     * Генерирует последовательность случайных битов, используя BBS-алгоритм длинны m
     * @param m (Длинна последовательности в битах)
     * @return result (Последовательность случайных бит)
     */
    public String generateBits(int m) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < m; i++) {
            // Вычисляем ui = ui-1^2 mod N
            u = u.pow(2).mod(N);
            // Вычисляем xi как самый младший бит двоичного представления числа ui
            result.append(u.testBit(0) ? "1" : "0");
        }
        return result.toString();
    }

    /**
     *
     * @param bitLength (Кол-во бит в числе)
     * @return prime (Число, проходящее по условиям, для p,q)
     */
    private BigInteger generatePrime(int bitLength) {
        BigInteger prime;
        do {
            prime = getRandom(bitLength);
        } while (!prime.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3)));
        return prime;
    }

    /**
     *
     * @param bitLength (Длинна в битах, для генерации случайного числа)
     * @return randomNumber (Сгенерированное случайное большое число)
     */
    private BigInteger getRandom(int bitLength) {
        Date randomTime = new Date();
        long b = Math.abs(randomTime.hashCode() * randomTime.getTime() * 1817834972);
        String c = b + "";
        while (c.length() < bitLength) {
            randomTime = new Date();
            b = Math.abs(randomTime.hashCode() * randomTime.getTime() * 1817834972);
            c += b;
        }
        String d = c.substring(0, bitLength);
        BigInteger randomNumber = new BigInteger(d);
        return randomNumber;
    }

    /**
     *
     * @param n (Число с которым заданное, должно быть взаимно простым)
     * @return result (Сгенерированное случайное число, взаимно простое с n)
     */
    private BigInteger generateCoprime(BigInteger n) {
        BigInteger result;
        do {
            result = getRandom(n.bitLength());
        } while (!result.gcd(n).equals(BigInteger.ONE));
        return result;
    }
}
