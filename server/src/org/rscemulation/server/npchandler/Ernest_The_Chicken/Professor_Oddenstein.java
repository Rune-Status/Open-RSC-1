/**
* Generated By NPCScript :: A scripting engine created for RSCEmulation by Zilent
*/

package org.rscemulation.server.npchandler.Ernest_The_Chicken;
import org.rscemulation.server.event.SingleEvent;
import org.rscemulation.server.event.DelayedGenericMessage;
import org.rscemulation.server.logging.Logger;
import org.rscemulation.server.logging.model.eventLog;
import org.rscemulation.server.model.InvItem;
import org.rscemulation.server.model.Npc;
import org.rscemulation.server.model.ChatMessage;
import org.rscemulation.server.model.MenuHandler;
import org.rscemulation.server.model.Quest;
import org.rscemulation.server.model.World;
import org.rscemulation.server.event.DelayedQuestChat;
import org.rscemulation.server.model.Player;
import org.rscemulation.server.npchandler.NpcHandler;
import org.rscemulation.server.util.DataConversions;
public class Professor_Oddenstein implements NpcHandler {
	public void handleNpc(final Npc npc, final Player owner) throws Exception {
		npc.blockedBy(owner);
		owner.setBusy(true);
		Quest q = owner.getQuest(7);
		if(q != null) {
			if(q.finished()) {
				finishedQuest(npc, owner);
			} else {
				switch(q.getStage()) {
					case 0:
						questJustStarted(npc, owner);
						break;
					case 1:
						lookingForParts(npc, owner);
					case 2:
				}
			}
		} else {
			questNotStarted(npc, owner);
		}
	}
	
	private final void lookingForParts(final Npc npc, final Player owner) {
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Have you found anything yet?"}, true) {
			public void finished() {
				//208 - oil can
				//213 - rubber tube
				//175 - Pressure gauge
				if(owner.getInventory().contains(208)) {
					if(owner.getInventory().contains(213)) {
						if(owner.getInventory().contains(175)) {
							World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"I have everything"}) {
								public void finished() {
									World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Give em here then"}) {
										public void finished() {
											World.getDelayedEventHandler().add(new DelayedGenericMessage(owner, new String[] {"You give a rubber tube, a pressure gauge, and a can of oil to the Professor", "Oddenstein starts up the machine", "The machine hums and shakes", "Suddenly a ray shoots out of the machine at the chicken"}, 2200) {
												public void finished() {
													owner.getInventory().remove(new InvItem(208, 1));
													owner.getInventory().remove(new InvItem(213, 1));
													owner.getInventory().remove(new InvItem(175, 1));
													owner.sendInventory();
													final Npc chicken = World.getNpc(91, 208, 215, 2438, 2442);
													if(chicken != null) {
														chicken.remove();
														final Npc ernest = new Npc(92, chicken.getX(), chicken.getY(), 208, 215, 2438, 2442);
														World.registerEntity(ernest);
														World.getDelayedEventHandler().add(new SingleEvent(null, 30000) {
															public void action() {
																World.unregisterEntity(ernest);
															}
														});
														World.getDelayedEventHandler().add(new DelayedQuestChat(ernest, owner, new String[] {"Thank you sir", "It was dreadfully irritating being a chicken", "How can I ever thank you?"}) {
															public void finished() {
																World.getDelayedEventHandler().add(new DelayedQuestChat(owner, ernest, new String[] {"Well a cash reward is always nice"}) {
																	public void finished() {
																		World.getDelayedEventHandler().add(new DelayedQuestChat(ernest, owner, new String[] {"Of course, of course"}) {
																			public void finished() {
																				World.getDelayedEventHandler().add(new DelayedGenericMessage(owner, new String[] {"Ernest hands you 300 coins"}, 1500) {
																					public void finished() {
																						owner.getInventory().add(new InvItem(10, 300));
																						owner.sendInventory();
																						owner.sendMessage("Well done. You have completed the Ernest the Chicken quest");
																						owner.sendMessage("@gre@You have gained 4 quest points!");
																						owner.finishQuest(7);
																						owner.setBusy(false);
																						npc.unblock();
																						Logger.log(new eventLog(owner.getUsernameHash(), owner.getAccount(), owner.getIP(), DataConversions.getTimeStamp(), "<strong>" + owner.getUsername() + "</strong>" + " has completed the <span class=\"recent_quest\">Ernest The Chicken</span> quest!"));
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
											});
										}
									});
								}
							});
						} else {
							World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"I have found some of the things you need:", "I have a can of oil", "I have a rubber tube"}) {
								public void finished() {
									World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Well that's a start", "You still need to find", "A Pressure Gauge"}) {
										public void finished() {
											World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Ok I'll try and find them"}) {
												public void finished() {
													owner.setBusy(false);
													npc.unblock();													
												}
											});

										}
									});
								}
							});
						}
					} else if(owner.getInventory().contains(175)) {
						World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"I have found some of the things you need:", "I have a can of oil", "I have a pressure gauge"}) {
							public void finished() {
								World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Well that's a start", "You still need to find", "A Rubber Tube"}) {
									public void finished() {
										World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Ok I'll try and find them"}) {
											public void finished() {
												owner.setBusy(false);
												npc.unblock();													
											}
										});

									}
								});
							}
						});
					} else {
						World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"I have found some of the things you need:", "I have a can of oil"}) {
							public void finished() {
								World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Well that's a start", "You still need to find", "A Pressure Gauge", "A Rubber Tube"}) {
									public void finished() {
										World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Ok I'll try and find them"}) {
											public void finished() {
												owner.setBusy(false);
												npc.unblock();													
											}
										});

									}
								});
							}
						});
					}
				} else if(owner.getInventory().contains(213)) {
					if(owner.getInventory().contains(175)) {
						World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"I have found some of the things you need:", "I have a pressure gauge", "I have a rubber tube"}) {
							public void finished() {
								World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Well that's a start", "You still need to find", "A Can of Oil"}) {
									public void finished() {
										World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Ok I'll try and find them"}) {
											public void finished() {
												owner.setBusy(false);
												npc.unblock();													
											}
										});

									}
								});
							}
						});
					} else {
						World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"I have found some of the things you need:", "I have a rubber tube"}) {
							public void finished() {
								World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Well that's a start", "You still need to find", "A Can of Oil", "A Pressure Gauge"}) {
									public void finished() {
										World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Ok I'll try and find them"}) {
											public void finished() {
												owner.setBusy(false);
												npc.unblock();													
											}
										});

									}
								});
							}
						});
					}
				} else if (owner.getInventory().contains(175)) {
					World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"I have found some of the things you need:", "I have a pressure gauge"}) {
						public void finished() {
							World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Well that's a start", "You still need to find", "A Rubber Tube", "A Can of Oil"}) {
								public void finished() {
									World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Ok I'll try and find them"}) {
										public void finished() {
											owner.setBusy(false);
											npc.unblock();													
										}
									});

								}
							});
						}
					});
				} else {
					World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"I'm afraid I don't have any yet!"}) {
						public void finished() {
							World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"I need a rubber tube, a pressure gauge, and a can of oil", "Then your friend can stop being a chicken"}) {
								public void finished() {
									owner.setBusy(false);
									npc.unblock();
								}
							});
						}
					});
				}
			}
		});
	}
	
	private final void questJustStarted(final Npc npc, final Player owner) {
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Be careful in here", "Lots of dangerous equipment in here"}, true) {
			public void finished() {
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
					public void action() {
						final String[] options0 = {"I'm looking for a guy called Ernest", "What does this machine do?", "Is this your house?"};
						owner.setBusy(false);
						owner.sendMenu(options0);
						owner.setMenuHandler(new MenuHandler(options0) {
							public void handleReply(final int option, final String reply) {
								owner.setBusy(true);
								for(Player informee : owner.getViewArea().getPlayersInView()) {
									informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
								}
								switch(option) {
									case 0:
										World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Ah Ernest, top notch bloke", "He's helping me with my experiements"}) {
											public void finished() {
												World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"So you know where he is then?"}) {
													public void finished() {
														World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"He's that chicken over there"}) {
															public void finished() {
																World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Ernest is a chicken?", "Are you sure?"}) {
																	public void finished() {
																		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Oh he isn't normally a chicken", "Or at least he wasn't", "Until he helped me test my pouletmorph machine", "It was originally going to be called a transmutation machine", "But after testing Pouletmorph seems more appropriate"}) {
																			public void finished() {
																				owner.setBusy(false);
																				owner.sendMenu(new String[] {"I'm glad Veronica didn't actually get engaged to a chicken", "Change him back this instant"});
																				owner.setMenuHandler(new MenuHandler(new String[] {"I'm glad Veronica didn't actually get engaged to a chicken", "Change him back this instant"}) {
																					public void handleReply(final int option, final String reply) {
																						owner.setBusy(true);
																						for(Player informee : owner.getViewArea().getPlayersInView()) {
																							informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
																						}
																						switch(option) {
																							case 0:
																								engage(npc, owner);
																								break;
																							case 1:
																								changeHimBack(npc, owner);
																								break;
																						}
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
										break;
									case 1:
										World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Nothing at the moment", "As it's broken", "It's meant to be a transmutation machine", "It has also spent time as a time travel machine", "And a dramatic lightning generator", "And a thing for generating monsters"}) {
											public void finished() {
												owner.setBusy(false);
												npc.unblock();
											}
										});
										break;
									case 2:
										World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"No, I'm just one of the tenants", "It belongs to the count", "Who lives in the basement"}) {
											public void finished() {
												owner.setBusy(false);
												npc.unblock();
											}
										});
										break;
								}
							}
						});
					}
				});
			}
		});
	}

	private final void changeHimBack(final Npc npc, final Player owner) {
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Um it's not so easy", "My machine is broken", "And the house gremlins", "Have run off with some vital bits"}) {
			public void finished() {
				World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Well, I can look out for them"}) {
					public void finished() {
						World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"That would be a help", "They'll be somewhere in the manor house or its grounds", "The gremlins never go further than the entrance gate", "I'm missing the pressure gauge and a rubber tube", "They've also taken my oil can", "Which I'm going to need to get this thing started again"}) {
							public void finished() {
								owner.incQuestCompletionStage(7);
								owner.setBusy(false);
								npc.unblock();
							}
						});
					}
				});
			}
		});
	}
	
	private final void engage(final Npc npc, final Player owner) {
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Who's Veronica?"}) {
			public void finished() {	
				World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Ernest's fiancee", "She probably doesn't want to marry a chicken"}) {
					public void finished() {
						World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Ooh I dunno", "She could have free eggs for breakfast"}) {
							public void finished() {
								World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"I think you'd better change him back"}) {
									public void finished() {
										changeHimBack(npc, owner);
									}
								});
							}
						});
					}
				});
			}
		});
	}
	
	private final void questNotStarted(final Npc npc, final Player owner) {
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Be careful in here", "Lots of dangerous equipment in here"}, true) {
			public void finished() {
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
					public void action() {
						final String[] options0 = {"What does this machine do?", "Is this your house?"};
						owner.setBusy(false);
						owner.sendMenu(options0);
						owner.setMenuHandler(new MenuHandler(options0) {
							public void handleReply(final int option, final String reply) {
								owner.setBusy(true);
								for(Player informee : owner.getViewArea().getPlayersInView()) {
									informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
								}
								switch(option) {
									case 0:
										World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Nothing at the moment", "As it's broken", "It's meant to be a transmutation machine", "It has also spent time as a time travel machine", "And a dramatic lightning generator", "And a thing for generating monsters"}) {
											public void finished() {
												owner.setBusy(false);
												npc.unblock();
											}
										});
										break;
									case 1:
										World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"No, I'm just one of the tenants", "It belongs to the count", "Who lives in the basement"}) {
											public void finished() {
												owner.setBusy(false);
												npc.unblock();
											}
										});
										break;
								}
							}
						});
					}
				});
			}
		});
	}
	
	private final void finishedQuest(final Npc npc, final Player owner) {
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Thank you for your help."}) {
			public void finished() {	
				owner.setBusy(false);
				npc.unblock();
			}
		});
	}
}