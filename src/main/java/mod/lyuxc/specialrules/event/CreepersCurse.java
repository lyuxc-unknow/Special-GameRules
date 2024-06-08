package mod.lyuxc.specialrules.event;

import mod.lyuxc.specialrules.Config;
import mod.lyuxc.specialrules.world.LoadData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber
public class CreepersCurse {
    @SubscribeEvent
    public static void updateToCreeper(EntityTickEvent.Pre event) {
        if(LoadData.getNowRule().equals(Config.creepersCurse) || LoadData.getNowRule().equals(Config.allCurse)) {
            Entity entity = event.getEntity();
            if(entity instanceof Mob) {
                if(!(entity instanceof Creeper) && !(entity instanceof Ghast)) {
                    Level level = entity.level();
                    double x = entity.getX();
                    double y = entity.getY();
                    double z = entity.getZ();
                    entity.setRemoved(Entity.RemovalReason.KILLED);
                    Creeper creeper = new Creeper(EntityType.CREEPER,level);
                    creeper.setPos(x,y,z);
                    creeper.getAttributes().getInstance(Attributes.MOVEMENT_SPEED).setBaseValue(0.5f);
                    level.addFreshEntity(creeper);
                }
            }
        }
    }
}
