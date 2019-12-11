package minecraftschurli.arsmagicalegacy.api.spell.crafting;

import minecraftschurli.arsmagicalegacy.objects.spell.EssenceType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * @author Minecraftschurli
 * @version 2019-11-19
 */
public class EssenceSpellIngredient implements ISpellIngredient {
    private int amount;
    private EssenceType essenceType;

    EssenceSpellIngredient(CompoundNBT nbt) {
        deserializeNBT(nbt);
    }

    public EssenceSpellIngredient(EssenceType type, int amount) {
        this.essenceType = type;
        this.amount = amount;
    }

    public EssenceSpellIngredient(EssenceType type) {
        this(type, 1);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString(TYPE_KEY, "essence");
        nbt.putString("essence", essenceType.name());
        nbt.putInt("amount", amount);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        nbt.remove(TYPE_KEY);
        essenceType = EssenceType.valueOf(nbt.getString("essence"));
        amount = nbt.getInt("amount");
    }

    @Override
    public boolean canCombine(ISpellIngredient other) {
        return (other instanceof EssenceSpellIngredient) && ((EssenceSpellIngredient) other).essenceType == this.essenceType;
    }

    @Override
    public ISpellIngredient combine(ISpellIngredient other) {
        return new EssenceSpellIngredient(essenceType, amount + ((EssenceSpellIngredient)other).amount);
    }

    @Override
    public ITextComponent getTooltip() {
        return new TranslationTextComponent(essenceType.getTranslationKey()).appendText(": "+amount);
    }
}
