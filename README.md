# CharacterCollisionTestbed

Simple demonstration of an odd behavior with character resting y-coordinates on a flat surface.

To reproduce, simply run the tests. This runs the test against a real headless server, which you can connect to 
through "join game" in a 2nd client.

Even without a client connected, it will generate some space on the `core:flat` world generator. Then it spawns 
roughly 400 entities of varying heights and radii. Finally it sends `CharacterMoveInputEvent`s with velocity set to 
zero. This causes physics processing to happen. The entities should fall to the bottom of the air block `y=41`. 
However, instead they come to rest somewhere inside of the sand block according to getWorldPosition. The 
effect is 100% reproducible, and is logged in the test output.
 
 