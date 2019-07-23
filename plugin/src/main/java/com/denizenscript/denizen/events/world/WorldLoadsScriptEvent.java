package com.denizenscript.denizen.events.world;

import com.denizenscript.denizen.objects.WorldTag;
import com.denizenscript.denizen.events.BukkitScriptEvent;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.scripts.containers.ScriptContainer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

public class WorldLoadsScriptEvent extends BukkitScriptEvent implements Listener {

    // <--[event]
    // @Events
    // world loads
    // <world> loads
    //
    // @Regex ^on [^\s]+ loads$
    //
    // @Group World
    //
    // @Triggers when a world is loaded.
    //
    // @Context
    // <context.world> returns the WorldTag that was loaded.
    //
    // -->

    public WorldLoadsScriptEvent() {
        instance = this;
    }

    public static WorldLoadsScriptEvent instance;
    public WorldTag world;
    public WorldLoadEvent event;

    @Override
    public boolean couldMatch(ScriptPath path) {
        return path.eventArgLowerAt(1).equals("loads");
    }

    @Override
    public boolean matches(ScriptPath path) {
        if (!runGenericCheck(path.eventArgAt(0), world.getName())) {
            return false;
        }
        return true;
    }

    @Override
    public String getName() {
        return "WorldLoads";
    }

    @Override
    public ObjectTag getContext(String name) {
        if (name.equals("world")) {
            return world;
        }
        return super.getContext(name);
    }

    @EventHandler
    public void onWorldLoads(WorldLoadEvent event) {
        world = new WorldTag(event.getWorld());
        this.event = event;
        fire(event);
    }
}
