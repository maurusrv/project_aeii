
package com.toyknight.aeii.core;

import com.toyknight.aeii.core.unit.Unit;

/**
 *
 * @author toyknight
 */
public interface GameListener {
	
	public void onUnitStandby(Unit unit);
	
	public void onUnitAttack(Unit attacker, Unit defencer, int damage);
	
	public void onUnitMove(Unit unit, int start_x, int start_y, int dest_x, int dest_y);
	
}
