/**
* Generated By NPCScript :: A scripting engine created for RSCEmulation by Zilent
*/

//scripted by Mr. Zain

package org.rscemulation.server.npchandler.Tree_Gnome_Village;
import org.rscemulation.server.event.FightEvent;
import org.rscemulation.server.event.DelayedQuestChat;
import org.rscemulation.server.model.Npc;
import org.rscemulation.server.model.Player;
import org.rscemulation.server.model.Quest;
import org.rscemulation.server.model.World;
import org.rscemulation.server.npchandler.NpcHandler;



public class Warlord implements NpcHandler {

	public void handleNpc(final Npc npc, final Player owner) throws Exception {
		npc.blockedBy(owner);
		owner.setBusy(true);
		Quest q = owner.getQuest(32);
		if(q != null) {
			if(q.finished()) {
				finished(npc, owner);
			} else {
				switch(q.getStage()) {
					case 6:
						questStage6(npc, owner);
						break;
					default:
						noQuestStarted(npc, owner);
						break;
				}
			}
		} 
		else 
		{
			noQuestStarted(npc, owner);
		}
	}
	
	private void noQuestStarted(final Npc npc, final Player owner)
	{
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Get out of here puny weakling"}, true) 
		{
			public void finished()
			{
				owner.setBusy(false);
				npc.unblock();
			}
		});
	}

	private void questStage6(final Npc npc, final Player owner) 
	{
		if (owner.getInventory().countId(741) > 0) 
		{
			owner.sendMessage("You already have the orb");
			owner.setBusy(false);
			npc.unblock();
		}
		else
		{
			World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"You there, stop!"}, true) {
				public void finished() {
					World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Go back to your pesky little green friends"}) {
						public void finished() {
							World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"I've come for the orbs"}) {
								public void finished() {
									World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"You're out of your depth traveller", "These orbs are part of a much larger picture"}) {
										public void finished() {
											World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"They're stolen goods", "Now give them here"}) {
												public void finished() {
													World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Hee hee, you really think you stand a chance?", "I'll crush you!"}) {
														public void finished() {						
															owner.setBusy(false);
															npc.unblock();
															FightEvent fe = new FightEvent(owner, npc, true);
															npc.setFightEvent(fe);
															owner.setFightEvent(fe);
															World.getDelayedEventHandler().add(fe);
														}
													});
												}
											});
										}
									});
								}
							});
						}
					});
				}
			});	
		}
	}
	
	private void finished(final Npc npc, final Player owner) {
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Get out of here puny weakling"}, true) {
			public void finished() {
			owner.setBusy(false);
			npc.unblock();
			}
		});
	}
	
}