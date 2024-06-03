package mod.lyuxc.specialrules.event;

import mod.lyuxc.specialrules.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Random;

@EventBusSubscriber
public class SpawnZombie {
    public static int i = 600;
    public static boolean set = true;
    @SubscribeEvent
    public static void spawnEvent(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        Level world = event.getEntity().level();
        if (set) {
            double px = player.getX();
            double py = player.getY();
            double pz = player.getZ();
            Random r = new Random();
            int dx = 8 + r.nextInt(8);
            int dz = 8 + r.nextInt(8);
            int x = (int) (r.nextBoolean() ? px + dx : px - dx);
            int z = (int) (r.nextBoolean() ? pz + dz : pz - (double)dz);

            for(int y = (int)py + 12; y >= (int)py - 12; --y) {
                if (world.isEmptyBlock(new BlockPos(x,y,z)) && world.isEmptyBlock(new BlockPos(x, y + 1, z)) && !world.isEmptyBlock(new BlockPos(x,y-1,z))) {
                    createZombie(x, y, z, world, player,1);
                    break;
                }
            }
            set = false;
        }
    }
    @SubscribeEvent
    public static void timeEvent(LevelTickEvent.Pre event) {
        if(!event.getLevel().isClientSide()) {
            if (Config.nowRule.equals(Config.spawnZombie) || Config.nowRule.equals(Config.allCurse)) {
                if (i <= 0) {
                    set = true;
                    i = 1200;
                } else {
                    --i;
                }
            }
        }
    }


    public static void createZombie(int x, int y, int z, Level level, Player player,int i1) {
        while(--i1>=0) {
            Zombie zombie = new Zombie(level);
            zombie.setPos(x, y, z);
            zombie.getAttributes().getInstance(Attributes.MOVEMENT_SPEED).setBaseValue(0.3f);
            zombie.setItemSlot(EquipmentSlot.HEAD,new ItemStack(Items.NETHERITE_HELMET));
            zombie.setItemSlot(EquipmentSlot.CHEST,new ItemStack(Items.NETHERITE_CHESTPLATE));
            zombie.setItemSlot(EquipmentSlot.LEGS,new ItemStack(Items.NETHERITE_LEGGINGS));
            zombie.setItemSlot(EquipmentSlot.FEET,new ItemStack(Items.NETHERITE_BOOTS));
            zombie.setItemSlot(EquipmentSlot.MAINHAND,new ItemStack(Items.NETHERITE_SWORD));
            zombie.setTarget(player);
            level.addFreshEntity(zombie);
        }
    }
}
