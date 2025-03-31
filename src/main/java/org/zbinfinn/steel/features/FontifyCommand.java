package org.zbinfinn.steel.features;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import dev.dfonline.flint.Flint;
import dev.dfonline.flint.feature.impl.CommandSenderFeature;
import dev.dfonline.flint.feature.trait.CommandFeature;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class FontifyCommand implements CommandFeature {
    private static final Map<Character, Character> smallCapsMapping = new HashMap<>();
    private static final Map<Character, Character> bubbleMapping = new HashMap<>();
    private static final Map<Character, Character> subScriptMapping = new HashMap<>();
    private static final Map<Character, Character> superScriptMapping = new HashMap<>();

    static {
        char[] smallCapsChars = "ᴀʙᴄᴅᴇꜰɢʜɪᴊᴋʟᴍɴᴏᴘꞯʀꜱᴛᴜᴠᴡxʏᴢᴀʙᴄᴅᴇꜰɢʜɪᴊᴋʟᴍɴᴏᴘꞯʀꜱᴛᴜᴠᴡxʏᴢ0123456789".toCharArray();
        char[] bubbleChars = "ⓐⓑⓒⓓⓔⓕⓖⓗⓘⓙⓚⓛⓜⓝⓞⓟⓠⓡⓢⓣⓤⓥⓦⓧⓨⓩⒶⒷⒸⒹⒺⒻⒼⒽⒾⒿⓀⓁⓂⓃⓄⓅⓆⓇⓈⓉⓊⓋⓌⓍⓎⓏ⓪①②③④⑤⑥⑦⑧⑨".toCharArray();
        char[] subScriptChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ₀₁₂₃₄₅₆₇₈₉".toCharArray();
        char[] superScriptChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ⁰¹²³⁴⁵⁶⁷⁸⁹".toCharArray();
        char[] normalChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

        for (int i = 0; i < normalChars.length; i++) {
            smallCapsMapping.put(normalChars[i], smallCapsChars[i]);
            bubbleMapping.put(normalChars[i], bubbleChars[i]);
            subScriptMapping.put(normalChars[i], subScriptChars[i]);
            superScriptMapping.put(normalChars[i], superScriptChars[i]);
        }
    }

    @Override
    public String commandName() {
        return "fontify";
    }

    @Override
    public Set<String> aliases() {
        return Set.of(
            "font"
        );
    }

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> createCommand(LiteralArgumentBuilder<FabricClientCommandSource> builder, CommandRegistryAccess commandRegistryAccess) {
        return builder.then(
            literal("smallcaps").then(
                argument("text", StringArgumentType.greedyString()).executes(context -> fontify(context, smallCapsMapping))
            )
        ).then(
            literal("bubble").then(
                argument("text", StringArgumentType.greedyString()).executes(context -> fontify(context, bubbleMapping))
            )
        ).then(
            literal("subscript").then(
                argument("text", StringArgumentType.greedyString()).executes(context -> fontify(context, subScriptMapping))
            )
        ).then(
            literal("superscript").then(
                argument("text", StringArgumentType.greedyString()).executes(context -> fontify(context, superScriptMapping))
            )
        );
    }

    private int fontify(CommandContext<FabricClientCommandSource> context, Map<Character, Character> map) {
        String text = StringArgumentType.getString(context, "text");

        for (Map.Entry<Character, Character> entry : map.entrySet()) {
            text = text.replaceAll(entry.getKey().toString(), entry.getValue().toString());
        }

        CommandSenderFeature.queue("txt " + text);

        return 0;
    }
}
