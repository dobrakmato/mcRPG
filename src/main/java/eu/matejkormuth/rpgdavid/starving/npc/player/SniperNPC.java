package eu.matejkormuth.rpgdavid.starving.npc.player;

import net.minecraft.server.v1_8_R1.World;

import org.bukkit.Location;
import org.bukkit.Material;

import com.mojang.authlib.GameProfile;

import eu.matejkormuth.bukkit.Items;
import eu.matejkormuth.rpgdavid.starving.npc.PlayerNPC;

public class SniperNPC extends PlayerNPC {
    public SniperNPC(Location spawnLocation, World world,
            GameProfile gameprofile) {
        super(spawnLocation, world, gameprofile);
        this.getBukkitEntity().setItemInHand(Items.of(Material.DIAMOND_AXE));
    }
}
