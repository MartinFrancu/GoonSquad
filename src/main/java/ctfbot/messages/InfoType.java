/*
 * Copyright (C) 2016 AMIS research group, Faculty of Mathematics and Physics, Charles University in Prague, Czech Republic
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ctfbot.messages;

/**
 *
 * @author student
 */
public enum InfoType {
	FRIEND,
	ENEMY,
	ITEM,
	OUR_FLAG,
	ENEMY_FLAG,
        LETS_KILL_THIS_ONE,
        HEAD_DIED,
        TAIL_DIED,
        BECAME_HEAD,
        BECAME_TAIL,
        DEFENDER_ADDED,
        DEFENDER_REMOVED,
        WHO_IS_WHO
}
