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

import cz.cuni.amis.pogamut.base3d.worldview.object.Location;
import cz.cuni.amis.pogamut.unreal.communication.messages.UnrealId;
import cz.cuni.amis.utils.token.IToken;
import cz.cuni.amis.utils.token.Tokens;

/**
 *
 * @author student
 */
public class CTFMessage extends Message{
    public static final IToken MESSAGE_TYPE = Tokens.get("Location");
    private final Location location;
    private final UnrealId senderId;
    private final InfoType infoType;
    private final UnrealId targetId;
            
    public Location getLocation() {
		return location;
	}

    public UnrealId getSenderId() {
		return senderId;
	}

    public InfoType getInfoType() {
		return infoType;
	}
    public UnrealId getTargetId() {
		return targetId;
	}
    
    
    
    public CTFMessage(Location location, UnrealId unrealId, InfoType infoType, UnrealId targetIdin) {
		super(String.format("Location - type: %s, unrealId %s, location: %s", infoType, unrealId, location), 
				MESSAGE_TYPE);
		this.location = location;
		this.senderId = unrealId;
		this.infoType = infoType;
                this.targetId = targetIdin;
	}
}
