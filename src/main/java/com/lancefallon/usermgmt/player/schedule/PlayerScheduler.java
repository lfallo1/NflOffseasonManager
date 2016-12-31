package com.lancefallon.usermgmt.player.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PlayerScheduler {
	
//	@Autowired
//	private PlayerService playerService;
//	
//	@Autowired
//	private EmailService<EmailConfigPlayers> emailService;
	
	/**
	 * run every day at 13:48:00
	 * @throws InterruptedException 
	 */
//	@Scheduled(cron = "0 48 13 * * *")
	@Scheduled(fixedDelay = 15000, initialDelay=7000)
	public void emailPlayerReport() throws InterruptedException{
//		EmailConfigPlayers config = new EmailConfigPlayers();
//		config.setTo("fallon.lance@gmail.com");
//		config.setSubject("Nightly player report");
//		config.setTemplate("velocity/nightlyPlayerSummary.vm");
//		config.setPlayers(playerService.findAll());
//		emailService.sendMail(config);
	}
	
	/**
	 * run every five seconds, with an initial 1 second delay
	 */
	@Scheduled(fixedDelay = 60000*60, initialDelay=1000)
	public void addDummyPlayers() {
//		for(int i = 0; i < 50; i++){
//			String title = "johndoe_" + new Date().getTime();
//			Player player = new Player(null, title, "action", new Date());
//			Integer newPlayerId = playerService.addPlayer(player);
//			System.out.println("New player added: playerId#" + newPlayerId);	
//		}
	}
	
}
