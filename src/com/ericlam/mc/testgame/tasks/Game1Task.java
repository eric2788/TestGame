package com.ericlam.mc.testgame.tasks;

import com.ericlam.mc.minigames.core.arena.Arena;
import com.ericlam.mc.minigames.core.character.GamePlayer;
import com.ericlam.mc.minigames.core.game.GameState;
import com.ericlam.mc.minigames.core.main.MinigamesCore;
import com.ericlam.mc.minigames.core.manager.PlayerManager;
import com.ericlam.mc.testgame.GameCreateArena;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.List;

public class Game1Task extends TestTask {

    private PlayerManager playerManager;

    @Override
    public void initTimer(PlayerManager playerManager) {
        MinigamesCore.getApi().getGameManager().setState(GameState.PRESTART);
        MinigamesCore.getApi().getGameManager().setState(GameState.IN_GAME);
        playerManager.getWaitingPlayer().forEach(playerManager::setGamePlayer);
        this.playerManager = playerManager;
        Bukkit.broadcastMessage("Game Section 1 Task started");
        Arena arena = MinigamesCore.getApi().getArenaManager().getFinalArena();
        GameCreateArena gameArena = arena.castTo(GameCreateArena.class);
        if (gameArena.isSendTitle()){
            playerManager.getTotalPlayers().forEach(p->p.getPlayer().sendTitle("","§aSection 1 Started",20, 40, 20));
        }
        List<Location> one = arena.getLocationsMap().get("tp-one");
        List<GamePlayer> players = playerManager.getTotalPlayers();
        for (int i = 0; i < players.size(); i++) {
            if (i == one.size()) break;
            players.get(i).getPlayer().teleportAsync(one.get(i));
        }
    }

    @Override
    public void onCancel() {
    }

    @Override
    public void onFinish() {
        Bukkit.broadcastMessage("Game Section 1 Taskcountdown finished");
    }

    @Override
    public long run(long l) {
        playerManager.getGamePlayer().forEach(p->{
            p.getPlayer().setLevel((int)l);
            if (l <= 10){
                p.getPlayer().sendMessage("Game Section 1 Task end in "+l+" secs");
            }
        });
        return l;
    }

    @Override
    public long getTotalTime() {
        return 25;
    }

    @Override
    public boolean shouldCancel() {
        return false;
    }
}
