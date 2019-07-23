package com.ericlam.mc.testgame.commands;

import com.ericlam.mc.minigames.core.commands.ArenaCommandNode;
import com.ericlam.mc.minigames.core.exception.arena.create.ArenaNotExistException;
import com.ericlam.mc.minigames.core.manager.ArenaCreateManager;
import com.ericlam.mc.testgame.GameCreateArena;
import com.hypernite.mc.hnmc.core.managers.ConfigManager;
import com.hypernite.mc.hnmc.core.misc.commands.CommandNode;
import com.hypernite.mc.hnmc.core.misc.permission.Perm;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.List;

public class ArenaSendTitleCommand extends ArenaCommandNode {

    private final ConfigManager configManager;

    public ArenaSendTitleCommand(ConfigManager configManager, CommandNode parent) {
        super(configManager.getPureMessage("prefix"), parent, "settitle", Perm.OWNER, "設置是否發送 title", "<arena> <boolean>", "set-title");
        this.configManager = configManager;
    }


    @Override
    protected boolean executeArenaOperation(@Nonnull Player player, @Nonnull List<String> list, @Nonnull ArenaCreateManager arenaCreateManager) throws ArenaNotExistException {
        final String arena = list.get(0);
        final boolean bool = Boolean.parseBoolean(list.get(1));
        GameCreateArena createArena = arenaCreateManager.getCreateArena(arena).castTo(GameCreateArena.class);
        createArena.setSendTitle(bool);
        createArena.setChanged(true);
        final String path = "arena.set-title";
        player.sendMessage(prefix + configManager.getPureMessage(path).replace("<arena>", arena).replace("<bool>", bool+""));
        return true;
    }
}
