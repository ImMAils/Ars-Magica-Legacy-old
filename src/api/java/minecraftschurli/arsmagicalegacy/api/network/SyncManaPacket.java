package minecraftschurli.arsmagicalegacy.api.network;

import minecraftschurli.arsmagicalegacy.api.*;
import minecraftschurli.arsmagicalegacy.api.capability.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.network.*;

import java.util.function.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-14
 */
public class SyncManaPacket implements IPacket {
    private float mana;
    private float maxMana;

    public SyncManaPacket(PacketBuffer buf) {
        this.maxMana = buf.readFloat();
        this.mana = buf.readFloat();
    }

    public SyncManaPacket(float mana, float maxMana) {
        this.maxMana = maxMana;
        this.mana = mana;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeFloat(this.maxMana);
        buf.writeFloat(this.mana);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ArsMagicaAPI.getLocalPlayer().getCapability(CapabilityHelper.getManaCapability()).ifPresent(iManaStorage -> {
            iManaStorage.setMaxMana(this.maxMana);
            iManaStorage.setMana(this.mana);
        });
        ctx.get().setPacketHandled(true);
    }
}
