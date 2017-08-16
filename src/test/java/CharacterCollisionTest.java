import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.entitySystem.entity.EntityManager;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.logic.characters.CharacterMoveInputEvent;
import org.terasology.logic.characters.CharacterMovementComponent;
import org.terasology.logic.characters.CharacterTeleportEvent;
import org.terasology.logic.location.LocationComponent;
import org.terasology.math.geom.Vector3f;
import org.terasology.math.geom.Vector3i;

/*
 * Copyright 2017 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class CharacterCollisionTest extends CharacterCollisionTestEnvironment {
    private Logger logger = LoggerFactory.getLogger(CharacterCollisionTest.class);

    @Test
    public void testCollision() {
        forceAndWaitForGeneration(Vector3i.zero());

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                spawnTestCharacter(i, j);
            }
        }

        runWhile(()-> {
            for (EntityRef entity : getHostContext().get(EntityManager.class).getEntitiesWith(CharacterMovementComponent.class)) {
                logger.debug("h: {} r: {} y: {}",
                        entity.getComponent(CharacterMovementComponent.class).height,
                        entity.getComponent(CharacterMovementComponent.class).radius,
                        entity.getComponent(LocationComponent.class).getWorldPosition().y
                );

                entity.send(new CharacterMoveInputEvent(
                        0, 0, 0, Vector3f.zero(),
                        false, false, false, 0
                ));
            }
            return true;
        });
    }

    private void spawnTestCharacter(float i, float j) {
        EntityManager entityManager = getHostContext().get(EntityManager.class);
        EntityRef entity = entityManager.create("CharacterCollisionTestbed:testcharacter");
        CharacterMovementComponent characterMovementComponent = entity.getComponent(CharacterMovementComponent.class);
        characterMovementComponent.height = i / 10.0f;
        characterMovementComponent.radius = j / 10.0f;
        entity.saveComponent(characterMovementComponent);

        Vector3i pos = new Vector3i(i*2, 41, j*2);
        entity.send(new CharacterTeleportEvent(pos.toVector3f()));

        runUntil(()-> {
            return entity.getComponent(LocationComponent.class).getWorldPosition().distance(pos.toVector3f()) < 0.1f;
        });
    }
}
