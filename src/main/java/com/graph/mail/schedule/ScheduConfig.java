package com.graph.mail.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.graph.mail.service.impl.ClientCredentialGrant;


@Configuration
@EnableScheduling
public class ScheduConfig {
	@Autowired
	private ClientCredentialGrant clientCredentialGrant;

	@Scheduled(cron = "10/60 * * * * ?")
	public void getTokenSchedu() throws Exception {
		clientCredentialGrant.getAccessTokenByClientCredentialGrant();
	}
}
