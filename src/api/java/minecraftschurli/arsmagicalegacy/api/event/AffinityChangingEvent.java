package minecraftschurli.arsmagicalegacy.api.event;

import minecraftschurli.arsmagicalegacy.api.affinity.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.eventbus.api.*;

/**
 * @author Minecraftschurli
 * @version 2020-02-13
 */
@Cancelable
public class AffinityChangingEvent extends PlayerEvent {
    public final Affinity affinity;
    public float amount;

    public AffinityChangingEvent(PlayerEntity caster, Affinity affinity, float amount) {
        super(caster);
        this.affinity = affinity;
        this.amount = amount;

    }
}
