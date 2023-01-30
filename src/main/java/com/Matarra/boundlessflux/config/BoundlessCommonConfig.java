package com.Matarra.boundlessflux.config;

import com.sun.jdi.FloatValue;
import net.minecraftforge.common.ForgeConfigSpec;

public class BoundlessCommonConfig
{
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    // MISC
    public static final ForgeConfigSpec.ConfigValue<Boolean> DRAGON_EGG_SPAWN_OVERRIDE;

    // SWORD
    public static final ForgeConfigSpec.ConfigValue<Integer> DEFAULT_MAX_ENERGY_SWORD;
    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_ENERGY_CAP_SWORD;
    public static final ForgeConfigSpec.ConfigValue<Double> PERCENT_ENERGY_CONSUMED_ONHIT_SWORD;
    public static final ForgeConfigSpec.ConfigValue<Double> PERCENT_ENERGY_CONVERTED_TO_DAMAGE_SWORD;

    // BOW DATA
    public static final ForgeConfigSpec.ConfigValue<Integer> DEFAULT_MAX_ENERGY_BOW;
    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_ENERGY_CAP_BOW;
    public static final ForgeConfigSpec.ConfigValue<Double> PERCENT_ENERGY_CONSUMED_ONFIRE_BOW;
    public static final ForgeConfigSpec.ConfigValue<Double> PERCENT_ENERGY_CONVERTED_TO_DAMAGE_BOW;
    static {
        BUILDER.push("-- Configs for Boundless Flux --");

        BUILDER.comment("-- MISC -- ");
        DRAGON_EGG_SPAWN_OVERRIDE = BUILDER.comment("Allows the dragon to spawn multiple eggs. Can " +
                "disable this if enough mod already handles this feature without confliction.")
                        .define("Dragon_Egg_Override", true);

        BUILDER.comment("-- SWORDS --");
        DEFAULT_MAX_ENERGY_SWORD = BUILDER.comment("Default Max Energy the Boundless Sword will have.")
                .defineInRange("Max_Energy_Sword", 1000000, 1, 999999999);
        MAX_ENERGY_CAP_SWORD = BUILDER.comment("Energy CAP the Boundless Sword will have. The max" +
                        " energy will not climb above this value.")
                .defineInRange("Energy_CAP_Sword", 999999999, 1, 999999999);
        PERCENT_ENERGY_CONSUMED_ONHIT_SWORD = BUILDER.comment("Percentage of energy consumed on hit. Ex: 0.01" +
                        " would consume 1% of your swords remaining energy on hit.")
                .defineInRange("Percentage_Energy_Consumed_Hit_Sword", 0.01d, 0.00001d, 1d);
        PERCENT_ENERGY_CONVERTED_TO_DAMAGE_SWORD = BUILDER.comment("Percentage of CONSUMED energy that will be converted" +
                        " to damage. Ex: 1,000,000 (current stored RF) * 0.01 (consumed energy) * 0.02 (damage conversion) = damage")
                .defineInRange("Percentage_Energy_Converted_Damage", 0.001d, 0.00001d, 1d);

        BUILDER.comment("-- BOWS --");
        DEFAULT_MAX_ENERGY_BOW = BUILDER.comment("Default Max Energy the Boundless Bow will have.")
                .defineInRange("Max_Energy_Bow", 1000000, 1, 999999999);
        MAX_ENERGY_CAP_BOW = BUILDER.comment("Energy CAP the Boundless Sword will have. The max" +
                        " energy will not climb above this value.")
                .defineInRange("Energy_CAP_Bow", 999999999, 1, 999999999);
        PERCENT_ENERGY_CONSUMED_ONFIRE_BOW = BUILDER.comment("Percentage of energy consumed on fire. Ex: 0.01" +
                        " would consume 1% of your bows remaining energy on fire.")
                .defineInRange("Percentage_Energy_Consumed_Fire_Bow", 0.01d, 0.00001d, 1d);
        PERCENT_ENERGY_CONVERTED_TO_DAMAGE_BOW = BUILDER.comment("Percentage of CONSUMED energy that will be converted" +
                        " to damage. Ex: 1,000,000 (current stored RF) * 0.01 (consumed energy) * 0.02 (damage conversion) = damage")
                .defineInRange("Percentage_Energy_Converted_Damage_Bow", 0.001d, 0.00001d, 1d);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
