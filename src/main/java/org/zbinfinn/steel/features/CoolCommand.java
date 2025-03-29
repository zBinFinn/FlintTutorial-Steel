package org.zbinfinn.steel.features;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import dev.dfonline.flint.Flint;
import dev.dfonline.flint.feature.trait.CommandFeature;
import dev.dfonline.flint.util.FlintSound;
import dev.dfonline.flint.util.message.impl.CompoundMessage;
import dev.dfonline.flint.util.message.impl.SoundMessage;
import dev.dfonline.flint.util.message.impl.prefix.InfoMessage;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.sound.SoundEvents;

public class CoolCommand implements CommandFeature {
    @Override
    public String commandName() {
        return "awesome";
    }

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> createCommand(LiteralArgumentBuilder<FabricClientCommandSource> builder, CommandRegistryAccess commandRegistryAccess) {
        return builder.executes(this::run);
    }

    private int run(CommandContext<FabricClientCommandSource> context) {
        Flint.getUser().sendMessage(new CompoundMessage(
            new InfoMessage("Cool! Command!!"),
            new SoundMessage(FlintSound.builder()
                                 .setSound(SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME)
                                 .setPitch(1.8f)
                                 .setVolume(1)
                                 .build())
        ));

        return 0;
    }
}
