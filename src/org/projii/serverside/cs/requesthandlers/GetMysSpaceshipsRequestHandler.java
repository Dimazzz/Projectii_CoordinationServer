package org.projii.serverside.cs.requesthandlers;

import org.jai.BSON.BSONArray;
import org.jai.BSON.BSONDocument;
import org.projii.commons.spaceship.Spaceship;
import org.projii.commons.spaceship.weapon.Weapon;
import org.projii.serverside.cs.DataStorage;
import org.projii.serverside.cs.SessionInfo;

import java.util.LinkedList;
import java.util.List;

import static org.projii.commons.net.CoordinationServerResponses.SHIPS_FULL_INFO;

public class GetMysSpaceshipsRequestHandler implements RequestHandler {
    private DataStorage dataStorage;

    public GetMysSpaceshipsRequestHandler(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    @Override
    public BSONDocument handle(BSONDocument request, SessionInfo sessionInfo) {
        List<Spaceship> userSpaceships = dataStorage.getUserSpaceships(sessionInfo.userId);

        BSONDocument response = new BSONDocument();
        response.add("type", SHIPS_FULL_INFO);

        BSONArray spaceships = new BSONArray();
        BSONArray spaceshipModels = new BSONArray();
        BSONArray engines = new BSONArray();
        BSONArray generators = new BSONArray();
        BSONArray weapons = new BSONArray();
        BSONArray shields = new BSONArray();

        List<Integer> usedModels = new LinkedList<>();
        List<Integer> usedWeaponModels = new LinkedList<>();
        List<Integer> usedEngineModels = new LinkedList<>();
        List<Integer> usedGeneratorModels = new LinkedList<>();
        List<Integer> usedShieldModels = new LinkedList<>();

        for (Spaceship spaceship : userSpaceships) {
            if (!usedModels.contains(spaceship.getModel().id)) {
                spaceshipModels.add(
                        new BSONDocument().
                                add("id", spaceship.getModel().id).
                                add("name", spaceship.getModel().name).
                                add("health", spaceship.getModel().health).
                                add("width", spaceship.getModel().length).
                                add("length", spaceship.getModel().width).
                                add("armor", spaceship.getModel().armor).
                                add("weaponSlotCount", spaceship.getModel().weaponSlotCount)
                );
                usedModels.add(spaceship.getModel().id);
            }

            if (!usedEngineModels.contains(spaceship.getEngine().getId())) {
                engines.add(
                        new BSONDocument().
                                add("id", spaceship.getEngine().getId()).
                                add("maneuverability", spaceship.getEngine().getManeuverability()).
                                add("maxSpeed", spaceship.getEngine().getMaxSpeed()).
                                add("acceleration", spaceship.getEngine().getAcceleration()).
                                add("name", spaceship.getEngine().getName())
                );
                usedEngineModels.add(spaceship.getEngine().getId());
            }

            if (!usedGeneratorModels.contains(spaceship.getGenerator().getModel().id)) {
                generators.add(
                        new BSONDocument().
                                add("id", spaceship.getGenerator().getModel().id).
                                add("name", spaceship.getGenerator().getModel().name).
                                add("maxEnergyLevel", spaceship.getGenerator().getModel().maxEnergyLevel).
                                add("regenerationSpeed", spaceship.getGenerator().getModel().regenerationSpeed)
                );

                usedGeneratorModels.add(spaceship.getGenerator().getModel().id);
            }

            if (!usedShieldModels.contains(spaceship.getEnergyShield().getModel().id)) {
                shields.add(
                        new BSONDocument().
                                add("id", spaceship.getEnergyShield().getModel().id).
                                add("maxEnergyLevel", spaceship.getEnergyShield().getModel().maxEnergyLevel).
                                add("name", spaceship.getEnergyShield().getModel().name).
                                add("regenerationDelay", spaceship.getEnergyShield().getModel().regenerationDelay).
                                add("regenerationSpeed", spaceship.getEnergyShield().getModel().regenerationSpeed)
                );
                usedShieldModels.add(spaceship.getEnergyShield().getModel().id);
            }


            BSONArray spaceshipWeapons = new BSONArray();
            for (Weapon w : spaceship.getWeapons()) {
                spaceshipWeapons.add(w.getModel().getId());

                if (!usedWeaponModels.contains(w.getModel().getId())) {
                    usedWeaponModels.add(w.getModel().getId());
                    weapons.add(
                            new BSONDocument().
                                    add("id", w.getModel().getId()).
                                    add("name", w.getModel().getName()).
                                    add("rate", w.getModel().getRate()).
                                    add("type", w.getModel().getType()).
                                    add("bulletSpeed", w.getModel().getBulletSpeed()).
                                    add("damage", w.getModel().getDamage()).
                                    add("energyConsumption", w.getModel().getEnergyConsumption()).
                                    add("distance", w.getModel().getDistance()).
                                    add("range", w.getModel().getRange()).
                                    add("cooldown", w.getModel().getCooldown())
                    );
                }

            }

            spaceships.add(
                    new BSONDocument().
                            add("id", spaceship.getId()).
                            add("modelId", spaceship.getModel().id).
                            add("shieldId", spaceship.getEnergyShield().getModel().id).
                            add("engineId", spaceship.getEngine().getId()).
                            add("generatorId", spaceship.getGenerator().getModel().id).
                            add("weapons", spaceshipWeapons));
        }

        response.add("spaceships", spaceships);
        response.add("weapons", weapons);
        response.add("spaceshipModels", spaceshipModels);
        response.add("engineModels", engines);
        response.add("generatorModels", generators);
        response.add("shieldModels", shields);

        return response;
    }
}
