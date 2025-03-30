package org.zbinfinn.steel.features;

import dev.dfonline.flint.Flint;
import dev.dfonline.flint.feature.trait.PacketListeningFeature;
import dev.dfonline.flint.feature.trait.RenderedFeature;
import dev.dfonline.flint.util.result.EventResult;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.OverlayMessageS2CPacket;
import net.minecraft.text.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LagSlayerDisplayFeature implements RenderedFeature, PacketListeningFeature {
    // CPU Usage: [▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮] (02.56%)

    private static final Pattern CPU_REGEX = Pattern.compile(
        "CPU Usage: \\[▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮] \\((\\d\\d\\.\\d\\d)%\\)"
    );

    private String usage = "";
    private long displayUntil = 0;

    @Override
    public void render(DrawContext draw, RenderTickCounter renderTickCounter) {
        if (System.currentTimeMillis() > displayUntil) {
            return;
        }
        TextRenderer textRenderer = Flint.getClient().textRenderer;
        Text text = Text.literal("CPU Usage: ").withColor(0xFF8888).append(
            Text.literal(usage + "%").withColor(0xFFAAAA)
        );
        int x = Flint.getClient().getWindow().getScaledWidth() - textRenderer.getWidth(text) - 3;
        int y = 3;

        draw.drawTextWithShadow(
            textRenderer,
            text,
            x, y,
            0xFFFFFFFF
        );
    }

    @Override
    public EventResult onReceivePacket(Packet<?> packet) {
        if (!(packet instanceof OverlayMessageS2CPacket(Text text))) {
            return EventResult.PASS;
        }

        Matcher matcher = CPU_REGEX.matcher(text.getString());
        if (!matcher.find()) {
            return EventResult.PASS;
        }

        String substring = matcher.group(1);
        displayUntil = System.currentTimeMillis() + 3000;
        usage = substring;

        return EventResult.CANCEL;
    }
}
