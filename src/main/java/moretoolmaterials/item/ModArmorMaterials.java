package moretoolmaterials.item;

import moretoolmaterials.MoreToolMaterials;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public enum ModArmorMaterials implements IArmorMaterial
{
    EMERALD("emerald", 33, new int[]{3, 6, 8, 3}, 15, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.0F, 0.0F, () ->
    {
        return Ingredient.of(Items.LEATHER);
    }),
    LAPIS("lapis", 30, new int[]{2, 5, 6, 2}, 30, SoundEvents.ARMOR_EQUIP_DIAMOND, 0.5F, 0.0F, () ->
    {
        return Ingredient.of(Items.IRON_INGOT);
    }),
    OBSIDIAN("obsidian", 37, new int[]{3, 6, 8, 3}, 12, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.0F, 0.1F, () ->
    {
        return Ingredient.of(Items.IRON_INGOT);
    }),
    REDSTONE("redstone", 27, new int[]{2, 5, 6, 2}, 9, SoundEvents.ARMOR_EQUIP_IRON, 0.5F, 0.0F, () ->
    {
        return Ingredient.of(Items.NETHERITE_INGOT);
    });

    private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
    private final String name;
    private final int maxDamageFactor;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyValue<Ingredient> repairMaterial;

    ModArmorMaterials(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, float toughness, float knockbackResistance, Supplier<Ingredient> repairMaterial)
    {
        this.name = MoreToolMaterials.MOD_ID + ":" + name;
        this.maxDamageFactor = maxDamageFactor;
        this.damageReductionAmountArray = damageReductionAmountArray;
        this.enchantability = enchantability;
        this.soundEvent = soundEvent;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairMaterial = new LazyValue<>(repairMaterial);
    }

    public int getDurabilityForSlot(EquipmentSlotType slotIn)
    {
        return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
    }

    public int getDefenseForSlot(EquipmentSlotType slotIn)
    {
        return this.damageReductionAmountArray[slotIn.getIndex()];
    }

    public int getEnchantmentValue()
    {
        return this.enchantability;
    }

    public SoundEvent getEquipSound()
    {
        return this.soundEvent;
    }

    public Ingredient getRepairIngredient()
    {
        return this.repairMaterial.get();
    }

    @OnlyIn(Dist.CLIENT)
    public String getName()
    {
        return this.name;
    }

    public float getToughness()
    {
        return this.toughness;
    }

    /**
     * Gets the percentage of knockback resistance provided by armor of the material.
     */
    public float getKnockbackResistance()
    {
        return this.knockbackResistance;
    }
}
