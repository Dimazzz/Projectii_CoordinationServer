package org.projii.serverside.cs.interaction.client.handlers;

import org.jai.BSON.BSONSerializable;
import org.jboss.netty.channel.Channel;
import org.projii.commons.net.Request;
import org.projii.commons.spaceship.Spaceship;
import org.projii.commons.spaceship.SpaceshipModel;
import org.projii.commons.spaceship.equipment.EnergyGeneratorModel;
import org.projii.commons.spaceship.equipment.EnergyShieldModel;
import org.projii.commons.spaceship.equipment.SpaceshipEngine;
import org.projii.commons.spaceship.weapon.Weapon;
import org.projii.commons.spaceship.weapon.WeaponModel;
import org.projii.serverside.cs.DataStorage;
import org.projii.serverside.cs.SessionInfo;
import org.projii.serverside.cs.SessionsManager;
import org.projii.serverside.cs.interaction.client.RequestHandler;
import org.projii.serverside.cs.interaction.client.responses.ShipsInfoResponse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GetMysSpaceshipsRequestHandler implements RequestHandler {
    private final DataStorage dataStorage;
    private final SessionsManager sessionsManager;

    public GetMysSpaceshipsRequestHandler(DataStorage dataStorage, SessionsManager sessionsManager) {
        this.dataStorage = dataStorage;
        this.sessionsManager = sessionsManager;
    }

    @Override
    public void handle(Request request, Channel channel) {
        SessionInfo sessionInfo = sessionsManager.getSessionByChannelId(channel.getId());
        List<Spaceship> userSpaceships = dataStorage.getUserSpaceships(sessionInfo.getUserId());
        List<SpaceshipInfo> spaceshipsInfo = new ArrayList<>(userSpaceships.size());
        HashSet<SpaceshipModel> usedSpaceshipModels = new HashSet<>();
        HashSet<WeaponModel> usedWeaponModels = new HashSet<>();
        HashSet<SpaceshipEngine> usedEngineModels = new HashSet<>();
        HashSet<EnergyGeneratorModel> usedGeneratorModels = new HashSet<>();
        HashSet<EnergyShieldModel> usedShieldModels = new HashSet<>();

        for (Spaceship spaceship : userSpaceships) {
            SpaceshipModel spaceshipModel = spaceship.getModel();
            SpaceshipEngine spaceshipEngine = spaceship.getEngine();
            EnergyGeneratorModel energyGeneratorModel = spaceship.getGenerator().getModel();
            EnergyShieldModel energyShieldModel = spaceship.getEnergyShield().getModel();

            usedSpaceshipModels.add(spaceship.getModel());
            usedEngineModels.add(spaceshipEngine);
            usedGeneratorModels.add(energyGeneratorModel);
            usedShieldModels.add(energyShieldModel);
            int i = 0;
            int[] weaponsIds = new int[spaceship.getWeapons().length];
            for (Weapon weapon : spaceship.getWeapons()) {
                weaponsIds[i] = weapon.getModel().getId();
                usedWeaponModels.add(weapon.getModel());
            }

            spaceshipsInfo.add(
                    new SpaceshipInfo(
                            spaceship.getId(),
                            spaceshipModel.id,
                            energyShieldModel.id,
                            spaceshipEngine.getId(),
                            energyGeneratorModel.id,
                            weaponsIds));
        }

        ShipsInfoResponse response = new ShipsInfoResponse(
                spaceshipsInfo,
                usedSpaceshipModels.toArray(new SpaceshipModel[usedSpaceshipModels.size()]),
                usedWeaponModels.toArray(new WeaponModel[usedWeaponModels.size()]),
                usedEngineModels.toArray(new SpaceshipEngine[usedEngineModels.size()]),
                usedGeneratorModels.toArray(new EnergyGeneratorModel[usedGeneratorModels.size()]),
                usedShieldModels.toArray(new EnergyShieldModel[usedShieldModels.size()]));
        channel.write(response);
    }

    public class SpaceshipInfo {
        @BSONSerializable
        private final int id;
        @BSONSerializable
        private final int modelId;
        @BSONSerializable
        private final int shieldId;
        @BSONSerializable
        private final int engineId;
        @BSONSerializable
        private final int generatorId;
        @BSONSerializable
        private final int[] weapons;

        SpaceshipInfo(int id, int modelId, int shieldId, int engineId, int generatorId, int[] weaponsIds) {
            this.id = id;
            this.modelId = modelId;
            this.shieldId = shieldId;
            this.engineId = engineId;
            this.generatorId = generatorId;
            this.weapons = weaponsIds;
        }
    }
}
