import org.terasology.entitySystem.entity.EntityManager;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterMode;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.entitySystem.systems.UpdateSubscriberSystem;
import org.terasology.logic.characters.CharacterMovementComponent;
import org.terasology.logic.location.LocationComponent;
import org.terasology.logic.nameTags.NameTagComponent;
import org.terasology.registry.In;
import org.terasology.registry.Share;

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

/**
 * Adds useful information to name tags
 */
@Share(NameTagHelperSystem.class)
@RegisterSystem(RegisterMode.ALWAYS)
public class NameTagHelperSystem extends BaseComponentSystem implements UpdateSubscriberSystem {
    @In private EntityManager entityManager;
    @Override
    public void update(float delta) {
        for (EntityRef entity : entityManager.getEntitiesWith(NameTagComponent.class)) {
            NameTagComponent nameTagComponent = entity.getComponent(NameTagComponent.class);
            LocationComponent locationComponent = entity.getComponent(LocationComponent.class);
            CharacterMovementComponent characterMovementComponent = entity.getComponent(CharacterMovementComponent.class);

            String text = String.format("h: %f r: %f y: %f",
                    characterMovementComponent.height,
                    characterMovementComponent.radius,
                    locationComponent.getWorldPosition().y
            );

            nameTagComponent.text = text;
            entity.saveComponent(nameTagComponent);
        }
    }
}
