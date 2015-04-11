package eu.matejkormuth.rpgdavid.starving.remote.netty.packets;

import io.netty.buffer.ByteBuf;
import eu.matejkormuth.rpgdavid.starving.remote.netty.Packet;

public class DebugPacket extends Packet {

    public String content;

    // Need to have argless constructor.
    public DebugPacket() {
    }

    public DebugPacket(String content) {
        this.content = content;
    }

    @Override
    public void writeTo(ByteBuf toBuffer) {
        toBuffer.writeShort(content.length());
        toBuffer.writeBytes(content.getBytes(PROTOCOL_ENCODING));
    }

    @Override
    public void readFrom(ByteBuf fromBuffer) {
        short length = fromBuffer.readShort();
        byte[] array = new byte[length];
        fromBuffer.readBytes(array);
        this.content = new String(array, PROTOCOL_ENCODING);
    }

}
