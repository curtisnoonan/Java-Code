/** Curtis Noonan
* CS 251
* 5/5/14
*/

Read Me: Space Invaders

    Gameplay
* Control the ship using the left and right arrow keys
* Use the spacebar to fire a laser
* Hit the aliens with lasers to gain points, 10 points per alien
* Kill all the aliens to advance to the next level, total score does not reset, but you get lives back
* Game is over when ship has no lives or the aliens reach the ship

    Program Internals
* Most logic contained in InvadersGameFrame.java
* Aliens move from the moveAliens() method, start moving right and when the edge is reached move down and move left
* Collisions are handled using the intersect method in GameObject.java and are checked for in the hitsAlien() and moveMissile() methods
* Game over is detected using a boolean gameDone and is set to true if the ship has 0 lives left or if the aliens reach the bottom, then
a new JPanel is set visible displaying game over while the gameboard JPanel is hidden

    Known Bugs
* Sometimes the aliens move down and move to the right or left simultaneously
