package minecraftschurli.arsmagicalegacy.api.network;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Minecraftschurli
 * @version 2020-02-13
 */
public class SyncAffinityPacket implements IPacket {
    private final Map<ResourceLocation, Double> affinities;

    public SyncAffinityPacket(Map<ResourceLocation, Double> affinities) {
        this.affinities = affinities;
    }

    public SyncAffinityPacket(PacketBuffer buf) {
        this.affinities = new HashMap<>();
        int len = buf.readInt();
        for (int i = 0; i < len; i++) {
            this.affinities.put(buf.readResourceLocation(), buf.readDouble());
        }
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeInt(this.affinities.size());
        for (Map.Entry<ResourceLocation, Double> entry : affinities.entrySet()) {
            buf.writeResourceLocation(entry.getKey());
            buf.writeDouble(entry.getValue());
        }
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ArsMagicaAPI.getLocalPlayer().getCapability(CapabilityHelper.getAffinityCapability()).ifPresent(iAffinityStorage -> {
            for (Map.Entry<ResourceLocation, Double> entry : affinities.entrySet()) {
                iAffinityStorage.setAffinityDepth(entry.getKey(), entry.getValue());
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
