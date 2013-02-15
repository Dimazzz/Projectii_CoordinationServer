package org.projii.serverside.cs.interaction.client.responses;

import org.jai.BSON.BSONSerializable;
import org.projii.commons.net.CoordinationServerResponses;
import org.projii.commons.net.Response;
import org.projii.commons.spaceship.SpaceshipModel;
import org.projii.commons.spaceship.equipment.EnergyGeneratorModel;
import org.projii.commons.spaceship.equipment.EnergyShieldModel;
import org.projii.commons.spaceship.equipment.SpaceshipEngine;
import org.projii.commons.spaceship.weapon.WeaponModel;
import org.projii.serverside.cs.interaction.client.handlers.GetMysSpaceshipsRequestHandler;

import java.util.List;

public class ShipsInfoResponse implements Response {

    @BSONSerializable
    private final int type;
    @BSONSerializable
    private final List<GetMysSpaceshipsRequestHandler.SpaceshipInfo> spaceships;
    @BSONSerializable
    private final SpaceshipModel[] spaceshipModels;
    @BSONSerializable
    private final WeaponModel[] weapons;
    @BSONSerializable
    private final SpaceshipEngine[] engineModels;
    @BSONSerializable
    private final EnergyGeneratorModel[] generatorModels;
    @BSONSerializable
    private final EnergyShieldModel[] shieldModels;

    public ShipsInfoResponse(List<GetMysSpaceshipsRequestHandler.SpaceshipInfo> spaceshipsInfo,
                             SpaceshipModel[] usedSpaceshipModels,
                             WeaponModel[] weapons,
                             SpaceshipEngine[] usedEngineModels,
                             EnergyGeneratorModel[] usedGeneratorModels,
                             EnergyShieldModel[] usedShieldModels) {

        this.type = CoordinationServerResponses.SPACESHIPS;
        this.spaceships = spaceshipsInfo;
        this.spaceshipModels = usedSpaceshipModels;
        this.weapons = weapons;
        this.engineModels = usedEngineModels;
        this.generatorModels = usedGeneratorModels;
        this.shieldModels = usedShieldModels;
    }

    @Override
    public int getType() {
        return type;
    }
}
