package net.runelite.client.plugins.paistisuite.scripts.testscript;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.GameState;
import net.runelite.client.plugins.paistisuite.api.*;
import net.runelite.client.plugins.paistisuite.api.WebWalker.api_lib.DaxWalker;
import net.runelite.client.plugins.paistisuite.api.WebWalker.api_lib.WebWalkerServerApi;
import net.runelite.client.plugins.paistisuite.api.WebWalker.api_lib.models.*;
import net.runelite.client.plugins.paistisuite.api.WebWalker.walker_engine.WalkerEngine;
import net.runelite.client.plugins.paistisuite.api.WebWalker.wrappers.RSTile;
import net.runelite.client.plugins.paistisuite.PScript;

import java.util.ArrayList;

@Slf4j
public class TestScript extends PScript {
    ArrayList<RSTile> path = null;
    @Override
    protected void loop() {
        PUtils.sleepNormal(3000, 5000);
        if (PUtils.getClient().getGameState() != GameState.LOGGED_IN) return;
        PUtils.sendGameMessage("Looping");

        if (path == null){
            path = WebWalkerServerApi.getInstance().getPath(new Point3D(PPlayer.location()), new Point3D(RunescapeBank.FALADOR_WEST.getPosition()), null).toRSTilePath();
        } else {
            WalkerEngine.getInstance().walkPath(path);
        }
    }


    @Override
    protected void onStart() {
        DaxWalker.setCredentials(new DaxCredentialsProvider() {
            @Override
            public DaxCredentials getDaxCredentials() {
                return new DaxCredentials("sub_DPjXXzL5DeSiPf", "PUBLIC-KEY");
            }
        });
        log.info("TestScript started");
        path = null;
    }

    @Override
    protected void onStop() {
        log.info("TestScript stopped");
    }
}
