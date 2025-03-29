package org.zbinfinn.steel;

import dev.dfonline.flint.FlintAPI;
import net.fabricmc.api.ClientModInitializer;
import org.zbinfinn.steel.features.CoolCommand;

public class Steel implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FlintAPI.registerFeature(
            new CoolCommand()
        );
    }
}
