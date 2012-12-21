/**
 * Arrangement of the game.
 *
 * On start of game new game session becomes created. Empty board and default tileset becomes initialized for
 * session. Board is constructed with empty slots according to selected layout. Slots become filled with tiles
 * that are contained in tileset. Filling made in random order.
 *
 * Move.
 *
 * When player makes successful move affected slots became removed from  the board.
 *
 * Shuffling
 *
 * When player calls for shuffle:
 * all tiles left on the board are stored in list.
 * all slots are marked as empty.
 * previously saved list of tiles is shuffled.
 * slots are filled with tiles from list.
 *
 * Saving game
 *
 * When user saves game state for playing later game session IO saves all slots from board together with it's tiles
 * to external storage.
 *
 * Loading previously saved game
 *
 * game session IO loads file and creates list of slots with tiles. Layout is recreated from slots loaded. Layout is validated.
 * After successful validation board is populated with slots.
 */
package net.anatolich.mahjong.game;
