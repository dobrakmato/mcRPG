/*
 *  Starving is a open source bukkit/spigot mmo game.
 *  Copyright (C) 2014-2015 Matej Kormuth
 *  This file is a part of Starving. <http://www.starving.eu>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package eu.matejkormuth.rpgdavid.starving.npc.util;

import java.util.Set;

import net.minecraft.server.v1_8_R2.EntityPlayer;
import net.minecraft.server.v1_8_R2.EnumProtocolDirection;
import net.minecraft.server.v1_8_R2.IChatBaseComponent;
import net.minecraft.server.v1_8_R2.MinecraftServer;
import net.minecraft.server.v1_8_R2.NetworkManager;
import net.minecraft.server.v1_8_R2.Packet;
import net.minecraft.server.v1_8_R2.PacketPlayInAbilities;
import net.minecraft.server.v1_8_R2.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R2.PacketPlayInBlockDig;
import net.minecraft.server.v1_8_R2.PacketPlayInBlockPlace;
import net.minecraft.server.v1_8_R2.PacketPlayInChat;
import net.minecraft.server.v1_8_R2.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R2.PacketPlayInCloseWindow;
import net.minecraft.server.v1_8_R2.PacketPlayInCustomPayload;
import net.minecraft.server.v1_8_R2.PacketPlayInEnchantItem;
import net.minecraft.server.v1_8_R2.PacketPlayInEntityAction;
import net.minecraft.server.v1_8_R2.PacketPlayInFlying;
import net.minecraft.server.v1_8_R2.PacketPlayInHeldItemSlot;
import net.minecraft.server.v1_8_R2.PacketPlayInKeepAlive;
import net.minecraft.server.v1_8_R2.PacketPlayInResourcePackStatus;
import net.minecraft.server.v1_8_R2.PacketPlayInSetCreativeSlot;
import net.minecraft.server.v1_8_R2.PacketPlayInSettings;
import net.minecraft.server.v1_8_R2.PacketPlayInSpectate;
import net.minecraft.server.v1_8_R2.PacketPlayInSteerVehicle;
import net.minecraft.server.v1_8_R2.PacketPlayInTabComplete;
import net.minecraft.server.v1_8_R2.PacketPlayInTransaction;
import net.minecraft.server.v1_8_R2.PacketPlayInUpdateSign;
import net.minecraft.server.v1_8_R2.PacketPlayInUseEntity;
import net.minecraft.server.v1_8_R2.PacketPlayInWindowClick;
import net.minecraft.server.v1_8_R2.PlayerConnection;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;

import eu.matejkormuth.rpgdavid.starving.annotations.NMSHooks;

@NMSHooks(version = "v1_8_R2")
public class NullPlayerConnection extends PlayerConnection {

    public NullPlayerConnection(MinecraftServer minecraftServer,
            NetworkManager conn, EntityPlayer entityplayer) {
        super(minecraftServer, new NullNetworkManager(
                EnumProtocolDirection.SERVERBOUND), entityplayer);
    }

    @Override
    public CraftPlayer getPlayer() {
        return super.getPlayer();
    }

    @Override
    public void c() {
    }

    @Override
    public NetworkManager a() {
        return super.a();
    }

    @Override
    public void disconnect(String s) {
        throw new UnsupportedOperationException("Can't disconnect NPC!");
    }

    @Override
    public void a(PacketPlayInSteerVehicle packetplayinsteervehicle) {
    }

    @Override
    public void a(PacketPlayInFlying packetplayinflying) {
    }

    @Override
    public void a(double d0, double d1, double d2, float f, float f1) {
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void a(double d0, double d1, double d2, float f, float f1, Set set) {
    }

    @Override
    public void teleport(Location dest) {
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void teleport(Location dest, Set set) {
    }

    @Override
    public void a(PacketPlayInBlockDig packetplayinblockdig) {
    }

    @Override
    public void a(PacketPlayInBlockPlace packetplayinblockplace) {
    }

    @Override
    public void a(PacketPlayInSpectate packetplayinspectate) {
    }

    @Override
    public void a(PacketPlayInResourcePackStatus packetplayinresourcepackstatus) {
    }

    @Override
    public void a(IChatBaseComponent ichatbasecomponent) {
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void sendPacket(Packet packet) {
    }

    @Override
    public void a(PacketPlayInHeldItemSlot packetplayinhelditemslot) {
    }

    @Override
    public void a(PacketPlayInChat packetplayinchat) {
    }

    @Override
    public void chat(String s, boolean async) {
    }

    @Override
    public void a(PacketPlayInArmAnimation packetplayinarmanimation) {
    }

    @Override
    public void a(PacketPlayInEntityAction packetplayinentityaction) {
    }

    @Override
    public void a(PacketPlayInUseEntity packetplayinuseentity) {
    }

    @Override
    public void a(PacketPlayInClientCommand packetplayinclientcommand) {
    }

    @Override
    public void a(PacketPlayInCloseWindow packetplayinclosewindow) {
    }

    @Override
    public void a(PacketPlayInWindowClick packetplayinwindowclick) {
    }

    @Override
    public void a(PacketPlayInEnchantItem packetplayinenchantitem) {
    }

    @Override
    public void a(PacketPlayInSetCreativeSlot packetplayinsetcreativeslot) {
    }

    @Override
    public void a(PacketPlayInTransaction packetplayintransaction) {
    }

    @Override
    public void a(PacketPlayInUpdateSign packetplayinupdatesign) {
    }

    @Override
    public void a(PacketPlayInKeepAlive packetplayinkeepalive) {
    }

    @Override
    public void a(PacketPlayInAbilities packetplayinabilities) {
    }

    @Override
    public void a(PacketPlayInTabComplete packetplayintabcomplete) {
    }

    @Override
    public void a(PacketPlayInSettings packetplayinsettings) {
    }

    @Override
    public void a(PacketPlayInCustomPayload packetplayincustompayload) {
    }

    @Override
    public boolean isDisconnected() {
        return false;
    }

}
