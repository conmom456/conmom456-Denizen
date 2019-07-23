package com.denizenscript.denizen.events.entity;

import com.denizenscript.denizen.objects.EntityTag;
import com.denizenscript.denizen.events.BukkitScriptEvent;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.scripts.containers.ScriptContainer;
import com.denizenscript.denizencore.utilities.CoreUtilities;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleCreateEvent;

public class VehicleCreatedScriptEvent extends BukkitScriptEvent implements Listener {

    // <--[event]
    // @Events
    // vehicle created
    // <vehicle> created
    //
    // @Regex ^on [^\s]+ created$
    // @Switch in <area>
    //
    // @Cancellable true
    //
    // @Triggers when a vehicle is created.
    //
    // @Context
    // <context.vehicle> returns the EntityTag of the vehicle.
    //
    // -->

    public VehicleCreatedScriptEvent() {
        instance = this;
    }

    public static VehicleCreatedScriptEvent instance;
    public EntityTag vehicle;
    public VehicleCreateEvent event;

    @Override
    public boolean couldMatch(ScriptContainer scriptContainer, String s) {
        String lower = CoreUtilities.toLowerCase(s);
        return CoreUtilities.xthArgEquals(1, lower, "created");
    }

    @Override
    public boolean matches(ScriptPath path) {

        if (!tryEntity(vehicle, path.eventArgLowerAt(0))) {
            return false;
        }

        if (!runInCheck(path, vehicle.getLocation())) {
            return false;
        }

        return true;
    }

    // TODO: Can the vehicle be an NPC?

    @Override
    public String getName() {
        return "VehicleCreated";
    }

    @Override
    public ObjectTag getContext(String name) {
        if (name.equals("vehicle")) {
            return vehicle;
        }
        return super.getContext(name);
    }

    @EventHandler
    public void onVehicleCreated(VehicleCreateEvent event) {
        Entity entity = event.getVehicle();
        EntityTag.rememberEntity(entity);
        vehicle = new EntityTag(entity);
        this.event = event;
        fire(event);
        EntityTag.forgetEntity(entity);
    }
}
