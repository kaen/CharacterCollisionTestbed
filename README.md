# CharacterCollisionTestbed

Simple demonstration of an odd behavior with character collision varying with height and radius of a character.

To reproduce, simply run the tests. This runs the test against a real headless server, which you can connect to 
through "join game" in a 2nd client.

Even without a client connected, it will generate some space on the `core:flat` world generator. Then it spawns 
roughly 400 entities of varying heights and radii. Finally it sends `CharacterMoveInputEvent`s with velocity set to 
zero. This causes physics processing to happen, which is where the issue begins. Depending on the height and radius 
of each entity, it may either rest at `y=41` as expected, or `y=40.3`. The effect is 100% reproducible, and is logged
 in the test output. An example is reproduced below:
 
 