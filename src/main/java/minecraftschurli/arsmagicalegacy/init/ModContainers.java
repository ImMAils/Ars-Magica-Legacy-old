package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.objects.block.inscriptiontable.InscriptionTableContainer;
import minecraftschurli.arsmagicalegacy.objects.item.spellbook.SpellBookContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Minecraftschurli
 * @version 2019-11-08
 */
public final class ModContainers implements IInit {

    public static final RegistryObject<ContainerType<SpellBookContainer>> SPELLBOOK = CONTAINERS.register("spellbook", () -> new ContainerType<>(SpellBookContainer::new));
    public static final RegistryObject<ContainerType<InscriptionTableContainer>> INSCRIPTION_TABLE = CONTAINERS.register("inscription_table", () -> new ContainerType<>(InscriptionTableContainer::new));
    //public static final RegistryObject<ContainerType<OcculusContainer>> OCCULUS = CONTAINERS.register("occulus", () -> new ContainerType<>(OcculusContainer::new));

    public static void register() {
    }
}
