package com.theuran.mappet.client;

import com.theuran.mappet.item.MappetItems;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

import java.time.LocalDate;

public class HammerRenderer {
    public static void init() {
        ModelPredicateProviderRegistry.register(
                MappetItems.HAMMER,
                new Identifier("date"),
                (stack, world, entity, seed) -> {
                    float value = 0.0f;

                    if (isDateInRange(12, 1, 3)) {
                        value = 0.2f;
                    } else if (isDateInRange(5, 2, 3)) {
                        value = 0.4f;
                    } else if (isDateInRange(11, 12, 3)) {
                        value = 0.6f;
                    } else if (isDateInRange(4, 22, 3)) {
                        value = 0.8f;
                    } else if (isDateInRange(12, 2, 7)) {
                        value = 1f;
                    }

                    return value;
                }
        );
    }

    private static boolean isDateInRange(int month, int startDay, int dayRange) {
        LocalDate today = LocalDate.now();
        return today.getMonthValue() == month &&
                today.getDayOfMonth() >= startDay &&
                today.getDayOfMonth() <= startDay + dayRange;
    }
}
