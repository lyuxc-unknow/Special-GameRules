package mod.lyuxc.specialrules.event;

import mod.lyuxc.specialrules.Config;
import mod.lyuxc.specialrules.utils.Utils;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber
public class EternalCursePlus {
    @SubscribeEvent
    public static void eternalCursePlusEvent(LivingIncomingDamageEvent event) {
        if(Utils.isEnableRule(Config.eternalCursePlus)) {
            event.setCanceled(true);
        }
    }
}
