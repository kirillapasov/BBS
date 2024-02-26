import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BBSGeneratorTest {
    
    /**
     *      Тест на случайность (тест проверяет, что вычисленная
     *      статистика для случайной последовательности битов
     *      меньше порогового значения 1.82138636. )
     */
    @Test
    public void testFrequencyTest() {
        BBSGenerator bbs = new BBSGenerator();

        String randomBits = bbs.generateBits(1000);
        int[] sequence = new int[randomBits.length()];
        for (int i = 0; i < randomBits.length(); i++) {
            sequence[i] = Integer.parseInt(String.valueOf(randomBits.charAt(i))) * 2 - 1;
        }

        int sum = 0;
        for (int num : sequence) {
            sum += num;
        }

        double statistic = Math.abs((double) sum) / Math.sqrt(randomBits.length());

        // Проверяем условие теста
        assertTrue(statistic < 1.82138636, "Последовательность не является случайной");
    }

    /**
     * Тест на равномерность (Через нормальное распределение с погрешностью 10%)
     */
    @Test
    public void testUniformity() {

        BBSGenerator bbs = new BBSGenerator();
        String randomBits = bbs.generateBits(10000);

        int countZeros = 0;
        int countOnes = 0;
        for (char bit : randomBits.toCharArray()) {
            if (bit == '0') {
                countZeros++;
            } else if (bit == '1') {
                countOnes++;
            } else {
                fail("Неверный символ в последовательности");
            }
        }

        int expectedCount = randomBits.length() / 2;
        double tolerance = expectedCount / 10;

        assertTrue(Math.abs(countZeros - expectedCount) < tolerance, "Неравномерное распределение нулей");
        assertTrue(Math.abs(countOnes - expectedCount) < tolerance, "Неравномерное распределение единиц");
    }
}
