package characters.romans;

import enums.Gender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PrefectTests {

    @Test
    void testLeadDoesNotThrow() {
        Prefect prefect = new Prefect(
                "Caius Bonus",
                Gender.MALE,
                180,
                45,
                9,
                6,
                100,
                5,
                3,
                0
        );

        assertDoesNotThrow(prefect::lead);
    }
}
